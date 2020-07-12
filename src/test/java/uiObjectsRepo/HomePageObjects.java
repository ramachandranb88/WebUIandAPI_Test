package uiObjectsRepo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import commonFunctionsRepo.GenericMethods;
import driverRepo.DriverClass;

public class HomePageObjects extends DriverClass {

	private By home_MenuHeader = By.id("header2");
	private By icon_SubNav     = By.id("h_sub_menu");
	private By menuLnk_Weather = By.linkText("WEATHER");

	// Callable method to verify home menu section
	public boolean isHomeMenuSecDisplayed() {
		genericMethods().waitForObjectExists(home_MenuHeader, GenericMethods.MAX_TIMEOUT);
		return driver.findElement(home_MenuHeader).isDisplayed();
	}
	
	// Callable method to get sub menu nav icon
	public WebElement getSubHeaderNav() {
		genericMethods().waitForObjectClickable(icon_SubNav);
		return driver.findElement(icon_SubNav);
	}
	
	// Callable method to get menu link Weather
	public WebElement getWeatherMenuLink() {
		genericMethods().waitForObjectClickable(menuLnk_Weather);
		return driver.findElement(menuLnk_Weather);
	}
	
	
	

}
