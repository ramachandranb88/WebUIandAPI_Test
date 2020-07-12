package driverRepo;

import org.apache.commons.io.FileUtils;
//import org.apache.poi.*;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.TestRunner;

import commonFunctionsRepo.GenericMethods;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import testFunctionsRepo.APIComponent;
import uiObjectsRepo.CommonPageObjects;
import uiObjectsRepo.HomePageObjects;
import uiObjectsRepo.WeatherPageObjects;

// Driver class defined with base driver configuration methods for
public class DriverClass implements DriverBase {

	public static WebDriver driver = null;
	private static String TCID;
	private static String TestName;
	private final String propertyFilePath = "Config//settings.properties";
	public static String outPutReportsPath = "test-output/Demo/Screenshots";

	public String driverPath;
	public String browser;

	public static Properties property = new Properties();
	public static String City = null;
	public static RequestSpecification request = RestAssured.given();
	public static Response response = null;

	@Override
	public String getTestCaseID() {
		return TCID;
	}

	@Override
	public String getTestName() {
		return TestName;
	}

	public void setTestCaseID(String TCID) {
		this.TCID = TCID;
	}

	public void setTestName(String TestName) {
		this.TestName = TestName;
	}

	public void setCity(String City) {
		this.City = City;
	}

	public String getPropertyValue(String key) {
		try {
			property.load(new FileInputStream(propertyFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return property.getProperty(key);
	}

	public void getDriver() {
		String browser = getPropertyValue("browser");
		switch (browser) {
		case "chrome":
			// System.setProperty("webdriver.chrome.driver",
			// property.getProperty("chromdriver_Path"));
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			// System.setProperty("webdriver.gecko.driver",
			// property.getProperty("geckodriver_Path"));
			driver = new FirefoxDriver();
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			// System.setProperty("webdriver.ie.driver",
			// property.getProperty("iedriver_Path"));
			driver = new EdgeDriver();
			break;

		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	public void closeDriver() {

		driver.close();
		driver.quit();
	}

//
//	public static String getJsonData(String KeyName){
//		
//		try { 
//		FileReader reader = new FileReader("C:\\Users\\ramachandran\\eclipse-workspace\\Selenium-TestNG\\src\\TestData\\testJson.json");
//		             JSONParser jsonParser = new JSONParser();	
//		             JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
//		             return jsonObject.get(KeyName).toString();
//		}catch(Exception e) {
//			 Reporter.log("Error in reading data from test json file : " + e);
//		}          
//		
//		return null;
//	}
//	
//
//	public static String getExcelData(String ColumnName) throws IOException {
//		
//		FileInputStream file = new FileInputStream("D:/CCP/TestData.xls");
//		HSSFWorkbook wb = new HSSFWorkbook(file);
//		HSSFSheet ws = wb.getSheetAt(0);
//		int columns = ws.getRow(0).getLastCellNum();
//		String Testdata = "";
//		
//        for(int j = 0; j<columns; j++)
//		{
//			String Val = ws.getRow(0).getCell(j).getStringCellValue();
//			if(Val.equals(ColumnName))
//			{
//				Testdata =  ws.getRow(TCID).getCell(j).getStringCellValue();
//				return Testdata;
//			}
//		}
//        
//        return null;
//	}
//	
//	
	public static void reportScreenshot(String FileName) {
		try {
			TakesScreenshot scrShot = ((TakesScreenshot) driver);

			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
			String DestPath = outPutReportsPath + "\\" + TestName + "\\" + FileName + ".png";
			File DestFile = new File(DestPath);

			FileUtils.copyFile(SrcFile, DestFile);

			Reporter.log("<br><img src='" + DestPath + "' height ='400' width = '500' /><br>");
		} catch (Exception e) {
			Reporter.log("Exception at take screenshot action : " + e);
		}
	}

	public GenericMethods genericMethods() {
		return new GenericMethods();
	}

	public CommonPageObjects commonPageObjects() {
		return new CommonPageObjects();
	}

	public HomePageObjects homePageObjects() {
		return new HomePageObjects();
	}

	public WeatherPageObjects weatherPageObjects() {
		return new WeatherPageObjects();
	}

	public APIComponent apiComponent() {
		return new APIComponent();
	}
}
