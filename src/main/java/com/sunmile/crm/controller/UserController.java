package com.sunmile.crm.controller;

import com.sunmile.crm.annotation.RequiredAnnotation;
import com.sunmile.crm.base.BaseController;
import com.sunmile.crm.base.ResultInfo;
import com.sunmile.crm.model.UserModel;
import com.sunmile.crm.query.UserQuery;
import com.sunmile.crm.service.UserService;
import com.sunmile.crm.util.LoginUserUtil;
import com.sunmile.crm.vo.Customer;
import com.sunmile.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/3 10:37
 * @Description
 * @Modified by Sunmile
 */
@RequestMapping("user")
@Controller
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    /**
     * /**
     * * 1.参数校验
     * *  用户名 非空
     * *  密码 非空
     * * 2.根据用户名 查询用户记录
     * * 3.校验用户存在性
     * *   不存在 -->记录不存在 方法结束
     * * 4.用户存在
     * *   校验密码
     * *    密码错误-->密码不正确 方法结束
     * * 5.密码正确
     * *   用户登录成功 返回用户相关信息
     * 
     * @param userName
     * @param userPwd
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public ResultInfo login(String userName,String userPwd){
        ResultInfo<UserModel> resultInfo = userService.login(userName, userPwd);
        return resultInfo;
    }

    /**
     * 页面跳转至修改密码页面
     * @return
     */
    @RequestMapping("toPasswordPage")
    public String toPasswordPage(){
        return "user/password";
    }

    /**
     * 修改密码操作
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param confirmPassword 确认新密码
     * @return 返回结果
     */
    @PostMapping("updatePassword")
    @ResponseBody
    public ResultInfo<User> updatePassword(HttpServletRequest request,
                                           String oldPassword,
                                           String newPassword,
                                           String confirmPassword){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        ResultInfo<User> resultInfo = userService.updatePassword(userId,oldPassword,newPassword,confirmPassword);
        return resultInfo;

    }

    /**
     * 查询所有的销售人员
     * @return 返回list集合
     */
    @PostMapping("queryAllSales")
    @ResponseBody
    public List<Map<String,Object>> queryAllSales(){
        return userService.queryAllSales();
    }

    /**
     * 进行页面的跳转，点击用户管理，跳转至相应的用户管理界面
     * @return
     */
    @RequestMapping("index")
    public String index(){

        return "user/user";
    }

    /**
     * 展示当前用户下的用户相关信息
     * @param userQuery 查询数据对象
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> listUserInfo(UserQuery userQuery){
        Map<String,Object> result = userService.listUserInfo(userQuery);
        return result;
    }

    /**
     * 点击添加用户按钮进行相应的页面跳转
     * @param id
     * @return
     */
    @RequestMapping("addOrUpdateUserPage")
    public String addOrUpdateUserPage(Integer id, Model model){
        User user = userService.selectByPrimaryKey(id);
        model.addAttribute("user1",user);
        return "user/add_update";
    }

    /**
     * 通过用户管理页面点击添加用户添加用户信息
     * @param user 从前端传入的添加用户的用户相关数据
     * @return
     */
    @ResponseBody
    @PostMapping("save")
    public ResultInfo save(User user){
        userService.saveUser(user);
        return success("用户信息添加成功");
    }

    /**
     * 用户信息更新
     * @param user 要被更新的用户信息
     * @return 返回更新结果
     */
    @PostMapping("update")
    @ResponseBody
    public ResultInfo update(User user){
        userService.updateUser(user);
        return success("用户信息更新成功");
    }

    /**
     * 删除用户
     * @param ids  一条或多条用户id 对应头部工具栏和行工具栏
     * @return
     */
    @ResponseBody
    @PostMapping("delete")
    @RequiredAnnotation(code = "601004")
    public ResultInfo deleteUser(Integer[] ids){
        userService.deleteUsers(ids);
        return success("删除用户");
    }

    /**
     * 查询客户信息中所有的管理人员的信息
     * @return
     */
    @PostMapping("queryAllCustomerManager")
    @ResponseBody
    public List<Map<String,Object>> queryAllCustomerManager(){
        return userService.queryAllCustomerManager();
    }
}
