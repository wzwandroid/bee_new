package com.bee.admin.service;


import com.bee.admin.service.base.IBaseService;
import com.bee.dba.entity.BeeUserEntity;

/**
 * 
 */
public interface IBeeUserService extends IBaseService<BeeUserEntity,String> {
    BeeUserEntity loadByNameAndPass(String username, String password);

    boolean modifyPassWord(String userId, String newPwd, String operatorId);


//    void saveTUserEntity(BeeUserEntity userBean);
//
//    void updateTUserEntity(BeeUserEntity userBean);
//
//    void grantRole(String[] rolePerms, String userId);

//    BeeUserEntity loadByNameAndPass(String userId, String password);
//
//    BeeUserEntity loadByUserName(String userId);
}
