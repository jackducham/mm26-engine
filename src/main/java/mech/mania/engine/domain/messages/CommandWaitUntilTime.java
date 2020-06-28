package mech.mania.engine.domain.messages;

import java.time.Instant;

public class CommandWaitUntilTime implements Command {

    Instant waitUntil;

    public CommandWaitUntilTime(Instant waitUntil) {
        this.waitUntil = waitUntil;
    }

    public Instant getWaitUntil() {
        return waitUntil;
    }
}
