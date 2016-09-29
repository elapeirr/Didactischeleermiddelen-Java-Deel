/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;




/**
 *
 * @author Axel
 */
@Entity
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationId;
   // @ManyToOne
    private Material material;
    private int amount;
  @Temporal(TemporalType.TIMESTAMP)
    private GregorianCalendar startDate;
   @Temporal(TemporalType.TIMESTAMP)
    private GregorianCalendar endDate;
    private String email;
    private int amountReturned;
    @OneToMany
    private List<Reservation> conflicts;
    private boolean materialReturned;
    private boolean conflict;

    public Reservation() {
    }
    
    public Reservation( Material material, int amount, GregorianCalendar startDate, GregorianCalendar endDate , String email) {
        //this.reservationId = reservationId;
        this.material = material;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.email=email;
        this.amountReturned=0;
        materialReturned =false;
        conflict=false;
      //  material.addToReservations(this);
    }

    public StringProperty getEmailProperty() {
        return new SimpleStringProperty(email);
    }
 public StringProperty getEndDateProperty(){
 
 return new SimpleStringProperty(endDate.toString());
 }
    public IntegerProperty getAmountProperty() {
        return new SimpleIntegerProperty(amount);
    }

    public StringProperty getConflictProperty(){ 
        String conflict ="Nee";
        if(isConflict()) {
            conflict="Ja";
        }

        return new SimpleStringProperty(conflict);
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getStartDate() {
        return startDate.getTime();
    }

    public void setStartDate(GregorianCalendar startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate.getTime();
    }

    public void setEndDate(GregorianCalendar endDate) {
        this.endDate = endDate;
    }

    public boolean isMaterialReturned() {
        return materialReturned;
    }

    public void setMaterialReturned(boolean materialReturned) {
        this.materialReturned = materialReturned;
    }

    public boolean isConflict() {
        return conflict;
    }

    public void setConflict(boolean conflict) {
        this.conflict = conflict;
    }



    public List<Reservation> getHasConflictsWith() {
        return conflicts;
    }
    
    public int getAmountReturned() {
        return amountReturned;
    }

    public void setAmountReturned(int amountReturned) {
        this.amountReturned = amountReturned;
    }
    
    public void addToConflicts(Reservation res){
        conflicts.add(res);
        
    }
    
    public void checkConflicts(){
        //Date now = GregorianCalendar.getInstance().getTime();
        
        Set<Reservation> reservations= getMaterial().getReservations();
        reservations.remove(this);
//            for(Reservation r : reservations){
//                if(r.getMaterial().getAmountAvailable(now)<r.getAmount()){
//                    r.setHasConflict(true);
//                }
//            }
        for(Reservation r : reservations){
            if(this.getEndDate().compareTo(r.getStartDate())>=0){
                if(r.getAmount()>r.getMaterial().getAmountAvailable(r.getStartDate())){
                        this.addToConflicts(r);
                        r.addToConflicts(this);
                        conflict = true;
                }    
            }
        }
     
    }
}
