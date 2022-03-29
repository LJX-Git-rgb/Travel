package com.travel.service.impl;

import com.travel.dao.RouteDao;
import com.travel.dao.UserDao;
import com.travel.dao.impl.RouteDaoImpl;
import com.travel.dao.impl.UserDaoImpl;
import com.travel.domain.Favorite;
import com.travel.domain.PageBean;
import com.travel.domain.Route;
import com.travel.domain.User;
import com.travel.service.RouteService;
import java.util.Date;
import java.util.List;

/**
 * @ClassName RouteServiceImpl
 * @description:
 * @author: LJX
 * @time: 2020/3/24 9:24
 * @version: 1.0
 */
public class RouteServiceImpl implements RouteService {
    RouteDao routeDao = new RouteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int currentPage, int rows, int cid,String rname) {
        PageBean<Route> pb = new PageBean<>();
        //计算总数和页面开始数
        int totalCount = routeDao.findTotalCount(cid,rname);
        int start = (currentPage - 1) * rows;

        //查询list
        List<Route> list = routeDao.findByPage(start, rows, cid,rname);

        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        pb.setTotalCount(totalCount);
        pb.setPageList(list);
        pb.setTotalPage(totalCount%rows == 0  ? totalCount/rows : totalCount/rows + 1);
        return pb;
    }

    @Override
    public Route findOne(int rid) {
        Route route = new Route();
        route = routeDao.findOne(rid);
        int sid = route.getSid();
        route.setSeller(routeDao.findSeller(sid));
        route.setRouteImgList(routeDao.findImgs(rid));
        route.setCount(routeDao.findFavoriteCount(rid));
        return route;
    }

    @Override
    public boolean favorite(int rid, int uid) {
        return  routeDao.saveFavorite(rid,uid);
    }

    @Override
    public boolean isFavorite(int rid, int uid) {
        Favorite fav = routeDao.findFavoriteByRidAndUid(rid, uid);
        return fav != null;
    }
}
