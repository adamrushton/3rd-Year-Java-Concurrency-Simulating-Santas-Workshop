/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

/**
 *
 * @author Adam Stores gift objects as they pass presents onto it
 */
public class ConveyorBelt {

    final int INDEX_OF_FIRST_DESTINATION = 4;
    Buffer produceBuffer, consumeBuffer;

    private int available;

    int beltId, capacity;
    int[] destinations = null;
    String[] splittedData;
    
    public Object[] elements = null;

    private int writePos = 0;
    
    public ConveyorBelt(String dataForBelt) {
        splittedData = dataForBelt.split("\\s+");
        beltId = Integer.parseInt(splittedData[0]);
        capacity = Integer.parseInt(splittedData[2]);

        this.elements = new Object[capacity]; // Fixed size array
        
        // index 4 til the end of the splitted data size is the amount of destinations
        destinations = new int[splittedData.length - INDEX_OF_FIRST_DESTINATION];

        try {
            for (int i = INDEX_OF_FIRST_DESTINATION; i < splittedData.length; i++) {
                destinations[i - INDEX_OF_FIRST_DESTINATION] = Integer.parseInt(splittedData[i]);
            }
        } catch (Exception e) {
            System.out.println("An error occured parsing a destination for  conveyorbelt. - " + e.getMessage());
        }

    }
    
    public void reset() {
        this.writePos = 0;
        this.available = 0;
    }

    public int capacity() {
        return this.capacity;
    }

    public int available() {
        return this.available;
    }

    public int remainingCapacity() {
        return this.capacity - this.available;
    }
    
    public boolean GiftAvailableForTurntable(Turntable table) {
        
        return false;
    }
    // Returns true if space and added
    // Returns false if not added
    public boolean put(Object element) {
        if (available < capacity) {
            if (writePos >= capacity) {
                writePos = 0;
            }
            elements[writePos] = element;
            writePos++;
            available++;
            System.out.println("Present added to the buffer");
            return true;
        }
        System.out.println("Present not added to the buffer, buffer is full");
        return false;
    }

    public Object take() {
        if (available == 0) {
            return null;
        }
        int nextSlot = writePos - available;
        if (nextSlot < 0) {
            nextSlot += capacity;
        }
        Object nextObj = elements[nextSlot];
        available--;
        return nextObj;
    }
}
