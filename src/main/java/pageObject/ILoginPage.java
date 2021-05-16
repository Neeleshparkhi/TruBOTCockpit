/**
 * 
 */
package pageObject;

import org.openqa.selenium.By;




import com.paulhammant.ngwebdriver.ByAngular;

/**
 * @author asoni
 *
 */
public interface ILoginPage 
{
	By txt_UserName = By.name("Username");
	By txt_Password = By.name("Password");
	By btn_SignIn = By.xpath("//span[text()='Sign in']");
	By chk_stayLoggedIn = By.xpath("//span[contains(text(),'Stay logged in')]");	
	By btn_Yes = By.xpath("//span[text()='Yes']");
	
}
