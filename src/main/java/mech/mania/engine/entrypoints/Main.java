package mech.mania.engine.entrypoints;

import mech.mania.engine.Bootstrap;
import mech.mania.engine.Config;
import mech.mania.engine.domain.messages.CommandStartInfraServer;
import mech.mania.engine.domain.messages.CommandStartTurn;
import mech.mania.engine.domain.messages.CommandStartVisualizerServer;
import mech.mania.engine.domain.messages.EventSendHistoryObjects;
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
        String infraPort = Config.getProperty("infraPort");
        if(args.length > 0) infraPort = args[0];

        // Take visualizer port as second arg
        String visPort = Config.getProperty("visualizerPort");
        if(args.length > 1) visPort = args[1];

        // start servers
        bus.handle(new CommandStartInfraServer(infraPort));
        bus.handle(new CommandStartVisualizerServer(visPort));

        int numTurns = Integer.parseInt(Config.getProperty("numTurns"));
        for (int turn = 1; (numTurns == -1 || turn < numTurns) && !bus.getUow().getGameOver(); turn++) {

            Instant turnStartTime = Instant.now();
            Instant nextTurnStart = turnStartTime.plusMillis(Long.parseLong(Config.getProperty("millisBetweenTurns")));

            bus.handle(new CommandStartTurn(turn));
            bus.handle(new EventSendHistoryObjects());

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
    }
}
