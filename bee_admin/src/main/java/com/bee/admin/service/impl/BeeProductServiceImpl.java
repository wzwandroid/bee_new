package com.bee.admin.service.impl;

import com.bee.admin.constants.SystemConstant;
import com.bee.admin.lang.StringUtils;
import com.bee.admin.service.IBeeProductService;
import com.bee.admin.service.base.AbstractBaseService;
import com.bee.admin.utils.IdGenerator;
import com.bee.dba.dao.IBeeProductDao;
import com.bee.dba.dao.base.IBaseDao;
import com.bee.dba.entity.BeeProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mark
 * @date 2015/6/18.
 * @comment
 */
@Service("beeProductService")
public class BeeProductServiceImpl extends AbstractBaseService<BeeProductEntity,String> implements IBeeProductService {

    @Autowired
    private IBeeProductDao beeProductDao;


    @Override
    public IBaseDao<BeeProductEntity, String> getBaseDao() {
        return this.beeProductDao;
    }


    @Override
    public boolean saveBeeProductEntity(BeeProductEntity formBean) {
        BeeProductEntity beeProductEntity = new BeeProductEntity();
        saveUpdateBeeProductEntity(formBean, beeProductEntity);

        int saveCount = beeProductDao.insert(beeProductEntity);
        return saveCount > 0;
    }

    @Override
    public boolean updateBeeProductEntity(BeeProductEntity formBean) {
        BeeProductEntity beeProductEntity = this.loadByPk(formBean.getProductId());//
        if(beeProductEntity == null){
            return false;
        }
        saveUpdateBeeProductEntity(formBean, beeProductEntity);

        int updateCount = this.beeProductDao.update(beeProductEntity);
        return updateCount > 0;
    }

    @Override
    public boolean deleteLogicByPks(List<String> delIds,String operatorId) {
        if(delIds == null || delIds.size() <=0){
            return false;
        }
        Map<String,Object> params = new HashMap<>();
        params.put("delIds",delIds);
        params.put("updateTime",new Date());
        params.put("status",SystemConstant.STATUS_NO.getCode());
        params.put("operatorId",operatorId);

        int updateCount = this.beeProductDao.deleteLogicByPrimaryKeys(params);
        return updateCount > 0;
    }

    private void saveUpdateBeeProductEntity(BeeProductEntity formBean,BeeProductEntity beeProductEntity) {
        if(StringUtils.isEmpty(formBean.getProductId())){
            beeProductEntity.setProductId(IdGenerator.getId());//TODO 目前Id生成规则
            beeProductEntity.setCreateTime(new Date());
            beeProductEntity.setStatus(SystemConstant.STATUS_YES.getCode()); //新增默认但写出 其他规则一样
        } else{
            beeProductEntity.setUpdateTime(new Date());
        }
        if(StringUtils.isNotEmpty(formBean.getType())){
            beeProductEntity.setType(formBean.getType());
        }
        if(StringUtils.isNotEmpty(formBean.getName())){
            beeProductEntity.setName(StringUtils.trim(formBean.getName()));
        }
        if(StringUtils.isNotEmpty(formBean.getPrice())){
            beeProductEntity.setPrice(formBean.getPrice());
        }
        if(StringUtils.isNotEmpty(formBean.getOperatorId())){
            beeProductEntity.setOperatorId(formBean.getOperatorId());
        }


    }

    @Override
    public BeeProductEntity loadByPk(String areaId) {
        BeeProductEntity beeProductEntity = new BeeProductEntity();
        beeProductEntity.setProductId(areaId);
        List<BeeProductEntity> beeProductEntities = this.beeProductDao.listByEntity(beeProductEntity);
        if (beeProductEntities != null && beeProductEntities.size() > 0) {
            return beeProductEntities.get(0);
        }
        return null;
    }
}
