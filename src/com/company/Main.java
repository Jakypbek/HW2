package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static final GsonBuilder BUILDER = new GsonBuilder();
    public static final Gson GSON = BUILDER.setPrettyPrinting().create();
    public static final Path WRITE_PATH = Paths.get("./autoPark.json");
    public static final Path WRITE_PATH_FOR_DRIVERS = Paths.get("./drivers.json");

    public static void main(String[] args) {

        Driver nully = new Driver(0, "     ");

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

        int i = 0;

        while (i < 10) {
            manageAutoPark(trucks, drivers);
            i++;
        }



        writer(autoPark, WRITE_PATH);
        writer(driver, WRITE_PATH_FOR_DRIVERS);

        System.out.println(reader());

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

    private static void manageAutoPark(Truck[] trucks, Driver[] drivers) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("-----------------Trucks-----------------");
        showAutoPark(trucks);
        System.out.print("Chose one of the truck: ");
        int a = scanner.nextInt();
        for (int i = 0; i < trucks.length; i++) {
            if (a != trucks[i].getId()) {
                System.out.println("There is not truck with id: " + a);
            }
        }
        truckInfo(trucks[(a - 1)]);
        System.out.println("Press 1 to change Driver");
        System.out.println("Press 2 to send to the Route");
        System.out.println("Press 3 to sent to Repairing");
        int b = scanner.nextInt();
        if (b == 1) {
            if (trucks[(a - 1)].getState().equals(State.ROUTE)) {
                System.out.println("------------------------------------");
                System.err.println("Truck on the road, you cant change driver");
                System.out.println("------------------------------------");
            }else if (trucks[(a - 1)].getState().equals(State.REPAIR)) {
                System.out.println("-----------------------------------------");
                System.out.println("You cant change driver, truck on the repair");
                System.out.println("-----------------------------------------");
            } else {
                changeDriver(trucks[(a - 1)], drivers);
            }
        } else if (b == 2) {

            if (trucks[(a - 1)].getState().equals(State.REPAIR)) {
                if (random.nextInt(2) % 2 == 1) {
                    trucks[a - 1].startDriving();

                } else {
                    trucks[a - 1].startRepair();
                }
            } else {
                trucks[a - 1].startDriving();
            }

            if (trucks[a - 1].getDriver().getId() != 0) {
                trucks[a - 1].startDriving();
            } else {
                System.out.println(trucks[a - 1].getName() + " have no driver");
            }
            if (trucks[a - 1].getState().equals(State.ROUTE)) {
                System.out.println("-----------------------------------");
                System.err.println("Truck already on the road");
                System.out.println("-----------------------------------");
            }
        } else if (b == 3) {

            if (trucks[a - 1].getState().equals(State.REPAIR)) {
                System.out.println("-----------------------------------");
                System.err.println("Truck already on the repair");
                System.out.println("-----------------------------------");
            } else {
                trucks[a - 1].startRepair();
            }
        } else {
            System.out.println("eeerrrr");
        }
        truckInfo(trucks[(a - 1)]);
        showDrivers(drivers);



    }

    private static void truckInfo(Truck truck) {
        System.out.println("----------Truck-INF------------");

        System.out.println("  N          : " + truck.getId());
        System.out.println("  Bus        : " + truck.getName());
        System.out.println("  Driver     : " + truck.getDriver().getName());
        System.out.println("  Bus State  : " + truck.getState());
    }

    private static void changeDriver(Truck truck, Driver[] drivers) {

        System.out.println("------------------------------------");

        for (Driver driver : drivers) {
            if (driver.getId() != 0 && driver.getTruck().getId() == 0) {
                truck.changeDriver(driver);
                driver.setTruck(truck);
                System.out.println("Driver changed successfully");
                System.out.println("--------------------------------------");
                break;
            }
        }

        if (truck.getDriver().getId() == 0) {
            System.out.println("There is no free drivers");
            System.out.println("------------------------------------");
        }
    }


}
