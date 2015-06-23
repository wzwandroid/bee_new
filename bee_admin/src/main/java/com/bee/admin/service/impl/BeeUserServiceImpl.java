package com.bee.admin.service.impl;


import com.bee.admin.constants.SystemConstant;
import com.bee.admin.security.MD5Encryption;
import com.bee.admin.service.IBeeUserService;
import com.bee.admin.service.base.AbstractBaseService;
import com.bee.dba.dao.IBeeUserDao;
import com.bee.dba.dao.base.IBaseDao;
import com.bee.dba.entity.BeeUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author mark
 * @date 2015-07-14
 */
@Service("beeUserService")
public class BeeUserServiceImpl extends AbstractBaseService<BeeUserEntity,String>
        implements IBeeUserService {

    @Autowired
    private IBeeUserDao beeUserDao;


    @Override
    public IBaseDao<BeeUserEntity, String> getBaseDao() {
        return this.beeUserDao;
    }

    @Override
    public BeeUserEntity loadByNameAndPass(String username, String password) {

        BeeUserEntity beeUserEntity = new BeeUserEntity();
        beeUserEntity.setStatus(SystemConstant.STATUS_YES.getCode());//有效记录
        beeUserEntity.setUserName(username);
        beeUserEntity.setPassword(MD5Encryption.encrypt(password));

        List<BeeUserEntity> beeUserEntities = this.beeUserDao.listByEntity(beeUserEntity);
        if(beeUserEntities != null && beeUserEntities.size()>0){
            return beeUserEntities.get(0);
        }

        return null;
    }

    @Override
    /**
     * 所有的操作必须在service完成 或者延伸到dao
     */
    public boolean modifyPassWord(String userId, String password, String operatorId) {

        BeeUserEntity beeUserEntity = new BeeUserEntity();
        beeUserEntity.setUserId(userId);
        beeUserEntity.setPassword(MD5Encryption.encrypt(password));
        beeUserEntity.setOperatorId(operatorId);
        beeUserEntity.setUpdateTime(new Date());
        int changeCount = this.beeUserDao.update(beeUserEntity);

        return changeCount > 0;
    }


}
