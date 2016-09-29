/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domein.Curricular;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author Axel
 */
public interface CurricularDao extends GenericDao<Curricular>  {
    public Curricular getCurricularByName(String name) throws EntityNotFoundException;
}
