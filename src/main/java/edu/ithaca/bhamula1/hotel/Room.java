package edu.ithaca.bhamula1.hotel;


import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Ben on 3/22/2018.
 */

public class Room implements RoomInterface {

    //should maybe get rid of this?
    private boolean available;
    private int roomNumber;
    private double price;
    private int bedNum;
    private String bedType;
    private String amenities;
    private boolean checkedIn;
    String reservationName;
    private List<Calendar> notAvailTheseDays;

    public Room(boolean test, boolean avail, int roomNum, double price, int bedNum, String bedType, String amenities, boolean checkedIn){
        this.available = avail;
        this.roomNumber = roomNum;
        this.price = price;
        this.bedNum = bedNum;
        this.bedType = bedType;
        this.amenities = amenities;
        this.checkedIn = checkedIn;
        this.notAvailTheseDays = new ArrayList<>();
    }

    public Room(boolean avail, int roomNum, double price, int bedNum, String bedType, String amenities, boolean checkedIn){
        this.available = avail;
        this.roomNumber = roomNum;
        this.price = price;
        this.bedNum = bedNum;
        this.bedType = bedType;
        this.amenities = amenities;
        this.checkedIn = checkedIn;
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
    When a reservation is created for a room, then add the dates that are booked here
    shouldn't add the dates if some of the dates are already in the blocked out list
    Will return 1 if it works
    Will return -1 if it doesn't not work because there is reservation conflicts
     */
    public int addReservation(Calendar date, int nightDuration) {

        Calendar dateClone = (Calendar) date.clone();
        boolean notAvailAlreadyContains = false;
        List<Calendar> blockedOutDates = new ArrayList<>();
        for (int i = 0; i < nightDuration; i++ ) {
            Calendar newDate = Calendar.getInstance();
            newDate.setTime(dateClone.getTime());
            dateClone.add(Calendar.DAY_OF_MONTH, 1);
            blockedOutDates.add(newDate);
            if (notAvailTheseDays.contains(newDate)) {
                  notAvailAlreadyContains = true;
                  break;
            }
        }
        if (!notAvailAlreadyContains) {
            this.notAvailTheseDays.addAll(blockedOutDates);
            return 1;
        }
        else
            return -1;
    }

    public void removeReservation(Calendar date, int nightDuration) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");
        Calendar dateClone = (Calendar) date.clone();
        boolean notAvailAlreadyContains = false;

        List<Calendar> blockedOutDates = new ArrayList<>();

        for (int i = 0; i < nightDuration; i++) {
            Calendar newDate = Calendar.getInstance();
            newDate.setTime(dateClone.getTime());
            dateClone.add(Calendar.DAY_OF_MONTH, 1);
            blockedOutDates.add(newDate);

        }
        for(int i = 0; i < nightDuration; i++ ) {
            Calendar newDate = Calendar.getInstance();
            newDate.setTime(dateClone.getTime());
            dateClone.add(Calendar.DAY_OF_MONTH, 1);

            for (int x = 0; x < notAvailTheseDays.size(); x++) {
                if (dateFormat.format(notAvailTheseDays.get(x).getTime()).equals(dateFormat.format(blockedOutDates.get(i).getTime())))
                    notAvailTheseDays.remove(notAvailTheseDays.get(x));
            }
        }
    }

    public boolean canReserve(Calendar date, int nightDuration){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");
        Calendar dateClone = (Calendar) date.clone();

        boolean notAvailAlreadyContains = false;

        for (int i = 0; i < nightDuration; i++) {
            Calendar newDate = Calendar.getInstance();
            newDate.setTime(dateClone.getTime());
            dateClone.add(Calendar.DAY_OF_MONTH, 1);

            for (int x = 0; x < notAvailTheseDays.size(); x++){
                if (dateFormat.format(notAvailTheseDays.get(x).getTime()).equals(dateFormat.format(newDate.getTime())))
                    notAvailAlreadyContains = true;
            }
        }
        return !notAvailAlreadyContains;
    }

    public List<Calendar> getNotAvailTheseDays() {return this.notAvailTheseDays;}

    public void setRoomNumber(int num) {this.roomNumber = num;}

    public void setRoomPrice(double price) {this.price = price;}

    public int getBedCount(){return this.bedNum;}

    public void setBedCount(int num){this.bedNum = num;}

    public String getBedType() {return this.bedType;}

    public void setBedType(String type) {this.bedType = type;}

    public void addAmenities(String amenities) {
        if (this.amenities.length() > 0)
            this.amenities = this.amenities + ", " + amenities;
        else
            this.amenities = amenities;
    }

    public boolean getIfAvailable() {return this.available;}

    public int getRoomNumber() {return this.roomNumber;}

    public double getRoomPrice() {return this.price;}

    public String getAmenities(){return this.amenities;}

    public String toString() {
        return " Room number: " + this.roomNumber + "\n\tType: "+ this.bedNum + " " + this.bedType+" bed(s)\n\tAmenities: " +
                this.amenities + "\n\tPrice: $"+ this.price+"0\n";
    }

    public String printDiscountedPrices() {
        double discountPrice = this.price - (this.price* .10);
        return "Room: " + this.roomNumber + " Type: "+ this.bedNum + " " + this.bedType+" bed(s) Amenities: " +
                this.amenities + " Price: $"+ discountPrice;
    }

    public boolean checkIn(CustomerInterface customer) {
        this.checkedIn = true;
        return true;
    }

    public boolean checkOut(CustomerInterface customer) {
        this.checkedIn=false;
        this.available = true;
        this.reservationName=null;
        return true;
    }

    public String getReservationName() {return reservationName;}

    public void setReservationName(String name){this.reservationName = name;}

    public boolean getCheckedIn(){return this.checkedIn;}

    public void printNotAvailDates(){
        System.out.println("This room is reserved for the following dates: ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");
        for (int na = 0; na < notAvailTheseDays.size(); na++)
            System.out.println(dateFormat.format(notAvailTheseDays.get(na).getTime()));
    }

}