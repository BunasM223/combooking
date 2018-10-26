package com.booking.test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;



public class FileWriteRead {
    public int yearInFile;
    public int yearOutFile;
    public int yearOldOutFile;
    public int monthInFile;
    public int monthOutFile;
    public int monthDayInFile;
    public int monthDayOutFile;
    public String cityFile;

    public FileWriteRead() {
        int yearIn = 2018;
        int yearOut = 2018;
        int yearOldOut = 2014;
        int monthIn = 10;
        int monthOut = 10;
        int monthDayIn = 27;
        int monthDayOut = 29;
        String city = "Minsk";


        FileWriter newFile = null;
        try {
            newFile = new FileWriter("variables.txt");

            newFile.write(yearIn + "\n");
            newFile.write(yearOut + "\n");
            newFile.write(yearOldOut + "\n");
            newFile.write(monthIn + "\n");
            newFile.write(monthOut + "\n");
            newFile.write(monthDayIn + "\n");
            newFile.write(monthDayOut + "\n");
            newFile.write(city + "\n");

            newFile.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {

        }
    }



    public FileWriteRead readDataFromFile() {
        FileReader readFile = null;
        try {
            readFile = new FileReader("variables.txt");
            Scanner scan = new Scanner(readFile);
            while (scan.hasNextLine()) {

                yearInFile = Integer.parseInt(scan.nextLine());
                yearOutFile = Integer.parseInt(scan.nextLine());
                yearOldOutFile = Integer.parseInt(scan.nextLine());
                monthInFile = Integer.parseInt(scan.nextLine());
                monthOutFile = Integer.parseInt(scan.nextLine());
                monthDayInFile = Integer.parseInt(scan.nextLine());
                monthDayOutFile = Integer.parseInt(scan.nextLine());
                cityFile = scan.nextLine();

            }
            readFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }


}
