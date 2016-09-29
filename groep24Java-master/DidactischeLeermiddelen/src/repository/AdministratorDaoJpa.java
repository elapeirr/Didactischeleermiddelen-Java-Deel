/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domein.Administrator;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

/**
 *
 * @author UGent
 */
public class AdministratorDaoJpa extends GenericDaoJpa<Administrator> implements AdministratorDao {

    public AdministratorDaoJpa() {
        super(Administrator.class);
    }

    @Override
    public Administrator getAdministratorByMail(String mail) throws EntityNotFoundException {
        try{
            return em.createNamedQuery("Administrator.findByMail",Administrator.class).setParameter("administratorMail", mail).getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
    }

    public String getPassword(String mail) {
         return em.createNamedQuery("Administrator.getPassword",String.class).setParameter("administratorMail", mail).getSingleResult();
    }
    


    

    
    
}
