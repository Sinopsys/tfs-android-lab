package tfs.converter;

/**
 * Created by sinopsys on 9/12/18.
 */

import org.json.JSONException;
import org.json.JSONObject;

/**
 * POJO country class.
 */
public class Country {
    private String id;
    private String currencyId;
    private String currencyName;
    private String currencySymbol;
    private String name;


    // Factory method to construct a Country from JSONObject.
    //
    public static Country fromJSONObject(JSONObject jo) throws JSONException {
        return new Country(
                jo.optString("id"),
                jo.optString("currencyId"),
                jo.optString("currencyName"),
                jo.optString("currencySymbol"),
                jo.optString("name")
        );
    }

    public Country(String id, String currencyId, String currencyName, String currencySymbol, String name) {
        this.id = id;
        this.currencyId = currencyId;
        this.currencyName = currencyName;
        this.currencySymbol = currencySymbol;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


// EOF
