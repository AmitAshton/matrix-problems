package internetProgrammingProject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This interface defines the functionality required of all concrete handlers
 */
public interface IHandler {
    /**
     *
     * @param fromClient input stream that give us the input from the client for what he wants
     * @param toClient output stream that gives us the functionality to write and deliver answers to the client on a socket
     * @throws IOException
     * @throws ClassNotFoundException
     */
    void handleClient(InputStream fromClient, OutputStream toClient)
            throws IOException, ClassNotFoundException;
}
