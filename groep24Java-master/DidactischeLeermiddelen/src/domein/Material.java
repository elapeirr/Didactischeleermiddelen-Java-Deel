/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Axel
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Material.findFirms", query = "select distinct b.firm from Material b"),   
    @NamedQuery(name = "Material.findByName", query = "select distinct b.name from Material b where b.name = :materialName")
})
public class Material implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int materialId;
    private String name;
    private String imagepath;
    private String description;
    private String itemNumber;
    private double price;
    private String firm;
    private String location;
    private Boolean lendable;
    private int broken;
    private int amount;
    private String emailContact;
    @OneToMany
    private Set<Curricular> curriculars = new HashSet<>();
    @OneToMany
    private Set<TargetAudience> targetAudiences = new HashSet<>();
    @OneToMany
    private Set<Reservation> reservations = new HashSet<>();
    
    public Material() {
    }
    
    public Material(String name, String description) {
        this.name = name;
        this.description = description;
        this.amount = 5;
        this.broken = 0;
        this.lendable = false;
        this.firm = "Rekenspelletjes NV";
        this.emailContact = "Firm@firm.be";
        this.itemNumber = "ItemNumber";
        this.price = 10.0;
        this.location = "Locatie";
    }

    public Material(String name, String imagepath, String description, String itemNumber, double price, String firm,String firmmail, String location, Boolean lendable, int broken, int amount) {
        this.name = name;
        this.imagepath = imagepath;
        this.description = description;
        this.itemNumber = itemNumber;
        this.price = price;
        this.firm = firm;
        this.location = location;
        this.lendable = lendable;
        this.broken = broken;
        this.amount = amount;
        emailContact = firmmail;
        //reservations = new ArrayList<>();
    }

    public Material(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }
    
    public void addCurricular(Curricular curricular) {
        curriculars.add(curricular);
    }

    public void addTargetAudience(TargetAudience targetAudience) {
        targetAudiences.add(targetAudience);
    }

    public StringProperty getNameProperty() {
        return new SimpleStringProperty(name);
    }

    public StringProperty getDescriptionProperty() {
        return new SimpleStringProperty(description);
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getLendable() {
        return lendable;
    }

    public void setLendable(Boolean lendable) {
        this.lendable = lendable;
    }

    public int getBroken() {
        return broken;
    }

    public void setBroken(int broken) {
        this.broken = broken;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public Set<Curricular> getCurriculars() {
        return curriculars;
    }

    public void setCurriculars(Set<Curricular> curriculars) {
        this.curriculars = curriculars;
    }

    public Set<TargetAudience> getTargetAudiences() {
        return targetAudiences;
    }

    public void setTargetAudiences(Set<TargetAudience> targetAudiences) {
        this.targetAudiences = targetAudiences;
    }


    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getFirmMail(){
        return emailContact;
    }
    
    public void setFirmMail(String emailcontact){
        this.emailContact = emailcontact;
    }

    public String giveOverview() {
        return String.format("%-8s %s %8.2f %d %d %-8s %-8s %-8s %s %s %s ", getName(),
                getDescription(),
                getPrice(),
                getAmount(),
                getBroken(),
                getTargetAudiences(),
                getCurriculars(),
                getFirm(),
                getFirmMail(),
                getEmailContact(),
                getLocation(),
                getLendable());
    }
    
    public void addToReservations(Reservation res){
        reservations.add(res);
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
    
 
    
    public int getAmountAvailable(Date day){
        int aantalGereserveerd =0;
        int aantalNietBinnen =0;
        for(Reservation r : getReservations()){
            if(r.getStartDate().compareTo(day)<=0 && r.getEndDate().compareTo(day)>0){
                aantalGereserveerd+=r.getAmount();
            }
            else if(r.getEndDate().compareTo(day)<=0 && !r.isMaterialReturned()){
                aantalNietBinnen+=r.getAmount();
            }
        }
        int aantal = amount -broken-aantalGereserveerd-aantalNietBinnen;
        
       return aantal;
    }
}
