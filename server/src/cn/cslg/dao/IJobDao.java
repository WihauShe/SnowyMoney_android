package cn.cslg.dao;

import cn.cslg.entity.Job;

import java.util.List;

public interface IJobDao {
    boolean insert(Job job);
    void deleteById(int id);
    List<Job> findLatestJobs();
    List<Job> findJobsByCategory(String category);
    List<Job> findJobsByPublisher(String publisherId);
    List<Job> findJobsByConstraint(String constraint);
    Job findJobById(int id);
}
