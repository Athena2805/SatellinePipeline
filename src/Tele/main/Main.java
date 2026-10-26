package Tele.main;
import Tele.queue.TelemetryBuffer;
import Tele.worker.TelemetryWorker;
import Tele.engine.AnamolyRuleEng;
import Tele.server.TelemetryServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        int port = 8080; // Port your Python mock satellite will connect to

        System.out.println("==================================================");
        System.out.println(" Starting Satellite Telemetry Pipeline MVP...");
        System.out.println("==================================================");

        // 1. Initialize the thread-safe buffer (The shock absorber)
        TelemetryBuffer buffer = new TelemetryBuffer();

        // 2. Initialize the anomaly rule engine
        AnamolyRuleEng ruleEngine = new AnamolyRuleEng();

        // 3. Create a thread pool using ExecutorService with 2 worker threads
        ExecutorService workerPool = Executors.newFixedThreadPool(2);

        // Submit worker tasks to the thread pool so they run concurrently in the background
        for (int i = 1; i <= 2; i++) {
            workerPool.submit(new TelemetryWorker(buffer, ruleEngine));
        }
        System.out.println("[Bootstrap] Worker thread pool started (2 concurrent workers).");

        // 4. Initialize and start the TCP Server
        TelemetryServer server = new TelemetryServer(port, buffer);

        // This call blocks the main thread, waiting patiently for the Python satellite script to connect
        server.startServer();
    }
}