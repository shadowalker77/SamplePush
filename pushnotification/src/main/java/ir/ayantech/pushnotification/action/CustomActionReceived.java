package ir.ayantech.pushnotification.action;

public interface CustomActionReceived {
    void onCustomActionReceived(String customActionName, String customInput);
}
