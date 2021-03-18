package XBeeProject.XBeeProject;

import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.AtCommand;
import com.rapplogic.xbee.api.AtCommandResponse;
import com.rapplogic.xbee.api.RemoteAtRequest;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress16;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeRequest;
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
	    			
	    			System.out.println("----- " +ioSample.getRemoteAddress64()+" : " + ioSample.getAnalog0());
	    			//System.out.println("Digital D4 (pin 11) is " + (ioSample.isD4On() ? "on" : "off"));
	    			
	    			CoSQL connexion = new CoSQL();
	    			connexion.Enregistrer(ioSample.getRemoteAddress64().toString(),ioSample.getAnalog0());
	    			
	    			if(ioSample.getAnalog0()<400) {
	    				try {
	    					AtCommand request2 = new AtCommand("D0", 5);
	    					AtCommandResponse response2 = (AtCommandResponse) xbee.sendSynchronous(request2, 5000);
	    				} catch (Exception e) {
	    				    // no response was received in the allotted time
	    					System.out.println("/!\\ error at if getAnalog<400 local : "+e);
	    				}
	    				try {
	    					RemoteAtRequest request = new RemoteAtRequest(ioSample.getRemoteAddress64(), "D1", new int [] {0x05} );
		    		           xbee.sendAsynchronous(request);
	    				} catch (Exception e) {
	    				    // no response was received in the allotted time
	    					System.out.println("/!\\ error at if getAnalog<400 remote : "+e);
	    				}
	    			}
	    			else {
	    				try {
	    					AtCommand request2 = new AtCommand("D0", 0);
	    					AtCommandResponse response2 = (AtCommandResponse) xbee.sendSynchronous(request2, 5000);
	    				} catch (Exception e) {
	    				    // no response was received in the allotted time
	    					System.out.println("/!\\ error at if getAnalog>=400 local : "+e);
	    				}
	    				
	    				try {
	    					RemoteAtRequest request = new RemoteAtRequest(ioSample.getRemoteAddress64(), "D1", 0);
	    		            XBeeResponse rsp = xbee.sendSynchronous(request, 5000);
	    		            System.out.println("response : "+rsp);
	    				} catch (Exception e) {
	    				    // no response was received in the allotted time
	    					System.out.println("/!\\ error at if getAnalog>=400 remote : "+e);
	    				}
	    			}
	    		}
	    	}	
    	} catch (XBeeException e) {
    		
    	}
    	
		System.out.println("Fin du try");
		
    }
}
