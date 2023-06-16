package com.sunmile.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunmile.crm.base.BaseService;
import com.sunmile.crm.base.ResultInfo;
import com.sunmile.crm.dao.UserMapper;
import com.sunmile.crm.dao.UserRoleMapper;
import com.sunmile.crm.model.UserModel;
import com.sunmile.crm.query.UserQuery;
import com.sunmile.crm.util.AssertUtil;
import com.sunmile.crm.util.Md5Util;
import com.sunmile.crm.util.PhoneUtil;
import com.sunmile.crm.util.UserIDBase64;
import com.sunmile.crm.vo.Customer;
import com.sunmile.crm.vo.SaleChance;
import com.sunmile.crm.vo.User;
import com.sunmile.crm.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author Sunmile_Cheung 张亮
 * @Date 2023/6/3 10:46
 * @Description
 * @Modified by Sunmile
 */
@Service
public class UserService extends BaseService<User, Integer> {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 依据传入的userName和userPwd判断用户能否登录，返回结果
     *
     * @param userName 用户名
     * @param userPwd  密码
     */
    public ResultInfo<UserModel> login(String userName, String userPwd) {
        ResultInfo<UserModel> resultInfo = new ResultInfo<>();
        User user = checkLoginParam(userName, userPwd);
        UserModel userModel = new UserModel(UserIDBase64.encoderUserID(user.getId()), user.getUserName(), user.getTrueName());
        resultInfo.setCode(200);
        resultInfo.success("登录成功", userModel);
        return resultInfo;
    }

