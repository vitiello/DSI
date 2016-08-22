package test.java;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Step;

public class DSI_Web extends SeleniumBase  {
	@Test
	public void test() throws Exception {			
	
		openHomePage( );
		NavigateValidate();
		softAssert.assertAll();
	}

	@Step("Open Homepage")
	private void openHomePage(){
		
		driver.get(DSI_WebObjects.WEB_PAGE);	
		System.out.println("###  Opening Homepage ### ");

	}
	
	@Step("NavigateValidate")
	private void NavigateValidate(){
		
		takeScreenshot();

		if(TGTENV.equals("HTC") || TGTENV.equals("iPhone 6")){
		
		
			textCheckpoint("REQUEST RESOURCES", 60);
			driver.findElementByXPath(DSI_WebObjects.Link_NAV).click();
		
		
			//Setup timers for 2nd event
			long threshold=60000;
			String description="REQUEST RESOURCES VALIDATION ";
			String name= "REQUEST_RESOURCES_VALIDATION";
			long uxTimer2 = test.java.Perfecto.FunctionLibrary.timerGet(driver, "ux");
			test.java.Perfecto.WindTunnelUtils.reportTimer(driver, uxTimer2, threshold, description, name); 	
			test.java.Perfecto.WindTunnelUtils.pointOfInterest(driver, name, "Success");
				
			//Validation and Navigation
			textCheckpoint("ADVERSE EVENTS", 60);
			driver.findElementByXPath(DSI_WebObjects.Link_ADVERSE).click();
		
			textCheckpoint("BENICAR ADVERSE REACTIONS", 60);
			driver.findElementByXPath(DSI_WebObjects.Link_REACTIONS).click();
		
	
		
			//Final Event
			threshold=60000;
			description="BENICAR ADVERSE REACTIONS ";
			name= "BENICAR_ADVERSE_REACTIONS";
			uxTimer2 = test.java.Perfecto.FunctionLibrary.timerGet(driver, "ux");
			test.java.Perfecto.WindTunnelUtils.reportTimer(driver, uxTimer2, threshold, description, name); 	
			test.java.Perfecto.WindTunnelUtils.pointOfInterest(driver, name, "Success");

			textCheckpoint(" The following adverse reactions occurred in", 10);
			textCheckpoint("The withdrawal rates due to", 60);
		}
		
		
		
		else{
			
			driver.findElementByXPath(DSI_WebObjects.Link_REACTIONS_WEB).click();
			textCheckpoint("BENICAR ADVERSE REACTIONS", 60);
			
		}
		
		
	}
	
	
	
}