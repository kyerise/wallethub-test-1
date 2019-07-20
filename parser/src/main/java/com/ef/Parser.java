/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ef;

import com.ef.service.LogProcessor;
import com.ef.model.ParseParameter;
import com.ef.service.LogProcessorImpl;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author com4t
 */
@Slf4j
public class Parser {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Parser parser=new Parser();
        parser.parseLog(args);
    }
    
    public void parseLog(String[] args){
        LogProcessor processor=new LogProcessorImpl();
        ParseParameter parameter=new ParseParameter(args);
        processor.process(parameter);
    }
    
}
