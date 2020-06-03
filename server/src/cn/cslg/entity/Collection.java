package cn.cslg.entity;

public class Collection {
    private int id;
    private int jid;
    private String uid;

    public Collection() {
    }

    public Collection(int jid, String uid) {
        this.jid = jid;
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJid() {
        return jid;
    }

    public void setJid(int jid) {
        this.jid = jid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
