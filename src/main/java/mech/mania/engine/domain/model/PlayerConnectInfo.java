package mech.mania.engine.domain.model;

public class PlayerConnectInfo {

    private String ipAddr;

    public PlayerConnectInfo(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getIpAddr() {
        return ipAddr;
    }
}
