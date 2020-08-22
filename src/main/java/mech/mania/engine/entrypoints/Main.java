package mech.mania.engine.entrypoints;

import mech.mania.engine.Bootstrap;
import mech.mania.engine.Config;
import mech.mania.engine.domain.messages.*;
import mech.mania.engine.service_layer.MessageBus;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.MILLIS;

@SpringBootApplication
public class Main {

    // default bootstrap argument is DatabaseAws (see Bootstrap.java)
    public static MessageBus bus = Bootstrap.bootstrap();

    @Bean
    public MessageBus bus() {
        return bus;
    }

    public static void main(String[] args) {
        // Take infra port as first arg
        String infraPort = Config.getInfraPort();
        if(args.length > 0) infraPort = args[0];

        // Take visualizer port as second arg
        String visPort = Config.getVisualizerPort();
        if(args.length > 1) visPort = args[1];

        // start servers
        bus.handle(new CommandStartInfraServer(infraPort));
        bus.handle(new CommandStartVisualizerServer(visPort));

        int numTurns = Config.getNumTurns();
        for (int turn = 1; !bus.getUow().getGameOver(); turn++) {
            Instant turnStartTime = Instant.now();
            Instant nextTurnStart = turnStartTime.plusMillis(Config.getMillisBetweenTurns());

            bus.handle(new CommandStartTurn(turn));

            // have the next turn start after waiting millisBetweenTurns
            // after this turn began (make sure time between turns is
            // actually as advertised)
            try {
                Instant now = Instant.now();
                long waitTime = MILLIS.between(now, nextTurnStart);
                if (waitTime < 0) waitTime = 0;
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        bus.handle(new CommandStopInfraServer());
        bus.handle(new CommandStopVisualizerServer());
    }
}
