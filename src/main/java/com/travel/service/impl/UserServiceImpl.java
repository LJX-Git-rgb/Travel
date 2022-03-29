package com.travel.service.impl;

import com.travel.dao.UserDao;
import com.travel.dao.impl.UserDaoImpl;
import com.travel.domain.ResultInfo;
import com.travel.domain.User;
import com.travel.service.UserService;
import com.travel.util.MySpring;
import com.travel.util.SMTPClient;
import com.travel.util.UuidUtil;

/**
 * @ClassName UserServiceImpl
 * @description:
 * @author: LJX
 * @time: 2020/3/10 17:12
 * @version: 1.0
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    private ResultInfo resultInfo = MySpring.getClassBean("com.travel.domain.ResultInfo");

    @Override//注册
    public boolean regist(User user) {
        //先根据用户名查询对象
        User findUser = userDao.findUserByUserName(user.getUsername());
        //判断用户名是否重复
        if(findUser != null){
            //获取返回值对象并封装失败信息为用户名已注册
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败,该用户名已被注册!");
            return false;
        }else {
            //设置用户的激活状态和激活码
            user.setStatus("N");
            user.setCode(UuidUtil.getUuid());

            //注册
            boolean flag = userDao.addUser(user);
            //设置
            if(flag){
                //发信息让顾客激活
//                String message = "<a href='http:192.168.1.105:8080/travel/User/active?code="+user.getCode()+"'>恭喜,你的账号已激活,请点此登录</a>";
//                try {
//                    new SMTPClient(message).sendMessage();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                resultInfo.setFlag(true);
                resultInfo.setErrorMsg("");
            }else {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("注册失败,请刷新页面重新注册!");
            }
            return flag;
        }
    }

    @Override
    public boolean activeUser(String code) {
        //先根据激活码查询用户
        User user = userDao.findUserByCode(code);
        if(user != null) {
            //如果用户存在就激活
            boolean activeUser = userDao.activeUser(user);
            return activeUser;
        }
        return false;
    }

    @Override
    public User userLogin(User user) {
        //判断用户名是否存在
        User userByUserName = userDao.findUserByUserName(user.getUsername());
        if(userByUserName == null){
            resultInfo.setErrorMsg("该账号不存在,请<a href=\"register.html\">注册</a>账号");
            return null;
        }
        //根据用户名查询用户的激活状态
        //进行登录
        User userLogin = userDao.userLogin(user.getUsername(), user.getPassword());
        resultInfo.setErrorMsg("您的密码错误");
        return userLogin;
    }

}
