/**
 * 
 */
package pageObject;

import org.openqa.selenium.By;

/**
 * @author aditya.soni
 *
 */
public interface IBot 
{
	By spn_addBot = By.xpath("//span[contains(.,'Add Bot')]");
	By switch_addBot_Status =  By.xpath("//*[contains(text(),'Status')]//following-sibling::dx-switch");
	By switch_addBot_Attended = By.xpath("//*[contains(text(),'Attended')]//following-sibling::dx-switch");
	By list_addBot_Process = By.xpath("//*[contains(text(),'Process')]//following-sibling::dx-select-box//self::input[@role='combobox']");
	By txt_addBot_Name = By.xpath("//input[@name='name']");
	By txt_addBot_Description = By.xpath("//textarea[@name='description']");
	By btn_addBot_Save = By.xpath("//span[contains(.,'Save')]");
	By btn_addBot_Cancel = By.xpath("//span[contains(.,'Cancel')]");
	By txt_addBotStation_ActiveFromDate = By.xpath("//*[contains(text(),'Active From')]//following-sibling::div//following-sibling::div//self::input");
	By txt_addBotStation_ActiveToDate = By.xpath("//*[contains(text(),'Active To')]//following-sibling::div//self::dx-date-box//following-sibling::div//self::input");
	
}
