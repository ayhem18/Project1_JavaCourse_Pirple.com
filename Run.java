package ayhem_bouabid_yeyeMan.Project1;


import java.util.Random;
import java.util.Scanner;

public class Run {
    public static final int basement=-1;
    public static final int ceiling=10;
    public static Random generator= new Random();
    public static Scanner input= new Scanner(System.in);
    public static void main(String[] args){

        Building ourBuilding= new Building(basement,ceiling);
        Elevator A = new Elevator("A",0,basement,ceiling-1);
        Elevator B = new Elevator("B",0,basement+1,ceiling);

        System.out.println("Can you specify for long should the elevator receive requests ? ");
        System.out.println("Can you enter the number of passengers ? ");
        int numberOfSeconds=input.nextInt(),numberOfPassengers=input.nextInt();

        int depart;
        Elevator.Direction direction;
        Passenger newPassenger;

        int [] times = numberOfDemandsEachSecond(numberOfSeconds,numberOfPassengers);
        display(times);


        for(int index=0; index<times.length;index++){
            System.out.println("AT SECOND "+ index);

            if(times[index]>0){

                for(int i=0;i<times[index];i++) {
                    depart = basement + generator.nextInt(ceiling);

                    if (depart % 2 == 0)
                        direction = Elevator.Direction.DOWN;
                    else
                        direction = Elevator.Direction.UP;

                    newPassenger = new Passenger(depart, direction);

                    ourBuilding.addPassengerWaiting(newPassenger);

                    if (Utilities.cannotUseElevator(newPassenger.pushDirectionButton(), A)) {
                        B.receiveRequest(newPassenger.pushDirectionButton());
                    } else if (Utilities.cannotUseElevator(newPassenger.pushDirectionButton(), B)) {
                        A.receiveRequest(newPassenger.pushDirectionButton());
                    } else {
                        if (index % 2 == 0) {
                            A.receiveRequest(newPassenger.pushDirectionButton());
                        } else {
                            B.receiveRequest(newPassenger.pushDirectionButton());
                        }
                    }
                }

            }
            A.move(ourBuilding);
            B.move(ourBuilding);

            ourBuilding.prolongWaitingOnFloors();
            System.out.println();
        }

        while(!A.toDoList.isEmpty() || !B.toDoList.isEmpty()){
            A.move(ourBuilding);
            B.move(ourBuilding);
            ourBuilding.prolongWaitingOnFloors();
        }
    }

    // This method return an array with size numberOfSeconds that determine for how many seconds should the elevators keep receiving requests
    // either up/down buttons or floorNumber buttons
    // the resulting array's cell contains the number of requests made within that particular second
    public static int[] numberOfDemandsEachSecond(int numberOfSeconds, int numberOfPassengers){
        int[] times= new int[numberOfSeconds+1];
        int second;
        for(int index=0; index<numberOfPassengers; index++){
            second=1+generator.nextInt(numberOfSeconds);
            times[second]++;
        }
        return times;
    }

    // method to display an array
    public static <T> void display(T[] array){
        for(T t: array){
            System.out.print(t.toString()+" ");
        }
        System.out.println();
    }
    public static void display(int [] array){
        for(int t : array){
            System.out.print(t+" ");
        }
        System.out.println();
    }


}
