package ayhem_bouabid_yeyeMan.Project1;

import java.util.Scanner;

public class Passenger {
    private final Scanner input;
    private final int departureFloor;
    private int destinationFloor;
    private final Elevator.Direction direction;
    private int waitingPeriod;

    Passenger(int departureFloor, Elevator.Direction direction){
        this.departureFloor=departureFloor;
        this.destinationFloor=departureFloor;
        this.direction=direction;
        input= new Scanner(System.in);
        waitingPeriod=0;
    }

    /**
     * when pushing the direction button this method should be called
     * @return a request that determines the depart and the direction
     */

    public Request pushDirectionButton(){
        return new Request(departureFloor,direction);
    }

    /**
     * when pushing the FloorNumber button this method should be called
     * @return a request that determines the depart and the destination floor
     */

    public Request pushFloorNumber(){
        System.out.println("Please enter the destination floor");
        int destinationFloor=input.nextInt();
        this.destinationFloor=destinationFloor;
        return new Request(departureFloor,destinationFloor);
    }

    public void waitLonger(){
        waitingPeriod++;
    }

    public void talkAboutWaiting(){
        System.out.println("I have waited for "+ waitingPeriod);
    }

    /**
     *
     * @param anotherPassenger the other passenger
     * @return whether the passenger is the same as this.
     */
    public boolean equals(Passenger anotherPassenger){
        return this.destinationFloor== anotherPassenger.getDestinationFloor() && this.departureFloor==anotherPassenger.getDepartureFloor();
    }

    //GETTERS
    public int getDepartureFloor() {
        return departureFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public Elevator.Direction getDirection() {
        return direction;
    }

}
