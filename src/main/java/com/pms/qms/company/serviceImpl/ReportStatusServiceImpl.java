package com.pms.qms.company.serviceImpl;

import com.pms.qms.company.entities.ReportStatus;
import com.pms.qms.company.repository.ReportStatusRepository;
import com.pms.qms.company.service.ReportStatusService;
import com.pms.qms.constant.QMSConstant;
import com.pms.qms.constant.SeverityContant;
import com.pms.qms.utils.QMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ReportStatusServiceImpl implements ReportStatusService {
    @Autowired
    private ReportStatusRepository reportStatusRepository;

    @Override
    public ResponseEntity<?> add(Map<String, String> requestMap) {
        try {
            reportStatusRepository.save(getDataFromMap(requestMap,false));
            return QMSUtils.getResponseEntity("Data successfully saved.", SeverityContant.success, HttpStatus.CREATED);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ReportStatus getDataFromMap(Map<String, String> requestMap, boolean b) {
        ReportStatus reportStatus=new ReportStatus();
        reportStatus.setAccountid(Integer.valueOf(requestMap.get("accountid")));
        reportStatus.setIncidentstatusid(Integer.valueOf(requestMap.get("statusid")));
        reportStatus.setRemarks(requestMap.get("remarks"));
        reportStatus.setIncidentdate(LocalDateTime.now());
        return reportStatus;
    }
}
