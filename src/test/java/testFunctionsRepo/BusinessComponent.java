package testFunctionsRepo;

import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import driverRepo.DriverClass;

public class BusinessComponent extends DriverClass {

	public void launchURL(String uRL) {
		driver.get(uRL);
		System.out.println("Test Execution Started :" + getTestCaseID() + "--" + getTestName());
		genericMethods().WaitForPageLoad(driver, 10);
		commonPageObjects().closeAlert();
	}

	public void verifyHomePage() {
		reportScreenshot("HomePage");
		Assert.assertEquals(true, commonPageObjects().isLogoDisplayed(), "NDTV logo validataion");
		Assert.assertEquals(true, homePageObjects().isHomeMenuSecDisplayed(), "NDTV Menu section validation");
	}

	public void navWeatherPage() {
		homePageObjects().getSubHeaderNav().click();
		homePageObjects().getWeatherMenuLink().click();
	}

	public void getWeatherDetails_UI() {
		weatherPageObjects().enterSearchCityText();
		weatherPageObjects().getCityMapIcon().click();
		reportScreenshot("WeatherReportPage");
		weatherPageObjects().getCityWeatherReport_UI();
	}

	public void compareReport() {
		SoftAssert softAssert = new SoftAssert();

		Double UI_Temp = Double.valueOf(weatherPageObjects().getCityWeatherReport_UI().get("Temp in Degrees"));
		Double API_Temp = Double.valueOf(apiComponent().getCityWeatherReport_API().get("Temp in Degrees"));
		Double temp_dif = UI_Temp > API_Temp ? UI_Temp - API_Temp : API_Temp - UI_Temp;
		boolean tempFlag = temp_dif > Integer.valueOf(getPropertyValue("tempVar")) ? false : true;
		softAssert.assertTrue(tempFlag, "Temperature difference is above accepted limit - UI vs API : "+UI_Temp +" vs "+API_Temp);
	

		Integer UI_Hum = Integer.valueOf(weatherPageObjects().getCityWeatherReport_UI().get("Humidity").replace("%", ""));
	    Integer API_Hum = Integer.valueOf(apiComponent().getCityWeatherReport_API().get("Humidity"));
	    Integer hum_dif= UI_Hum > API_Hum ? UI_Hum - API_Hum : API_Hum - UI_Hum;
	    boolean humFlag = hum_dif > Integer.valueOf(getPropertyValue("humidityVar")) ? false : true;
		softAssert.assertTrue(humFlag, "Humidity difference is above accepted limit - UI vs API : "+UI_Hum + " vs "+API_Hum);	     

		String wind = weatherPageObjects().getCityWeatherReport_UI().get("Wind");
		Double UI_Wind = Double.valueOf(wind.substring(0,wind.indexOf("KPH")).trim());
		Double API_Wind = Double.valueOf(apiComponent().getCityWeatherReport_API().get("WindSpeed"));
	    Double wind_dif= UI_Wind > API_Wind ? UI_Wind - API_Wind : API_Wind - UI_Wind;
	    boolean windFlag = wind_dif > Integer.valueOf(getPropertyValue("windVar")) ? false : true;
		softAssert.assertTrue(humFlag, "Wind difference is above accepted limit - UI vs API : "+ UI_Wind + " vs "+API_Wind);	      
	    softAssert.assertAll();
	}
}


