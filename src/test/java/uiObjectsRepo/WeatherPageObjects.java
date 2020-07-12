package uiObjectsRepo;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import driverRepo.DriverClass;

public class WeatherPageObjects extends DriverClass{

	private By txt_PinCitySearch = By.cssSelector("input#searchBox.searchBox");
	private By mapIcon_SearchCity = By.xpath("//div[@title='"+City+"']");
	private By txtList_WeatherReport = By.xpath("//span[@class='heading']/b");
	
	public void enterSearchCityText() {
		genericMethods().WaitForPageLoad(driver, 20);
		genericMethods().waitForElement(txt_PinCitySearch);
		driver.findElement(txt_PinCitySearch).sendKeys(City);
	}

	public WebElement getCityMapIcon() {
		genericMethods().waitForElement(mapIcon_SearchCity);
		return driver.findElement(mapIcon_SearchCity);
	}
	
	public HashMap<String,String> getCityWeatherReport_UI() {
		HashMap<String, String> uiValues = new HashMap<String, String>();
		genericMethods().waitForObjectExists(txtList_WeatherReport);
		List<WebElement> reportList = driver.findElements(txtList_WeatherReport);
	    for(WebElement report : reportList) {
	        String[] values = report.getText().split(":");
	        uiValues.put(values[0].trim(), values[1].trim());
	    }
	    return uiValues;
	}
	
}
