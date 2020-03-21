package mech.mania.engine.server.communication.player.model;

import java.util.Date;

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
