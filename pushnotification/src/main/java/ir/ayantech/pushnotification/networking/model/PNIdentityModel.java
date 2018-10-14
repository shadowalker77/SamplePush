package ir.ayantech.pushnotification.networking.model;

/**
 * Created by shadoWalker on 1/19/18.
 */

public class PNIdentityModel {
    private String JsonWebToken = "eyJmZHQiOiIyMDE4LTAzLTIwVDE1OjMyOjAxLjgzMyIsInRkdCI6IjIwMjgtMDMtMjBUMTU6MzI6MDEuODMzIiwic2VhIjoxLCJwam4iOiJJbmZyYVB1c2hOb3RpZmljYXRpb24iLCJwcmEiOmZhbHNlLCJzaWQiOjEwNDg3OTF9.ZFB0cTdTVFMrZHR1NFNuSElFc3BkOHFuSk85c1FjNXFoYjRNWWt6TEYxemdoY2w2Tks2TkNvbWJpcU5lSWFSN2FHTm9OdGY2ZDdiM0xRNzBwUE9HR2VFaWw5K3NqNUI5ZGoxc1NTYmhmWmc9.eyJ2bHUiOiI1MTVBNjIzRENBODJCQjE4NzQwODg1NkE0MDVDMDMyQkY2ODAxQkM0QzM2QzJBOENGNUQyM0FEM0JDNDVGRjQ3In0=";

    public String getJsonWebToken() {
        return JsonWebToken;
    }

    public void setJsonWebToken(String jsonWebToken) {
        JsonWebToken = jsonWebToken;
    }
}
