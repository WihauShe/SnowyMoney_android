package cn.cslg.dao;

import cn.cslg.entity.Trade;

public interface ITradeDao {
    void insert(Trade trade);
    Trade getTradeByNo(String tradeNo);
    void updateStatus(String tradeNo);
    void deleteTrade(String tradeNo);
}
