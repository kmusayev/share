package pizzaOrder;
import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import demo.TestBase;


public class DemoTest extends TestBase {
	

String name = "Kevin";
String email = "kevin@gmail.com";
String phone = "9715165555";
PizzaTypes pizzaType = PizzaTypes.MEDIUM_TWOTOPPINGS;
PizzaToppings pizzaTop1 = PizzaToppings.MUSHROOMS;
PizzaToppings pizzaTop2= PizzaToppings.OLIVES;
String qty = "1";
public void choisePizza() {
	
	WebElement pizzaTypes=driver.findElement(By.id("pizza1Pizza"));
    WebElement toppings1=driver.findElement(By.xpath("//div[@id='pizza1']//select[@class='toppings1']"));
    WebElement toppings2=driver.findElement(By.xpath("//div[@id='pizza1']//select[@class='toppings2']"));
    WebElement quantity=driver.findElement(By.id("pizza1Qty"));
    WebElement cost=driver.findElement(By.id("pizza1Cost"));
    
    
    Select selectType = new Select(pizzaTypes);
    Select selectTop1 = new Select(toppings1);
    Select selectTop2 = new Select(toppings2);
   
    selectType.selectByValue(pizzaType.getDisplayName());
    selectTop1.selectByValue(pizzaTop1.getDisplayName());
    selectTop2.selectByValue(pizzaTop2.getDisplayName());
    quantity.clear();
    quantity.sendKeys(qty);
    quantity.sendKeys(Keys.ENTER);
    cost.isDisplayed();
    
}
public void pickUpInfo(String deleteName) {
	WebElement name=driver.findElement(By.id("name"));
    WebElement email=driver.findElement(By.id("email"));
    WebElement phone=driver.findElement(By.id("phone"));
     
    
    name.sendKeys(this.name);
    email.sendKeys(this.email);
    phone.sendKeys(this.phone);
    
    if(deleteName.equalsIgnoreCase("yes")) {
    	name.clear();
    	
    }
    
    
	
}

public void payment(String paymentType) {
	WebElement creditRadioBtn=driver.findElement(By.id("ccpayment"));
	WebElement cashRadioBtn=driver.findElement(By.id("cashpayment"));
	if(paymentType.equalsIgnoreCase("credit")) {
	creditRadioBtn.click();}
	

else if(paymentType.equalsIgnoreCase(paymentType)) {
	cashRadioBtn.click();
	
}
	
	
}	
public void placeOrder() {
	
	WebElement placeOrder=driver.findElement(By.id("placeOrder"));
	placeOrder.click();
	
}

public void reset() {
	WebElement reset=driver.findElement(By.id("reset"));
	reset.click();
}

public String verifyMessage() {
	WebElement dialog=driver.findElement(By.id("dialog"));
	WebElement dialogText=driver.findElement(By.xpath("//div[@id='dialog']/p"));
	dialog.isDisplayed();
	return dialogText.getText();
	
	
}
    @Test
    public void positiveTest() throws InterruptedException {
    assertEquals(driver.getTitle(), "Pizza Order Form");	
    driver.manage().window().maximize();
    choisePizza();
    pickUpInfo("no");
    payment("credit");
    payment("cash");
    reset();
    choisePizza();
    pickUpInfo("no");
    payment("credit");
    placeOrder();
    
    assertEquals("Thank you for your order! TOTAL: "+pizzaType.getCost()+" "+pizzaType.getDisplayName(),verifyMessage());
   
		
	
    }
    
    @Test
    public void negativeTest() {
    	
    	 assertEquals(driver.getTitle(), "Pizza Order Form");	
    	    driver.manage().window().maximize();
    	    choisePizza();
    	    pickUpInfo("yes");
    	    payment("credit");
    	    placeOrder();
    	    assertEquals("Missing name",verifyMessage().trim());
    	   
    			
    } 
}
