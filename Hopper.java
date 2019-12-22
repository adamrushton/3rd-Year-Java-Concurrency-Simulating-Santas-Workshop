/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

/**
 *
 * @author Adam Hopper specification ARE Threads Store collection of Presents
 * Associated with ConveyorBelt. Have a speed of working Using the speed, at
 * interval times until empty, a hopper will try to place presents onto the
 * conveyor belt, as long as there is space This class will place presents onto
 * the conveyor belt, aslong as there is space
 */
public final class Hopper extends Thread {

    String[] splittedData;
    Present[] present;
    ConveyorBelt belt;
    int hopperId, beltId, capacity, speed;
    String range;

    Hopper(String dataForHopper, Present[] present, ConveyorBelt[] belt) {
        splittedData = dataForHopper.split("\\s+");
        hopperId = Integer.parseInt(splittedData[0]);
        beltId = Integer.parseInt(splittedData[2]);
        capacity = Integer.parseInt(splittedData[4]);
        speed = Integer.parseInt(splittedData[6]);
        
        // Get all presents and get the connecting belt
        this.present = present;
        
        this.belt = belt[beltId];
    }

    @Override
    public void run() {
        AddPresentToConveyorBelt();
    }

    private void AddPresentToConveyorBelt() {
        // Passes 1 present onto the first conveyor belt        
        if (!belt.BeltFull()) {
            for (Present p : present) {
                belt.Insert(p, p.GetCount());     
            }
        }
    }

    public int GetPresentCount() {
        if (present != null) {
            return present.length;
        }
        return -1; // This will happen when the id on presents doesnt match a hopper id
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
