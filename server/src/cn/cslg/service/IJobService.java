package cn.cslg.service;

import cn.cslg.entity.Job;

import java.util.List;
import java.util.Map;

public interface IJobService {
    boolean addJob(Job job);
    void deleteById(int id);
    Job findJobById(int id);
    List<Job> getLatestJobs();
    List<Job> getTeachJobs();
    List<Job> getLeafletJobs();
    List<Job> getCallJobs();
    List<Job> getCasualJobs();
    List<Job> getOtherJobs();
    List<Job> getIssuedJobs(String publisherId);
    List<Job> getConstrainedJobs(String constraint);
    void addCollection(String jid,String uid);
    void deleteCollection(String id);
    boolean isCollectionExist(String jid,String uid);
    Map<String,Job> getCollections(String id);
}
