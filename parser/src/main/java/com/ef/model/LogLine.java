/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ef.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author com4t
 */
@Data
@Slf4j
@Entity
@Table(name = "log_line")
@NoArgsConstructor
public class LogLine implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date;
    private String ip;
    private String request;
    private int status;
    private String userAgent;
    private static final DateFormat DATE_FORMAT=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public LogLine(String logLine) {
        String[] logs=logLine.replace("\"", "").split("\\|");
        try {
            date=DATE_FORMAT.parse(logs[0]);
        } catch (ParseException ex) {
            log.error("line with error:{}:field:{}",logLine,logs[0]);
            throw new RuntimeException(ex);
        }
        ip=logs[1];
        request=logs[2];
        try {
        status=Integer.parseInt(logs[3]);
        } catch (Exception ex) {
            log.error("line with error:{}:field:{}",logLine,logs[3]);
            throw new RuntimeException(ex);
        }
        userAgent=logs[4];
    }    
}
