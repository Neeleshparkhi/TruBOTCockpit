/**
 * 
 */
package pageObject;

import org.openqa.selenium.By;

/**
 * @author aditya.soni
 *
 */
public interface IBotStation
{
	By spn_addBotStation = By.xpath("//div[@class='dx-button-content'][contains(.,'Add Bot Station')]");
	By txt_addBotStation_Name = By.xpath("//input[@name='name']");
	By list_addBotStation_StationType = By.xpath("//*[contains(text(),'Station Type')]//following-sibling::div//following-sibling::div//self::input");
	By list_addBotStation_CredentialType = By.xpath("//*[contains(text(),'Select Credential')]//following-sibling::div//following-sibling::div//self::input");
	By txtArea_addBotStationDescription = By.xpath("//textarea[contains(@class,'dx-texteditor-input')]");
	By txt_addBotStation_IpAddress = By.xpath("//input[contains(@name,'ipAddress')]");
	By txt_addBotStation_PortNumber = By.xpath("//input[@name='WinRMPortNumber']");
	By txt_addBotStation_ActiveFromDate = By.xpath("//*[contains(text(),'Active From')]//following-sibling::div//following-sibling::div//self::input");
	By txt_addBotStation_ActiveToDate = By.xpath("//*[contains(text(),'Active To')]//following-sibling::div//self::dx-date-box//following-sibling::div//self::input");
	By spn_addBotStation_TestConnection = By.xpath("//span[contains(.,'Test Connection')]");
	By spn_addBotStation_Cancel = By.xpath("//div[@class='dx-button-content'][contains(.,'Cancel')]");
	By spn_addBotStation_Save = By.xpath("//div[@class='dx-button-content'][contains(.,'Save')]");
	By txt_addBotStation_MacAddress = By.xpath("//div[@class='dx-field-label'][contains(.,'MAC Address')]");
	
	
}
