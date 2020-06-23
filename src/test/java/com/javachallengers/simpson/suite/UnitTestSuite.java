package com.javachallengers.simpson.suite;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.PropertySource;

/**
 * Unit test suite.
 *  
 * It runs only unit tests, test with <i>UnitTest</i> Tag.
 */
@RunWith(JUnitPlatform.class)
@PropertySource("classpath:application.properties")
@SelectPackages(value = "com.javachallengers.simpson")
@IncludeTags("UnitTest")
public class UnitTestSuite {

}
