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
    ConveyorBelt[] belt;
    Present[] present;
    Turntable[] turntable;
    int hopperId, beltId, capacity, speed;
    String range;

    Hopper(String dataForHopper, ConveyorBelt[] belt, Turntable[] turntable) {
        splittedData = dataForHopper.split("\\s+");
        hopperId = Integer.parseInt(splittedData[0]);
        beltId = Integer.parseInt(splittedData[2]);
        capacity = Integer.parseInt(splittedData[4]);
        speed = Integer.parseInt(splittedData[6]);
        this.belt = belt;
        this.turntable = turntable;
    }

    // Set presents when the id matches
    public void LoadPresents(Present[] present) {
        this.present = present;
        AddPresentToConveyorBelt();
    }

    public int GetPresentCount() {
        if (present != null) {
            return present.length;
        }
        return -1;
    }

    private void AddPresentToConveyorBelt() {
        // Passes 1 present onto the first conveyor belt
        if (!belt[0].BeltFull()) {
            for (Present p : present) {
                belt[0].AddPresentToBelt(p, turntable[0]);
                System.out.println("Present " + p.GetName() + " has been added to conveyor belt 0 with turntable " + turntable[0] + " destination shoot is " + p.GetDestinationShoot());
            }
        }
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
