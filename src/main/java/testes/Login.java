package testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Driver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class Login {
	
	WebDriver driver = new ChromeDriver();
	JavascriptExecutor js = (JavascriptExecutor) driver;
	
	@Before
	public void SetUp() {
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
		driver.get("https://www.saucedemo.com/");
	}
	
	@After
	public void TearDown() {
		driver.quit();
	}
	
	@Test
	public void DeveRealizarLoginStandard() {
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		
		assertEquals(true, driver.getCurrentUrl().contains("inventory"));
	}
	
	@Test
	public void DeveRealizarLoginUsuarioLocked() {
		driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		
		assertEquals("Epic sadface: Sorry, this user has been locked out.", driver.findElement(
					By.cssSelector("#login_button_container > div > form > div.error-message-container.error")).
					getAttribute("innerText"));
		
	}
	
	@Test
	public void DeveRealizarLoginProblem() {
		driver.findElement(By.id("user-name")).sendKeys("problem_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(driver.findElement(By.id("item_4_img_link")).getAttribute("src"), driver.findElement(By.id("item_0_img_link")).getAttribute("src"));
	}
	
	@Test
	public void DeveFazerCompraEDarErroAoTentarRemoverPeloBotao() {
		driver.findElement(By.id("user-name")).sendKeys("error_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
		driver.findElement(By.id("remove-sauce-labs-backpack")).click();
		assertFalse(driver.findElement(By.id("remove-sauce-labs-backpack")).isDisplayed() == false);
	}
	
	@Test
	public void DeveTestarVisualUser() {
		driver.findElement(By.id("user-name")).sendKeys("visual_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		
		
		String imgSelectionSrc = (String) js.executeScript("return document.querySelector('#item_4_img_link > img').src");
		
		driver.findElement(By.id("item_4_img_link")).click();
		
		String imgDetailedSrc = (String) js.executeScript("return document.querySelector('#inventory_item_container > div > div > div.inventory_details_img_container > img').src");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertFalse(imgSelectionSrc == imgSelectionSrc);
	}
	
	@Test
	public void Deslogar() {
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		
		
		try {
			Thread.sleep(10000);
			js.executeScript("document.querySelector('#react-burger-menu-btn').click()");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		driver.findElement(By.id("logout_sidebar_link")).click();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(driver.findElement(By.id("user-name")).isEnabled());
	}
	
	@Test
	public void DeveRealizarUmaCompra() {
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		
		driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
		
		driver.findElement(By.id("shopping_cart_container")).click();
		assertEquals("Your Cart", js.executeScript("return document.querySelector('#header_container > div.header_secondary_container > span').innerText"));
		driver.findElement(By.id("checkout")).click();
		assertEquals("Checkout: Your Information", js.executeScript("return document.querySelector('#header_container > div.header_secondary_container > span').innerText"));
		driver.findElement(By.id("first-name")).sendKeys("Standard");
		driver.findElement(By.id("last-name")).sendKeys("User");
		driver.findElement(By.id("postal-code")).sendKeys("12389465");
		driver.findElement(By.id("continue")).click();
		assertEquals("Checkout: Overview", js.executeScript("return document.querySelector('#header_container > div.header_secondary_container > span').innerText"));
		driver.findElement(By.id("finish")).click();
		assertEquals("Checkout: Complete!", js.executeScript("return document.querySelector('#header_container > div.header_secondary_container > span').innerText"));
		assertEquals("Thank you for your order!", js.executeScript("return document.querySelector('#checkout_complete_container > h2').innerText"));
		driver.findElement(By.id("back-to-products")).click();


	}
}
