package cn.cslg.dao;

import cn.cslg.entity.Job;
import cn.cslg.util.DruidUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobDao implements IJobDao {
    @Override
    public boolean insert(Job job) {
        QueryRunner qr = new QueryRunner();
        String sql = "insert into job(category,salary,duration,position,demands,date,flag,publisherName,publisherId) values(?,?,?,?,?,?,?,?,?)";
        Object[] params = {job.getCategory(),job.getSalary(),job.getDuration(),job.getPosition(),job.getDemands(),job.getDate(),job.getFlag(),job.getPublisherName(),job.getPublisherId()};
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
    public void deleteById(int id) {
        QueryRunner qr = new QueryRunner();
        String sql = "delete from job where id = ?";
        try {
            qr.update(DruidUtil.getConn(),sql,id);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Job> findLatestJobs() {
        QueryRunner qr = new QueryRunner();
        String sql = "select * from job order by date desc limit 10";
        List<Job> jobs = new ArrayList<>();
        try {
            jobs = qr.query(DruidUtil.getConn(),sql,new BeanListHandler<>(Job.class));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jobs;
    }

    @Override
    public List<Job> findJobsByCategory(String category) {
        QueryRunner qr = new QueryRunner();
        String sql = "select * from job where category = ?";
        List<Job> jobs = new ArrayList<>();
        try{
            jobs = qr.query(DruidUtil.getConn(),sql,new BeanListHandler<>(Job.class),category);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jobs;
    }

    @Override
    public List<Job> findJobsByPublisher(String publisherId) {
        QueryRunner qr = new QueryRunner();
        String sql = "select * from job where publisherId = ?";
        List<Job> jobs = new ArrayList<>();
        try {
            jobs = qr.query(DruidUtil.getConn(),sql,new BeanListHandler<>(Job.class),publisherId);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jobs;
    }

    @Override
    public List<Job> findJobsByConstraint(String constraint) {
        QueryRunner qr = new QueryRunner();
        String sql = "select * from job where concat(category,position,demands) like concat('%',?,'%')";
        List<Job> jobs = new ArrayList<>();
        try {
            jobs = qr.query(DruidUtil.getConn(),sql,new BeanListHandler<>(Job.class),constraint);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jobs;
    }

    @Override
    public Job findJobById(int id) {
        QueryRunner qr = new QueryRunner();
        String sql = "select * from job where id = ?";
        Job job = null;
        try{
            job = qr.query(DruidUtil.getConn(),sql,new BeanHandler<>(Job.class),id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return job;
    }
}
