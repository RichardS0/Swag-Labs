package testes;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
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
import pageObjects.*;

public class Login {
	
	private WebDriver driver;
	private JavascriptExecutor js;
	private POSwagLabs po = new POSwagLabs();
	
	@Before
	public void SetUp() {
		driver = po.Driver();
		js = po.Js();
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
		driver.get("https://www.saucedemo.com/");
	}
	
	@After
	public void TearDown() {
		driver.quit();
	}
	
	@Test
	public void DeveRealizarLoginStandard() {
		po.Login("standard_user", "secret_sauce");
		assertEquals(true, driver.getCurrentUrl().contains("inventory"));
	}
	
	@Test
	public void DeveRealizarLoginUsuarioLocked() {
		po.Login("locked_out_user", "secret_sauce");
		assertEquals("Epic sadface: Sorry, this user has been locked out.", driver.findElement(
					By.cssSelector("#login_button_container > div > form > div.error-message-container.error")).
					getAttribute("innerText"));
	}
	
	@Test
	public void DeveRealizarLoginProblem() {
		po.Login("problem_user", "secret_sauce");
		po.Delay(5000);
		String img1 = (String) js.executeScript("return document.querySelector('#item_4_img_link > img').src");
		String img2 = (String) js.executeScript("return document.querySelector('#item_0_img_link > img').src");
		assertEquals(img1, img2);
	}
	
	@Test
	public void DeveFazerCompraEDarErroAoTentarRemoverPeloBotao() {
		po.Login("error_user", "secret_sauce");
		po.Delay(3000);
		po.SelectProduct("backpack");
		driver.findElement(By.id("remove-sauce-labs-backpack")).click();
		assertFalse(driver.findElement(By.id("remove-sauce-labs-backpack")).isDisplayed() == false);
	}
	
	@Test
	public void DeveTestarVisualUser() {
		po.Login("visual_user", "secret_sauce");
		
		String imgSelectionSrc = (String) js.executeScript("return document.querySelector('#item_4_img_link > img').src");
		
		driver.findElement(By.id("item_4_img_link")).click();
		
		String imgDetailedSrc = (String) js.executeScript("return document.querySelector('#inventory_item_container > div > div > div.inventory_details_img_container > img').src");
		
		po.Delay(3000);
		
		assertFalse(imgSelectionSrc == imgDetailedSrc);
	}
	
	@Test
	public void Deslogar() {
		po.SetUser("standard_user");
		po.SetPassword("secret_sauce");
		po.ClickLogin();
		po.Delay(1000);
		po.ExpandSideBar();
		po.Delay(2000);
		po.ClickLogout();	
		assertTrue(driver.findElement(By.id("user-name")).isEnabled());
	}
	
	@Test
	public void DeveRealizarUmaCompra() {
		po.SetUser("standard_user");
		po.SetPassword("secret_sauce");
		po.ClickLogin();
		
		po.SelectProduct("BIKELIGHT");
		po.SelectProduct("test");
		
		po.ClickShoppingCart();
		
		assertEquals("Your Cart", 
				js.executeScript("return document.querySelector('#header_container > div.header_secondary_container > span').innerText"));
		po.ClickCheckout();
		assertEquals("Checkout: Your Information", 
				js.executeScript("return document.querySelector('#header_container > div.header_secondary_container > span').innerText"));
		po.SetFirstName("Standard");
		po.SetLastName("User");
		po.SetPostalCode("12389465");
		po.ClickContinue();
		assertEquals("Checkout: Overview", 
				js.executeScript("return document.querySelector('#header_container > div.header_secondary_container > span').innerText"));
		po.ClickFinish();
		assertEquals("Checkout: Complete!", 
				js.executeScript("return document.querySelector('#header_container > div.header_secondary_container > span').innerText"));
		assertEquals("Thank you for your order!", 
				js.executeScript("return document.querySelector('#checkout_complete_container > h2').innerText"));
		po.ClickBackToProducts();

		//usar return no script de js para que traga um valor string
	}
}
