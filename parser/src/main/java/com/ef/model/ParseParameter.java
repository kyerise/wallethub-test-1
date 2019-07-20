/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ef.model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

/**
 *
 * @author com4t
 */
@Slf4j
@Data
public class ParseParameter {

    private String filePath;
    private Path accesslog;
    private Date startDate;
    private Duration duration;
    private int threshold;
    private static DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");

    public ParseParameter(String[] args) {
        Options options = new Options();

        Option accesslogO = new Option("a", "accesslog", true, "log file path");
        accesslogO.setRequired(true);
        options.addOption(accesslogO);

        Option startDateO = new Option("s", "startDate", true, "processing Start Date");
        startDateO.setRequired(true);
        options.addOption(startDateO);

        Option durationO = new Option("d", "duration", true, "duration as hourly or daily");
        durationO.setRequired(true);
        options.addOption(durationO);

        Option thresholdO = new Option("t", "threshold", true, "integer threshold");
        thresholdO.setRequired(true);
        options.addOption(thresholdO);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;
            String accesslogString = "";
            String startDateString = "";
            String durationString = "";
            String thresholdString = "";

        try {
            cmd = parser.parse(options, args);
             accesslogString = cmd.getOptionValue("accesslog");
             startDateString = cmd.getOptionValue("startDate");
             durationString = cmd.getOptionValue("duration");
             thresholdString = cmd.getOptionValue("threshold"); 
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }
        //threshold processing
        try{
            threshold=Integer.valueOf(thresholdString);
        }catch(NumberFormatException e){
            System.out.println(e.getMessage());            
            System.out.println("threshold must be and integer");
        }
        //duration processing
        try{
            duration=Duration.valueOf(durationString);
        }catch(Exception e){
            System.out.println(e.getMessage());            
            System.out.println("duration must be hourly or daily");
        }
        try{
            startDate=dateFormat.parse(startDateString);
        }catch(java.text.ParseException e){
            System.out.println(e.getMessage());            
            System.out.println("startDate must be of format yyyy-MM-dd.HH:mm:ss like 2017-01-01.13:00:00");
        }
        try{
            accesslog=Paths.get(accesslogString);
            boolean exists = Files.isReadable(accesslog);
            if(!exists){
                System.out.println("accesslog is not readable");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());            
            //System.out.println("threshold must be and integer");
        }

    }

}
