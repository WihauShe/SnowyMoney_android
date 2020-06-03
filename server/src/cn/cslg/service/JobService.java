package cn.cslg.service;

import cn.cslg.dao.CollectionDao;
import cn.cslg.dao.ICollectionDao;
import cn.cslg.dao.IJobDao;
import cn.cslg.dao.JobDao;
import cn.cslg.entity.Collection;
import cn.cslg.entity.Job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobService implements IJobService{

    @Override
    public boolean addJob(Job job) {
        IJobDao jobDao = new JobDao();
        return jobDao.insert(job);
    }

    @Override
    public void deleteById(int id) {
        IJobDao jobDao = new JobDao();
        jobDao.deleteById(id);
        ICollectionDao collectionDao = new CollectionDao();
        collectionDao.deleteByJid(id);
    }

    @Override
    public Job findJobById(int id) {
        IJobDao jobDao = new JobDao();
        return jobDao.findJobById(id);
    }

    @Override
    public List<Job> getLatestJobs() {
        IJobDao jobDao = new JobDao();
        return jobDao.findLatestJobs();
    }

    @Override
    public List<Job> getTeachJobs() {
        IJobDao jobDao = new JobDao();
        return jobDao.findJobsByCategory("家教");
    }

    @Override
    public List<Job> getLeafletJobs() {
        IJobDao jobDao = new JobDao();
        return jobDao.findJobsByCategory("传单");
    }

    @Override
    public List<Job> getCallJobs() {
        IJobDao jobDao = new JobDao();
        return jobDao.findJobsByCategory("话务员");
    }

    @Override
    public List<Job> getCasualJobs() {
        IJobDao jobDao = new JobDao();
        return jobDao.findJobsByCategory("临时工");
    }

    @Override
    public List<Job> getOtherJobs() {
        IJobDao jobDao = new JobDao();
        return jobDao.findJobsByCategory("其他");
    }

    @Override
    public List<Job> getIssuedJobs(String publisherId) {
        IJobDao jobDao = new JobDao();
        return jobDao.findJobsByPublisher(publisherId);
    }

    @Override
    public List<Job> getConstrainedJobs(String constraint) {
        IJobDao jobDao = new JobDao();
        return jobDao.findJobsByConstraint(constraint);
    }

    @Override
    public void addCollection(String jid, String uid) {
        ICollectionDao collectionDao = new CollectionDao();
        Collection collection = new Collection(Integer.parseInt(jid),uid);
        collectionDao.insert(collection);
    }

    @Override
    public void deleteCollection(String id) {
        ICollectionDao collectionDao = new CollectionDao();
        collectionDao.deleteById(Integer.parseInt(id));
    }

    @Override
    public boolean isCollectionExist(String jid, String uid) {
        ICollectionDao collectionDao = new CollectionDao();
        Collection collection = collectionDao.getCollection(Integer.parseInt(jid),uid);
        return collection != null;
    }

    @Override
    public Map<String, Job> getCollections(String id) {
        ICollectionDao collectionDao = new CollectionDao();
        IJobDao jobDao = new JobDao();
        Map<String,Job> map = new HashMap<>();
        List<Collection> collections = collectionDao.getCollectionsByUser(id);
        for(Collection collection : collections){
            Job job = jobDao.findJobById(collection.getJid());
            map.put(String.valueOf(collection.getId()),job);
        }
        return map;
    }
}
