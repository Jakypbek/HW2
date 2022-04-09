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

    public static void main(String[] args) {

        String[] drivers = {
                "",
                "",
                ""
        };

        Truck[] trucks = {
                new Truck(1, "Renault Magnum", drivers[0], State.ON_BASE),
                new Truck(2, "Volvo FH12    ", drivers[1], State.ON_BASE),
                new Truck(3, "DAF XF        ", drivers[2], State.ON_BASE)
        };

        String autoPark = GSON.toJson(trucks);

        writer(autoPark);

        showAutoPark(trucks);


    }
    private static void writer(String object) {

        Path write = Paths.get(String.valueOf(WRITE_PATH));

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
}
