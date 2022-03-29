package com.travel.web.servlet;

import com.travel.domain.*;
import com.travel.service.CategoryService;
import com.travel.service.RouteService;
import com.travel.service.impl.CategoryServiceImpl;
import com.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.List;

@WebServlet("/Route/*")
public class RouteServlet extends BaseServlet {
    RouteService routeService = new RouteServiceImpl();
    ResultInfo resultInfo = new ResultInfo();

    //分页查询
    public void pageQuery(HttpServletRequest request,HttpServletResponse response){
        //获取参数
        String currentPageStr = request.getParameter("currentPage");
        String rowsStr = request.getParameter("rows");
        String cidStr = request.getParameter("cid");
        String rname = request.getParameter("rname");

        //当前页码默认为第一页,显示行数默认为5行,数据类别cid
        int currentPage = 1,rows = 5,cid = 1;

        //
        if(currentPageStr != null && currentPageStr.length() > 0){
            currentPage = Integer.parseInt(currentPageStr);
        }
        if(rowsStr != null && rowsStr.length() > 0){
            rows = Integer.parseInt(rowsStr);
        }
        if(cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }

        //调用service获取PageBean对象
        PageBean<Route> pageBean = routeService.pageQuery(currentPage, rows, cid,rname);
        //封装json字符串返回客户端
        writeValue(pageBean,response);
    }
    //根据rid查询一个旅游线路的详细信息
    public void findOneInfo(HttpServletRequest request,HttpServletResponse response){
        //获取rid
        int rid = 1;
        String ridStr = request.getParameter("rid");
        if(ridStr != null && !ridStr.equals(null) && ridStr.length() > 0){
            rid = Integer.parseInt(ridStr);
        }
        //调用service
        Route route = routeService.findOne(rid);
        //封装json返回客户端
        writeValue(route,response);
    }
    //收藏旅游路线
    public void favorite(HttpServletRequest request , HttpServletResponse response){
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        int uid;
        if(loginUser == null){
            return ;
        }else {
            uid = loginUser.getUid();
        }
        int rid = Integer.parseInt(request.getParameter("rid"));
        boolean favorite = routeService.favorite(rid,uid);
        writeValue(favorite,response);
    }
    //判断路线是否收藏
    public void isFavorite(HttpServletRequest request , HttpServletResponse response){
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        int uid;
        if(loginUser == null){
            uid = 0;
        }else {
            uid = loginUser.getUid();
        }
        int rid = Integer.parseInt(request.getParameter("rid"));
        boolean flag = routeService.isFavorite(rid, uid);
        writeValue(flag, response);
    }
}
