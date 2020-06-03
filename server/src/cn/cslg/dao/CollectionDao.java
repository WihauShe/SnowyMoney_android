package cn.cslg.dao;

import cn.cslg.entity.Collection;
import cn.cslg.util.DruidUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollectionDao implements ICollectionDao{

    @Override
    public void insert(Collection collection) {
        QueryRunner qr = new QueryRunner();
        String sql = "insert into collection(jid,uid) values(?,?)";
        Object[] params = {collection.getJid(),collection.getUid()};
        try{
            qr.update(DruidUtil.getConn(),sql,params);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Collection getCollection(int jid, String uid) {
        QueryRunner qr = new QueryRunner();
        String sql = "select * from collection where jid = ? and uid = ?";
        Collection collection = null;
        Object[] params = {jid,uid};
        try{
            collection = qr.query(DruidUtil.getConn(),sql,new BeanHandler<>(Collection.class),params);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return collection;
    }

    @Override
    public List<Collection> getCollectionsByUser(String uid) {
        QueryRunner qr = new QueryRunner();
        String sql = "select * from collection where uid = ?";
        List<Collection> collections = new ArrayList<>();
        try {
            collections = qr.query(DruidUtil.getConn(),sql,new BeanListHandler<>(Collection.class),uid);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return collections;
    }

    @Override
    public void deleteById(int id) {
        QueryRunner qr = new QueryRunner();
        String sql = "delete from collection where id = ?";
        try {
            qr.update(DruidUtil.getConn(),sql,id);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByJid(int jid) {
        QueryRunner qr = new QueryRunner();
        String sql = "delete from collection where jid = ?";
        try {
            qr.update(DruidUtil.getConn(),sql,jid);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
