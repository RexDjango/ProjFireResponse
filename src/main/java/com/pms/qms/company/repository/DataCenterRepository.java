package com.pms.qms.company.repository;

import com.pms.qms.company.entities.DataCenter;
import com.pms.qms.company.projection.DataCenterPorjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataCenterRepository extends JpaRepository<DataCenter,Integer> {
    @Query(value = "select id,fullname,contactno,lat,lng,compaddress,cityaddress,datefiled from datacenter",nativeQuery = true)
    List<DataCenterPorjection> findAllIncedent();

    @Query(value = "select a.id,a.fullname,a.contactno,a.lat,a.lng,a.compaddress,a.cityaddress,a.datefiled from datacenter a inner join report_status b on b.datacenter_id=a.id inner join reportstatus c on c.id=b.report_id " +
            "where c.incidentstatusid!='12'",nativeQuery = true)
    List<DataCenterPorjection> findAllActive();

    @Query(value = "select a.id,a.fullname,a.contactno,a.lat,a.lng,a.compaddress,a.cityaddress,a.datefiled from datacenter a inner join report_status b on b.datacenter_id=a.id inner join reportstatus c on c.id=b.report_id " +
            "where c.incidentstatusid!='1'",nativeQuery = true)
    List<DataCenterPorjection> findUnActive();
}
