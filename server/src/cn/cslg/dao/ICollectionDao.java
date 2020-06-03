package cn.cslg.dao;

import cn.cslg.entity.Collection;

import java.util.List;

public interface ICollectionDao {
    void insert(Collection collection);
    Collection getCollection(int jid,String uid);
    List<Collection> getCollectionsByUser(String uid);
    void deleteById(int id);
    void deleteByJid(int jid);
}
