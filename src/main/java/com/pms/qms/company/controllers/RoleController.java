package com.pms.qms.company.controllers;

import com.pms.qms.constant.QMSConstant;
import com.pms.qms.constant.SeverityContant;
import com.pms.qms.company.service.RoleService;
import com.pms.qms.utils.QMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseEntity<?> list(){
        try {
            return roleService.list();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
