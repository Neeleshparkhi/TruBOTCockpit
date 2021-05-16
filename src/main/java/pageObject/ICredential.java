/**
 * 
 */
package pageObject;

import org.openqa.selenium.By;

/**
 * @author aditya.soni
 *
 */
public interface ICredential 
{
	By btn_addCredential = By.xpath("//span[contains(.,'Add Credential')]");
	By txt_addCredential_Name = By.xpath("//input[@name='name']");
	By txt_addCredential_Description = By.xpath("//textarea[contains(@name,'description')]");
	By dxSwith_addCredential_Status = By.xpath("//dx-switch[@name='status']");
	By btn_addCredential_Save = By.xpath("//span[contains(text(),'Save')]");
	By btn_addCredential_Cancel = By.xpath("//span[contains(text(),'Cancel')]");
	By btn_addCredential_OK = By.xpath("//span[contains(text(),'OK')]");
	By txt_addCredential_ActiveFromDate = By.xpath("//*[contains(text(),'Active From')]//following-sibling::div//following-sibling::div//self::input");
	By txt_addCredential_ActiveToDate = By.xpath("//*[contains(text(),'Active To')]//following-sibling::div//self::dx-date-box//following-sibling::div//self::input");
	By txt_addCredential_UserName = By.xpath("(//input[@type='text'])[6]");
	By txt_addCredential_UserNameDescription = By.xpath("(//input[@type='text'])[7]");
	By txt_addCredential_Pasword = By.xpath("//input[@type='password']");
	By txt_addCredential_PaswordDescription = By.xpath("(//input[@type='text'])[9]");
	
}
