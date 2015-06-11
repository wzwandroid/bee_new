package com.bee.mybatis.v3_2;

import com.bee.mybatis.Pager;
import com.bee.mybatis.util.ReflectHelper;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import javax.xml.bind.PropertyException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;


@SuppressWarnings("unchecked")
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class PaginationInterceptorEnhance implements Interceptor {
	private static String dialect = "";
	private static String pageSqlId = "";

	public Object intercept(Invocation invocation) throws Throwable {
		if(invocation.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
			if(mappedStatement.getId().matches(pageSqlId)) {// 拦截需要分页的SQL
				BoundSql boundSql = delegate.getBoundSql();
				Object parameterObject = boundSql.getParameterObject();// 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
				if(null == parameterObject) {
					throw new NullPointerException("parameterObject尚未实例化！");
				} else {
					Connection connection = (Connection) invocation.getArgs()[0];
					int count = -1;
					String sql = boundSql.getSql();
					Pager<?> pager;
					if(parameterObject instanceof Pager) {
						pager = (Pager) parameterObject;
						//pagination.setTotalCount(count);
					} else if(parameterObject instanceof Map) {
						Map<String, Object> map = (Map<String, Object>) parameterObject;
						pager = (Pager) map.get("pager");
						if(null == pager) {
							pager = new Pager();
						}
						//pagination.setTotalCount(count);
					} else {
						Field pageField = ReflectHelper.getFieldByFieldName(parameterObject, "pager");
						if(null != pageField) {
							pager = (Pager) ReflectHelper.getValueByFieldName(parameterObject, "pager");
							
							if(null == pager) {
								pager = new Pager();
							}
							ReflectHelper.setValueByFieldName(parameterObject, "pager", pager);
						} else {
							throw new NoSuchFieldException(parameterObject.getClass().getName());
						}
					}
					
					String pageSql = generatePageSql(sql,pager);
					ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql);
					if(pager.isAutoCount()) {
						String countSql = "select count(*) from (" + sql + ")";
						PreparedStatement countStmt = connection.prepareStatement(countSql);
						
						BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), parameterObject);
						setParameters(countStmt,mappedStatement,countBS,parameterObject);
//                      BoundSql countBS = this.copyFromBoundSql(mappedStatement, boundSql, countSql);
                        //bug fix 参数?没有替换，导致分页查询的记录数和分页结果不一致
                        DefaultParameterHandler defaultParameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, countBS);
                        defaultParameterHandler.setParameters(countStmt);
                        ResultSet rs = countStmt.executeQuery();
						if(rs.next()) {
							count = rs.getInt(1);
						}
						rs.close();
						countStmt.close();
					}
					pager.setTotalCount(count);
				}
			}
		}
		return invocation.proceed();
	}


    public Object plugin(Object obj) {
		return Plugin.wrap(obj, this);
	}

	public void setProperties(Properties prop) {
		dialect = prop.getProperty("dialect");
		if(null == dialect || "".equals(dialect)) {
			try {
				throw new PropertyException("database dialect is not found ! ");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
		pageSqlId = prop.getProperty("pageSqlId");
		if(null == pageSqlId || "".equals(pageSqlId)) {
			try {
				throw new PropertyException("query page sqlId is not found ! ");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
	}

    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            String prop;
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

    private void setParameters(PreparedStatement ps,MappedStatement mappedStatement,BoundSql boundSql,Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if(null != parameterMappings) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null:configuration.newMetaObject(parameterObject);
			for(int i =0;i<parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if(parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if(null == parameterObject) {
						value = null;
					} else if(typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if(boundSql.hasAdditionalParameter(propertyName)){
						value = boundSql.getAdditionalParameter(propertyName);
					} else if(propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if(value != null) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if(null == typeHandler) {
						throw new ExecutorException("There was no TypeHandler found for parameter" + propertyName + " of statement " + mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}
	
	private String generatePageSql(String sql,Pager pagination) {
		if(null != pagination &&(null != dialect && !dialect.isEmpty())) {
			StringBuffer pageSql = new StringBuffer();
			if("mysql".equals(dialect)) {
				pageSql.append(sql);
				pageSql.append(" limit " + pagination.getFirstResultSize() + "," + pagination.getPageSize());
			} else if("oracle".equals(dialect)) {
				pageSql.append("select * from ( select temp_tb.*,rownum row_id from ( " );
				pageSql.append(sql);
				pageSql.append(") temp_tb where rownum <= ");
				pageSql.append(pagination.getFirstResultSize() + pagination.getPageSize());
				pageSql.append(") where row_id > ");
				pageSql.append(pagination.getFirstResultSize());
			}
			return pageSql.toString();
		} else {
			return sql;
		}
	}

}
