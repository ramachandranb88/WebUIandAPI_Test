package commonFunctionsRepo;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import driverRepo.DriverClass;

public class GenericMethods  {

	public static final int MAX_TIMEOUT = 20;
	public static final int MIN_TIMEOUT = 5;
	
	Actions action = new Actions(DriverClass.driver);

	
	
     //Callable Method to wait until page load is complete for defined timeout seconds	
	 public  void WaitForPageLoad(WebDriver driver, int WaitInSeconds)
	 {
		    JavascriptExecutor js=(JavascriptExecutor) driver;
		   for(int i =0; i<=WaitInSeconds;i++)
		   {
		    if(js.executeScript("return document.readyState").equals("complete"))
		    {
		    	return;
		    }
		   }
		   
		//Need to Add user defined exception
	 }
	
	
 	//waitForElement 
	public void waitForElement(By locator) {
		  WebDriverWait wait = new WebDriverWait(DriverClass.driver, 5);		  
	        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	
	// UserDefined timeOutSeconds
	public void waitForElement(By locator, long timeOutSeconds) {
		  WebDriverWait wait = new WebDriverWait(DriverClass.driver, timeOutSeconds);		  
	        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	public boolean waitForObjectClickable(By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(DriverClass.driver, MAX_TIMEOUT);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	//Callable Method to get Parent Element - Return WebElement
	public WebElement getParent(WebElement element) {
		return element.findElement(By.xpath("parent::*"));
	}
	
	
	public boolean isObjectExists(By locator) {
		List<WebElement> elements = DriverClass.driver != null ? DriverClass.driver.findElements(locator) : null;
		return (elements != null && elements.size() > 0) ? true : false;
	}
	
	
	
	public boolean isObjectExists(By locator, int waitInSeconds) {
		
		int retryCount = 0;
		List<WebElement> elements = DriverClass.driver != null ? DriverClass.driver.findElements(locator) : null;
		do{
		if(elements.size()>0) break;
		try{Thread.sleep(1000);}catch(Exception e) {}
	    elements = DriverClass.driver.findElements(locator);
		retryCount++;
		}while (retryCount < waitInSeconds);
		
		return (elements != null && elements.size() > 0) ? true : false;
		
	}
	
	

	public boolean waitForObjectExists(By locator) {
		return waitForObjectExists(locator, MAX_TIMEOUT);
	}
	

	public boolean waitForObjectExists(WebElement pElement, By locator) {
		return waitForObjectExists(pElement, locator, MAX_TIMEOUT);
	}
	


	public boolean waitForObjectExists(By locator, int timeout) {
		ExecutorService executor = Executors.newCachedThreadPool();
		WaitForObjectExists task = new WaitForObjectExists(DriverClass.driver, null, locator);
		Future<Boolean> future = executor.submit(task);
		try {
			return future.get(timeout, TimeUnit.SECONDS);
		} catch (Exception e) {
		} finally {
			executor.shutdownNow();
		}
		return false;
	}
	
	
	public boolean waitForObjectExists(WebElement pElement, By locator, int timeout) {
		ExecutorService executor = Executors.newCachedThreadPool();
		WaitForObjectExists task = new WaitForObjectExists(null, pElement, locator);
		Future<Boolean> future = executor.submit(task);
		try {
			return future.get(timeout, TimeUnit.SECONDS);
		} catch (Exception e) {
		} finally {
			executor.shutdownNow();
		}
		return false;
	}
	
	
	public boolean checkElementExists(String objDesc, By locator) {
		ExecutorService executor = Executors.newCachedThreadPool();
		CheckForObjectExists task = new CheckForObjectExists(DriverClass.driver, null, locator);
		Future<Boolean> future = executor.submit(task);
		try {
			if (future.get(MIN_TIMEOUT, TimeUnit.SECONDS))
				return true;
		} catch (TimeoutException e) {
		} catch (Exception e) {
		} finally {
			executor.shutdownNow();
		}
		return false;
	}
	

	public boolean checkElementExists(WebElement pElement, String objDesc, By locator) {
		ExecutorService executor = Executors.newCachedThreadPool();
		CheckForObjectExists task = new CheckForObjectExists(null, pElement, locator);
		Future<Boolean> future = executor.submit(task);
		try {
			if (future.get(MIN_TIMEOUT, TimeUnit.SECONDS))
				return true;
		} catch (TimeoutException e) {
		} catch (Exception e) {
		} finally {
			executor.shutdownNow();
		}
             return false;
		}
	
	//Callable Method to get Actions class events - Move to Element/hover

	 public void MoveToElement(WebElement Element)
	    {
	        action.moveToElement(Element).build().perform();
	    }
	  
	 //Callable Method to Actions class events - Click and Hold
	 public void ClickAndHold(WebElement Element)
	    {
	    	action.clickAndHold(Element).build().perform();
	    }
	
	// Generic Resusuable Methods class Implementation
	class WaitForObjectExists implements Callable<Boolean> {
		WebDriver driver = null;
		WebElement pElement = null;
		By locator = null;

		WaitForObjectExists(WebDriver driver, WebElement pElement, By locator) {
			this.driver = driver;
			this.pElement = pElement;
			this.locator = locator;
		}

		@Override
		public Boolean call() throws Exception {
			for (;;) {
				try {
					Thread.sleep(500);
					if ((driver != null && driver.findElements(locator).size() != 0)
							|| (pElement != null && pElement.findElements(locator).size() != 0))
						return true;
				} catch (Exception e) { }
			}
		}
	}

	
	//Generic Reusuable Methods Class Implementation
	class CheckForObjectExists implements Callable<Boolean> {
		WebDriver driver = null;
		WebElement pElement = null;
		By locator = null;

		CheckForObjectExists(WebDriver driver, WebElement pElement, By locator) {
			this.driver = driver;
			this.pElement = pElement;
			this.locator = locator;
		}

		@Override
		public Boolean call() throws Exception {
			Thread.sleep(500);
			List<WebElement> elements = driver != null ? driver.findElements(locator)
					: pElement != null ? pElement.findElements(locator) : null;
			return (elements != null && elements.size() > 0) ? true : false;
		}
	}


	
	
}
