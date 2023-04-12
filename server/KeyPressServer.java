import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class KeyPressServer {

    public static void main(String[] args) {
        int port = 12345; // Server port
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started and listening on port " + port);

            while (true) {
                // Wait for a client to connect
                Socket clientSocket = serverSocket.accept();

                // Start a new thread to handle client request
                new ClientHandlerThread(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandlerThread extends Thread {
        private Socket clientSocket;

        public ClientHandlerThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String request = reader.readLine();

                if (request != null) {
                    System.out.println("Received request: " + request);

                    // Check the request and simulate key press accordingly
                    if ("space".equals(request)) {
                        // Simulate spacebar key press
                        System.out.println("Simulating SPACE key press...");
                        Robot robot = new Robot();
                        robot.keyPress(KeyEvent.VK_SPACE);
                        robot.delay(100);
                        robot.keyRelease(KeyEvent.VK_SPACE);
                    } else {
                        System.out.println("Invalid request");
                    }
                }

                // PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                // writer.println("Request received"); // Send response to client
                // writer.close();
                // reader.close();
                clientSocket.close();
            } catch (IOException | AWTException e) {
                e.printStackTrace();
            }
        }
    }
}
