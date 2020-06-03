package cn.cslg.servlet;

import cn.cslg.service.IJobService;
import cn.cslg.service.JobService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "deleteJobServlet",urlPatterns = "/deleteJob")
public class deleteJobServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jobId = req.getParameter("jobId");
        IJobService jobService = new JobService();
        jobService.deleteById(Integer.parseInt(jobId));
        jobService.deleteCollection(jobId);
    }
}
