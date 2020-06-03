package cn.cslg.service;

import cn.cslg.entity.Report;

import java.util.List;

public interface IReportService {
    boolean addReport(String ruser,String reason,String evidence);
    boolean deleteReport(int id);
    List<Report> getAllReports();
    List<Report> findReportByCondition(int from,int rows);
    int getCountByCondition(int from,int rows);
}
