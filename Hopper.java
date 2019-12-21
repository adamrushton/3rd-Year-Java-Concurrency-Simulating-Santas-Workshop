/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

/**
 *
 * @author Adam
 * This class will place presents onto the conveyor belt, aslong as there is space
 */
public class Hopper extends Thread {
    String[] splittedData;   
    ConveyorBelt[] belt;
    Present[] present;
    int hopperId, beltId, capacity, speed;
    String range;
    
    Hopper(String dataForHopper, ConveyorBelt[] belt) {
        splittedData = dataForHopper.split("\\s+");
        hopperId = Integer.parseInt(splittedData[0]);
        beltId = Integer.parseInt(splittedData[2]);
        capacity = Integer.parseInt(splittedData[4]);
        speed = Integer.parseInt(splittedData[6]);
        this.belt = belt;
    }
    
    // Set presents when the id matches
    public void LoadPresents(Present[] present) {
        this.present = present;
    }
    
    public Present[] GetPresents() {
        return present;
    }
    
    public void AddPresentToConveyorBelt() {
        // Passes 1 present onto the first conveyor belt
        while (!belt[0].BeltFull()) {
            for (Present p : present) {
                belt[0].AddPresentToBelt(p);
                System.out.println("Present " + p.GetName() + " has been added to conveyor belt 0 with turntable .. destination shoot is .." + p.GetDestinationShoot());
            }        
        }
        present = null;
    }
    
    public int GetHopperId() {
        return hopperId;
    }

    public int GetBeltId() {
        return beltId;
    }

    public int GetCapacity() {
        return capacity;
    }

    public int GetSpeed() {
        return speed;
    }

    public String GetRange() {
        return range;
    }
}
