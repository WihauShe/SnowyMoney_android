package cn.cslg.servlet;

import cn.cslg.entity.Job;
import cn.cslg.service.IJobService;
import cn.cslg.service.IUserService;
import cn.cslg.service.JobService;
import cn.cslg.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(name = "addJobServlet",urlPatterns = "/addJob")
public class addJobServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Map<String,String[]> map = req.getParameterMap();
        Job job = new Job();
        try {
            BeanUtils.populate(job,map);
        }catch (IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
        IJobService jobService = new JobService();
        boolean res1 = jobService.addJob(job);
        IUserService userService = new UserService();
        boolean res2 = userService.deductUserBalance(job.getPublisherId(),1);
        resp.getWriter().print(res1&&res2);
    }
}
