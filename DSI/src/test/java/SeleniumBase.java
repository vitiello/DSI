package test.java;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.exception.ReportiumException;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import ru.yandex.qatools.allure.annotations.Attachment;

public class SeleniumBase extends TestBase {

	RemoteWebDriver driver;	
	
	

	@BeforeClass(alwaysRun = true)
	public void baseBeforeClass(ITestContext context) throws MalformedURLException {
		Map<String, String> params = context.getCurrentXmlTest().getAllParameters();

		driver = createDriver(params.get("targetEnvironment"));
		reportiumClient = getReportiumClient(driver);
	}
	
    @AfterClass(alwaysRun = true)
    public void baseAfterClass() {
        System.out.println("Report url = " + reportiumClient.getReportUrl());
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTest(Method method) {
        String testName = method.getDeclaringClass().getSimpleName() + "." + method.getName();
        reportiumClient.testStart(testName, new TestContext());
    }
    
    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult testResult) {
        int status = testResult.getStatus();
        switch (status) {
            case ITestResult.FAILURE:
                reportiumClient.testStop(TestResultFactory.createFailure("An error occurred", testResult.getThrowable()));
                break;
            case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
            case ITestResult.SUCCESS:
                reportiumClient.testStop(TestResultFactory.createSuccess());
                break;
            case ITestResult.SKIP:
                // Ignore
                break;
            default:
                throw new ReportiumException("Unexpected status " + status);
        }
    }
  
    
    
	public RemoteWebDriver createDriver(String targetEnvironment) throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		//use my two phones.
		
		capabilities.setCapability("openDeviceTimeout", 5);
		
		
		
