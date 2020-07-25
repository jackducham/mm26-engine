package mech.mania.engine.domain.messages;

public class CommandStartVisualizerServer implements Command {
    private String port;

    public CommandStartVisualizerServer(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }
}
