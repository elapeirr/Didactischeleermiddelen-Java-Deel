/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domein.Reservation;

/**
 *
 * @author Elise Lapeirre
 */
public class ReservationDaoJpa extends GenericDaoJpa<Reservation> implements ReservationDao{

    public ReservationDaoJpa() {
        super(Reservation.class );
    }
    
    
}
