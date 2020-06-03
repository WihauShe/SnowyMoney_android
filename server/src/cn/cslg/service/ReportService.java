package cn.cslg.service;

import cn.cslg.dao.IReportDao;
import cn.cslg.dao.ReportDao;
import cn.cslg.entity.Report;

import java.util.List;

public class ReportService implements IReportService{
    private IReportDao reportDao = new ReportDao();

    @Override
    public boolean addReport(String ruser, String reason, String evidence) {
        Report report = new Report(ruser,reason,evidence);
        return reportDao.insert(report);
    }

    @Override
    public boolean deleteReport(int id) {
        return reportDao.deleteById(id);
    }

    @Override
    public List<Report> getAllReports() {
        return reportDao.findAllReport();
    }

    @Override
    public List<Report> findReportByCondition(int from, int rows) {
        return reportDao.findReportByCondition(from,rows);
    }

    @Override
    public int getCountByCondition(int from, int rows) {
        return reportDao.getCountByCondition(from,rows);
    }
}
