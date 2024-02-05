package testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.sql.Driver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class Login {
	
	WebDriver driver = new ChromeDriver();
	
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
}
