package ayhem_bouabid_yeyeMan.Project1;

import java.util.ArrayList;



public class Elevator {
    enum Direction{
        UP,DOWN,STILL
    }

    // the direction of the elevator's movement
    private Direction direction;
    // the highest and lowest floors the elevators can be reached
    // the user interface should contain only values with this range inclusive
    public final int lowestFloor;
    public final int highestFloor;
    // the floor from which the elevator starts and goes back to after completing all the trips
    private final int startPoint;

    private int destinationFloor;
    private int currentFloor;

    // List of passengers on the elevator
    ArrayList<Passenger> onBoard;
    // List of floors that the elevator needs to stop by
    ArrayList<Integer> toDoList;

    public final String name;


    private boolean emergency;

    /**
     * when pushing the emergency button this method is called
     */
    public void declareEmergency(){
        emergency=true;
    }

    /**
     * when pushing the reset button this method is called
     */
    public void reset(){
        emergency=false;
    }

    //constructor
    public Elevator(String name,int startPoint ,int lowestFloor,int highestFloor) {
        this.name=name;
        this.startPoint = startPoint;
        this.destinationFloor =startPoint;
        this.currentFloor = startPoint;
        this.onBoard = new ArrayList<>();
        this.toDoList=new ArrayList<>();
        direction=Direction.STILL;
        this.lowestFloor=lowestFloor;
        this.highestFloor=highestFloor;
        this.emergency=false;
    }

    /**
     * This method will update the destinationFloor field according to the request passed
     * if the request has a destination floor in the same way/direction as the elevator's current direction
     * then elevator.destinationFloor is updated to request.destinationFloor
     * and added at the beginning of the list
     * otherwise the destinationFloor value of the request is added to the end of the list
     *
     * @param request produced either by pushing the direction button or a floor number button
     */
    public void receiveRequest(Request request){
        int newDestination= request.getDestinationFloor();
        if(emergency){
            System.out.println("The elevator is out of order until the emergency is lifted");
            return;
        }
        if(toDoList.isEmpty()){
            this.destinationFloor= newDestination;
            this.direction=request.getDirection();
            toDoList.add(destinationFloor);
        }
        else if(direction==Direction.UP){

            if(destinationFloor>=newDestination && newDestination>=currentFloor){
                toDoList.add(0,newDestination);
                destinationFloor= newDestination;

            }
            else{
                toDoList.add(toDoList.size(),newDestination);
            }
        }
        else if(direction==Direction.DOWN){
            if(destinationFloor<=newDestination && newDestination<=currentFloor){
                toDoList.add(0,newDestination);
                destinationFloor= newDestination;
                toDoList.add(0,newDestination);
            }
            else{
                toDoList.add(toDoList.size(),newDestination);
            }
        }

    }

    /**
     * the elevator will move every second
     * if elevator reaches its destination then it opens its doors lets passengers in and out and then closes the doors : 1 second passes
     * otherwise its move to its next floor either up or down according to its direction : 1 second passed as well
     * @param building the building where both the passengers and the elevators are!!
     */

    public void move(Building building){
        if(emergency){
            doorsRemainOpen();
            return;
        }
        // if the destinationFloor is set to startPoint then the elevators has not received any requests yet

        if(destinationFloor!=startPoint ){

        if(currentFloor!=destinationFloor){
            moveToNextFloor();
        }
        else{
            openDoors();
            passengersOut(building);
            resetDestination();
            passengersIn(building);
            closeDoors();

        }
        prolongWaitingOnBoard();
        }
    }

    public void moveToNextFloor(){
        if(direction==Direction.UP){
            currentFloor++;
        }
        else {
            currentFloor--;
        }
        System.out.println("elevator "+ name +" moves to floor "+currentFloor);
    }

    public void openDoors(){
        System.out.println("elevator "+ name + " opens doors at floor "+currentFloor);
    }

    /**
     * the passengers are the current floor are added to the "onBoard" field of the elevator
     * each passenger enters its destination floor by calling the method pushFloorNumber();
     * pushing the same number of the user-interface should call pushFloorNumber();
     * @param building : the building where elevators and passengers are
     */
    public void passengersIn(Building building){
        for(Passenger p : building.passengersAtFloor(currentFloor)){
            receiveRequest(p.pushFloorNumber());
        }
        onBoard.addAll(onBoard.size(),building.passengersAtFloor(currentFloor));

    }

    /**
     * when reaching its destination the elevators iterates through the onBoard field
     * and collect all passengers with the same destination and remove them from the building
     * by calling the Building.reachDestination() and remove them from the onBoard field
     *
     * @param building building
     */

    public void passengersOut(Building building){
        ArrayList<Passenger> luckyPassengers= new ArrayList<>();
        for(Passenger passenger: onBoard){
            if (passenger.getDestinationFloor()==currentFloor){
                luckyPassengers.add(luckyPassengers.size(),passenger);

            }
        }
        for(Passenger passenger: luckyPassengers){
            onBoard.remove(passenger);
        }
        building.reachDestination(luckyPassengers);
    }

    /**
     * if the toDoList is non-empty, then the new destination is the first in the toDoList
     * else : no more requests to respond and the elevators heads to the startPoint
     *
     */

    public void resetDestination(){
        if(!toDoList.isEmpty()){
        toDoList.remove(0);
        destinationFloor=toDoList.get(0);
        }
        else{
            destinationFloor=startPoint;
        }
    }


    public void closeDoors(){
        System.out.println("elevator "+ name + " closes doors at "+currentFloor);
    }

    /**
     * add a second to the waitingPeriod of each passenger in the elevator
     */
    public void prolongWaitingOnBoard(){
        for(Passenger ElevatorPassenger : onBoard){
            ElevatorPassenger.waitLonger();
        }

    }

    public void doorsRemainOpen(){
        System.out.println("This is an emergency. The doors will remain open until the reset button is pushed!! ");
    }

}
