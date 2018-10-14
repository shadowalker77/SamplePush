package ir.ayantech.pushnotification.networking.model;

/**
 * Created by shadoWalker on 3/26/18.
 */

public class AppExtraInfo extends ExtraInfo {
    private String HookToken;
    private String HookApplicationName = "TrafficFines";

    public AppExtraInfo(String hookToken) {
        HookToken = hookToken;
    }
}
