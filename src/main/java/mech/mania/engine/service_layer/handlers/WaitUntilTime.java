
package mech.mania.engine.service_layer.handlers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.domain.messages.CommandWaitUntilTime;
import mech.mania.engine.service_layer.AbstractUnitOfWork;

import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.SECONDS;

public class WaitUntilTime extends CommandHandler {
    public WaitUntilTime(AbstractUnitOfWork uow) {
        super(uow);
    }

    @Override
    public void handle(Command command) {
        Instant waitUntil = ((CommandWaitUntilTime) command).getWaitUntil();

        // sleep this thread until time
        try {
            Instant now = Instant.now();
            Thread.sleep(MILLIS.between(now, waitUntil));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
