package edu.ithaca.bhamula1.hotel;


import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Ben on 3/22/2018.
 */

public class Room implements RoomInterface {

    boolean available;
    int roomNumber;
    double price;
    int bedNum;
    String bedType;
    String amenities; // such as view?
    boolean checkedIn;
    String reservationName;
    List<Calendar> notAvailTheseDays;

    public Room(boolean avail, int roomNum, double price, int bedNum, String bedType, String amenities){
        this.available = avail;
        this.roomNumber = roomNum;
        this.price = price;
        this.bedNum = bedNum;
        this.bedType = bedType;
        this.amenities = amenities;
        this.checkedIn = false;
        this.notAvailTheseDays = new ArrayList<>();
    }

    public Room(){
        //if the room doesn't have data, it shouldn't be allowed to reserve
        this.available = false;
        this.roomNumber = 0;
        this.price = 0;
        this.bedNum = 0;
        this.bedType = "";
        this.amenities = "";
        this.checkedIn = false;
        this.notAvailTheseDays = new ArrayList<>();
    }


    public void setIfAvailable(boolean avail){
        this.available = avail;
    }

    /**
     * Cloning objects - this is used to ensure date pointer passed is not incremented
     * can be used for deep copying other objects
     * @return
     * @throws CloneNotSupportedException
     */
    protected Object clone()throws CloneNotSupportedException{
        return super.clone();
    }


    /*
    when a reservation is created for a room, then add the dates that are booked here
    shouldn't add the dates if some of the dates are already in the blocked out list
    will return 1 if it works
    will return -1 if it doesn't not work because there is reservation conflicts
     */
    public int addReservation(Calendar date, int nightDuration){

        Calendar dateClone = (Calendar) date.clone();
        boolean notAvailAlreadyContains = false;


            List<Calendar> blockedOutDates = new ArrayList<>();

            for(int i =0; i< nightDuration; i++ ) {
                Calendar newDate = Calendar.getInstance();
                newDate.setTime(dateClone.getTime());
                dateClone.add(Calendar.DAY_OF_MONTH, 1);
                blockedOutDates.add(newDate);

                if (notAvailTheseDays.contains(newDate)) {
                      notAvailAlreadyContains = true;
                      break;
                }
            }

            if(!notAvailAlreadyContains){
                this.notAvailTheseDays.addAll(blockedOutDates);
                return 1;

            }else{
                return -1;
            }

    }

    public boolean canReserve(Calendar date, int nightDuration){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");
        Calendar dateClone = (Calendar) date.clone();

//        for(int x =0; x<notAvailTheseDays.size(); x++){
//            System.out.println("ALREADY IN HERE " + dateFormat.format(notAvailTheseDays.get(x).getTime()));
//        }


        boolean notAvailAlreadyContains = false;

        for(int i =0; i< nightDuration; i++ ) {
            Calendar newDate = Calendar.getInstance();
            newDate.setTime(dateClone.getTime());
            dateClone.add(Calendar.DAY_OF_MONTH, 1);

            //System.out.println("here "+ dateFormat.format(newDate.getTime()));

            for(int x =0; x<notAvailTheseDays.size(); x++){
               // System.out.println("DOES IT HERE HEREKJ"+ "  new date"+dateFormat.format(newDate.getTime()));
                //System.out.println("DOES IT HERE HEREKJ"+ "  STORED  date"+dateFormat.format(notAvailTheseDays.get(x).getTime()));

                if(dateFormat.format(notAvailTheseDays.get(x).getTime()).equals(dateFormat.format(newDate.getTime()))){

                    notAvailAlreadyContains = true;
                    //System.out.println("does this work pleas work");

                }
            }
        }

        if(!notAvailAlreadyContains){
            return true;

        }else{
            return false;
        }
    }

    public List<Calendar> getNotAvailTheseDays(){
        return this.notAvailTheseDays;
    }



    public void setRoomNumber(int num){
        this.roomNumber = num;

    }

    public void setRoomPrice(double price){
        this.price = price;

    }

    public void setBedCount(int num){
        this.bedNum = num;
    }

    public void setBedType(String type){
        this.bedType = type;
    }

    public void addAmenities(String amenities){

        if(this.amenities.length()> 0){
            this.amenities = this.amenities + ", " + amenities;

        }else{
            this.amenities = amenities;
        }
    }


    public boolean getIfAvailable(){
        return this.available;
    }



    public int getRoomNumber(){
        return this.roomNumber;
    }

    public int getBedCount(){
        return this.bedNum;
    }

    public double getRoomPrice(){
        return this.price;
    }

    public String getBedType (){
        return this.bedType;
    }

    public String getAmenities(){
        return this.amenities;
    }


    public String toString(){
        return "Room: " + this.roomNumber + " Type: "+ this.bedNum + " " + this.bedType+" bed(s) Amenities: " + this.amenities +
                " Price: $"+ this.price + " Available: " + this.available;
    }

    public boolean checkIn(CustomerInterface customer){
        this.checkedIn=true;
        return true;
    }

    public boolean checkOut(CustomerInterface customer){
        this.checkedIn=false;
        this.available = true;
        this.reservationName=null;
        return true;
    }

    public void setReservationName(String name){
        this.reservationName = name;
    }

    public String getReservationName() {
        return reservationName;
    }

    public boolean getCheckedIn(){
        return this.checkedIn;
    }

    public void makeRequestFromRoom(){
        Requests req = new Requests();
        req.makeRequest(roomNumber);
    }

    public void viewAvailableRequests(){
        Requests req = new Requests();
        req.viewRequests();
    }

    public void printNotAvailDates(){
        System.out.println("This room is reserved for the following dates: ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");
        for(int na = 0; na < notAvailTheseDays.size(); na++){
            System.out.println( dateFormat.format(notAvailTheseDays.get(na).getTime()));

        }
    }

}
