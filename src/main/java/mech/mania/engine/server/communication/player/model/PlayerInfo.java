package mech.mania.engine.server.communication.player.model;

import java.util.Date;

public class PlayerInfo {
    String ipAddr;
    // TODO: decide on whether this should be Date or first turn
    Date loginTime;
    int turnJoined;

    public PlayerInfo(String ipAddr, Date loginTime, int turnJoined) {
        this.ipAddr = ipAddr;
        this.loginTime = loginTime;
        this.turnJoined = turnJoined;
    }

    public int getTurnJoined() {
        return turnJoined;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public Date getLoginTime() {
        return loginTime;
    }
}
