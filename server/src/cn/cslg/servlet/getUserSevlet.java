package cn.cslg.servlet;

import cn.cslg.service.IUserService;
import cn.cslg.service.UserService;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "getUserServlet",urlPatterns = "/getUser")
public class getUserSevlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("userId");
        IUserService userService = new UserService();
        Map<String,String> userInfo = userService.getUserInfo(id);
        String result = JSON.toJSONString(userInfo);
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }
}
