package Tele.worker;

import com.google.gson.Gson;
import Tele.model.TelemetryPacket;
import Tele.queue.TelemetryBuffer;
import Tele.engine.AnamolyRuleEng;

public class TelemetryWorker implements Runnable {
    
    private final TelemetryBuffer buffer;
    private final AnamolyRuleEng ruleEngine;
    private final Gson gson;

    public TelemetryWorker(TelemetryBuffer buffer, AnamolyRuleEng ruleEngine) {
        this.buffer = buffer;
        this.ruleEngine = ruleEngine;
        this.gson = new Gson(); // JSON parsing library tool
    }

    @Override
    public void run() {
        try {
            // Continuously process incoming packets while the thread is active
            while (!Thread.currentThread().isInterrupted()) {
                
                // 1. Pull raw JSON string from the thread-safe buffer 
                // (Safely sleeps if the queue is empty until new data arrives)
                String rawJsonPacket = buffer.consume();
                
                if (rawJsonPacket != null) {
                    // 2. Deserialize JSON text into a TelemetryPacket Java object using Gson
                    TelemetryPacket packet = gson.fromJson(rawJsonPacket, TelemetryPacket.class);
                    
                    // 3. Pass the object to the rule engine for evaluation
                    ruleEngine.evaluate(packet);
                }
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            System.err.println("Worker thread terminated unexpectedly: " + e.getMessage());
        }
    }
}