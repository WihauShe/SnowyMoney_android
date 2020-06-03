package cn.cslg.service;

import cn.cslg.entity.Trade;

public interface ITradeService {
    void insert(String tradeNo,String uid,String total);
    Trade getTradeByNo(String tradeNo);
    void updateStatus(String tradeNo);
    void deleteTrade(String tradeNo);
}
