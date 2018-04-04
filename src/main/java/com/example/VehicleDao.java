package com.example;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by shwetha on 11/7/2016.
 */
@Repository
@Transactional

public class VehicleDao {
    @PersistenceContext
    private EntityManager entityManager;
    int id=1;
    public void addVehicle(Vehicle newVehicle)
    {
        entityManager.persist(newVehicle);
    }

    public Vehicle updateVehicle(Vehicle newVehicle)
    {
        Vehicle v1=entityManager.find(Vehicle.class,newVehicle.getId());
        if (v1 != null ) {
            entityManager.getTransaction().begin();
            v1.setId(newVehicle.getId());
            v1.setMakeModel(newVehicle.getMakeModel());
            v1.setYear(newVehicle.getYear());
            v1.setRetailPrice(newVehicle.getRetailPrice());
            entityManager.getTransaction().commit();
            return v1;
        }
        return null;
    }

    public Vehicle getVehicle(int id) {
        return entityManager.find(Vehicle.class, id);
    }

    public boolean deleteVehicle(int id) {
        Vehicle findVehicle = entityManager.find(Vehicle.class, id);
        if (findVehicle != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(findVehicle);
            entityManager.getTransaction().commit();
            return true;
        }
        return false;
    }
}
