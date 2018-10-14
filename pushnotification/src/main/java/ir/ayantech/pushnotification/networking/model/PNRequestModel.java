package ir.ayantech.pushnotification.networking.model;

import com.google.gson.Gson;

/**
 * Created by Administrator on 6/10/2017.
 */

public class PNRequestModel {
    private PNInputModel Parameters;
    private PNIdentityModel Identity;

    public PNRequestModel(PNInputModel Parameters) {
        this();
        this.Parameters = Parameters;
    }

    public PNRequestModel() {
        Identity = new PNIdentityModel();
    }

    public PNIdentityModel getIdentity() {
        return Identity;
    }

    public void setIdentity(PNIdentityModel identity) {
        Identity = identity;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
