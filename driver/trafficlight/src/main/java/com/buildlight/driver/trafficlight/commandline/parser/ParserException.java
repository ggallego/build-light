package com.buildlight.driver.trafficlight.commandline.parser;

/**
 * @author zutherb
 */
@SuppressWarnings("serial")
public class ParserException extends RuntimeException {
	
    public ParserException(Exception e) {
        super(e);
    }

    public ParserException(String msg) {
        super(msg);
    }
    
}
