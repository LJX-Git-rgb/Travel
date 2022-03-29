package com.travel.dao.impl;

import com.travel.dao.RouteDao;
import com.travel.domain.*;
import com.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName RouteDaoImpl
 * @description:
 * @author: LJX
 * @time: 2020/3/24 9:24
 * @version: 1.0
 */
public class RouteDaoImpl implements RouteDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override//计算查询到的总记录数
    public int findTotalCount(int cid,String rname) {
        StringBuilder sql = new StringBuilder("select count(*) from tab_route where 1=1");
        List list = new ArrayList<>();
        if(cid != 0){
            sql.append(" and cid = ?");
            list.add(cid);
        }
        if(rname != null && !rname.equals("null")){
            sql.append(" and rname like ?");
            list.add("%" +rname+ "%");
        }

        Integer integer = jdbcTemplate.queryForObject(sql.toString(), Integer.class, list.toArray());
        return integer;
    }

    @Override//分页查询
    public List<Route> findByPage(int start, int rows, int cid,String rname) {
        StringBuilder sql = new StringBuilder("select * from tab_route where 1=1 ");
        List list = new ArrayList<>();
        if(cid != 0){
            sql.append(" and cid = ?");
            list.add(cid);
        }
        if(rname != null && !rname.equals("null")){
            sql.append(" and rname like ?");
            list.add("%" +rname+ "%");
        }
        sql.append(" limit ? , ? ");
        list.add(start);
        list.add(rows);

        List<Route> query = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<Route>(Route.class),list.toArray());
        return query;
    }

    @Override//查询单个旅游路线的信息
    public Route findOne(int rid) {
        Route route = null;
        String sql = "select * from tab_route where rid = ?";
        try {
            route = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return route;
    }

    @Override//查询商家信息
    public Seller findSeller(int sid) {
        String sql = "select * from tab_seller where sid = ?";
        Seller seller = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Seller.class), sid);
        return seller;
    }

    @Override//查询路线应用的图片信息
    public List<RouteImg> findImgs(int rid) {
        List<RouteImg> routeImgs = null;
        String sql = "select * from tab_route_img where rid = ?";
        try {
            routeImgs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RouteImg.class), rid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return routeImgs;
    }

    @Override//查询收藏信息
    public Favorite findFavoriteByRidAndUid(int rid, int uid) {
        Favorite favorite = null;
        String sql = "select * from tab_favorite where rid = ? and uid = ?";
        try {
            favorite = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class), rid, uid);
        }catch (Exception e){
//            e.printStackTrace();
        }
        return favorite;
    }

    @Override//查询收藏次数
    public int findFavoriteCount(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";
        Integer integer = jdbcTemplate.queryForObject(sql, Integer.class, rid);
        return integer;
    }

    @Override//加入收藏
    public boolean saveFavorite(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        int i = jdbcTemplate.update(sql, rid, new Date(), uid);
        return i > 0;
    }
}
