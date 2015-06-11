package com.bee.admin.web.servlet;



import com.bee.admin.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 容器启动完成后的一些初始化操作
 * Created by mark on 2015/06/18.
 * Updated by mark on 2015/06/18.
 *
 */
public class InitServlet implements ApplicationListener<ContextRefreshedEvent> {
    private static final transient Logger logger = LoggerFactory.getLogger(InitServlet.class);
    private static WebApplicationContext wac;

//    private static Map<SysGroupConfEntity,List<SysParamsConfEntity>> tblColStaParams;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //初始化spring的工厂
        if (contextRefreshedEvent.getApplicationContext() instanceof WebApplicationContext) {
            wac = (WebApplicationContext) contextRefreshedEvent.getApplicationContext();
            logger.info("获取Spring的WebApplicationContext实例对象成功");
        } else {
            logger.warn("初始化spring工厂失败");
        }

//        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
//            logger.info("**************InstantiationSysParamsTracingBeanPostProcessor.onApplicationEvent executing********************");
//            //获取bean对象
//            ISysGroupConfService sysGroupConfService = wac.getBean(ISysGroupConfService.class);
//            ISysParamsConfService sysParamsConfService = wac.getBean(ISysParamsConfService.class);
//
//            List<SysGroupConfEntity> sysGroupConfEntityList = sysGroupConfService.listAll();
//            List<SysParamsConfEntity> sysParamsConfEntityList = sysParamsConfService.listAll();
//            generateSysParams(sysGroupConfEntityList, sysParamsConfEntityList);
//            logger.info("数据字典初始化成功");
//            logger.info("**************InstantiationSysParamsTracingBeanPostProcessor.onApplicationEvent finished********************");
//        }
    }

