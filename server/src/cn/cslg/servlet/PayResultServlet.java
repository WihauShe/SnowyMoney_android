package cn.cslg.servlet;

import cn.cslg.alipay.AlipayConfig;
import cn.cslg.entity.Trade;
import cn.cslg.service.ITradeService;
import cn.cslg.service.IUserService;
import cn.cslg.service.TradeService;
import cn.cslg.service.UserService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@WebServlet(name = "PayResultServlet",urlPatterns = "/payresult")
public class PayResultServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("payServlet");
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = req.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);

        }

        boolean signVerified = false; //调用SDK验证签名
        try{
            signVerified= AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset,AlipayConfig.sign_type);
            if(signVerified){
                String tradeNo = req.getParameter("out_trade_no");
                ITradeService tradeService = new TradeService();
                Trade trade = tradeService.getTradeByNo(tradeNo);
                String uid = trade.getUid();
                String total = trade.getTotal();
                int flag = (int)(Double.parseDouble(total)*10);
                IUserService userService = new UserService();
                userService.raiseUserBalance(uid,flag);
                tradeService.updateStatus(tradeNo);
            }else {
                System.out.println("verified fail!!!");
            }
        }catch (AlipayApiException  e){
            e.printStackTrace();
        }


    }
}
