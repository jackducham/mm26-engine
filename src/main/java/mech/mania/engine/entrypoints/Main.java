package mech.mania.engine.entrypoints;

import mech.mania.engine.Bootstrap;
import mech.mania.engine.Config;
import mech.mania.engine.domain.messages.CommandStartInfraServer;
import mech.mania.engine.domain.messages.CommandStartTurn;
import mech.mania.engine.domain.messages.CommandStartVisualizerServer;
import mech.mania.engine.domain.messages.EventSendHistoryObjects;
import mech.mania.engine.service_layer.MessageBus;
import mech.mania.engine.service_layer.UnitOfWorkFake;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
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

    // Represents the presence of the --enableInfra flag (used by Spring services)
    public static boolean enableInfra;

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    @Bean
    public MessageBus bus() {
        return bus;
    }

    public boolean enableInfra(){ return enableInfra; }

    public static void main(String[] args) {
        // Configure commandline options for this program
        Options options = new Options();
        options.addOption("ei", "enableInfra", false,
                "This flag tells the engine to run in infra mode and attempt to connect to AWS.");
        options.addOption("ip", "infraPort", true,
                "The port number for the /infra endpoints.");
        options.addOption("vp", "visualizerPort", true,
                "The port number for the visualizer websocket.");

        // Get current options
        CommandLine cmd;
        try {
            cmd = new DefaultParser().parse(options, args);
        } catch(ParseException e){
            System.err.println("Error in parsing command line options: " + e.getMessage());
            return;
        }

        // Default to false unless set by command line args
        enableInfra = cmd.hasOption("enableInfra");

        // Default to ports in Config, but command line args can override
        String infraPort = cmd.hasOption("infraPort") ?
                cmd.getOptionValue("infraPort") : Config.getProperty("infraPort");
        String visualizerPort = cmd.hasOption("visualizerPort") ?
                cmd.getOptionValue("visualizerPort") : Config.getProperty("visualizerPort");

        if(!enableInfra){
            // Don't connect to AWS if infra is not enabled
            bus = Bootstrap.bootstrap(new UnitOfWorkFake());
        }

        // Start servers
        bus.handle(new CommandStartInfraServer(infraPort));
        bus.handle(new CommandStartVisualizerServer(visualizerPort));

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
