/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domein.Curricular;
import domein.TargetAudience;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author Axel
 */
public interface TargetAudienceDao extends GenericDao<TargetAudience>  {
    public TargetAudience getTargetAudienceByName(String name) throws EntityNotFoundException;
}
