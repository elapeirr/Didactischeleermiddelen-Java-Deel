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
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.MaterialDaoJpa;
import repository.ReservationDaoJpa;

/**
 *
 * @author Axel
 */
public class ReservationController {

    private ReservationDaoJpa reservationRepo;
    private MaterialDaoJpa materialRepo;
    private Reservation currentReservation;

    public ReservationController() {
        this(true);
    }

    public ReservationController(boolean withInit) {
        materialRepo = new MaterialDaoJpa();
        reservationRepo = new ReservationDaoJpa();
    }

    public void removeReservation(Reservation res) {
        reservationRepo.startTransaction();
        reservationRepo.delete(res);
        reservationRepo.commitTransaction();
    }

    public ObservableList<Reservation> getReservations() {
        List<Reservation> reservations = reservationRepo.findAll();
        for (Reservation r : reservations) {
            r.checkConflicts();
        }

        return FXCollections.observableArrayList(reservations);
    }

    public void returnReservation() {

    }

    public Reservation getCurrentReservation() {
        return currentReservation;
    }

    public void setCurrentReservation(Reservation currentReservation) {
        this.currentReservation = currentReservation;
    }

    public void updateReservation(Reservation r) {
        reservationRepo.startTransaction();
        reservationRepo.update(r);
        reservationRepo.commitTransaction();
    }

    public void addReservation(Reservation r) {

        reservationRepo.startTransaction();
        reservationRepo.insert(r);
        reservationRepo.commitTransaction();

    }

    public ObservableList<Reservation> getReservationsOfSelectedWeek(Calendar pff) {
        Calendar old = pff;
        old.add(Calendar.DAY_OF_MONTH, -1);
        Date d = pff.getTime();

        pff.add(Calendar.DAY_OF_MONTH, 8);
        Date e = pff.getTime();
        List<Reservation> reservations = new ArrayList<>();
        List<Reservation> res = reservationRepo.findAll();

        for (int i = 0; i < res.size(); i++) {
            Reservation r = res.get(i);
            r.checkConflicts();
            if (r.getStartDate().after(d) && r.getStartDate().before(e)) {
                reservations.add(r);
            }

        }
//System.out.println(reservations);
        return FXCollections.observableArrayList(reservations);

    }

    public void checkConcflicts() {
        List<Reservation> reservations = reservationRepo.findAll();
        for (Reservation r : reservations) {
            r.checkConflicts();
        }

    }

    public List<Reservation> getReservedOfMaterial(Material m) {

        List<Reservation> special = new ArrayList<>();
        List<Reservation> reservations = reservationRepo.findAll();
        for (Reservation r : reservations) {

            if (r.getMaterial() == m) {
                special.add(r);
            }

        }

        return special;
    }
}
