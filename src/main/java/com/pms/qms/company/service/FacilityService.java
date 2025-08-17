package com.pms.qms.company.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface FacilityService {
    ResponseEntity<?> add(Map<String, String> requestMap);

    ResponseEntity<?> update(Map<String, String> requestMap);

    ResponseEntity<?> listdata();
}
