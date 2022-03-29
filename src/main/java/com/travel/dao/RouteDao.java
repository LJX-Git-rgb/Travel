package com.travel.dao;

import com.travel.domain.*;

import java.util.Date;
import java.util.List;

/**
 * @ClassName RouteDaoImpl
 * @description:
 * @author: LJX
 * @time: 2020/3/24 9:24
 * @version: 1.0
 */
public interface RouteDao {

    //根据cid查询总记录数
    int findTotalCount(int cid,String rname);

    //根据cid,start,rows查询分页的记录
    List<Route> findByPage(int start, int rows, int cid,String rname);

    Route findOne(int rid);

    Seller findSeller(int sid);

    List<RouteImg> findImgs(int rid);

    Favorite findFavoriteByRidAndUid(int rid, int uid);

    int findFavoriteCount(int rid);

    boolean saveFavorite(int rid, int uid);
}
