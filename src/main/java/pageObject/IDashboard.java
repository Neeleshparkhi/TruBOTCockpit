package pageObject;

import org.openqa.selenium.By;

public interface IDashboard
{
	By img_DatamaticsLogo = By.xpath("//img[contains(@src,'assets/images/Datamatics.svg')]");
	By div_DashboardTitle = By.xpath("//*[@role='menuitem']");
	By btn_Users = By.xpath("//a[@href='/usermanagement']");
	By spn_Role = By.xpath("//span[@class='role']");
	By spn_Logout = By.xpath("//span[contains(.,'Logout')]");
	By spn_menuItem = By.xpath("//i[contains(@class,'dx-icon dx-icon-menu')]");
	By spn_BotManagement = By.xpath("//span[contains(.,'Bot Management')]");
	By spn_Process = By.xpath("//span[contains(.,'Process')]");
	By spn_botManagement = By.xpath("//img[@src='/assets/images/sidebar/process_management.svg']");
	By spn_botStationManagement = By.xpath("//img[@src='/assets/images/sidebar//bot_station_management.svg']");
	By spn_BotStation = By.xpath("//span[@class='dx-menu-item-text'][contains(.,'Bot Stations')]");
	By spn_Bot = By.xpath("//span[@class='dx-menu-item-text'][contains(.,'Bots')]");
	
	By spn_credential = By.xpath("//img[@src='/assets/images/sidebar/credential_management.svg']");
}
