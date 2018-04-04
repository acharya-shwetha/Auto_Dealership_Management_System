package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;

/**
 * Created by shwetha on 10/29/2016.
 */
@RestController
public class Controller {
    @Autowired
    private VehicleDao vehicleDao;

    @RequestMapping(value = "/addVehicle",method = RequestMethod.POST)
    public Vehicle addVehicle(@RequestBody Vehicle newVehicle) throws IOException
    {
        vehicleDao.addVehicle(newVehicle);
        return newVehicle;
    }

    @RequestMapping(value="/updateVehicle", method=RequestMethod.PUT)
    public Vehicle updateVehicle(@RequestBody Vehicle newVehicle) throws IOException {
        Vehicle updatedVehicle=vehicleDao.updateVehicle(newVehicle);
        return updatedVehicle;
    }

    @RequestMapping(value="/getVehicle/{id}", method=RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable("id") int id) throws IOException {
        Vehicle veh=vehicleDao.getVehicle(id);
        return veh;
    }

    @RequestMapping(value = "/deleteVehicle/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteVehicle(@PathVariable("id") int id) throws IOException {
        boolean isMatched;
        isMatched=vehicleDao.deleteVehicle(id);
        if(isMatched==true)
        {
            System.out.println("Deleted id is:" +id);
            return new ResponseEntity<String>("deleted", HttpStatus.OK);
        }
        System.out.println("id not found");
        return new ResponseEntity<String>("not found",HttpStatus.OK);
    }

}

