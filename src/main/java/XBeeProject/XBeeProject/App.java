package XBeeProject.XBeeProject;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.wpan.RxResponseIoSample;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Hello world!
 *
 */
public class App 
{
	private final static Logger log = Logger.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	PropertyConfigurator.configure("log4j.properties");
    	try {
	    	XBee xbee = new XBee();	
	    	
			xbee.open("COM7", 9600);
			System.out.println("Port ouvert !");			
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
    	
		System.out.println("Fin du try");
    }
}
