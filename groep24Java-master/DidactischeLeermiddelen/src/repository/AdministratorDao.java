/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domein.Administrator;
import domein.Curricular;
import java.util.List;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author UGent
 */
public interface AdministratorDao extends GenericDao<Administrator> {
    
    public Administrator getAdministratorByMail(String mail) throws EntityNotFoundException;
    
    
}
