package com.pms.qms.company.projection;

import java.time.LocalDateTime;

public interface DataCenterPorjection {
    Integer getId();
    String getFullname();
    String getContactno();
    String getLat();
    String getLng();
    Integer getIncstatusid();
    String getIncstatus();
    String getCompaddress();
    String getCityaddress();
    LocalDateTime getDatefiled();
}
