/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domein.Material;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import static repository.GenericDaoJpa.em;

/**
 *
 * @author Axel
 */
public class MaterialDaoJpa extends GenericDaoJpa<Material> implements MaterialDao {
    
    public MaterialDaoJpa() {
        super(Material.class);
    }

    @Override
    public List<String> getFirms() throws EntityNotFoundException {
        try {
            return em.createNamedQuery("Material.findFirms", String.class).getResultList();
        } catch (NoResultException ex) {
            throw new EntityNotFoundException();
        }
    }
    
    public String findBy(String name){
        try {
            return em.createNamedQuery("Material.findByName", String.class).setParameter("materialName", name).getSingleResult();
        } catch (NoResultException ex) {
            throw new EntityNotFoundException();
        }
    }

 
}
