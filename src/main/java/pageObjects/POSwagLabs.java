package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class POSwagLabs {
	public WebDriver driver;
	public JavascriptExecutor js;
	
	public WebDriver Driver() {
		return driver = new ChromeDriver();
	}
	
	public JavascriptExecutor Js() {
		return js = (JavascriptExecutor) driver;
	}

	public void SetUser(String user) {
		driver.findElement(By.id("user-name")).sendKeys(user);
	}
	
	public void SetPassword(String password) {
		driver.findElement(By.id("password")).sendKeys(password);
	}
	
	public void ClickLogin() {
		driver.findElement(By.id("login-button")).click();
	}
	
	public void ExpandSideBar() {
		js.executeScript("document.querySelector('#react-burger-menu-btn').click()");
	}
	
	public void ClickLogout() {
		driver.findElement(By.id("logout_sidebar_link")).click();
	}
	
	public void SetFirstName(String name) {
		driver.findElement(By.id("first-name")).sendKeys(name);
	}
	
	public void SetLastName(String lastName) {
		driver.findElement(By.id("last-name")).sendKeys(lastName);
	}
	
	public void SetPostalCode(String code) {
		driver.findElement(By.id("postal-code")).sendKeys("12389465");
	}
	
	public void ClickCheckout() {
		driver.findElement(By.id("checkout")).click();
	}
	
	public void ClickContinue() {
		driver.findElement(By.id("continue")).click();
	}
	
	public void ClickFinish() {
		driver.findElement(By.id("finish")).click();
	}
	
	public void ClickBackToProducts() {
		driver.findElement(By.id("back-to-products")).click();
	}
	
	public void SelectProduct(String product) {
		switch (product.toUpperCase()) {
		case "BACKPACK":
			ScrollToItem("add-to-cart-sauce-labs-backpack");
			driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
			break;
		case "BIKELIGHT":
			ScrollToItem("add-to-cart-sauce-labs-bike-light");
			driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
			break;
		case "BOLT":
			ScrollToItem("add-to-cart-sauce-labs-bolt-t-shirt");
			driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();
			break;
		case "JACKET":
			ScrollToItem("add-to-cart-sauce-labs-fleece-jacket");
			driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket")).click();
			break;
		case "ONESIE":
			ScrollToItem("add-to-cart-sauce-labs-onesie");
			driver.findElement(By.id("add-to-cart-sauce-labs-onesie")).click();
			break;
		case "TEST":
			ScrollToItem("add-to-cart-test.allthethings()-t-shirt-(red)");
			driver.findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)")).click();
			break;
		default:
			break;
		}
	}
	
	public void ScrollToItem(String id) {
		js.executeScript("window.scrollBy(0, arguments[0])", driver.findElement(By.id(id)).getLocation().y);
	}
	
	public void ClickShoppingCart() {
		driver.findElement(By.id("shopping_cart_container")).click();
	}
	
	public void Delay(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Login(String user, String password) {
		SetUser(user);
		SetPassword(password);
		ClickLogin();
	}
}
