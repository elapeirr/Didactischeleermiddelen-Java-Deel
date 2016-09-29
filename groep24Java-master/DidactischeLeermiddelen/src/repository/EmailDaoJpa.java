/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domein.Email;

/**
 *
 * @author Elise Lapeirre
 */
public class EmailDaoJpa extends GenericDaoJpa<Email> implements EmailDao{
    
    public EmailDaoJpa() {
        super(Email.class);
    }
    
}
