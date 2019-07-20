/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ef.service;

import com.ef.dao.Dao;
import com.ef.model.BlockedIp;
import com.ef.model.LogLine;
import com.ef.model.ParseParameter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author com4t
 */
@Slf4j
public class LogProcessorImpl implements LogProcessor {

    private ParseParameter parameter;
    private final Dao lineDao = new Dao();

    @Override
    public void process(ParseParameter parameter) {
        this.parameter = parameter;
        try (Stream<String> stream = Files.lines(parameter.getAccesslog())) {
            lineDao.initTransaction();
            stream.
                    //   parallel().
                    filter(s->s!=null&&!s.trim().isEmpty()).map(s -> new LogLine(s)).forEach(lineDao::createLogLine);//.forEach(System.out::println);
            lineDao.commitTransaction();

            lineDao.initTransaction();
            lineDao.selectIps(parameter).stream().map(s->s.toString()).map(s -> new BlockedIp(s, parameter)).map(lineDao::createBlockedIpLogLine).map(s -> s.getIp()).forEach(System.out::println);
            lineDao.commitTransaction();
        } catch (IOException ex) {
            log.error("IO Error:", ex);
            throw new RuntimeException(ex);
        }
    }
}
