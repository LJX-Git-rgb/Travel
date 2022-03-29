package com.travel.web.servlet;

import com.travel.dao.UserDao;
import com.travel.dao.impl.UserDaoImpl;
import com.travel.domain.ResultInfo;
import com.travel.domain.User;
import com.travel.service.UserService;
import com.travel.service.impl.UserServiceImpl;
import com.travel.util.MySpring;
import org.apache.commons.beanutils.BeanUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

    @WebServlet("/User/*")
    public class UserServlet extends BaseServlet {
        UserService userService = new UserServiceImpl();

        //用户退出
        protected void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //销毁session
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath() + "/login.html");
        }

        //用户登录
        protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //单例创建返回信息对象
            ResultInfo resultInfo = MySpring.getClassBean("com.travel.domain.ResultInfo");

            //接收验证码的值并与用户输入的验证码比较
            HttpSession session = request.getSession();
            String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
            session.removeAttribute("CHECKCODE_SERVER");

            //比较
            if(checkcode_server != null && checkcode_server.equalsIgnoreCase(request.getParameter("check"))) {
                Map<String, String[]> parameterMap = request.getParameterMap();
                User user = new User();

                //封装对象
                try {
                    BeanUtils.populate(user, parameterMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //调用服务
                User loginUser = userService.userLogin(user);
                if(loginUser != null){
                    session.setAttribute("loginUser",loginUser);
                    resultInfo.setFlag(true);
                    resultInfo.setErrorMsg("");
                }else{
                    resultInfo.setFlag(false);
                }
            }else{
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("登录失败,验证码错误!");
            }
            //序列化ResultInfo对象为json
            //写回客户端
            writeValue(resultInfo,response);
        }

        //登录后用户名显示
        protected void loginName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Object loginUser = request.getSession().getAttribute("loginUser");
            writeValue(loginUser,response);
        }

        //用户注册
        protected void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //单例创建信息返回的JavaBean对象
            ResultInfo resultInfo = MySpring.getClassBean("com.travel.domain.ResultInfo");

            //获取验证码
            HttpSession session = request.getSession();
            String checkCode = (String) session.getAttribute("CHECKCODE_SERVER");
            session.removeAttribute("CHECKCODE_SERVER");
            //验证码的验证
            if(checkCode != null) {
                if (!(checkCode.equalsIgnoreCase(request.getParameter("check")))) {
                    //如果验证错误,设置返回值为失败,返回信息为验证码错误
                    resultInfo.setFlag(false);
                    resultInfo.setErrorMsg("注册失败,验证码错误!");
                } else {
                    //获取数据
                    Map<String, String[]> userMap = request.getParameterMap();
                    //封装JavaBean对象
                    User user = new User();
                    try {
                        BeanUtils.populate(user, userMap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //调用service注册
                    userService.regist(user);
                }
            }
            //序列化ResultInfo对象为json
            writeValue(resultInfo,response);
        }

        //用户激活
        protected void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取激活码
        String code = request.getParameter("code");
        response.setHeader("contentType","text/html;charset=utf8");
        //判断激活码是否为空
        if(code != null){
            //进行激活
            boolean activeUser = userService.activeUser(code);
            if(activeUser){
                response.getWriter().write("<script>alert('激活成功')</script>");
                response.sendRedirect("http://localhost:8080/travel/login.html");
            }
            else {
                response.sendRedirect("http://localhost:8080/travel/register.html");
                response.getWriter().write("<script>alert('激活失败,请联系管理员')</script>");

            }
        }else{
            response.sendRedirect("http://localhost:8080/travel/register.html");
            response.getWriter().write("<script>alert('对不起,你未进行有效注册!')</script>");
        }
    }

        //判断用户是否登录
        public void isLogin(HttpServletRequest request,HttpServletResponse response){
            User loginUser = (User) request.getSession().getAttribute("loginUser");
            writeValue(loginUser != null,response);
        }
}
