package com.company;

public class Truck {

    private int id;
    private String name;
    private Driver driver;
    private State state;

    public Truck() {
    }

    public Truck(int id, String name, Driver driver, State state) {
        this.id = id;
        this.name = name;
        this.driver = driver;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Driver getDriver() {
        return driver;
    }

    public void changeDriver(Driver driver) {
        this.driver = driver;
    }

    public State getState() {
        return state;
    }

    public void startDriving() {
        this.state = State.ROUTE;
        System.out.println("--------------------------------------");
        System.out.println("Successfully on the road");
        System.out.println("--------------------------------------");
    }

    public void startRepair() {
        this.state = State.REPAIR;
        System.out.println("------------------------------------");
        System.out.println("Truck successfully go to the repair");
        System.out.println("------------------------------------");
    }

    @Override
    public String toString() {
        return "  " + id + "  | " + name + "    | " + driver.getName() + "           | " + state;
    }
}
