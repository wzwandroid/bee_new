package com.bee.admin.service.base;

import com.bee.admin.web.filter.SystemContext;
import com.bee.dba.dao.base.IBaseDao;
import com.bee.mybatis.Pager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础service服务的抽象实现，该实现类不能被直接应用。需要子类继承并且实现{@linkplain #getBaseDao()}方法
 * Created by wzw on 2014/7/27.
 */
public abstract class AbstractBaseService<E, PK extends Serializable> implements IBaseService<E, PK> {

    /**
     * 分页标识，该标志主要用于Mybatis分页插件
     */
    public static final String PAGER_NAME = "pager";

    public abstract IBaseDao<E, PK> getBaseDao();

    /**
     * 根据主键删除
     *
     * @param pk 主键
     */
    @Override
    public int deleteByPk(PK pk) {
        return this.getBaseDao().deleteByPrimaryKey(pk);
    }

    @Override
    public int deleteByPks(List<PK> pks) {
        return this.getBaseDao().deleteByPrimaryKeys(pks);
    }

    /**
     *逻辑删除
     *
     */
    public int deleteLogicByPrimaryKeys(Map<String, Object> params){
        return this.getBaseDao().deleteLogicByPrimaryKeys(params);
    }

    /**
     * 添加对象，空属性不会插入
     *
     * @param entity pojo对象
     */
    @Override
    public int insert(E entity) {
        return this.getBaseDao().insert(entity);
    }

    /**
     * 根据主键查询
     *
     * @param pk 查询条件,主键值
     * @return pojo对象
     */
    @Override
    public E loadByPk(PK pk) {
        return this.getBaseDao().selectByPrimaryKey(pk);
    }

    /**
     * 根据主键修改，空值条件不会修改成null
     *
     * @param entity 要修改成的值
     */
    @Override
    public int update(E entity) {
        return this.getBaseDao().update(entity);
    }

    /**
     * 列表查询所有数据
     * @return pojo集合对象
     */
    @Override
    public List<E> listAll() {
        return this.getBaseDao().listAll();
    }

    /**
     * 根据条件列表查询
     * @param entity 查询条件
     * @return pojo集合对象
     */
    @Override
    public List<E> listByEntity(E entity) {
        return this.getBaseDao().listByEntity(entity);
    }

    /**
     * 分页查询用户信息列表
     *
     * @param params 查询条件
     */
    @Override
    public Pager<E> pageSelect(Map<String, Object> params) {
        Pager<E> pager = new Pager<E>();
        Integer pageNo = SystemContext.getPageNo();
        Integer pageSize = SystemContext.getPageSize();
        pager.setPageSize(pageSize);
        pager.setPageNo(pageNo);
        if (params == null) {
            params = new HashMap<String,Object>(10);
        }
        params.put(PAGER_NAME, pager);

        List<E> result = this.getBaseDao().pageSelect(params);
        pager.setResult(result);

        return pager;
    }
}
