package ru.stqa.pft.tests;

import org.testng.annotations.Test;

/**
 * Created by Julia on 5/28/2017.
 */
public class RegistrationTests extends TestBase {

    @Test

    public void testRegistration(){
        app.registration().start("user1","user1@localhost.localdomain");
    }
}
