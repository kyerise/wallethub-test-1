/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ef.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "blocked_ip")
@NoArgsConstructor
public class BlockedIp implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date date=new Date();
    private String ip;
    @Lob
    private String comment;

    public BlockedIp(String ip,ParseParameter p) {
        this.ip=ip;
        this.comment=String.format("IP blocked for exeeding %d requests in one %s starting %s",
                p.getThreshold(),p.getDuration()==Duration.daily?"Day":"Hour",p.getStartDate());
    }
    
    
}
