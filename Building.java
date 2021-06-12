package ayhem_bouabid_yeyeMan.Project1;

import java.util.ArrayList;

public class Building {

    private final int basement;
    private final int ceiling;

    private ArrayList<Passenger>[] waitList;

    // initialize the array and every ArrayList in the array
    public Building(int basement,int ceiling){
        this.basement=basement;
        this.ceiling=ceiling;
        waitList= new ArrayList[ceiling-basement+1];
        for(int index=0; index<ceiling-basement+1; index++){
            waitList[index]= new ArrayList<>();
        }
    }

    // since the basement is usually negative and stored in cell 0
    // a floor n should be stored in cell index n-basement in the array
    public int floorIndexInArray(int floor){
        return floor-basement;
    }

    /**
     * according to the departureFloor of the new passenger it is added to the ArrayList in the adequate cell
     * @param passenger the new Passenger
     */
    public void addPassengerWaiting(Passenger passenger){
        int index = floorIndexInArray(passenger.getDepartureFloor());
        waitList[index].add(waitList[index].size(),passenger);
    }

    /**
     * remove the passengers in the passed list from the waitingList
     * @param luckyPassengers: passengers that reached their destinations
     */
    public void reachDestination (ArrayList<Passenger> luckyPassengers){
        int index;
        for(Passenger p: luckyPassengers){
            index=floorIndexInArray(p.getDestinationFloor());
            p.talkAboutWaiting();
            waitList[index].remove(p);
        }
    }

    /**
     *
     * @param floor : a certain floor
     * @return a list with Passengers waiting in that floor
     */
    public ArrayList<Passenger> passengersAtFloor(int floor){
        int index=floorIndexInArray(floor);
        return waitList[index];
    }

    public int getBasement() {
        return basement;
    }
    public int getCeiling() {
        return ceiling;
    }

    /**
     * add a second to the waitingPeriod of every Passenger that is not in an Elevator yet.
     */
    public void prolongWaitingOnFloors(){
        for(int index=0; index<ceiling-basement+1;index++){
            for(Passenger p: waitList[index]){
                p.waitLonger();
            }
        }
    }
}
