package mech.mania.engine.domain.messages;

public class CommandStartInfraServer implements Command {
    private String port;

    public CommandStartInfraServer(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }
}
