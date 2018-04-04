package com.example;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by shwetha on 10/29/2016.
 */
@Component
public class MyTasks {
    RestTemplate restTemplate=new RestTemplate();
    int id=0;
    @Scheduled(fixedRate = 1000)
    public void addVehicle()
    {
        //to create id
        id++;
        //to create a random string for makeModel
        String alpha = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder( 20 );
        for( int i = 0; i < 20; i++ )
            sb.append( alpha.charAt( secureRandom.nextInt(alpha.length()) ) );
        String makeModel=sb.toString();
        //to create a random number for year bw 1986-2016
        Random random = new Random();
        int min = 1986;
        int max = 2016;
        int year = random.nextInt(max-min) + min;
        //to create a random number for retailPrice bw 15000-45000
        double min1 = 15000.0;
        double max1 = 45000.0;
        double retailPrice = min1 + (max1 - min1) * random.nextDouble();
        //to create a vehicle object with desired id,makeModel,year and retailPrice
        Vehicle veh=new Vehicle(id,makeModel,year,retailPrice);
        veh.setId(id);
        veh.setMakeModel(makeModel);
        veh.setYear(year);
        veh.setRetailPrice(retailPrice);
        String url="http://localhost:8080/addVehicle";
        Vehicle v=restTemplate.postForObject(url,veh,Vehicle.class);
        System.out.println(v.toString());
    }
    @Scheduled(fixedRate=2000)
    public void updateVehicle()
    {
        Vehicle updateVehicle=new Vehicle();
        Random random = new Random();
        int min = 1;
        int max = 10;
        int id = random.nextInt(max-min) + min;
        Vehicle v1=restTemplate.getForObject("http://localhost:8080/getVehicle/"+id,Vehicle.class);
        if (v1 != null ) {
            updateVehicle.setId(id);
            updateVehicle.setMakeModel("111-111-1111");
            updateVehicle.setYear(1111);
            updateVehicle.setRetailPrice(1111111.11);
            restTemplate.put("http://localhost:8080/updateVehicle", updateVehicle);
        }
    }

    @Scheduled(fixedRate = 1500)
    public void deleteVehicle()
    {
        Random random = new Random();
        int min = 1;
        int max = 10;
        int id = random.nextInt(max-min) + min;
        System.out.println("deleting id: "+id);
        restTemplate.delete("http://localhost:8080/deleteVehicle/"+id);
    }
}
