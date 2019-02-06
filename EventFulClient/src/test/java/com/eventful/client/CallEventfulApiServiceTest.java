package com.eventful.client;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventful.client.CallEventfulApiService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CallEventfulApiServiceTest {

	@Before
	public void before() {
		System.out.println("Before");
	}

	@After
	public void after() {
		System.out.println("After");
	}

	@BeforeClass
	public static void beforeClass() {
		System.out.println("Before Class");
	}

	@AfterClass
	public static void afterClass() {
		System.out.println("After Class");
	}

	@InjectMocks
	CallEventfulApiService callEventfulServiceAPi;
	
	@Test
	public void populateEventSearchResultTest() {

		assertNotNull(callEventfulServiceAPi.populateEventSearchResult("music", "singapore"));
	}
	
	
	
	
	
	

}
