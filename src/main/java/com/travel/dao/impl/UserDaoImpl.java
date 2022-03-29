package com.travel.dao.impl;

import com.travel.dao.UserDao;
import com.travel.domain.User;
import com.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @ClassName UserDaoImpl
 * @description:
 * @author: LJX
 * @time: 2020/3/10 17:12
 * @version: 1.0
 */
public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override//根据用户名查询用户
    public User findUserByUserName(String userName) {
        //查询不到的话会报异常,中断运行,所以trycatch一下
        User user = null;
        try {
            String sql = "select * from tab_user where username = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), userName);
        }catch (Exception e){ }
        return user;
    }

    @Override//根据激活码查询用户
    public User findUserByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), code);
        }catch (Exception e){

        }
        return user;
    }

    @Override//添加用户
    public boolean addUser(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code)" +
                        "values(?,?,?,?,?,?,?,?,?)";
        int i = jdbcTemplate.update(
                sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode()
        );
        return i>0;
    }

    @Override//激活用户
    public boolean activeUser(User user) {
        String sql = "update tab_user set status = ? where code = ?";
        int active = jdbcTemplate.update(sql,"Y",user.getCode());
        return active != 0;
    }

    @Override//用户登录
    public User userLogin(String userName,String userPwd) {
        User user = null;
        try {
            String sql = "select * from tab_user where username = ? and password = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), userName,userPwd);
        }catch (Exception e){

        }
        return user;
    }

    @Override
    public User findUserByUid(int uid) {
        User user = null;
        String sql = "select * from tab_user where uid = ?";
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), uid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
