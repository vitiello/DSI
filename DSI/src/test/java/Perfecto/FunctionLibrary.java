package test.java.Perfecto;

import java.util.Map;
import java.util.HashMap;
import org.openqa.selenium.remote.RemoteWebDriver;

public class FunctionLibrary
{
    public static void scrollToVisualObject(RemoteWebDriver driver, String needle, String swipeParms)
    {
        System.out.println(swipeParms + " to find: " + needle);
        String command = "mobile:checkpoint:text";
        Map<String, Object> Parms = new HashMap<String, Object>();
        Parms.put("content", needle);
        Parms.put("scrolling", "scroll");
        Parms.put("next", swipeParms);
        driver.executeScript(command, Parms);
    }
    
 // Gets the user experience (UX) timer
    public static long timerGet(RemoteWebDriver driver2, String timerType) {
         String command = "mobile:timer:info";
         Map<String,String> params = new HashMap<String,String>();
         params.put("type", timerType);
         long result = (long)driver2.executeScript(command, params);
         return result;
    }
}
