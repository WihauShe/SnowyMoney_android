package cn.cslg.dao;

import cn.cslg.entity.Report;

import java.util.List;

public interface IReportDao {
    boolean insert(Report report);
    boolean deleteById(int id);
    List<Report> findAllReport();
    List<Report> findReportByCondition(int from,int rows);
    int getCountByCondition(int from,int rows);
}
