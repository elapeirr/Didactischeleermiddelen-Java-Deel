/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domein.Administrator;
import domein.PopulateDB;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.AdministratorDaoJpa;

/**
 *
 * @author UGent
 */
public class AdministratorController {
    
    private AdministratorDaoJpa administratorRepo;
     
    private Administrator currentAdmin;

    public AdministratorController() {
        this(true);
    }
    
    public AdministratorController(boolean withInit) {
//        if (withInit) {
//            new PopulateDB().run();
//        }
        administratorRepo = new AdministratorDaoJpa();
    }
    
    public void updateAdministrator(Administrator administrator) {
        administratorRepo.startTransaction();
        administratorRepo.update(administrator);
        administratorRepo.commitTransaction();
    }

    public void removeAdministrator(Administrator administrator) {
        administratorRepo.startTransaction();
        administratorRepo.delete(administrator);
        administratorRepo.commitTransaction();
    }
    
    public void addAdministrator(Administrator administrator){
        administratorRepo.startTransaction();
        administratorRepo.insert(administrator);
        administratorRepo.commitTransaction();
    }
    
    public ObservableList<Administrator> getAdministrators() {
        return FXCollections.observableArrayList(administratorRepo.findAll());
    }

    public Administrator getCurrentAdmin() {
        return currentAdmin;
    }

    public void setCurrentAdmin(Administrator currentAdmin) {
        this.currentAdmin = currentAdmin;
    }

    public Administrator findByMail(String mail) {
        List<Administrator> admins = administratorRepo.findAll();
        Administrator bla = null;
        for(Administrator admin : admins){
            if(admin.getEmail().equals(mail)) bla = admin;
        }
        return bla;
    }

    
    
    
    
    
    
    
    
    
    
}
