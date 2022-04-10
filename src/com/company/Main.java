package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {

    public static final GsonBuilder BUILDER = new GsonBuilder();
    public static final Gson GSON = BUILDER.setPrettyPrinting().create();
    public static final Path WRITE_PATH = Paths.get("./autoPark.json");
    public static final Path WRITE_PATH_FOR_DRIVERS = Paths.get("./drivers.json");

    public static void main(String[] args) {

        Driver nully = new Driver(0, "");

        Driver[] drivers = {
             new Driver(1, "Sasha"),
             new Driver(2, "Petya"),
             new Driver(3, "Vasya")
        };

        Truck[] trucks = {
                new Truck(1, "Renault Magnum", nully, State.ON_BASE),
                new Truck(2, "Volvo FH12    ", nully, State.ON_BASE),
                new Truck(3, "DAF XF        ", nully, State.ON_BASE)
        };

        String autoPark = GSON.toJson(trucks);

        String driver = GSON.toJson(drivers);

        writer(autoPark, WRITE_PATH);
        writer(driver, WRITE_PATH_FOR_DRIVERS);

        showAutoPark(trucks);
        System.out.println("-------------------------------------------------");
        showDrivers(drivers);


    }
    private static void writer(String object, Path path) {

        Path write = Paths.get(String.valueOf(path));

        try {
            Files.writeString(write, object, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    private static String reader() {

        String json = "";

        try {
            FileReader fileReader = new FileReader(String.valueOf(WRITE_PATH));

            int a;

            while ((a = fileReader.read()) != -1) {
                json += (char) a;
            }

        } catch (IOException e) {
            e.getMessage();
        }
        return json;
    }

    private static void showAutoPark(Truck[] trucks) {
        System.out.println("""
                    #  | Bus               | Driver     | State
                  -----+-------------------+------------+----------""");
        for (int i = 0; i < trucks.length; i++) {
            System.out.println(trucks[i]);
        }
    }

    private static void showDrivers(Driver[] drivers) {
        System.out.println("""
                    #  | Driver               | Bus   
                  -----+----------------------+----""");
        for (int i = 0; i < drivers.length; i++) {
            System.out.println(drivers[i]);
        }
    }
}
