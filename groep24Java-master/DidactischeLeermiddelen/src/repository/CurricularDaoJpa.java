/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domein.Curricular;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

/**
 *
 * @author Axel
 */
public class CurricularDaoJpa extends GenericDaoJpa<Curricular> implements CurricularDao {

    public CurricularDaoJpa() {
        super(Curricular.class);
    }

    @Override
    public Curricular getCurricularByName(String name) throws EntityNotFoundException {
        try {
            return em.createNamedQuery("Curricular.findByName", Curricular.class).setParameter("curricularName", name).getSingleResult();
        } catch (NoResultException ex) {
            throw new EntityNotFoundException();
        }
    }

}
