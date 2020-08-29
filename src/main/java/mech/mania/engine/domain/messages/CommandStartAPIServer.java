package mech.mania.engine.domain.messages;

public class CommandStartAPIServer implements Command{
    private String port;

    public CommandStartAPIServer(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }
}
