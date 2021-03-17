package XBeeProject.XBeeProject;

import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.wpan.RxResponseIoSample;
import com.rapplogic.xbee.api.zigbee.ZNetRxIoSampleResponse;

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
    	System.out.println(" ");
    	//PropertyConfigurator.configure("log4j.properties");
    	try {
	    	XBee xbee = new XBee();	
	    	
			xbee.open("COM7", 9600);
			System.out.println("\n--------------Port ouvert !------------\n");			
	    	while (true) {
	    		XBeeResponse response = xbee.getResponse();

	    		if (response.getApiId() == ApiId.ZNET_IO_SAMPLE_RESPONSE) {
	    			ZNetRxIoSampleResponse ioSample = (ZNetRxIoSampleResponse) response;
	    			
	    			System.out.println("Received a sample from " + ioSample.getRemoteAddress64());

	    			System.out.println("Analog D0 (pin 20) 10-bit reading is " + ioSample.getAnalog0());
	    			//System.out.println("Digital D4 (pin 11) is " + (ioSample.isD4On() ? "on" : "off"));
	    			
	    			CoSQL connexion = new CoSQL();
	    			connexion.Enregistrer(ioSample.getRemoteAddress64().toString(),ioSample.getAnalog0());
	    		}
	    	}	
    	} catch (XBeeException e) {
    		
    	}
    	
		System.out.println("Fin du try");
		
    }
}
