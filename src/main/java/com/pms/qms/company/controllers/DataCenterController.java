package com.pms.qms.company.controllers;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.pms.qms.company.service.DataCenterService;
import com.pms.qms.constant.QMSConstant;
import com.pms.qms.constant.SeverityContant;
import com.pms.qms.utils.QMSUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.net.InetAddress;
import java.util.Map;

import static com.pms.qms.constant.QMSConstant.geo_path;

@RestController
@RequestMapping("/api/datacenter")
public class DataCenterController {

    @Autowired
    private DataCenterService dataCenterService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Map<String,String> requestMap){
        try {
            return dataCenterService.add(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/listinactive")
    public ResponseEntity<?> listinactive(){
        try {
            return dataCenterService.listinactive();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/listactive")
    public ResponseEntity<?> listactive(){
        try {
            return dataCenterService.listactive();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/listdata")
    public ResponseEntity<?> listdata(){
        try {
            return dataCenterService.listdata();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(HttpServletRequest request){
        try {
            File database = new File(geo_path);

// This reader object should be reused across lookups as creation of it is
// expensive.
            DatabaseReader reader = new DatabaseReader.Builder(database).build();

// If you want to use caching at the cost of a small (~2MB) memory overhead:
// new DatabaseReader.Builder(file).withCache(new CHMCache()).build();


//            System.out.println("Client IP Address: " + request.getRemoteAddr());
            InetAddress ipAddress = InetAddress.getByName("222.127.179.19");
//
            CityResponse response = reader.city(ipAddress);
//
            City city = response.getCity();
            System.out.println(city.getName());
            System.out.println(city.getNames());
            System.out.println(city.getGeoNameId());
            System.out.println(city.getConfidence());
            System.out.println(city.getClass());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
