package mech.mania.engine.domain.model;

public class PlayerInfo {
    String ipAddr;
    int turnJoined;

    public PlayerInfo(String ipAddr, int turnJoined) {
        this.ipAddr = ipAddr;
        this.turnJoined = turnJoined;
    }

    public int getTurnJoined() {
        return turnJoined;
    }

    public String getIpAddr() {
        return ipAddr;
    }
}
