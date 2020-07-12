package uiObjectsRepo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import driverRepo.DriverClass;

public class CommonPageObjects extends DriverClass {
	
    private By btn_alertNotNow = By.xpath(".//*[@class='notnow']");
	public By pageLogo    = By.className("ndtvlogo");
	
	

	
	public void closeAlert() {
		genericMethods().waitForObjectClickable(btn_alertNotNow);
		driver.findElement(btn_alertNotNow).click();
	}
	
	public boolean isLogoDisplayed() {	
        genericMethods().waitForObjectExists(pageLogo);
        return driver.findElement(pageLogo).isDisplayed();
	}

	
	
}
