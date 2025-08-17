package com.pms.qms.company.serviceImpl;

import com.pms.qms.company.entities.Facility;
import com.pms.qms.company.projection.DataCenterPorjection;
import com.pms.qms.company.projection.FacilityProjection;
import com.pms.qms.company.repository.FacilityRepository;
import com.pms.qms.company.service.FacilityService;
import com.pms.qms.constant.QMSConstant;
import com.pms.qms.constant.SeverityContant;
import com.pms.qms.utils.QMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FacilityServiceImpl implements FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;

    @Override
    public ResponseEntity<?> add(Map<String, String> requestMap) {
        try {
            facilityRepository.save(getDataFromMap(requestMap,false));
            return QMSUtils.getResponseEntity("Data successfully saved.", SeverityContant.success, HttpStatus.CREATED);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> update(Map<String, String> requestMap) {
        try {
            facilityRepository.save(getDataFromMap(requestMap,true));
            return QMSUtils.getResponseEntity("Data successfully updated.", SeverityContant.success, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> listdata() {
        try {
            List<FacilityProjection> facilityProjections=facilityRepository.findFacility();
            if(null!=facilityProjections){
                return new ResponseEntity<>(facilityProjections,HttpStatus.OK);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Facility getDataFromMap(Map<String, String> requestMap, boolean isValid) {
        Facility facility=new Facility();
        if(isValid==true){
            facility.setId(Integer.valueOf(requestMap.get("id")));
        }
        facility.setName(requestMap.get("facility"));
        facility.setAddress(requestMap.get("address"));
        facility.setContactno(requestMap.get("contactno"));
        facility.setLat(requestMap.get("lat"));
        facility.setLng(requestMap.get("lng"));
        return facility;
    }
}
