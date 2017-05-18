package project.modelClass;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Created by Mohammad-Ghouri on 3/2/17.
 */

public class ServerCLBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String callLodId;
    private String userRefId;
    private List<CallLogsBean> logsBean;
    private Date createdDate;
    private int transactionId;

    public String getCallLodId() {
        return callLodId;
    }

    public void setCallLodId(String callLodId) {
        this.callLodId = callLodId;
    }

    public String getUserRefId() {
        return userRefId;
    }

    public void setUserRefId(String userRefId) {
        this.userRefId = userRefId;
    }

    public List<CallLogsBean> getLogsBean() {
        return logsBean;
    }

    public void setLogsBean(List<CallLogsBean> logsBean) {
        this.logsBean = logsBean;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public ServerCLBean(String userRefId, List<CallLogsBean> logsBean, Date createdDate, int transactionId) {

        this.userRefId = userRefId;
        this.logsBean = logsBean;
        this.createdDate = createdDate;
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "{\"callLodId\":" + "\"" + callLodId
                + "\"" + ", \"userRefId\":" + "\"" + userRefId
                + "\"" + ", \"logsBean\":" + "" + logsBean
                + "" + ", \"createdDate\":" + "\"" + createdDate
                + "\"" + ", \"transactionId\":" + "\"" + transactionId
                + "\"" + "}";
    }
}
