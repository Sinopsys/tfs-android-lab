package tfs.converter;

/**
 * Created by sinopsys on 9/12/18.
 */

import org.json.JSONException;
import org.json.JSONObject;

/**
 * POJO currency class.
 */
public class Currency {


    private String id;
    private String currencyName;
    private String currencySymbol;

    public Currency(String id, String currencyName, String currencySymbol) {
        this.id = id;
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    @Override
    public String toString() {
        return id;
    }
}


// EOF
