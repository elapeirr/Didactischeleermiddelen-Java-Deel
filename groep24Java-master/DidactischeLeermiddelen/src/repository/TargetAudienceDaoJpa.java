/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domein.Curricular;
import domein.TargetAudience;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import static repository.GenericDaoJpa.em;

/**
 *
 * @author Axel
 */
public class TargetAudienceDaoJpa extends GenericDaoJpa<TargetAudience> implements TargetAudienceDao {

    public TargetAudienceDaoJpa() {
        super(TargetAudience.class);
    }

    @Override
    public TargetAudience getTargetAudienceByName(String name) throws EntityNotFoundException {
        try {
            return em.createNamedQuery("TargetAudience.findByName", TargetAudience.class).setParameter("targetAudienceName", name).getSingleResult();
        } catch (NoResultException ex) {
            throw new EntityNotFoundException();
        }
    }

}
