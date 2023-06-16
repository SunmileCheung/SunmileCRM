package com.sunmile.crm.controller;


import com.sunmile.crm.base.BaseController;
import com.sunmile.crm.service.PermissionService;
import com.sunmile.crm.service.UserService;
import com.sunmile.crm.util.LoginUserUtil;
import com.sunmile.crm.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 系统登录页
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "index";
    }


    // 系统界面欢迎页
    @RequestMapping("welcome")
    public String welcome(){
        return "welcome";
    }

    /**
     * 后端管理主页面
     * @return
     */
    @RequestMapping("main")
    public String main(HttpServletRequest request){
        //从cookie中拿出userId属性，并通过userId从数据库中拿出user信息，写入session域中
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        User user = userService.selectByPrimaryKey(userId);
        HttpSession session = request.getSession();
        session.setAttribute("user",user);
        //通过userId从数据库中拿出权限信息，并写入session域中
        List<String> permissions = permissionService.queryAllPermissionCodeByUserId(userId);
        session.setAttribute("permissions",permissions);
        return "main";
    }
}
