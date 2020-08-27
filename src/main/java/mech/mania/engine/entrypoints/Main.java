package mech.mania.engine.entrypoints;

import mech.mania.engine.Bootstrap;
import mech.mania.engine.Config;
import mech.mania.engine.domain.messages.CommandStartInfraServer;
import mech.mania.engine.domain.messages.CommandStartTurn;
import mech.mania.engine.domain.messages.CommandStartVisualizerServer;
import mech.mania.engine.domain.messages.EventSendHistoryObjects;
import mech.mania.engine.service_layer.MessageBus;
import mech.mania.engine.service_layer.UnitOfWorkFake;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.logging.Logger;

import static java.time.temporal.ChronoUnit.MILLIS;

@SpringBootApplication
public class Main {

    // default bootstrap argument is DatabaseAws (see Bootstrap.java)
    // public static MessageBus bus = Bootstrap.bootstrap();
    // use this line of code if no AWS credentials
    // public static MessageBus bus = Bootstrap.bootstrap(new UnitOfWorkFake());
    public static MessageBus bus = Bootstrap.bootstrap();

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    @Bean
    public MessageBus bus() {
        return bus;
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println(String.format("Please enter at least enableInfra argument (boolean)." +
                    "There are three arguments available (in order specified) (defaults are located in src/main/resources/config.properties):\nenableInfra (bool), infraPort " +
                    "(int; default: %s), visualizerPort (int; default: %s)",
                    Config.getProperty("infraPort"),
                    Config.getProperty("visualizerPort")));
            return;
        }
        String enableInfra = args[0];

        try {
            if (!Boolean.parseBoolean(enableInfra)) {
                bus = Bootstrap.bootstrap(new UnitOfWorkFake());
            }
        } catch(Exception e) {
            System.out.println(String.format("Unable to parse boolean enableInfra '%s'", enableInfra));
        }

        // Take infra port as first arg
        String infraPort = Config.getProperty("infraPort");
        if(args.length > 1) infraPort = args[1];

        // Take visualizer port as second arg
        String visPort = Config.getProperty("visualizerPort");
        if(args.length > 2) visPort = args[2];

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
            // actually as advertised); assumes that turns will take less than
            // time mentioned. can't do anything about turns taking longer
            // because we can't guarantee that the game state is properly
            // updated.
            try {
                Instant now = Instant.now();
                long waitTime = MILLIS.between(now, nextTurnStart);
                if (waitTime < 0) {
                    LOGGER.warning("Turn took over " + Config.getProperty("millisBetweenTurns") + " ms (" + (-waitTime) + " ms too long).");
                    waitTime = 0;
                }
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
