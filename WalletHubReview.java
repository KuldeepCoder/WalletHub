package com.FBLogin.tests;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class WalletHubReview {
	static WebDriver driver = null;
	static WebDriverWait wait = null;
	static JavascriptExecutor js = null;
	static Actions actions = null;
	public static void main(String args[]) throws InterruptedException
	{

		String insUrl = "http://wallethub.com/profile/test_insurance_company/";
		String baseUrl = "https://wallethub.com/";
		String profile = "https://wallethub.com/profile/kkuldeepkumar1980";

		String txtArea ="//*[@id=\"reviews-section\"]//div[1]/textarea"	;
		String txtReview = "This is test review for test insurance company. The assignment says the review text should contain at least two hundred characters. This was a good exercise and I thoroughly enjoyed it.This exercise also gave me an opportunity to learn new things.";
		String starPath = "//*[@id='reviews-section']/div[1]/div[3]/review-star/div/*";
		String uName = "";
		String pwd ="";

		setUp();
		launchbaseUrl(baseUrl);
		login(uName,pwd);
		Thread.sleep(5000);
		//navInsurance(insUrl);
		driver.get(insUrl);
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
		verScroll();
		hoverRate(starPath);
		submitReview(txtArea,txtReview);
			openProfile(profile);
		//driver.get(profile);
		goToReview();

		String review = driver.findElement(By.xpath("//*[@id=\"reviews-section\"]/section/article[1]/div[4]")).getText();
		txtAssert(txtReview,review); 

	}
	public static void setUp()
	{
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"//Drivers//ChromeDriver.exe");
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver,50);
		js = (JavascriptExecutor) driver;
		actions = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		

	}
	public static void launchbaseUrl(String baseUrl)
	{
		driver.get(baseUrl);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();

	}
	public static void login(String uName,String pwd)
	{
		driver.findElement(By.linkText("Login")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"join-login\"]/form/div[1]/input")));
		driver.findElement(By.xpath("//*[@id=\"join-login\"]/form/div[1]/input")).sendKeys("kkuldeepkumar1980@gmail.com");
		driver.findElement(By.name("pw")).sendKeys("Password@1");
		driver.findElement(By.xpath("//*[@id=\"join-login\"]/form/div[4]/button[2]")).click();
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));

	}
	public static void navInsurance(String insUrl)
	{
		driver.get(insUrl);
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
	}

	public static void verScroll()
	{
		js.executeScript("window.scrollBy(0,1000)");
	}
	public static void hoverRate(String starPath) throws InterruptedException
	{
		List<WebElement> svg = driver.findElements(By.xpath(starPath));
		System.out.println(svg.size());

		for(int i = 1;i<=svg.size()-1;i++)
		{
			
			WebElement ele = driver.findElement(By.xpath(starPath+"["+i+"]"));
			Thread.sleep(5000);
			actions.moveToElement(ele).perform();
			if (i==4)
			{
				ele.click();
			}

		} 
	}

	public static void submitReview(String txtArea,String txtReview) throws InterruptedException
	{
		WebElement drop = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[@class='dropdown-placeholder'])[2]")));
		drop.click();
		Thread.sleep(3000);

		driver.findElement(By.xpath("//*[@id=\"reviews-section\"]//div/ul/li[2]")).click();
		driver.findElement(By.xpath(txtArea)).click();
		
		driver.findElement(By.xpath(txtArea)).sendKeys(txtReview);
		String submit = "//*[@id=\"reviews-section\"]//sub-navigation/div/*";
		WebElement btnSubmit = driver.findElement(By.xpath(submit+"["+2+"]")); 
		btnSubmit.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='scroller']//div[3]/div[2]")));
		driver.findElement(By.xpath("//*[@id='scroller']//div[3]/div[2]")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"scroller\"]//div/div[2]/a")));
	}
	public static void goToReview()
	{
		driver.findElement(By.xpath("//*[@id=\"scroller\"]//div/div[2]/a")).click();
	}
	public static void openProfile(String profile)
	{
		driver.get(profile);
	}
	public static boolean txtAssert(String txtReview,String review)
	{
		Assert.assertEquals(txtReview, review);
		System.out.println("Review found");
		return true;
	}
	
	public void tearDown()
	{
		driver.quit();
	}

}

