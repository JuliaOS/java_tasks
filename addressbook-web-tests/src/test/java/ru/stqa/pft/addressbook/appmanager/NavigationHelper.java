package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by Julia on 4/16/2017.
 */
public class NavigationHelper extends HelperBase{

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }

    public void homePage() {
        if (isElementPresent(By.id("maintable"))){
            return;
        } else
            click(By.linkText("home"));
    }

    public void addNewPage() {
        if (isElementPresent(By.tagName("h1"))
                && wd.findElement(By.tagName("h1")).getText().equals("Edit / add address book entry")
                && isElementPresent(By.name("submit"))){
            return;
        } else {
            click(By.linkText("add new"));
        }
    }

    public void groupPage() {
        if (isElementPresent(By.tagName("h1"))
                && wd.findElement(By.tagName("h1")).getText().equals("Groups")
                && isElementPresent(By.name("new"))){
            return;
        } else{
            click(By.linkText("groups"));
        }
    }

}
