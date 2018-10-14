package ir.ayantech.pushnotification.networking.model;

import com.google.gson.Gson;

/**
 * Created by Administrator on 6/10/2017.
 */

public class PNResponseModel {
    private PNStatusModel Status;

    public PNResponseModel(PNStatusModel status) {
        this.Status = status;
    }

    public PNStatusModel getStatus() {
        return Status;
    }

    public void setStatus(PNStatusModel status) {
        this.Status = status;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
