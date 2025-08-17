package com.pms.qms.company.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface DataCenterService {
    ResponseEntity<?> add(Map<String, String> requestMap);

    ResponseEntity<?> listdata();

    ResponseEntity<?> listactive();

    ResponseEntity<?> listinactive();
}
