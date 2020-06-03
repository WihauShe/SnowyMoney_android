package cn.cslg.service;

import cn.cslg.dao.ITradeDao;
import cn.cslg.dao.TradeDao;
import cn.cslg.entity.Trade;

public class TradeService  implements ITradeService {
    private ITradeDao tradeDao = new TradeDao();
    @Override
    public void insert(String tradeNo, String uid, String total) {
        Trade trade = new Trade(tradeNo,uid,total);
        tradeDao.insert(trade);
    }

    @Override
    public Trade getTradeByNo(String tradeNo) {
        return tradeDao.getTradeByNo(tradeNo);
    }

    @Override
    public void updateStatus(String tradeNo) {
        tradeDao.updateStatus(tradeNo);
    }

    @Override
    public void deleteTrade(String tradeNo) {
        tradeDao.deleteTrade(tradeNo);
    }
}
