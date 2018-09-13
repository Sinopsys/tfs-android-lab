package tfs.converter;

/**
 * Created by sinopsys on 9/12/18.
 */

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * POJO currency class.
 */
public class Currency implements Parcelable {
    private String id;
    private String currencyName;
    private String currencySymbol;

    private Currency(Parcel in) {
        id = in.readString();
        currencyName = in.readString();
        currencySymbol = in.readString();
    }

    public static final Creator<Currency> CREATOR = new Creator<Currency>() {
        @Override
        public Currency createFromParcel(Parcel in) {
            return new Currency(in);
        }

        @Override
        public Currency[] newArray(int size) {
            return new Currency[size];
        }
    };

    // Factory method to construct a Currency from JSONObject.
    //
    public static Currency fromJSONObject(JSONObject jo) throws JSONException {
        return new Currency(
                jo.optString("id"),
                jo.optString("currencyName"),
                jo.optString("currencySymbol")
        );
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(currencyName);
        dest.writeString(currencySymbol);
    }
}


// EOF
