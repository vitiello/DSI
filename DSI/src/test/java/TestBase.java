package test.java;


import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.asserts.SoftAssert;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class TestBase {
	public DesiredCapabilities capabilities;
	Map<String, Object> perfectoCommand = new HashMap<>();
	protected SoftAssert softAssert = new SoftAssert();
	String OS;
	ReportiumClient reportiumClient;
	
	
    protected void sleep(Integer time){
    	try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    protected static ReportiumClient getReportiumClient(RemoteWebDriver driver){
		PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
				.withProject(new Project("WebMD Web" , "1.0")) //Optional
				.withContextTags("Regression" , "SampleTag1" , "SampleTag2") //Optional 
				.withWebDriver(driver)
				.build();

		return new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
	}
    
    protected static ReportiumClient getReportiumClient(IOSDriver driver){
		PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
				.withProject(new Project("WebMD Native" , "1.0")) //Optional
				.withContextTags("Regression" , "SampleTag1" , "SampleTag2") //Optional 
				.withWebDriver(driver)
				.build();

		return new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
	}
    
    protected static ReportiumClient getReportiumClient(AndroidDriver driver){
		PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
				.withProject(new Project("WebMD Native" , "1.0")) //Optional
				.withContextTags("Regression" , "SampleTag1" , "SampleTag2") //Optional 
				.withWebDriver(driver)
				.build();

		return new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
	}
    
    
	
}
