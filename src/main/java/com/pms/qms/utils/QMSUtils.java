package com.pms.qms.utils;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class QMSUtils {
    private QMSUtils() {
    }
    public static ResponseEntity<String> getResponseEntity(String responseMessage, String responseSeverity, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"message\":\""+responseMessage+"\",\"severity\":\""+responseSeverity+"\"}", httpStatus);
    }

    public static ResponseEntity<String> getResponseEntity1(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>(responseMessage, httpStatus);
    }

    public static String getUUID(){
        Date date=new Date();
        long time= date.getTime();
        return "Leave-"+time;
    }

    public static String getDateNow() {
        Date date = new Date();
        String strDateFormat = "MM/dd/yyyy hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        return formattedDate;
    }

    public static JSONArray getJsonArrayFromString(String data)throws JSONException {
        JSONArray jsonArray=new JSONArray(data);
        return jsonArray;
    }

    public static Map<String, Object> getMapFromJson(String data){
        log.info("Inside getMapFromJson {}",data);
        if(!Strings.isNullOrEmpty(data))
            return new Gson().fromJson(data,new TypeToken<Map<String,Object>>(){}.getType());
        return new HashMap<>();
    }
}
