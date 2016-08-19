package test.java;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.exception.ReportiumException;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

public class AppiumBase extends TestBase {
		AppiumDriver driver;
		ReportiumClient reportiumClient;
		
		
		@BeforeClass(alwaysRun = true)
		public void baseBeforeClass(ITestContext context) throws MalformedURLException {
			Map<String, String> params = context.getCurrentXmlTest().getAllParameters();

			driver = createDriver(params.get("targetEnvironment"));
			reportiumClient = getReportiumClient(driver);
			closeApp("NATIVE_APP");
			openApp("NATIVE_APP");	
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
		
		
	    public AppiumDriver createDriver(String targetEnvironment) throws MalformedURLException {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			
			capabilities.setCapability("openDeviceTimeout", 5);
			
			switch (targetEnvironment) {	
			case "iPhone 6":
				System.out.println("###  iOS ### " + targetEnvironment);
				capabilities.setCapability("platformName", "iOS");   
				capabilities.setCapability("deviceName", "4EC7F3A2FA62E31A81C64FE30AA719225E2492F2");
				break;
			case "HTC":
				System.out.println("###  HTC ### " + targetEnvironment);
				capabilities.setCapability("platformName", "Android");
				capabilities.setCapability("deviceName", "FA53XYJ18872");
				break;		
			}
			capabilities.setCapability("windTunnelPersona", "Peter");
			capabilities.setCapability("user", Constants.USERNAME);
			capabilities.setCapability("password", Constants.PASSWORD);			
			capabilities.setCapability("newCommandTimeout", "120");	
			capabilities.setCapability("scriptName", "WebMD Symptom Checker Native");
			OS = capabilities.getCapability("platformName").toString();
			
			if(OS.equals("iOS")) {
				driver = new IOSDriver(new Resources().getCloudUrl(), capabilities);
			
			} else {
				driver = new AndroidDriver(new Resources().getCloudUrl(), capabilities);
			}
			
			driver.context("NATIVE_APP");
			
			driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT, TimeUnit.SECONDS);
			driver.manage().window().maximize();

			return driver;
		}
		

		protected void openApp(String context) {
			if (context.equals("NATIVE_APP")) {
				Map<String, Object> params9 = new HashMap<>();
				params9.put("name", Constants.APP_NAME);
				driver.executeScript("mobile:application:open", params9);
			}
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
		
		@Attachment 
		protected byte[] downloadWTReport() {
			String reportUrl = (String)driver.getCapabilities().getCapability("windTunnelReportUrl");
			String returnString = "<html><head><META http-equiv=\"refresh\" content=\"0;URL=";
			returnString = returnString + reportUrl + "\"></head><body /></html>";

			return returnString.getBytes();
		}
		
		protected void closeApp(String context) {
			if (context.equals("NATIVE_APP")) {
			
				Map<String, Object> params8 = new HashMap<>();
				params8.put("name", Constants.APP_NAME);
				try{
					driver.executeScript("mobile:application:close", params8);
				} catch (WebDriverException e) { }
			}
		}
	
		protected Boolean textCheckpoint(String textToFind, Integer timeout) {
			perfectoCommand.put("content", textToFind);
			perfectoCommand.put("timeout", timeout);
			Object result = driver.executeScript("mobile:checkpoint:text", perfectoCommand);
			Boolean resultBool = Boolean.valueOf(result.toString());
			perfectoCommand.clear();
			
			
			System.out.println("###  result ### " + textToFind + "   " + resultBool);
			
			return resultBool;
		}
		
	    @Attachment
	    protected byte[] takeScreenshot() {
	        System.out.println("Taking screenshot");
	        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	    }
	    
		private static void switchToContext(RemoteWebDriver driver, String context) {
	        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
	        Map<String,String> params = new HashMap<String,String>();
	        params.put("name", context);
	        executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
	    }
		
		public void PauseScript(int How_Long_To_Pause) {
			
			//convert to seconds
			How_Long_To_Pause=How_Long_To_Pause*1000;
			
		 	try {
	 		    Thread.sleep(How_Long_To_Pause);                
	 		} catch(InterruptedException ex) {
	 		    Thread.currentThread().interrupt();
	 		}
			
		}
	    
}
