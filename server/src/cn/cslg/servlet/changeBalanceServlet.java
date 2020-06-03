package cn.cslg.servlet;

import cn.cslg.dao.IAccountDao;
import cn.cslg.dao.IJobDao;
import cn.cslg.dao.JobDao;
import cn.cslg.service.IJobService;
import cn.cslg.service.IUserService;
import cn.cslg.service.JobService;
import cn.cslg.service.UserService;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "changeBalanceServlet",urlPatterns = "/changeBalance")
public class changeBalanceServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IUserService userService = new UserService();
        String userId = req.getParameter("userId");
        String flag = req.getParameter("flag");
        boolean res;
        IJobService jobService = new JobService();
        String jobId = req.getParameter("jobId");
        Map<String, Boolean> map = new HashMap<>();
        res = userService.deductUserBalance(userId, Integer.parseInt(flag));
        boolean isExist = jobService.isCollectionExist(jobId, userId);
        map.put("result", res);
        map.put("exist", isExist);
        String result = JSON.toJSONString(map);
        resp.getWriter().print(result);
    }
}
