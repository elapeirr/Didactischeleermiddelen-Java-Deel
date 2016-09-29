/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import repository.CurricularDaoJpa;
import repository.GenericDaoJpa;
import repository.MaterialDaoJpa;
import repository.ReservationDaoJpa;
import repository.TargetAudienceDaoJpa;

/**
 *
 * @author Elise Lapeirre
 */
public class SystemConfiguration implements Serializable {

    private int reservationperiod;
    private int maxProlongation;
    private int pickUpDay;
    private int returnDay;
    private List<String> locations;
    private List<Email> emails;

    private transient GenericDaoJpa<Reservation> reservationRepo;
    private transient GenericDaoJpa<Material> materialRepo;
    private transient GenericDaoJpa<TargetAudience> targetaudienceRepo;
    private transient GenericDaoJpa<Curricular> curricularRepo;

    public SystemConfiguration() {
        locations = new ArrayList<>();
        emails = new ArrayList<>();

        pickUpDay = 0;
        returnDay = 4;
        maxProlongation = 2;
        reservationperiod = 5;
        
        locations.add("Locatie");
    }

    public int getReservationperiod() {
        return reservationperiod;
    }

    public void setReservationperiod(int reservationperiod) {
        if (reservationperiod < 0) {
            throw new NumberFormatException("Getal moet groter zijn dan 0");
        }

        this.reservationperiod = reservationperiod;
    }

    public int getMaxProlongation() {
        return maxProlongation;
    }

    public void setMaxProlongation(int maxProlongation) {
        if (maxProlongation < 0) {
            throw new NumberFormatException("Getal moet groter zijn dan 0");
        }

        this.maxProlongation = maxProlongation;
    }

    public int getPickUpDay() {
        return pickUpDay;
    }

    public void setPickUpDay(int pickUpDay) {
        if (pickUpDay > 6 || pickUpDay < 0) {
            throw new NumberFormatException("Kies een geldige weekdag");
        }

        this.pickUpDay = pickUpDay;
    }

    public int getReturnDay() {
        return returnDay;
    }

    public void setReturnDay(int returnDay) {
        if (returnDay > 6 || returnDay < 0) {
            throw new NumberFormatException("Kies een geldige weekdag");
        }

        this.returnDay = returnDay;
    }

    public List<TargetAudience> getTa() {
        return targetaudienceRepo.findAll();
    }

    public List<Curricular> getCu() {
        return curricularRepo.findAll();
    }

    public void setReservationRepo(GenericDaoJpa<Reservation> reservationRepo) {
        this.reservationRepo = reservationRepo;
    }

    public void setMaterialRepo(GenericDaoJpa<Material> materialRepo) {
        this.materialRepo = materialRepo;
    }

    public void setTargetaudienceRepo(GenericDaoJpa<TargetAudience> targetaudienceRepo) {
        this.targetaudienceRepo = targetaudienceRepo;
    }

    public void setCurricularRepo(GenericDaoJpa<Curricular> curricularRepo) {
        this.curricularRepo = curricularRepo;
    }

    public void addTargetAudience(TargetAudience targetAudience) {
        targetaudienceRepo.insert(targetAudience);
    }

    public void addCurricular(Curricular curricular) {
        curricularRepo.insert(curricular);
    }

    public void addLocation(String location) {
        locations.add(location);
    }
    
    public void addEmail(Email email) {
        emails.add(email);
    }

    public List<String> getLocations() {
        return locations;
    }

    public List<Email> getEmails() {
        return emails;
    }
}
