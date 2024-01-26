package internetProgrammingProject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * This class represents our server.
 * working on a TCP protocol and handles clients concurrently
 * it helps us handle clients and do the questions 1-4.
 *
 */
public class InternetServer {

    private final int port; //the port that we are listening on
    private boolean stopServer; // if false - we are handling clients. if true - we are stopping to handle clients
    private ThreadPoolExecutor clientsPool; // handle multiple clients concurrently

    private IHandler requestHandler; //the concrete handler that we will do the clients request through him

    /**
     * This constructor is setting the port number to it's port attribute.
     * all other is already set by java default values
     * @param port the port that we will listen through
     */
    public InternetServer(int port){
        this.port = port;
        this.clientsPool = null;
        this.requestHandler = null;
        this.stopServer = false; // if server should handle clients' requests
    }

    /**
     * This method implements and defines how we will handle our clients concurrently
     * @param concreteHandler the concrete IHandler that is going to implement from IHandler interface and will handle specific case problems to solve for the client
     */
    public void supportClients(IHandler concreteHandler){
        this.requestHandler = concreteHandler;

        Runnable clientHandling = ()->{ //using a Runnable interface to handle the client a-synchrony(concurrent programming)
            this.clientsPool =
                    new ThreadPoolExecutor(
                            10,15,200, TimeUnit.MILLISECONDS,
                            new LinkedBlockingQueue<>()); //initialize a new clientPool to handle multiple clients concurrently with at least 10 different threads.
            try {
                ServerSocket serverSocket = new ServerSocket(this.port,50); //initialize a server socket to listen for clients on the port attribute with a max queue length of 50
                System.out.println("Server: Handling a client in " + Thread.currentThread().getName() + " Thread");
                while (!stopServer){ //while the client wants us to handle his requests
                    Runnable acceptBlockingCall = ()-> { //using a new runnable to handle the accept blocking call to interrupt the concurrent programming
                        try {
                            // listen for client requests + accept them (phases 3+4), are done by accept method
                            Socket clientToServerConnection = serverSocket.accept(); //the operational Socket on the server-side
                            System.out.println("Server: Handling a client into " + Thread.currentThread().getName() + " Thread");
                            new Thread(()->{ //opening a new thread to handle the clients in a separate thread than the accept thread
                                Runnable specificClientHandling = ()->{
                                    System.out.println("Server: Handling a client inside " + Thread.currentThread().getName() + " Thread");
                                    try {
                                        this.requestHandler.handleClient(clientToServerConnection.getInputStream()
                                                ,clientToServerConnection.getOutputStream());
                                    } catch (IOException | ClassNotFoundException ioException) {
                                        ioException.printStackTrace();
                                    }
                                    // we stopped handling the specific client
                                    try {
                                        clientToServerConnection.close();
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                };
                                clientsPool.execute(specificClientHandling); //starting the thread-pool to handle the queue missions concurrently
                            }).start(); //starting the new thread for handling clients concurrently
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    };
                    new Thread(acceptBlockingCall).start(); //starting the accept-runnable on a new thread
                    };
                serverSocket.close(); //closing the server socket
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        };
        new Thread(clientHandling).start(); //starting the outside-runnable on a new thread
    }

    /**
     * This main method activates the server on port 8010 and calling for method supportClients with the matrixHandler class to handle the clients with the matrix problems(Questions 1-4)
     * @param args
     */
    public static void main(String[] args) {
        InternetServer server = new InternetServer(8080);
        server.supportClients(new MatrixHandler());
    }

}