		switch (targetEnvironment) {	
		case "iPhone 6":
			System.out.println("###  iOS ### " + targetEnvironment);
			capabilities.setCapability("platformName", "iOS");
			capabilities.setCapability("deviceName", "4EC7F3A2FA62E31A81C64FE30AA719225E2492F2");
			//capabilities.setCapability("deviceName", "1C3B401545D2CDBEC9D323460D914AD7319F31D9");
			capabilities.setCapability("windTunnelPersona", "GEORGIA");
			
			
			break;
		case "HTC":
			System.out.println("###  HTC ### " + targetEnvironment);
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("deviceName", "FA53XYJ18872");
			capabilities.setCapability("browserName", "mobileChrome");
			capabilities.setCapability("windTunnelPersona", "PETER");
			
			break;		
			
		case "iPad Air 2":
			System.out.println("###  IPAD ### " + targetEnvironment);
			capabilities.setCapability("platformName", "iOS");
			capabilities.setCapability("model", "iPad Air 2");
			capabilities.setCapability("browserName", "mobileSafari");
			break;
		case "Galaxy Tab S2":
			System.out.println("###  GALAXY TAB 2 ### " + targetEnvironment);
			
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("deviceName", "3300A5DB0E4923EB");
			///capabilities.setCapability("browserName", "mobileChrome");
			capabilities.setCapability("browserName", "mobileChrome");
			break;
		case "Chrome 51":
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "7");
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "51");
			capabilities.setCapability("resolution", "1366x768");
			capabilities.setCapability("location", "US East");
			System.out.println("###  CHROME 51 ### " + targetEnvironment);
			
			break;	
		
		case "Internet Explorer 11":
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "8.1");
			capabilities.setCapability("browserName", "Internet Explorer");
			capabilities.setCapability("browserVersion", "11");
			capabilities.setCapability("resolution", "1366x768");
			capabilities.setCapability("location", "US East");
			break;
		
		case "Firefox 46":
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "7");
			capabilities.setCapability("browserName", "Firefox");
			capabilities.setCapability("browserVersion", "46");
			capabilities.setCapability("resolution", "1366x768");
			capabilities.setCapability("location", "US East");
			break;
			
		case "Firefox 45":
			System.out.println("###  FIREFOX 45 ### " + targetEnvironment);
			
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "7");
			capabilities.setCapability("browserName", "Firefox");
			capabilities.setCapability("browserVersion", "45");
			capabilities.setCapability("resolution", "1440x900");
			capabilities.setCapability("location", "US East");
			break;
			
		case "Internet Explorer 10":
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "7");
			capabilities.setCapability("browserName", "Internet Explorer");
			capabilities.setCapability("browserVersion", "10");
			capabilities.setCapability("resolution", "1440x900");
			capabilities.setCapability("location", "US East");
			break;
		
		case "Chrome 50":
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "7");
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "50");
			capabilities.setCapability("resolution", "1024x768");
			capabilities.setCapability("location", "US East");
			break;
		}
		

		
		capabilities.setCapability("user", Constants.USERNAME);
		capabilities.setCapability("password", Constants.PASSWORD);			
		capabilities.setCapability("newCommandTimeout", "120");	
		///capabilities.setCapability("windTunnelPersona", "PETER");
		capabilities.setCapability("scriptName", "DSI Web");

		driver = new RemoteWebDriver (new Resources().getCloudUrl(), capabilities);

		OS = capabilities.getCapability("platformName").toString();
		driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		return driver;
	}

	@Attachment
	protected byte[] saveImage(byte[] imageToSave) {
		return imageToSave;
	}

	protected void closeWebDriver () throws IOException {
		// make sure web driver is closed
		if (!hasQuit(driver)) {

			//driver.close();
			Map<String, Object> params = new HashMap<>(); 
			((JavascriptExecutor) driver).executeScript("mobile:execution:close", params);
			//downloadReport("html"); 
			downloadWTReport();
			driver.quit();	
		}
	}

	protected static boolean hasQuit(RemoteWebDriver driver) {
		return ((RemoteWebDriver)driver).getSessionId() == null;
	}

	@Attachment 
	protected byte[] downloadWTReport() {
		String reportUrl = (String)driver.getCapabilities().getCapability("windTunnelReportUrl");
		String returnString = "<html><head><META http-equiv=\"refresh\" content=\"0;URL=";
		returnString = returnString + reportUrl + "\"></head><body /></html>";

		return returnString.getBytes();
	}

	@Attachment
	protected byte[] downloadReport(String type) throws IOException
	{	
		String command = "mobile:report:download";
		Map<String, String> params = new HashMap<>();
		params.put("type", type);
		String report = (String)((RemoteWebDriver) driver).executeScript(command, params);
		byte[] reportBytes = OutputType.BYTES.convertFromBase64Png(report);
		return reportBytes;
	}

	protected static void switchToContext(RemoteWebDriver driver, String context) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		Map<String,String> params = new HashMap<String,String>();
		params.put("name", context);
		executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
	}	

	protected Boolean textCheckpoint(String textToFind, Integer timeout) {
		perfectoCommand.put("content", textToFind);
		perfectoCommand.put("timeout", timeout);
		Object result = driver.executeScript("mobile:checkpoint:text", perfectoCommand);
		Boolean resultBool = Boolean.valueOf(result.toString());
		perfectoCommand.clear();
		return resultBool;
	}

	@Attachment
	protected byte[] takeScreenshot() {
		System.out.println("Taking screenshot");
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	protected void pointOfInterest(String desc, String status){
		perfectoCommand.clear();
		perfectoCommand.put("description", desc);
		perfectoCommand.put("status", status);
		driver.executeScript("mobile:status:event", perfectoCommand);
		perfectoCommand.clear();	
	}

	

	
	protected void PauseScript(int How_Long_To_Pause) {	
		//convert to seconds
		How_Long_To_Pause=How_Long_To_Pause*1000;
		
	 	try {
 		    Thread.sleep(How_Long_To_Pause);                
 		} catch(InterruptedException ex) {
 		    Thread.currentThread().interrupt();
 		}	
	}
	
	protected void selectValue (String xpath, String visibleText) {
		Select select = new Select (driver.findElementByXPath(xpath));
		select.selectByVisibleText(visibleText);
	}
    
	protected void tapCoordinates (Integer xCoord, Integer yCoord) {
		perfectoCommand.clear();
		perfectoCommand.put("location", xCoord + "," + yCoord);
		driver.executeScript("mobile:touch:tap", perfectoCommand);
		perfectoCommand.clear();
	}
}
