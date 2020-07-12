package apiwebui;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import driverRepo.DriverClass;
import testFunctionsRepo.APIComponent;
import testFunctionsRepo.BusinessComponent;

public class TestDemo {

	DriverClass driver = new DriverClass();
	BusinessComponent testFlow = new BusinessComponent();
	APIComponent apiTest = new APIComponent();

	@BeforeClass
	public void setUp() throws FileNotFoundException, IOException {
		driver.getDriver();
	}

	@AfterClass
	public void close() {
		driver.closeDriver();
	}

	@Parameters({ "test_ID", "test_Name",  "test_City"})
	@Test
	public void webUITest(String TCID, String TestName, String City) {
		driver.setTestCaseID(TCID);
		driver.setTestName(TestName);
		driver.setCity(City);
		testFlow.launchURL(driver.getPropertyValue("url"));
		testFlow.verifyHomePage();
		testFlow.navWeatherPage();
		testFlow.getWeatherDetails_UI();
	 	apiTest.getWeather_API();
	 	testFlow.compareReport();
		
	}

}
