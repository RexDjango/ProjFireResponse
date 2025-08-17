package com.pms.qms.company.repository;

import com.pms.qms.company.entities.IncidentStatus;
import com.pms.qms.company.projection.IncidentStatusProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentStatusRepository extends JpaRepository<IncidentStatus,Integer> {
    @Query(value = "select * from incidentstatus",nativeQuery = true)
    List<IncidentStatusProjection> findAllData();

    IncidentStatus findByName(String name);
}
