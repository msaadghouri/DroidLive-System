package project.modelClass;

import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Mohammad-Ghouri on 6/10/17.
 */

public class AccountsBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accountsId;
    private String userRefId;
    private ArrayList<JSONObject> accountsList;
    private Date createdDate;
    private int transactionId;

    public AccountsBean(String userRefId, ArrayList<JSONObject> accountsList, Date createdDate, int transactionId) {
        this.userRefId = userRefId;
        this.accountsList = accountsList;
        this.createdDate = createdDate;
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "{\"accountsId\":" + "\"" + accountsId
                + "\"" + ", \"userRefId\":" + "\"" + userRefId
                + "\"" + ", \"accountsList\":" + "" + accountsList
                + "" + ", \"createdDate\":" + "\"" + createdDate
                + "\"" + ", \"transactionId\":" + "\"" + transactionId
                + "\"" + "}";
    }
}
