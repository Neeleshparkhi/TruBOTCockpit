package pageObject;

import org.openqa.selenium.By;

//author: Prashant Gupta

//User Management Interface
public interface IUserManagement
{
	By btn_AddUser = By.xpath("//span[text()='Add Users']");
	By btn_User = By.xpath("//a[@href='/usermanagement/user']");
	By btn_Group = By.xpath("//a[@href='/usermanagement/group']");
	By btn_Role = By.xpath("//a[@href='/usermanagement/role']");
	By tr_UserTable = By.xpath("//table[@class='dx-datagrid-table dx-datagrid-table-fixed dx-select-checkboxes-hidden']//self::tr");
	By th_UserTable = By.xpath("//table[@class='dx-datagrid-table dx-datagrid-table-fixed']//self::tr");
	
}
