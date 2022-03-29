package com.travel.service;

import com.travel.domain.Favorite;
import com.travel.domain.PageBean;
import com.travel.domain.Route;

import java.util.List;

/**
 * @ClassName RouteService
 * @description:
 * @author: LJX
 * @time: 2020/3/24 9:23
 * @version: 1.0
 */
public interface RouteService {

    PageBean<Route> pageQuery(int currentPage, int rows, int cid,String rname);

    Route findOne(int rid);

    boolean favorite(int rid, int uid);

    boolean isFavorite(int rid, int uid);
}
