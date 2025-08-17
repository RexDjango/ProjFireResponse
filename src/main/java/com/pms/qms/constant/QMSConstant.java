package com.pms.qms.constant;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class QMSConstant {
//    System.getProperty("user.dir")
    public static String UPLOAD_DIRECTORY =  "uploads";
    public static String QRCODE_DIRECTORY =  "qrcode";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

//    public static final String base_path="http://172.16.0.156:8088/"; //--UPAR
//    public static final String report_path="\\\\172.16.0.88\\workpay\\report"; //--UPAR
//    public static final LocalDate expire_date=LocalDate.parse("2026-03-24"); //--UPAR
//    public static final String static_root="\\\\172.16.100.5\\workpay\\fileupload\\"; //--MANDALUYONG
//    public static final String base_path="http://172.16.100.5:8088/"; //--MANDALUYONG
//    public static final String report_path="\\\\172.16.100.5\\workpay\\report"; //--MANDALUYONG
    public static final String static_root="\\\\172.16.0.133\\Public Folder\\qms_upload\\"; //--UPAR QMS
    public static final String base_path="http://172.16.0.250:8091/"; //--UPAR QMS
    public static final String report_path="\\\\172.16.0.250\\qmsapp\\report"; //--UPAR QMS

    public static final String geo_path="C:\\Users\\KALI\\Desktop\\FireResponse v1.0\\fireresponsefrontend\\fireresponse-app\\GeoLite2-City.mmdb";
    public static final LocalDate expire_date=LocalDate.parse("2026-01-14"); //--MANDALUYONG
//    public static final String static_root="C:\\qms_upload\\";
//    public static final String base_path="http://localhost:8091/";
//    public static final String report_path="C:\\Users\\REX\\Desktop\\QMS v1.0\\report";

    public static final String SOMETHING_WENT_WRONG="Something Went Wrong.";
    public static final String INVALID_DATA="Invalid Data.";
    public static final String UNAUTHORIZE_ACCESS="Unauthorized access.";

}
