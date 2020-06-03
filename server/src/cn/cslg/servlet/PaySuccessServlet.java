package cn.cslg.servlet;

import cn.cslg.entity.Trade;
import cn.cslg.service.ITradeService;
import cn.cslg.service.IUserService;
import cn.cslg.service.TradeService;
import cn.cslg.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PaySuccessServlet",urlPatterns = "/paySuccess")
public class PaySuccessServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String tradeNo = req.getParameter("tradeNo");
        System.out.println(tradeNo);
        ITradeService tradeService = new TradeService();
        Trade trade = tradeService.getTradeByNo(tradeNo);
        String uid = trade.getUid();
        String total = trade.getTotal();
        int flag = (int)(Double.parseDouble(total)*10);
        IUserService userService = new UserService();
        boolean result = userService.raiseUserBalance(uid,flag);
        tradeService.updateStatus(tradeNo);
        resp.getWriter().print(result);
    }
}
