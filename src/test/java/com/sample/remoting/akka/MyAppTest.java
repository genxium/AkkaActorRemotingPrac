package com.sample.remoting.akka;

import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class MyAppTest {
    @Test
    public void testAppHasAGreeting() {
        final MyApp classUnderTest = new MyApp();
        assertNotNull(classUnderTest.getGreeting());
    }
}
