package cn.cslg.dao;

import cn.cslg.entity.Trade;
import cn.cslg.util.DruidUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class TradeDao implements ITradeDao {

    @Override
    public void insert(Trade trade) {
        QueryRunner qr = new QueryRunner();
        String sql = "insert into trade(tradeNo,uid,total) values(?,?,?)";
        Object[] params = {trade.getTradeNo(),trade.getUid(),trade.getTotal()};
        try{
            qr.update(DruidUtil.getConn(),sql,params);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Trade getTradeByNo(String tradeNo) {
        QueryRunner qr = new QueryRunner();
        String sql = "select * from trade where tradeNo = ?";
        Trade trade = null;
        try{
            trade = qr.query(DruidUtil.getConn(),sql,new BeanHandler<>(Trade.class),tradeNo);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return trade;
    }

    @Override
    public void updateStatus(String tradeNo) {
        QueryRunner qr = new QueryRunner();
        String sql = "update trade set status = 1 where tradeNo = ?";
        try{
            qr.update(DruidUtil.getConn(),sql,tradeNo);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTrade(String tradeNo) {
        QueryRunner qr = new QueryRunner();
        String sql = "delete from trade where tradeNo = ?";
        try{
            qr.update(DruidUtil.getConn(),sql,tradeNo);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
