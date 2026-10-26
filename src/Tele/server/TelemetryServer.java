package Tele.server;

import Tele.queue.TelemetryBuffer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TelemetryServer {
	private final int port;
    private final TelemetryBuffer buffer;

    public TelemetryServer(int port, TelemetryBuffer buffer) {
        this.port = port;
        this.buffer = buffer;
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("TCP Server started. Waiting for satellite connection on port " + port + "...");

            // 1. Block and wait until the mock satellite connects via TCP
            Socket clientSocket = serverSocket.accept();
            System.out.println("Satellite connected from: " + clientSocket.getRemoteSocketAddress());

            // 2. Open an input stream to read incoming raw text bytes from the socket
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                String rawJsonPacket;
                
                // 3. Continuously read incoming lines sent over the TCP network stream
                while ((rawJsonPacket = reader.readLine()) != null) {
                    // Push the raw JSON string directly into our thread-safe buffer
                    buffer.produce(rawJsonPacket);
                }
            }

        } catch (Exception e) {
            System.err.println("Server encountered an error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
