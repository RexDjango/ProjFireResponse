package com.pms.qms.company.controllers;

import com.pms.qms.company.service.FacilityService;
import com.pms.qms.constant.QMSConstant;
import com.pms.qms.constant.SeverityContant;
import com.pms.qms.utils.QMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/facility")
public class FacilityController {

    @Autowired
    private FacilityService facilityService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Map<String,String> requestMap){
        try {
            return facilityService.add(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody Map<String,String> requestMap){
        try {
            return facilityService.update(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/listdata")
    public ResponseEntity<?> listdata(){
        try {
            return facilityService.listdata();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
