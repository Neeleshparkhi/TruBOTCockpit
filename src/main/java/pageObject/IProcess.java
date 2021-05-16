package pageObject;

import org.openqa.selenium.By;

public interface IProcess 
{
	By btn_addProcess = By.xpath("//span[contains(.,'Add Process')]");
	By btn_closeMenuItem = By.xpath("//*[@aria-label='close']");
	By btn_addProcess_BrowsePackege = By.xpath("//input[@type='file']");
	By txt_addProcess_Version = By.xpath("//div[@class='dx-field-label'][contains(.,'Version*')]//following-sibling::div//self::input");
	By txt_addProcess_ActiveFromDate = By.xpath("//*[contains(text(),'Active From')]//following-sibling::div//following-sibling::div//self::input");
	By txt_addProcess_ActiveToDate = By.xpath("//*[contains(text(),'Active To')]//following-sibling::div//self::dx-date-box//following-sibling::div//self::input");
	By btn_addProcess_Save = By.xpath("//span[contains(text(),'Save')]");
	By btn_addProcess_Cancel = By.xpath("//span[contains(text(),'Cancel')]");
	By btn_addProcess_OK = By.xpath("//span[contains(text(),'OK')]");
	By div_ProcessAlreadyExists = By.xpath("//div[contains(text(),'C10137- A version of this process already exists. Please change the version to add this Process.')]");
	/*By txt_addProcessGUID = By.xpath("//*[contains(text(),'GUID')]//following-sibling::div");*/
	By txt_addProcessGUID = By.xpath("//*[text()='GUID']//parent::div//self::input");
	By txt_addProcessName = By.xpath("//*[text()='Name']//parent::div//self::input");
	By chkBox_addProcess_SelectAll = By.xpath("//*[@class='dx-list-select-all']//self::span");
	By switch_addProcess_Status =  By.xpath("//*[contains(text(),'Status')]//following-sibling::dx-switch");
	By div_addCategory = By.xpath("//*[contains(text(),'Add Category')]");
	By txt_categoryName = By.xpath("//*[text()='Category Name']//following-sibling::dx-text-box//self::input");
	By txt_categoryDescription = By.xpath("//*[text()='Category Description']//following::textarea");
	By switch_CategoryStatus = By.xpath("//*[text()='Category Name']//following::dx-switch");
	By btn_addCategory_Save = By.xpath("//*[text()='Category Description']//following::div//self::span[contains(text(),'Save')]");
	By txt_parameterName = By.xpath("//*[text()='Parameter Name']//following-sibling::dx-text-box//self::input");
	By switch_ParameterStatus = By.xpath("//*[text()='Parameter Status']//following::dx-switch");
	By switch_Required = By.xpath("//*[text()='Required']//following::dx-switch");
	By btn_addParameter_Save = By.xpath("//*[text()='Required']//following::div//self::span[contains(text(),'Save')]");
	By btn_parameterMapping_Save = By.xpath("//span[contains(text(),'Save')]");
	By div_mappingStatusHeader = By.xpath("//div[text()='Mapping Status']");
	By div_addProcess_VersionErrorMessage = By.xpath("//div[@class='dx-dialog-message'][contains(.,'C10145- Version already exists for this GUID')]");
}

