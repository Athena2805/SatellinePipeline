package Tele.engine;

import Tele.model.TelemetryPacket;

public class AnamolyRuleEng {
	
	private static final double TEMP_THRESHOLD = 80.0; // Max normal temperature in Celsius
    private static final double VOLTAGE_THRESHOLD = 11.0; // Min safe battery voltage

    public void evaluate(TelemetryPacket packet) {
        boolean isAnomaly = false;
        StringBuilder alertMessage = new StringBuilder();

        // Rule 1: Check for Overheating
        if (packet.getTemperature() > TEMP_THRESHOLD) {
            isAnomaly = true;
            alertMessage.append(" [WARNING: HIGH TEMP -> ")
                        .append(packet.getTemperature())
                        .append("°C]");
        }

        // Rule 2: Check for Battery Voltage Drop
        if (packet.getBatteryVoltage() < VOLTAGE_THRESHOLD) {
            isAnomaly = true;
            alertMessage.append(" [CRITICAL: LOW BATTERY -> ")
                        .append(packet.getBatteryVoltage())
                        .append("V]");
        }

        // Log results to console
        if (isAnomaly) {
            System.out.println("ANOMALY DETECTED on " + packet.getSatId() + ":" + alertMessage.toString());
        } else {
            System.out.println("Processed normal telemetry: " + packet);
        }
    }
}

