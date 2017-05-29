package ru.stqa.pft.tests;

import org.testng.annotations.Test;

import static ru.stqa.pft.tests.TestBase.app;

/**
 * Created by Julia on 5/29/2017.
 */
public class PasswordChangeTest extends TestBase{

    @Test

    public void changePasswordTest(){

        app.changePassword().start();

    }
}
