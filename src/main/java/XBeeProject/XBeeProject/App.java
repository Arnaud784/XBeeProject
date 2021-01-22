package XBeeProject.XBeeProject;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.wpan.RxResponseIoSample;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	try {
    	XBee xbee = new XBee();	
    	
		xbee.open("COM15", 9600);
    				
    	while (true) {
    	    RxResponseIoSample ioSample;
    	    
			ioSample = (RxResponseIoSample) xbee.getResponse();

    	    System.out.println("We received a sample from " + ioSample.getSourceAddress());	
    				
    	    if (ioSample.containsAnalog()) {
    	        System.out.println("10-bit temp reading (pin 20) is " + ioSample.getSamples()[0].getAnalog0());
    	    }
    	}
    	} catch (XBeeException e) {
    		
    	}
    }
}
