package com.pms.qms.company.repository;

import com.pms.qms.company.entities.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportStatusRepository extends JpaRepository<ReportStatus,Integer> {
}
