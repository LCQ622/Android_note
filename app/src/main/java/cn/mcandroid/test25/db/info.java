package cn.mcandroid.test25.db;

public class info {
    private int id;
    private int Upid;
    private String msg;
    private String Upmsg;


    public int getUpid() {
        return Upid;
    }

    public void setUpid(int upid) {
        Upid = upid;
    }

    public String getUpmsg() {
        return Upmsg;
    }

    public void setUpmsg(String upmsg) {
        Upmsg = upmsg;
    }

    public info() {

    }

    public info(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }


    public int  getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    @Override
    public String toString() {
        return "info{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                '}';
    }
}
