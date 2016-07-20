package io.vertx.example.dto;

/**
 * Created by manishk on 7/19/16.
 */
public class DevicePolicy {

    private String easDeviceId;

    private String deviceTpe;

    public String getEasDeviceId() {
        return easDeviceId;
    }

    public DevicePolicy setEasDeviceId(String easDeviceId) {
        this.easDeviceId = easDeviceId;
        return this;
    }

    public String getDeviceTpe() {
        return deviceTpe;
    }

    public DevicePolicy setDeviceTpe(String deviceTpe) {
        this.deviceTpe = deviceTpe;
        return this;
    }
}