    /**
     * 检查传入的用户名和密码是否符合要求
     *
     * @param userName 用户名
     * @param userPwd  密码
     * @return 返回从数据库中查出的user对象的信息
     */
    private User checkLoginParam(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd), "用户密码不能为空");
        User user = userMapper.queryUserInfoByUserName(userName);
        AssertUtil.isTrue(Objects.isNull(user), "您输入的用户名不存在");
        AssertUtil.isTrue(!Md5Util.encode(userPwd).equals(user.getUserPwd()), "用户密码不匹配");
        return user;
    }

    /**
     * 依据传入的新旧密码等参数进行判空等操作，并返回修改密码的结果
     *
     * @param userId          用户id
     * @param oldPassword     旧密码
     * @param newPassword     新密码
     * @param confirmPassword 确认新密码
     * @return 返回结果集
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public ResultInfo<User> updatePassword(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
        ResultInfo<User> resultInfo = new ResultInfo<>();
        User user = checkUpdateParam(userId, oldPassword, newPassword, confirmPassword);
        Integer row = userMapper.updateByPrimaryKey(user);
        if (row > 0) resultInfo.success("密码修改成功", user);
        else resultInfo.failed("密码修改失败", null);
        return resultInfo;
    }

    /**
     * 判断前端传入的新旧密码以及确认密码的信息是否符合要求
     *
     * @param userId          用户id
     * @param oldPassword     旧密码
     * @param newPassword     新密码
     * @param confirmPassword 确认密码
     * @return 返回User对象
     */
    private User checkUpdateParam(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
        User user = userMapper.selectByPrimaryKey(userId);
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword), "旧密码不能为空");
        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(oldPassword)), "您输入的旧密码不是有效密码");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword), "新密码不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword), "确认密码不能为空");
        AssertUtil.isTrue(!confirmPassword.equals(newPassword), "确认密码与设定的新密码不一致，请检查输入");
        AssertUtil.isTrue(user.getUserPwd().equals(Md5Util.encode(newPassword)), "请设置一个与原来密码不同的密码");
        user.setUserPwd(Md5Util.encode(newPassword));
        return user;
    }


    /**
     * 返回所有的销售人员的信息
     *
     * @return id 销售人员id uname 销售人员姓名
     */
    public List<Map<String, Object>> queryAllSales() {
        return userMapper.queryAllSales();
    }


    /**
     * @param userQuery
     * @return
     */
    public Map<String, Object> listUserInfo(UserQuery userQuery) {
        HashMap<String, Object> resultMap = new HashMap<>();
        AssertUtil.isTrue(Objects.isNull(userQuery), "传入的saleChanceQuery对象为空");
        List<User> userList = userMapper.selectByParams(userQuery);
        PageHelper.startPage(userQuery.getPage(), userQuery.getLimit());
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        resultMap.put("count", pageInfo.getTotal());
        resultMap.put("code", 0);
        resultMap.put("msg", "success");
        resultMap.put("data", pageInfo.getList());
        return resultMap;
    }


    /**
     * 通过用户管理页面点击添加用户添加用户信息
     *
     * @param user 从前端传入的添加用户的用户相关数据
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(User user) {
        checkAddOrUpdateUserParam(user.getUserName(), user.getPhone(), user.getEmail(), null);
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode("123456"));
        AssertUtil.isTrue(userMapper.insertSelective(user) < 1, "用户信息保存失败");
        //进行用户与角色的关联操作
        relationUserAndRole(user.getId(), user.getRoleIds());
    }

    /**
     * 依据用户id进行用户与角色的关联操作
     * 用户角色分配
     * 原始角色不存在  添加新的角色记录
     * 原始角色存在   添加新的角色记录
     * 原始角色存在   清空所有角色
     * 原始角色存在   移除部分角色
     * 如何进行角色分配???
     * 如果用户原始角色存在 首先清空原始所有角色
     * 添加新的角色记录到用户角色表
     *
     * @param userId  用户id
     * @param roleIds 从前端传过来的用户id
     */
    private void relationUserAndRole(Integer userId, String roleIds) {
        //首先查询该用户下是否存在角色数据
        Integer roleCount = userRoleMapper.queryRoleCountByUserId(userId);
        //将从前端获取到的roleIds字段转换为对应的角色id的数组
        List<Integer> roleIdsInteger = new ArrayList<>();
        if (StringUtils.isNotBlank(roleIds)){
            roleIdsInteger = Arrays.stream(roleIds.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        }
        if (roleCount > 0) {
            //如果数据库中存在角色信息，首先进行清除操作
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId) != roleCount,"角色信息清除失败，无法进行进一步的更新操作");
        }
        //遍历角色id,将数据放进一个list集合中，以备最终批量写入数据库中
        ArrayList<UserRole> userRoles = new ArrayList<>();
        for (Integer roleId : roleIdsInteger) {
            userRoles.add(new UserRole(null,userId,roleId,new Date(),new Date()));
        }
        if (userRoles.size() > 0){
            AssertUtil.isTrue(userRoleMapper.insertBatch(userRoles) != userRoles.size(),"角色写入失败");
        }

    }

    /**
     * 校验前端传入的用户名等用户信息是否符合格式要求
     *
     * @param userName
     * @param phone
     * @param email
     */
    private void checkAddOrUpdateUserParam(String userName, String phone, String email, Integer userId) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空");
        User user = userMapper.queryUserInfoByUserName(userName);
        //如果用户id不为空，说明是更新操作，此时该用户名可以与传入的用户名一致
        if (Objects.nonNull(userId)) {
            User user1 = userMapper.selectByPrimaryKey(userId);
            AssertUtil.isTrue(Objects.isNull(user1) || user1.getIsValid() == 0, "该用户id对应的用户记录不存在");
        } else {
            //如果用户id为空，说明为插入操作，此时需要判断数据库中是否已经存在相同userName的用户记录
            AssertUtil.isTrue(Objects.nonNull(user) && user.getIsValid() == 1, "该用户名已存在，请检查后重新输入");
        }
        AssertUtil.isTrue(StringUtils.isBlank(phone), "用户手机号不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(email), "用户邮箱不能为空");
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone), "您输入的手机号格式不正确");
    }

    /**
     * 用户信息更新
     *
     * @param user 要被更新的用户信息
     * @return 返回更新结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user) {
        checkAddOrUpdateUserParam(user.getUserName(), user.getPhone(), user.getEmail(), user.getId());
        user.setIsValid(1);
        user.setUpdateDate(new Date());
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) != 1, "用户数据更新失败");
        Integer userId = userMapper.queryUserInfoByUserName(user.getUserName()).getId();
        relationUserAndRole(userId,user.getRoleIds());
    }

    /**
     * 删除用户
     *
     * @param ids 一条或多条用户id 对应头部工具栏和行工具栏
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUsers(Integer[] ids) {
        AssertUtil.isTrue(Objects.isNull(ids) || ids.length < 1, "请传入用户id");
        for (Integer id : ids) {
            User user = userMapper.selectByPrimaryKey(id);
            AssertUtil.isTrue(Objects.isNull(id) || Objects.isNull(user),"待删除记录不存在");
        }
        AssertUtil.isTrue(userMapper.deleteBatch(ids) < ids.length, "用户删除失败");
        //对用户关联的角色信息进行删除
        for (Integer id : ids) {
            relationUserAndRole(id,"");
        }
    }

    /**
     * 查询所有的管理人员信息
     * @return
     */
    public List<Map<String,Object>> queryAllCustomerManager() {
     return userMapper.queryAllCustomerManager();
    }
}
