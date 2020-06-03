package cn.cslg.servlet;

import cn.cslg.dao.IReportDao;
import cn.cslg.service.IReportService;
import cn.cslg.service.IUserService;
import cn.cslg.service.ReportService;
import cn.cslg.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "deleteReportServlet",urlPatterns = "/deleteReport")
public class deleteReportServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reportId = req.getParameter("reportId");
        String ifPunish = req.getParameter("ifPunish");
        if(ifPunish.equals("true")){
            String userId = req.getParameter("userId");
            IUserService userService = new UserService();
            userService.deductUserCredit(userId);
        }
        IReportService reportService = new ReportService();
        boolean result = reportService.deleteReport(Integer.parseInt(reportId));
        resp.getWriter().print(true);
    }
}
