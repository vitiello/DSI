package test.java;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Step;

public class DSI_Web extends SeleniumBase  {
	@Test
	public void test() throws Exception {			
		openHomePage();
		NavigateValidate();
		softAssert.assertAll();
	}

	@Step("Open Homepage")
	private void openHomePage(){
		driver.get("http://benicarhcp-q.dsi.com");
		
		//Setup timers for first event
		long threshold=25000;
		String description="OPEN HOME PAGE ";
		String name= "OPEN_HOME_PAGE";
		long uxTimer2 = test.java.Perfecto.FunctionLibrary.timerGet(driver, "ux");
		test.java.Perfecto.WindTunnelUtils.reportTimer(driver, uxTimer2, threshold, description, name); 	
	    // Wind Tunnel: Add success point of interest to the Wind Tunnel report
		test.java.Perfecto.WindTunnelUtils.pointOfInterest(driver, name, "Success");

		
		System.out.println("###  Opening Homepage ### ");
		//takeScreenshot();
	}
	
	@Step("NavigateValidate")
	private void NavigateValidate(){
		
		takeScreenshot();
		textCheckpoint("REQUEST RESOURCES", 30);
		driver.findElementByXPath(DSI_WebObjects.Link_NAV).click();
		
		
		//Setup timers for 2nd evend
		long threshold=25000;
		String description="REQUEST RESOURCES VALIDATION ";
		String name= "REQUEST_RESOURCES_VALIDATION";
		long uxTimer2 = test.java.Perfecto.FunctionLibrary.timerGet(driver, "ux");
		test.java.Perfecto.WindTunnelUtils.reportTimer(driver, uxTimer2, threshold, description, name); 	
		// Wind Tunnel: Add success point of interest to the Wind Tunnel report
		test.java.Perfecto.WindTunnelUtils.pointOfInterest(driver, name, "Success");
				
		//Validation and Navigation
		textCheckpoint("ADVERSE EVENTS", 30);
		driver.findElementByXPath(DSI_WebObjects.Link_ADVERSE).click();
		
		textCheckpoint("BENICAR ADVERSE REACTIONS", 30);
		driver.findElementByXPath(DSI_WebObjects.Link_REACTIONS).click();
		
	
		
		//Final Event
		 threshold=25000;
		 description="BENICAR ADVERSE REACTIONS ";
		 name= "BENICAR_ADVERSE_REACTIONS";
		 uxTimer2 = test.java.Perfecto.FunctionLibrary.timerGet(driver, "ux");
		 test.java.Perfecto.WindTunnelUtils.reportTimer(driver, uxTimer2, threshold, description, name); 	
	     // Wind Tunnel: Add success point of interest to the Wind Tunnel report
		 test.java.Perfecto.WindTunnelUtils.pointOfInterest(driver, name, "Success");

		textCheckpoint(" The following adverse reactions occurred in", 10);
		
		
		//takeScreenshot();
		textCheckpoint("The withdrawal rates due to", 30);
		
		
	}
	
	
	
}