package com.travel.service.impl;

import com.travel.dao.CategoryDao;
import com.travel.dao.impl.CategoryDaoImpl;
import com.travel.domain.Category;
import com.travel.domain.Route;
import com.travel.service.CategoryService;
import com.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @ClassName CategoryService
 * @description:
 * @author: LJX
 * @time: 2020/3/23 18:43
 * @version: 1.0
 */
public class CategoryServiceImpl implements CategoryService {
    CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAllType() {
        List<Category> allType = null;
        Jedis jedis = JedisUtil.getJedis();

        //jedis查询
        Set<Tuple> tuples = jedis.zrangeWithScores("category", 0, -1);

        //若没查到,从数据库查询再添加到redis中
        if(tuples == null || tuples.size() == 0){
            allType = categoryDao.findAllType();
            for (int i = 0; i < allType.size(); i++) {
                jedis.zadd("category",allType.get(i).getCid(), allType.get(i).getCname());
            }
        }else {
            //查到了
            allType = new ArrayList<Category>();
            for (Tuple tuple : tuples){
                String cname = tuple.getElement();
                int cid = (int) tuple.getScore();

                Category category = new Category();
                category.setCname(cname);
                category.setCid(cid);
                allType.add(category);
            }
        }
        jedis.close();
        return allType;
    }

    @Override
    public List<Route> findByCid() {
        List<Route> routeList = null;
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> tuples = jedis.zrangeWithScores("route", 0, -1);
        return routeList;
    }
}
