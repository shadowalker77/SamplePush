package ir.ayantech.pushnotification.networking.model;

/**
 * Created by Administrator on 6/10/2017.
 */

public class PNStatusModel {
    private String Code;
    private String Description;

    public PNStatusModel(String code, String description) {
        Code = code;
        Description = description;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
