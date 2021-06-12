package ayhem_bouabid_yeyeMan.Project1;

public class Request {
    private final int departureFloor;
    private final int destinationFloor;

    private Elevator.Direction direction;

    // A constructor only with direction and departure floor
    // it is called when the passenger press the UP or DOWN button
    Request(int departureFloor, Elevator.Direction direction) {
        this.direction = direction;
        this.departureFloor = departureFloor;
        destinationFloor = departureFloor;
    }

    // A constructor with both destination and start floor
    // it is called when the passenger enters the elevator
    Request(int departureFloor, int destinationFloor) {
        this.departureFloor = departureFloor;
        this.destinationFloor = destinationFloor;

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