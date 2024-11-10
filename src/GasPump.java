public class GasPump {
    private int currentFuelLevel;
    private int maxFuelLevel;

    public GasPump(int maxFuelLevel) {
        this.maxFuelLevel = maxFuelLevel;
        this.currentFuelLevel = maxFuelLevel;

    }

    public void dispenseFuel(int amount){
        if (currentFuelLevel - amount < 0) {
            System.out.println("Not enough fuel");
            return;
        }
        else{
            currentFuelLevel -= amount;
            System.out.println("Dispensing " + amount + " liters of fuel");
        }
    }

    public void refill(){
        currentFuelLevel = maxFuelLevel;
        System.out.println("Refilling fuel tank");
    }

    public int getCurrentFuelLevel(){
        return currentFuelLevel;
    }
}

