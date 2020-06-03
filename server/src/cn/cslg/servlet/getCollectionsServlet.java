package cn.cslg.servlet;

import cn.cslg.entity.Job;
import cn.cslg.service.IJobService;
import cn.cslg.service.JobService;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "getCollectionsServlet",urlPatterns = "/getCollections")
public class getCollectionsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        IJobService jobService = new JobService();
        Map<String, Job> collections = jobService.getCollections(userId);
        String result = JSON.toJSONString(collections);
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }
}
