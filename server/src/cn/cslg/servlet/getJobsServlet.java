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
import java.util.List;

@WebServlet(name = "getJobsServlet",urlPatterns = "/getJobs")
public class getJobsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        IJobService jobService = new JobService();
        List<Job> jobs = null;
        switch (type){
            case "1":jobs = jobService.getLatestJobs();break;
            case "2":jobs = jobService.getTeachJobs();break;
            case "3":jobs = jobService.getLeafletJobs();break;
            case "4":jobs = jobService.getCallJobs();break;
            case "5":jobs = jobService.getCasualJobs();break;
            case "6":jobs = jobService.getOtherJobs();break;
            case "7":String publisherId = req.getParameter("publisherId");jobs = jobService.getIssuedJobs(publisherId);break;
            case "8":String constraint = req.getParameter("constraint");jobs = jobService.getConstrainedJobs(constraint);break;
        }

        String result = JSON.toJSONString(jobs);
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }
}
