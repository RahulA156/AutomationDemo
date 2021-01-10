package stepDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class VerifyIncident extends TestVariables {

	public ChromeDriver driver;
	String updatedDescription="";
	String x="";

	@Given("open the chrome browser and maximize the window")
	public void launchBrowser() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Given("load the service now application")
	public void loadAppUrl() {
		driver.get("https://dev68594.service-now.com/");

	}

	@Given("enter username as {string} and password as {string} to login")
	public void enter_username_as_and_password_as_to_login(String username, String password) {
		driver.switchTo().frame("gsft_main");
		driver.findElementById("user_name").sendKeys(username);
		driver.findElementById("user_password").sendKeys(password);
		driver.findElementByName("not_important").click();
		driver.switchTo().defaultContent();
	}

	@Given("search incident and select all also look up icon for caller")
	public void search_incident_and_select_all_also_look_up_icon_for_caller() throws InterruptedException {
		driver.findElementById("filter").sendKeys("incident");
		Thread.sleep(5000);
		driver.findElementByXPath("(//div[text()='All'])[2]").click();
		driver.switchTo().frame("gsft_main");
		driver.findElementByXPath("//button[text()='New']").click();
		driver.findElementByXPath("//input[@id='incident.number']");
		driver.findElementByXPath("//span[text()='Caller']/following::span[@class='icon icon-search'][1]").click();

	}

	@When("search as {string} in lookup window and {string} from results and enter short_description as {string}")
	public void search_as_in_lookup_window_and_from_results_and_enter_short_description_as(String name, String fullName, String desc) {
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> listHandles = new ArrayList<String>(windowHandles);
		String secondWin = listHandles.get(1);
		String firstWin = listHandles.get(0);
		driver.switchTo().window(secondWin);
		driver.findElementByXPath("//input[@placeholder='Search']").sendKeys(name, Keys.ENTER);
		driver.findElementByLinkText(fullName).click();
		driver.switchTo().window(firstWin);
		driver.switchTo().frame("gsft_main");
		incidentNo = driver.findElementById("incident.number").getAttribute("value");
		System.out.println(incidentNo);
		driver.findElementById("incident.short_description").sendKeys(desc);
	}


	@Then("click on submit and search the created one and verify")
	public void click_on_submit_and_search_the_created_one_and_verify() throws InterruptedException {
		driver.findElementById("sysverb_insert").click();
		WebElement searchBy = driver.findElementByXPath("//select[contains(@id,'select') and @class='form-control default-focus-outline']");
		Select dd = new Select(searchBy);
		dd.selectByVisibleText("Number");
		System.out.println(incidentNo+"new");
		driver.findElementByXPath("(//input[@class='form-control'])[1]").sendKeys(incidentNo, Keys.ENTER);
		System.out.println(incidentNo);
		Thread.sleep(3000);
		boolean displayed=driver.findElementByLinkText(incidentNo).isDisplayed();
		Assert.assertTrue(displayed);
		driver.close();
	}

	@Given("login using username as {string} and password as {string} and enter incident and click all")
	public void enter_incident_and_click_all(String username, String password) throws InterruptedException {
		driver.switchTo().frame("gsft_main");
		driver.findElementById("user_name").sendKeys(username);
		driver.findElementById("user_password").sendKeys(password);
		driver.findElementByName("not_important").click();
		driver.switchTo().defaultContent();
		driver.findElementById("filter").sendKeys("incident");
		Thread.sleep(5000);
		driver.findElementByXPath("(//div[text()='All'])[2]").click();
		driver.switchTo().frame("gsft_main");

	}

	@Given("search for previous incident link")
	public void search_for_previous_incident_link() {

		driver.findElementByXPath("(//input[@class='form-control'])[1]").sendKeys(incidentNo, Keys.ENTER);
		driver.findElementByXPath("//td[@class='vt']").click();
		driver.findElementByXPath("//input[@id='incident.short_description']").clear();
		x=incidentNo;
		


	}

	@When("update the description as {string} and submit")
	public void update_the_description_as_and_submit(String desc) {
		driver.findElementByXPath("//input[@id='incident.short_description']").sendKeys(desc);
		driver.findElementByXPath("//button[contains(text(),'Update')]").click();
		updatedDescription=desc;
		
	}

	@Then("verify the updated description")
	public void verify_the_updated_description() throws InterruptedException {
		WebElement searchBy = driver.findElementByXPath("//select[contains(@id,'select') and @class='form-control default-focus-outline']");
		Select dd = new Select(searchBy);
		dd.selectByVisibleText("Number");
		System.out.println(x+"new");
		driver.findElementByXPath("(//input[@class='form-control'])[1]").sendKeys(x, Keys.ENTER);
		Thread.sleep(4000);
		String t=driver.findElementByXPath("//td[@class='vt'][3]").getText();
		//boolean displayed=driver.findElementByLinkText(updatedDescription).isDisplayed();
		Assert.assertEquals(updatedDescription, t);
		driver.close();

	}

}
