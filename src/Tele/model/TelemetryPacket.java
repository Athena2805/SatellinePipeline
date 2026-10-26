package Tele.model;

public class TelemetryPacket {
	private String satId;
    private double temperature;
    private double batteryVoltage;

    // 2. Default constructor (needed for JSON mapping libraries)
    public TelemetryPacket() {
    }

    // 3. Parameterized constructor
    public TelemetryPacket(String satId, double temperature, double batteryVoltage) {
        this.satId = satId;
        this.temperature = temperature;
        this.batteryVoltage = batteryVoltage;
    }

    // 4. Getters (to read the data)
    public String getSatId() {
        return satId;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getBatteryVoltage() {
        return batteryVoltage;
    }

    // Setters (to modify the data if needed)
    public void setSatId(String satId) {
        this.satId = satId;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setBatteryVoltage(double batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    // 5. toString() method for easy console logging and debugging
    @Override
    public String toString() {
        return "TelemetryPacket{" +
                "satId='" + satId + '\'' +
                ", temperature=" + temperature +
                ", batteryVoltage=" + batteryVoltage +
                '}';
    }
}
