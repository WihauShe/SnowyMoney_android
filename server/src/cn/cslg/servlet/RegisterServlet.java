package cn.cslg.servlet;

import cn.cslg.service.IUserService;
import cn.cslg.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterServlet",urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("userId");
        String password = req.getParameter("userPass");
        String name = req.getParameter("userName");
        String sex = req.getParameter("userSex");
        String age = req.getParameter("userAge");
        String school = req.getParameter("userSchool");

        IUserService userService = new UserService();
        boolean result = userService.registerUser(id,name,sex,Integer.parseInt(age),school,password);
        resp.getWriter().print(result);
    }
}
