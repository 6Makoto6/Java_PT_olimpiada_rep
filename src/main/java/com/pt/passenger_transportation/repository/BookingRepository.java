/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.pt.passenger_transportation.repository;

import com.pt.passenger_transportation.entity.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 8314
 */

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer>{
    
}
