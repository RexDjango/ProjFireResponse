package com.pms.qms.company.serviceImpl;

import com.pms.qms.company.entities.DataCenter;
import com.pms.qms.company.entities.ReportStatus;
import com.pms.qms.company.entities.Role;
import com.pms.qms.company.projection.DataCenterPorjection;
import com.pms.qms.company.repository.DataCenterRepository;
import com.pms.qms.company.repository.ReportStatusRepository;
import com.pms.qms.company.service.DataCenterService;
import com.pms.qms.constant.QMSConstant;
import com.pms.qms.constant.SeverityContant;
import com.pms.qms.utils.QMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DataCenterServiceImpl implements DataCenterService {

    @Autowired
    private DataCenterRepository dataCenterRepository;
    @Autowired
    private ReportStatusRepository reportStatusRepository;

    @Override
    public ResponseEntity<?> add(Map<String, String> requestMap) {
        try {
            dataCenterRepository.save(getDataFromMap(requestMap,false));
            return QMSUtils.getResponseEntity("Data successfully saved.", SeverityContant.success, HttpStatus.CREATED);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> listdata() {
        try {
            List<DataCenterPorjection> dataCenterPorjections=dataCenterRepository.findAllIncedent();
            if(null!=dataCenterPorjections){
                return new ResponseEntity<>(dataCenterPorjections,HttpStatus.OK);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> listactive() {
        try {
            List<DataCenterPorjection> dataCenterPorjections=dataCenterRepository.findAllActive();
            if(null!=dataCenterPorjections){
                return new ResponseEntity<>(dataCenterPorjections,HttpStatus.OK);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> listinactive() {
        try {
            List<DataCenterPorjection> dataCenterPorjections=dataCenterRepository.findUnActive();
            if(null!=dataCenterPorjections){
                List<ReportStatus> reportStatuses=new ArrayList<>();
                dataCenterPorjections.stream().forEach(data->{
                    ReportStatus reportStatus=reportStatusRepository.getById(data.getIncstatusid());
                    reportStatuses.add(reportStatus);
                });

                return new ResponseEntity<>(dataCenterPorjections,HttpStatus.OK);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private DataCenter getDataFromMap(Map<String, String> requestMap, boolean b) {
        DataCenter dataCenter=new DataCenter();
        dataCenter.setContactno(requestMap.get("contact"));
        dataCenter.setFullname(requestMap.get("fullname"));
        dataCenter.setLat(requestMap.get("latitude"));
        dataCenter.setLng(requestMap.get("longitude"));
        dataCenter.setCompaddress(requestMap.get("compaddress"));
        dataCenter.setCityaddress(requestMap.get("cityaddress"));
        dataCenter.setDatefiled(LocalDateTime.now());
        List<ReportStatus> reportStatuses=new ArrayList<>();
        ReportStatus reportStatus = new ReportStatus();
        reportStatus.setIncidentstatusid(1);
        reportStatus.setIncidentdate(LocalDateTime.now());
        reportStatuses.add(reportStatus);
        dataCenter.setReportStatuses(reportStatuses);
        return dataCenter;
    }
}
