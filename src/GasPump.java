public class GasPump {
    private double currentFuelLevel;
    private double maxFuelLevel;

    public GasPump(double maxFuelLevel) {
        this.maxFuelLevel = maxFuelLevel;
        this.currentFuelLevel = maxFuelLevel;
    }

    public void dispenseFuel(double amount){
        if (currentFuelLevel - amount < 0) {
            return;
        }
        currentFuelLevel -= amount;
        System.out.println("Dispensing " + amount + " liters of fuel");
    }

    public void refill(){
        currentFuelLevel = maxFuelLevel;
        System.out.println("Refilling fuel tank");
    }

    public double getCurrentFuelLevel(){
        return currentFuelLevel;
    }
}

