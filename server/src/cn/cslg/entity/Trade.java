package cn.cslg.entity;

public class Trade {
    private String tradeNo;
    private String uid;
    private String total;
    private int status;

    public Trade() {
    }
    public Trade(String tradeNo, String uid, String total) {
        this.tradeNo = tradeNo;
        this.uid = uid;
        this.total = total;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
