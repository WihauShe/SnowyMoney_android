package cn.cslg.servlet;

import cn.cslg.entity.GridBean;
import cn.cslg.entity.Report;
import cn.cslg.service.IReportService;
import cn.cslg.service.ReportService;
import cn.cslg.util.JsonUtil;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@WebServlet(name = "ReportServlet",urlPatterns = "/reportServlet")
public class ReportServlet extends HttpServlet {
    private IReportService reportService = new ReportService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        req.getRequestDispatcher("WEB-INF/jsp/reports.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        String method = req.getParameter("method");
        Class c = ReportServlet.class;
        try{
            Method m = c.getMethod(method,HttpServletRequest.class,HttpServletResponse.class);
            m.invoke(this,req,resp);
        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
    }

    public void getReportByCondition(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
        String page = req.getParameter("page");
        String pageSize = req.getParameter("rows");
        int from = (Integer.parseInt(page)-1)*Integer.parseInt(pageSize);

        List<Report> list = reportService.findReportByCondition(from,Integer.parseInt(pageSize));
        int total = reportService.getCountByCondition(from,Integer.parseInt(pageSize));
        GridBean<Report> g = new GridBean<>(total,list);
        resp.getWriter().print(JsonUtil.getJsonString4JavaPOJO(g));
    }
}
