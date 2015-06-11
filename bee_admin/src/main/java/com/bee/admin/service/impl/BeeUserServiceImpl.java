package com.bee.admin.service.impl;


import com.bee.admin.service.IBeeUserService;
import com.bee.admin.service.base.AbstractBaseService;

import com.bee.dba.dao.IBeeUserDao;
import com.bee.dba.dao.base.IBaseDao;
import com.bee.dba.entity.BeeUserEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 附件管理
 * Created by tomi on 2014/10/17.
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


//    @Override
//    public BeeUserEntity loadByNameAndPass(String userId, String password) {
//        return null;
//    }
//
//    @Override
//    public BeeUserEntity loadByUserName(String userId) {
//        return null;
//    }
}
