package com.bee.dba.dao.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author mark
 * @date 2015-06-12
 * service组件层的基本增删改查和分页查询
 */
public interface IBaseDao<E, PK extends Serializable> {
    /**
     * 根据主键删除
     *
     * @param pk 主键
     */
    int deleteByPrimaryKey(PK pk);

    /**
     * 根据主键列表值进行删除，该方法提供的多值删除。<br>
     * 如果只有一个值请调用{@linkplain #deleteByPrimaryKey(java.io.Serializable)}方法
     *
     * @param pks 主键列表
     */
    int deleteByPrimaryKeys(List<PK> pks);

    /**
     * 添加对象，空属性不会插入
     *
     * @param entity pojo对象
     */
    int insert(E entity);

    /**
     * 根据主键查询
     *
     * @param pk 查询条件,主键值
     * @return pojo对象
     */
    E selectByPrimaryKey(PK pk);

    /**
     * 根据查询pojo对象获取新的pojo对象
     *
     * @param entity pojo对象
     * @return 如果查询且仅有一条记录则返回pojo对象，如果查询结果出现多条记录则抛出异常，如果查询结果为空则返回{@cod null}
     */
    E selectByEntity(E entity);

    /**
     * 根据主键修改，空值条件不会修改成null
     *
     * @param entity 要修改成的值
     */
    int update(E entity);

    /**
     * 根据主键更新，当有空值属性也会被修改
     *
     * @param entity 要修改成的值
     */
    int updateByPrimaryKey(E entity);

    /**
     * 列表查询所有数据
     *
     * @return pojo集合对象
     */
    List<E> listAll();

    /**
     * 根据条件列表查询
     *
     * @param entity 查询条件
     * @return pojo集合对象
     */
    List<E> listByEntity(E entity);

    /**
     * 分页查询用户信息列表
     *
     * @param params 查询条件
     */
    List<E> pageSelect(Map<String, Object> params);

}
