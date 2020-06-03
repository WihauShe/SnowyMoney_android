package cn.cslg.entity;

public class Report{
    private int id;
    private String ruser;
    private String reason;
    private String evidence;

    public Report() {
    }

    public Report(String ruser, String reason, String evidence) {
        this.ruser = ruser;
        this.reason = reason;
        this.evidence = evidence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRuser() {
        return ruser;
    }

    public void setRuser(String ruser) {
        this.ruser = ruser;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }
}
