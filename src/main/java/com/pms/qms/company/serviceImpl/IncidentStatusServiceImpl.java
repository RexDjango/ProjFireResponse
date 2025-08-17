package com.pms.qms.company.serviceImpl;

import com.pms.qms.company.projection.DataCenterPorjection;
import com.pms.qms.company.projection.IncidentStatusProjection;
import com.pms.qms.company.repository.IncidentStatusRepository;
import com.pms.qms.company.service.IncidentStatusService;
import com.pms.qms.constant.QMSConstant;
import com.pms.qms.constant.SeverityContant;
import com.pms.qms.utils.QMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentStatusServiceImpl implements IncidentStatusService {

    @Autowired
    private IncidentStatusRepository incidentStatusRepository;

    @Override
    public ResponseEntity<?> list() {
        try {
            List<IncidentStatusProjection> incidentStatusProjections=incidentStatusRepository.findAllData();
            if(null!=incidentStatusProjections){
                return new ResponseEntity<>(incidentStatusProjections, HttpStatus.OK);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return QMSUtils.getResponseEntity(QMSConstant.SOMETHING_WENT_WRONG, SeverityContant.error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
