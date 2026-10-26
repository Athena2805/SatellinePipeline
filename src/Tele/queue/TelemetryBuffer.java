package Tele.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TelemetryBuffer {
	private final BlockingQueue<String> queue;

    public TelemetryBuffer() {
        // LinkedBlockingQueue provides thread safety out-of-the-box
        this.queue = new LinkedBlockingQueue<>();
    }

    /**
     * Producer method: Called by TelemetryServer to push raw JSON packets into the buffer.
     * If the queue is full (if bounded), it automatically blocks until space is available.
     */
    public void produce(String rawPacket) {
        try {
            queue.put(rawPacket);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Producer thread was interrupted while buffering packet.");
        }
    }

    /**
     * Consumer method: Called by TelemetryWorker threads to pull raw JSON packets out.
     * If the queue is empty, worker threads automatically wait (sleep) safely until new data arrives.
     */
    public String consume() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Consumer thread was interrupted while fetching packet.");
            return null;
        }
    }

    /**
     * Returns the current number of packets waiting in the buffer.
     */
    public int size() {
        return queue.size();
    }
}