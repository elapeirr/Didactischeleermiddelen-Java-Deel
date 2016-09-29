/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domein.Curricular;
import domein.Email;
import domein.Material;
import domein.Reservation;
import domein.SystemConfiguration;
import domein.TargetAudience;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import repository.CurricularDaoJpa;
import repository.GenericDaoJpa;
import repository.MaterialDaoJpa;
import repository.ReservationDaoJpa;
import repository.TargetAudienceDaoJpa;

/**
 *
 * @author Elise Lapeirre
 */
public class SystemconfigurationController {

    SystemConfiguration sc;

    FileInputStream fis;
    FileOutputStream fos;

    public SystemconfigurationController() {
        try {
            fis = new FileInputStream("sc.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            sc = (SystemConfiguration) ois.readObject();
            ois.close();
        } catch (Exception ex) {
            try {
                fos = new FileOutputStream("sc.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                sc = new SystemConfiguration();
                oos.writeObject(sc);
                oos.close();
            } catch (Exception ex1) {
                Logger.getLogger(SystemconfigurationController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        sc.setReservationRepo(new ReservationDaoJpa());
        sc.setMaterialRepo(new MaterialDaoJpa());
        sc.setTargetaudienceRepo(new TargetAudienceDaoJpa());
        sc.setCurricularRepo(new CurricularDaoJpa());
    }

    public int getReservationperiod() {
        return sc.getReservationperiod();
    }

    public List<TargetAudience> getTargetAudiences() {
        return sc.getTa();
    }

    public List<Curricular> getCurriculars() {
        return sc.getCu();
    }

    public int getMaxProlongation() {
        return sc.getMaxProlongation();
    }

    public int getPickUpDay() {
        return sc.getPickUpDay();
    }

    public int getReturnDay() {
        return sc.getReturnDay();
    }

    public List<String> getLocations() {
        return sc.getLocations();
    }
    
    public List<Email> getEmails() {
        return sc.getEmails();
    }

    public void addTargetAudience(String targetAudience) {
        sc.addTargetAudience(new TargetAudience(targetAudience));
    }

    public void addCurricular(String curricular) {
        sc.addCurricular(new Curricular(curricular));
    }

    public void addLocation(String location) {
        sc.addLocation(location);
        update();
    }
    
    public void addEmail(Email email) {
        sc.addEmail(email);
        update();
    }

    public void save(int reservationPeriod, int pickup, int returnday, int maxLendable) {
        sc.setReservationperiod(reservationPeriod);
        sc.setPickUpDay(pickup);
        sc.setReturnDay(returnday);
        sc.setMaxProlongation(maxLendable);

        update();
    }

    public void update() {
        try {
            fos = new FileOutputStream("sc.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(sc);
            oos.close();
        } catch (Exception ex1) {
            Logger.getLogger(SystemconfigurationController.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }
}
