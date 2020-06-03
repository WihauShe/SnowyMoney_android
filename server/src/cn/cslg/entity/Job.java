package cn.cslg.entity;

public class Job {
    private int id;
    private String category;
    private String salary;
    private String duration;
    private String demands;
    private String position;
    private String date;
    private int flag;
    private String publisherName;
    private String publisherId;

    public Job() {
    }

    public Job(int id, String category, String salary, String duration, String demands, String position, String date, int flag, String publisherName, String publisherId) {
        this.id = id;
        this.category = category;
        this.salary = salary;
        this.duration = duration;
        this.demands = demands;
        this.position = position;
        this.date = date;
        this.publisherName = publisherName;
        this.publisherId = publisherId;
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDemands() {
        return demands;
    }

    public void setDemands(String demands) {
        this.demands = demands;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }


}
