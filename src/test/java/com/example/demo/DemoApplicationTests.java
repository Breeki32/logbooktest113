package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	/**
	 * With the Logging Level set to something that would lead to requests not getting logged (=INFO), Logbook "eats" the body of the
	 * response from /services...
	 */
	@Test
	public void cxfServiceListIsNull() {
		DemoApplication.main(new String[] { "--server.port=32404", "--logging.level.org.zalando=INFO" });
		ResponseEntity<String> response = new RestTemplate().getForEntity("http://localhost:32404/services", String.class);
		Assert.assertEquals(200, response.getStatusCodeValue());
		Assert.assertNull(response.getBody());
	}

	/**
	 * ... but if the log level is set to TRACE and therefore activates the logging, the response is correct and is not null.
	 */
	@Test
	public void cxfServiceListIsNotNull() {
		DemoApplication.main(new String[] { "--server.port=32405", "--logging.level.org.zalando=TRACE" });
		ResponseEntity<String> response = new RestTemplate().getForEntity("http://localhost:32405/services", String.class);
		Assert.assertEquals(200, response.getStatusCodeValue());
		Assert.assertNotNull(response.getBody());
	}

}
