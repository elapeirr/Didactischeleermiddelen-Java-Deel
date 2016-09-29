/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domein.Administrator;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextField;
import repository.AdministratorDaoJpa;

/**
 *
 * @author UGent
 */
public class LoginController {
    
    private AdministratorDaoJpa administratorRepo;

    public LoginController() {
       administratorRepo=new AdministratorDaoJpa();
    }
    
    public boolean loginCorrect(String email, String password){
     Boolean bool =false;
        List<Administrator>admins = administratorRepo.findAll();
     // System.out.println(admins);
        List<Administrator>hulp= new ArrayList<>();
        for(int i=0;i<admins.size();i++){
        if(admins.get(i).getEmail().equals(email)){
        hulp.add(admins.get(i));
        }
        
        if(!hulp.isEmpty() && hulp.get(0).getPassword().equals(password)){
        bool=true;
        }
//        if(administratorRepo.getAdministratorByMail(email)!=null){
//           String pw = administratorRepo.getPassword(email);
//           return pw.equals(password); 
//        }
//        else return false;
   }
        return bool;
}

    public boolean isHeadAdministratror(String email){
        
        Administrator admin = new Administrator();
        List<Administrator>admins = administratorRepo.findAll();
        List<Administrator>hulp= new ArrayList<>();
        for(int i=0;i<admins.size();i++){
        if(admins.get(i).getEmail().equals(email)){
        hulp.add(admins.get(i));
        }
        
    }
        if(!hulp.isEmpty()){
           admin = hulp.get(0); 
        }
        
        return admin.isHeadAdministrator();
    }

}
