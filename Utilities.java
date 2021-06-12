package ayhem_bouabid_yeyeMan.Project1;

public class Utilities {

    /**
     * This method checks whether a request is made from either the highest or the lowest floor in direction
     * that may take the elevator out of its range
     *
     * @param request : a given request
     * @param elevator : a given elevator
     * @return whether this request can be made by the elevator
     *
     */
    public static boolean cannotUseElevator(Request request,Elevator elevator){
        int departureFloor=request.getDepartureFloor();
        Elevator.Direction direction=request.getDirection();
        return (departureFloor==elevator.lowestFloor && direction== Elevator.Direction.DOWN) ||
                (departureFloor==elevator.highestFloor && direction== Elevator.Direction.UP);

    }

}
