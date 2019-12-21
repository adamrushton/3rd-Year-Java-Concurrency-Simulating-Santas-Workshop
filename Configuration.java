/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Adam
 */
public class Configuration {

    private String fileName;
    private int delay;
    
    private List<String> belts = new ArrayList<>();
    private List<String> hoppers = new ArrayList<>();
    private List<String> sacks = new ArrayList<>();
    private List<String> turntables = new ArrayList<>();
    private List<String> presents = new ArrayList<>();
    private List<String> timer = new ArrayList<>();

    // Read from Config file
    // Create Hoppers, Belts, Turntables and Sacks based on the file
    // Fill hoppers with Presents according to the configuration file
    // Call run methods on the threaded objects
    // Instigate shutdown of the machine
    // Display to console the final report
    public void Run() {
        LoadConfiguration();
    }

    // Loading only the important information from the file 
    private List<String> LoadImportantDetailsFromFile(String filter) {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            if (!"-".equals(filter)) {
                return stream.filter(line -> line.contains(filter)).collect(Collectors.toList());
            } else { // check for less than 7, supporting age range up to 3 digits <xxx-xxx>
                return stream.filter(line -> line.length() < 7 && line.contains(filter) || line.contains("PRESENTS")).collect(Collectors.toList());
            }
        } catch (Exception e) {
            System.out.println("Error loading from file" + e.getMessage());
        }
        return null;
    }

    private void LoadConfiguration() {    
        belts = LoadImportantDetailsFromFile("length");
        hoppers = LoadImportantDetailsFromFile("speed");
        sacks = LoadImportantDetailsFromFile("age");
        turntables = LoadImportantDetailsFromFile("ib");
        presents = LoadImportantDetailsFromFile("-");
        timer = LoadImportantDetailsFromFile("TIMER");
        delay = Integer.parseInt(timer.get(0).replaceAll("[\\D]", ""));
       
        // Record sack number and delete first line 
        int sackNumber = Integer.parseInt(presents.get(0).replaceAll("[\\D]", ""));
        presents.remove(0);
        
        // Creation of the objects
        ConveyorBelt[] belt   = new ConveyorBelt[belts.size()];
        Hopper[] hopper       = new Hopper[hoppers.size()];
        Sack[] sack           = new Sack[sacks.size()];
        Turntable[] turntable = new Turntable[turntables.size()];
        Present[] present     = new Present[presents.size()];
        
        for (int i = 0; i < presents.size(); i++) {
            present[i] = new Present(presents.get(i), sackNumber);
            switch (present[i].GetDestinationShoot()) {
                case "0-3":
                    // sack 1
                    break;
                case "4-6":
                    // sack 2 or 4
                    break;
                case "7-10":
                    // sack 3
                    break;
                case "11-16":
                    // sack 5
                    break;
            }
           
        }
        for (int i = 0; i < belts.size(); i++) {
            belt[i] = new ConveyorBelt(belts.get(i));
        }

        for (int i = 0; i < hoppers.size(); i++) {
            hopper[i] = new Hopper(hoppers.get(i), belt);
            //hopper[i].LoadPresents(present);
        }
        // One hopper to load all the presents
        // Make this randomise which hopper its goes in next time
        hopper[0].LoadPresents(present);
        System.out.println("Presents loaded into hopper 0: " + hopper[0].GetPresents().length);
        
        for (int i = 0; i < sacks.size(); i++) {
            sack[i] = new Sack(sacks.get(i));
        }
        
        for (int i = 0; i < turntables.size(); i++) {
            turntable[i] = new Turntable(turntables.get(i));
        }
        // thread stuff
        try {
            turntable[0].join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
        turntable[0].start();
        
        System.out.println("COUNT: " + present[0].GetCount());
        
        
        // Everything is now set up. Need to start concurrency
        // Concurrency
    }

    public Configuration(String fileName) {
        this.fileName = fileName;
    }

}
