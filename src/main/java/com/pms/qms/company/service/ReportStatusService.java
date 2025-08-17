package com.pms.qms.company.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ReportStatusService {
    ResponseEntity<?> add(Map<String, String> requestMap);
}
