/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

/**
 *
 * @author Adam
 */
public class Present {
    static int count = 0;
    private String destinationShoot;
    private String[] splittedData;
    private int sackNumber;
    private String name;
    
    // To-do: sort this string itno appropiate variables
    public Present(String dataForPresent, int sackNumber) {
        splittedData = dataForPresent.split("\\s+");
        System.out.println("Splitted data for present:" + dataForPresent);
        destinationShoot = splittedData[0];
        this.sackNumber = sackNumber;
        count++;
        name = "Bob " + count;
    }

    public String GetDestinationShoot() {
        return destinationShoot;
    }
    
    public String GetName() {
        return name;
    }
    
    public int GetSackNumber() {
        return sackNumber;
    }
    
    public int GetCount() {
        return count;
    }
}
