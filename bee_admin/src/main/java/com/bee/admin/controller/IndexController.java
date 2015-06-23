package com.bee.admin.controller;


import com.bee.admin.constants.SystemConstant;
import com.bee.admin.lang.StringUtils;
import com.bee.admin.service.IBeeMenuService;
import com.bee.admin.service.IBeeRightService;
import com.bee.admin.service.IBeeUserService;
import com.bee.admin.web.common.AjaxMsg;
import com.bee.dba.entity.BeeMenuEntity;
import com.bee.dba.entity.BeeUserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author mark
 * @date 2015/6/13.
 * @function 主要功能
 */

@Controller
@RequestMapping("/admin")
public class IndexController {

    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IBeeUserService beeUserService;
    @Autowired
    private IBeeRightService beeRightService;
    @Autowired
    private IBeeMenuService beeMenuService;

    //Logger logger = LoggerFactory.getLogger(IndexController.class);

    public static final String INNER_REDIRECT_URL = "/admin/index";


    /**
     * function：首页
     * author:wzw
     */
    @RequestMapping("/index")
    public String list() {
        BeeUserEntity userEntity = new BeeUserEntity();
//        userEntity.setStatus();
//        List<BeeUserEntity> beeUserEntities = beeUserService.listByEntity();
//        int a = 1;
//        System.out.print(beeUserEntities.getClass().getCanonicalName());

        return "index";
    }


    @RequestMapping({"/", "/login"})
    public String login(HttpSession session) {
        String testLogger1 = "{}和后面的参数相对应可以多个1";
        String testLogger2 = "{}和后面的参数相对应可以多个2";
        logger.info("进入登陆页面，请求参数 param1={},param2是{}", testLogger1, testLogger2);
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public AjaxMsg login(String username, String password, HttpSession session) {
        logger.info("进入用户登录接口，请求参数username={},password={}", username, password);
        AjaxMsg ajaxMsg = new AjaxMsg(AjaxMsg.FAIL);

        System.out.println("wangdashen+++++++++++++++++++++");

        //参数合法性校验
        if (StringUtils.isEmpty(username)) {
            ajaxMsg.setMessage("用户名不能为空！");
            return ajaxMsg;
        }
        if (StringUtils.isEmpty(password)) {
            ajaxMsg.setMessage("密码不能为空！");
            return ajaxMsg;
        }

        // 首先到数据库中验证用户名密码是否正确
        BeeUserEntity beeUserEntity = this.beeUserService.loadByNameAndPass(username, password);
        if (beeUserEntity == null) {
            ajaxMsg.setMessage("用户名或者密码不正确！");
            return ajaxMsg;
        }

        try {
            //把登陆用户相关信息加载到Session中
            if (session.getAttribute(SystemConstant.LOGIN_USER_INFO.getCode()) != null && !((BeeUserEntity) session.getAttribute(SystemConstant.LOGIN_USER_INFO.getCode())).getUserId().equals(beeUserEntity.getUserId())) {
                ajaxMsg.setMessage("同一个浏览器（会话）只能有一名用户使用系统 ！");
                return ajaxMsg;
            } else {
                session.setAttribute(SystemConstant.LOGIN_USER_INFO.getCode(), beeUserEntity);
                session.setAttribute(SystemConstant.LOGIN_USER_NAME.getCode(),username);
            }
            // .是否有权限 搜出所有的权限codes
            List<BeeMenuEntity> rightCodes = this.beeMenuService.getUserMenus(beeUserEntity.getUserId());
            if (rightCodes == null || rightCodes.size() < 1) {
                ajaxMsg.setMessage("您没有权限登陆，请联系管理人员！");
                return ajaxMsg;
            }
            session.setAttribute("rightCodes", rightCodes);

            ajaxMsg.setData(rightCodes);
            ajaxMsg.setStatusCode(AjaxMsg.SUCCESS);
            ajaxMsg.setForwardUrl(INNER_REDIRECT_URL);
            return ajaxMsg;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxMsg.setMessage(e.getMessage());
            return ajaxMsg;
        }

    }

    @RequestMapping(value = "/loadTrees", method = RequestMethod.POST)
    @ResponseBody
    public AjaxMsg loadTrees(HttpSession session) {
        AjaxMsg ajaxMsg = new AjaxMsg();

        //从Session获取权限信息
        List<BeeMenuEntity> tMenuEntities = (List<BeeMenuEntity>) session.getAttribute("rightCodes");
        ajaxMsg.setData(tMenuEntities);

        return ajaxMsg;
    }

}
