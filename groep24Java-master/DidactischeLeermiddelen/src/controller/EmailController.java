/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domein.Email;
import repository.EmailDaoJpa;

/**
 *
 * @author Elise Lapeirre
 */
public class EmailController {
    private EmailDaoJpa emailRepo;

    public EmailController() {
            emailRepo=new EmailDaoJpa();
    }
    
    public void updateEmail(Email email) {
       emailRepo.startTransaction();
     emailRepo.update(email);
       emailRepo.commitTransaction();
    }

    public void removeEmail(Email email) {
        emailRepo.startTransaction();
        emailRepo.delete(email);
        emailRepo.commitTransaction();
    }
    
    public void addEmail(Email email){
       emailRepo.startTransaction();
emailRepo.insert(email);
emailRepo.commitTransaction();
    }
}
