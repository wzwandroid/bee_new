package com.bee.admin.service.base;


import com.bee.mybatis.Pager;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * service组件层的基本增删改查和分页查询
 * Created by Administrator on 2014/7/27.
 */
public interface IBaseService<E,PK extends Serializable> {
    /**
     * 根据主键删除
     * @param pk 主键
     */
    int deleteByPk(PK pk);

    int deleteByPks(List<PK> pks);

    /**
     *逻辑删除
     *
     */
    int deleteLogicByPrimaryKeys(Map<String, Object> params);


    /**
     * 添加对象，空属性不会插入
     * @param entity pojo对象
     */
    int insert(E entity);

    /**
     * 根据主键查询
     * @param pk 查询条件,主键值
     * @return pojo对象
     */
    E loadByPk(PK pk);

    /**
     * 根据主键修改，空值条件不会修改成null
     *
     * @param entity 要修改成的值
     */
    int update(E entity);

    /**
     * 列表查询所有数据
     * @return pojo集合对象
     */
    List<E> listAll();

    /**
     * 根据条件列表查询
     * @param entity 查询条件
     * @return pojo集合对象
     */
    List<E> listByEntity(E entity);

    /**
     * 分页查询用户信息列表
     * @param params 查询条件
     */
    Pager<E> pageSelect(Map<String, Object> params);
}
