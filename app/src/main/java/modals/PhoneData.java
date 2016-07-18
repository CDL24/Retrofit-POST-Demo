package modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by C.limbachiya on 7/13/2016.
 */
public class PhoneData {

    @SerializedName("mobile")
    @Expose
    public String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
