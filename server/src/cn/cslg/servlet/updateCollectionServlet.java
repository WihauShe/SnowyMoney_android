package cn.cslg.servlet;

import cn.cslg.service.IJobService;
import cn.cslg.service.JobService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "updateCollectionServlet",urlPatterns = "/updateCollection")
public class updateCollectionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String collectionId = req.getParameter("id");
        IJobService jobService = new JobService();
        jobService.deleteCollection(collectionId);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jid = req.getParameter("jobId");
        String uid = req.getParameter("userId");
        IJobService jobService = new JobService();
        if(!jobService.isCollectionExist(jid,uid)) jobService.addCollection(jid,uid);
    }
}
