/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domein.Material;
import domein.Reservation;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Elise Lapeirre
 */
public class ReservationControllerTest {
    
    public ReservationControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
         Material wereldbol = new Material("Wereldbol", "wereldbol");
                       Reservation r = new Reservation( wereldbol, 5, new GregorianCalendar(2016, 4, 2), new GregorianCalendar(2016, 4, 6), "test@hotmail.com");

    }
    
    @After
    public void tearDown() {
    }




 

    @Test
    public void testGetReservationsOfSelectedWeek() { 
         Material wereldbol = new Material("Wereldbol", "wereldbol");
                               Reservation r = new Reservation( wereldbol, 5, new GregorianCalendar(2016, 4, 2), new GregorianCalendar(2016, 4, 6), "test@hotmail.com");

        System.out.println("getReservationsOfSelectedWeek");
        Calendar pff = new GregorianCalendar(2016,5,8);
        ReservationController instance = new ReservationController();
        instance.addReservation(r);
      List<Reservation> exResult =new ArrayList<>();
        exResult.add(r);
        ObservableList<Reservation>expResult=FXCollections.observableArrayList(exResult);;
        ObservableList<Reservation> result = instance.getReservationsOfSelectedWeek(pff);
        assertEquals(expResult, result);

    }


    
}