//    private void generateSysParams(List<SysGroupConfEntity> sysGroupConfEntityList,List<SysParamsConfEntity> sysParamsConfEntityList) {
////        if(tblColStaParams == null) {
////            tblColStaParams = new HashMap<SysGroupConfEntity,List<SysParamsConfEntity>>();
////        }
////        for(SysGroupConfEntity sysGroupConfEntity : sysGroupConfEntityList) {
////
////            List<SysParamsConfEntity> params = new ArrayList<SysParamsConfEntity>();
////            for(SysParamsConfEntity sysParamsConfEntity : sysParamsConfEntityList) {
////                if (sysParamsConfEntity.getParamGroupId().equals(sysGroupConfEntity.getId())) {
////                    params.add(sysParamsConfEntity);
////                }
////            }
////            tblColStaParams.put(sysGroupConfEntity, params);
////        }
//    }

    /**
     * 提供了Servlet完成了初始化工作后，对外提供的可调用的API操作
     * Created by mzllon on 2014/7/29.
     */
    public static class InitServletUtils {

        /**
         * 对外提供了Spring容器获取
         */
        public static WebApplicationContext getWebApplicationContext() {
            return wac;
        }

        /**
         * 获取所有系统数据库表的参数组和参数列表映射信息(未经过任何处理)
         */
//        public static Map<SysGroupConfEntity,List<SysParamsConfEntity>> getAllTblColStaParams( ) {
//            return tblColStaParams;
//        }
//
//        /**
//         * 根据字段组关键字获取参数组对象信息和组下的参数列表信息
//         * @param paramGroupName 参数组关键字
//         */
//        public static Map<SysGroupConfEntity,List<SysParamsConfEntity>> findTblColStaParamsByGroupKeyword(String paramGroupName) {
//            if (!StringUtils.hasLength(paramGroupName)) {
//                return null;
//            }
//            for (SysGroupConfEntity sysGroupConfEntity : tblColStaParams.keySet()) {
//                if (sysGroupConfEntity.getParamGroupName().equals(paramGroupName)) {
//                    Map<SysGroupConfEntity, List<SysParamsConfEntity>> single = new HashMap<SysGroupConfEntity, List<SysParamsConfEntity>>(2);
//                    single.put(sysGroupConfEntity, tblColStaParams.get(sysGroupConfEntity));
//                    return single;
//                }
//            }
//            return null;
//        }

        /**
         * 根据参数组关键字获取参数组对象信息
         * @param paramGroupName 参数组关键字
         */
//        public static SysGroupConfEntity findSysGroupConfEntity(String paramGroupName){
//            if (paramGroupName == null) {
//                return null;
//            }
//            for (SysGroupConfEntity sysGroupConfEntity : tblColStaParams.keySet()) {
//                if (sysGroupConfEntity.getParamGroupName().equals(paramGroupName)) {
//                    return sysGroupConfEntity;
//                }
//            }
//            return null;
//        }

        /**
         * 根据参数组关键字获取参数列表集合信息
         * @param paramGroupName 参数组关键字
         */
//        public static List<SysParamsConfEntity> findSysParamsConfList(String paramGroupName) {
//            if (paramGroupName == null) {
//                return null;
//            }
//            SysGroupConfEntity sysGroupConfEntity = findSysGroupConfEntity(paramGroupName);
//            return tblColStaParams.get(sysGroupConfEntity);
//        }

        /**
         * 根据参数组关键字和参数名获取参数对象信息
         * @param paramGroupName 参数组关键字
         * @param paramName 参数名
//         */
//        public static SysParamsConfEntity findSysParamsConfEntity(String paramGroupName,String paramName){
//            List<SysParamsConfEntity> sysParamsConfEntities =findSysParamsConfList(paramGroupName);
//            if (sysParamsConfEntities == null) {
//                return null;
//            }
//
//            for (SysParamsConfEntity sysParamsConfEntity : sysParamsConfEntities) {
//                if (sysParamsConfEntity.getParamName().equals(paramName)) {
//                    return sysParamsConfEntity;
//                }
//            }
//            return null;
//        }

        /**
         * 根据参数组关键字和参数主键值获取参数对象信息
         * @param paramGroupName 参数组关键字
         * @param paramId 参数的主键值
         */
//        public static SysParamsConfEntity findSysParamsConfEntity(String paramGroupName,Integer paramId){
//            List<SysParamsConfEntity> sysParamsConfEntities =findSysParamsConfList(paramGroupName);
//            if (sysParamsConfEntities == null) {
//                return null;
//            }
//
//            for (SysParamsConfEntity sysParamsConfEntity : sysParamsConfEntities) {
//                if (sysParamsConfEntity.getId().equals(paramId)) {
//                    return sysParamsConfEntity;
//                }
//            }
//            return null;
//        }

        /**
         * 根据参数名(paramName)、参数所属组(paramGroupId)、参数依赖父参数(parentId)查询唯一参数信息对象
         * @param paramName 参数名
         * @param paramGroupId 参数所属组
         * @param parentId 参数所依赖的父参数ID
         * @see 1.1
         * @author mzllon
         */
//        public static SysParamsConfEntity findSysParamsConfEntity(String paramName,Integer paramGroupId,Integer parentId) {
//            if (!StringUtils.hasText(paramName) || paramGroupId == null || parentId == null) {
//                return null;
//            }
//            for (SysGroupConfEntity sysGroupConfEntity : tblColStaParams.keySet()) {
//                if (sysGroupConfEntity.getId().equals(paramGroupId)) {
//                    List<SysParamsConfEntity> sysParamsConfEntityList = tblColStaParams.get(sysGroupConfEntity);
//                    for (SysParamsConfEntity sysParamsConfEntity : sysParamsConfEntityList) {
//                        if (sysParamsConfEntity.getParamName().equals(paramName) && sysParamsConfEntity.getParentId().equals(parentId)) {
//                            return sysParamsConfEntity;
//                        }
//                    }
//                }
//            }
//            return null;
//        }

        /**
         * 根据参数所属组编号(paramGroupId)、参数需依赖的父参数编号(parentId)查询一组列表
         * @param paramGroupId 参数所属组编号
         * @param parentId 父参数编号
         * @return 一组参数列表
         * @see 1.1
         * @author mzllon
         */
//        public static List<SysParamsConfEntity> findSysParamsConfEntityList(Integer paramGroupId, Integer parentId) {
//            if (paramGroupId == null || parentId == null) {
//                return null;
//            }
//            for (SysGroupConfEntity sysGroupConfEntity : tblColStaParams.keySet()) {
//                if (sysGroupConfEntity.getId().equals(paramGroupId)) {
//                    List<SysParamsConfEntity> sysParamsConfEntityList = tblColStaParams.get(sysGroupConfEntity);
//                    List<SysParamsConfEntity> findList = new ArrayList<SysParamsConfEntity>(sysParamsConfEntityList.size());
//                    for (SysParamsConfEntity sysParamsConfEntity : sysParamsConfEntityList) {
//                        if (sysParamsConfEntity.getParentId().equals(parentId)) {
//                            findList.add(sysParamsConfEntity);
//                        }
//                    }
//                    return findList;
//                }
//            }
//            return null;
//        }

        /**
         * 根据参数编号（ID）查询参数对象信息
         * @param paramsId 参数ID
         * @return pojo对象
         */
//        public static SysParamsConfEntity findSysParamsConfEntity(Integer paramsId){
//            if (paramsId == null) {
//                return null;
//            }
//            for (List<SysParamsConfEntity> sysParamsConfEntityList : tblColStaParams.values()) {
//                for (SysParamsConfEntity sysParamsConfEntity : sysParamsConfEntityList) {
//                    if (sysParamsConfEntity.getId().equals(paramsId)) {
//                        return sysParamsConfEntity;
//                    }
//                }
//            }
//            return null;
//        }
    }

}
