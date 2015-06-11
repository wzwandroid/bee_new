package com.bee.admin.controller;



import com.bee.admin.service.IBeeUserService;
import com.bee.dba.entity.BeeUserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wzw on 2014/10/9
 *
 * @function 菜单管理
 */

@Controller
@RequestMapping("/admin")
public class IndexController {

    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IBeeUserService beeUserService;

    //Logger logger = LoggerFactory.getLogger(IndexController.class);

    public static final String INNER_REDIRECT_URL = "/admin/index";


    /**
     * function：菜单列表--查看所有菜单项目
     * author:wzw
     */
    @RequestMapping("/index")
    public String list() {

        List<BeeUserEntity> beeUserEntities = beeUserService.listAll();
        int a = 1;
        System.out.print(beeUserEntities.getClass().getCanonicalName());

        return "index";
    }



}
