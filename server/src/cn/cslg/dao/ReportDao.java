package cn.cslg.dao;

import cn.cslg.entity.Report;
import cn.cslg.util.DruidUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportDao implements IReportDao {
    @Override
    public boolean insert(Report report) {
        QueryRunner qr = new QueryRunner();
        String sql = "insert into report(ruser,reason,evidence) values(?,?,?)";
        Object[] params = {report.getRuser(),report.getReason(),report.getEvidence()};
        try {
            int flag = 0;
            flag = qr.update(DruidUtil.getConn(),sql,params);
            if(flag>0) return  true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  false;
    }

    @Override
    public boolean deleteById(int id) {
        QueryRunner qr = new QueryRunner();
        String sql = "delete from report where id = ?";
        try {
            int flag = 0;
            flag = qr.update(DruidUtil.getConn(),sql,id);
            if(flag>0) return  true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  false;
    }
    @Override
    public List<Report> findAllReport() {
        QueryRunner qr = new QueryRunner();
        String sql = "select * from report";
        List<Report> reports = new ArrayList<>();
        try{
            reports = qr.query(DruidUtil.getConn(),sql,new BeanListHandler<>(Report.class));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return reports;
    }

    @Override
    public List<Report> findReportByCondition(int from, int rows) {
        QueryRunner qr = new QueryRunner();
        String sql = "select * from report limit ?,?";
        List<Report> reports = new ArrayList<>();
        Object[] params = {from,rows};
        try{
            reports = qr.query(DruidUtil.getConn(),sql,new BeanListHandler<>(Report.class),params);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return reports;
    }

    @Override
    public int getCountByCondition(int from, int rows) {
        QueryRunner qr = new QueryRunner();
        String sql = "select count(0) from report limit ?,?";
        int count = 0;
        Object[] params = {from,rows};
        try{
            count = Integer.parseInt(qr.query(DruidUtil.getConn(),sql,new ScalarHandler<>(),params).toString());
        }catch (SQLException e){
            e.printStackTrace();
        }
        return count;
    }
}
