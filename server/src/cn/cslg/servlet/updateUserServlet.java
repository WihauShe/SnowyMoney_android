package cn.cslg.servlet;

import cn.cslg.service.IUserService;
import cn.cslg.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "updateUserServlet",urlPatterns = "/updateUser")
public class updateUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("userId");
        String field = req.getParameter("field");
        String value = req.getParameter("value");
        IUserService userService = new UserService();
        boolean result = userService.updateUserInfo(id,field,value);
        resp.getWriter().print(result);
    }
}
