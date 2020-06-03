package cn.cslg.servlet;

import cn.cslg.service.IUserService;
import cn.cslg.service.UserService;
import com.alibaba.druid.support.json.JSONWriter;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet",urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        IUserService userService = new UserService();
        String[] logInfo = userService.getLoginInfo(userId);
        String result = JSON.toJSONString(logInfo);
        resp.getWriter().write(result);
    }
}
