package au.com.lionslogistics.lionstv.model;

/**
 * Created by alex-daphne on 5/08/2017.
 * All rights reserved
 */

public class AuthPostRequest {
    private String username;
    private String password;
    private String deviceModel;
    private String androidVersion;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }
}
