package com.pms.qms.company.controllers;

import com.pms.qms.company.entities.IncidentStatus;
import com.pms.qms.company.service.IncidentStatusService;
import com.pms.qms.constant.QMSConstant;
import com.pms.qms.constant.SeverityContant;
import com.pms.qms.utils.QMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/incidentstatus")
public class IncidentStatusController {
    @Autowired
    private IncidentStatusService incidentStatusService;

    @GetMapping("/list")
    public ResponseEntity<?> list(){
        try {
            return incidentStatusService.list();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
