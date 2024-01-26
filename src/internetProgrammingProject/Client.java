package internetProgrammingProject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            Socket clientSocket = new Socket("127.0.0.1",8080);
            System.out.println("Socket created");

            ObjectOutputStream toServer = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream fromServer = new ObjectInputStream(clientSocket.getInputStream());

            //Question 1 example
            System.out.println("Question 1:");
            int[][] question1 = {
                    {1,0,1},
                    {1,0,1},
                    {0,0,1}
            };
            toServer.writeObject("Question 1");
            toServer.writeObject(question1);
            System.out.println(fromServer.readObject());

            //Question 2 example
            System.out.println("Question 2:");
            int[][] question2 = {
                    {1,0,0},
                    {1,1,0},
                    {1,1,0}
            };
            Index source2 = new Index(0,0);
            Index dest2 = new Index(2,0);
            toServer.writeObject("Question 2");
            toServer.writeObject(question2);
            toServer.writeObject(source2);
            toServer.writeObject(dest2);
            System.out.println(fromServer.readObject());

            //Question 3 example
            System.out.println("Question 3:");
            int[][] question3 = {
                    {1,0,1},
                    {1,0,1},
                    {1,0,1},
                    {0,0,0},
                    {1,1,1}
            };
            toServer.writeObject("Question 3");
            toServer.writeObject(question3);
            System.out.println(fromServer.readObject());

            //Question 4 example
            System.out.println("Question 4:");
            int[][] question4 = {
                    {100,100,100},
                    {500,900,300}
            };
            Index source4 = new Index(1,0);
            Index dest4 = new Index(1,2);
            toServer.writeObject("Question 4");
            toServer.writeObject(question4);
            toServer.writeObject(source4);
            toServer.writeObject(dest4);
            System.out.println(fromServer.readObject());

            toServer.writeObject("stop");

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
