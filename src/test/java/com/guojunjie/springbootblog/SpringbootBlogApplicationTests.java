package com.guojunjie.springbootblog;

import com.guojunjie.springbootblog.service.BlogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootBlogApplicationTests {

	@Autowired
	BlogService blogService;

	@Test
	void contextLoads() {
	}

	@Test
	public void testSixMonth(){
		blogService.getBlogCountInRecentlySixMonth();
	}

}
