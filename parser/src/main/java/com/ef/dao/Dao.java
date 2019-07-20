/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ef.dao;

import com.ef.dao.exceptions.NonexistentEntityException;
import com.ef.model.BlockedIp;
import com.ef.model.LogLine;
import com.ef.model.ParseParameter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author com4t
 */
public class Dao implements Serializable {
    private static final DateFormat SEARCH_DATE_FORMAT=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Dao() {
        this.emf = Persistence.createEntityManagerFactory("PU");
    }
    private final EntityManagerFactory emf;
    private EntityManager em=null;

    public void initTransaction(){
        em=emf.createEntityManager();
            em.getTransaction().begin();
    }
    public void commitTransaction(){
            em.getTransaction().commit();
            em=null;
    }
    public LogLine createLogLine(LogLine logLine) {
            em.persist(logLine);
            return logLine;
    }
    public BlockedIp createBlockedIpLogLine(BlockedIp blockedIp) {
            em.persist(blockedIp);
            return blockedIp;
    }
    public List<Object> selectIps(ParseParameter p){
        String sqlString="select IP from (SELECT count(*) as num, IP FROM accesslog.log_line " +
"where DATE between '"+SEARCH_DATE_FORMAT.format(p.getStartDate())+"' and '"+
                SEARCH_DATE_FORMAT.format(new Date(p.getStartDate().getTime()+p.getDuration().getMilliseconds()))
                +"' " +
"group by ip order by num desc) s where num>"+p.getThreshold()+";";
        return emf.createEntityManager().createNativeQuery(sqlString).getResultList();
    }

    
}
