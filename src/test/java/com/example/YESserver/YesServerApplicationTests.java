package com.example.YESserver;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import static org.hamcrest.Matchers.containsString;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class YesServerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testHome() throws Exception {

		String result = mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		Assert.isTrue(result.equals("<h1>Welcome</h1>"));
	}

	@Test
	public void testUser() throws Exception {

		String result = mockMvc.perform(post("/login?username=Charlie_Xu_01&password=charlie1").with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/"))
				.andReturn().getResponse().getContentAsString();
	}

	@Test
	public void testWrongUser() throws Exception {

		String result = mockMvc.perform(post("/login?username=Charlie_Xu_01&password=charlie2").with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/login?error"))
				.andReturn().getResponse().getContentAsString();
	}

	// This test should pass if vulnerability doesn't exist
	@Test
	public void testSQLInjection1() throws Exception {
		System.out.println("---------------------------");
		String result = mockMvc.perform(post("/sql_injection_test?user_id=\"Charlie_Xu_01\";+drop+table+if+exists+users").with(csrf()))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		Assert.isTrue(result.equals(""));
	}

	// This test should pass if vulnerability doesn't exist
	@Test
	public void testSQLInjection2() throws Exception {
		System.out.println("---------------------------");
		String result = mockMvc.perform(post("/sql_injection_test?user_id=\"Charlie_Xu_01\"").with(csrf()))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		Assert.isTrue(result.equals("Success"));
	}

	// This test should pass if vulnerability doesn't exist
	@Test
	public void testXSS() throws Exception {
		String result = mockMvc
				.perform(get("/xss_test?username=Charlie_Xu_01&password=charlie1&name=<script>alert('XSS!')</script>").accept(MediaType.TEXT_HTML_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(content().string(containsString("<p>Hello. </p>Sincerely, <script>alert('XSS!')</script>")))
				.andReturn().getResponse().getContentAsString();
		Assert.isTrue(result.equals("<p>Hello. </p>Sincerely, <script>alert('XSS!')</script>"));
	}

	// This test should pass if vulnerability doesn't exist
	@Test
	public void testCSRF() throws Exception {
		String result = mockMvc
				.perform(post("/admin/get_id_by_name?username=Nicholas_Zeppos_01&password=nicholas1" + "&userName=admin"))
				.andExpect(status().isForbidden())
				.andReturn().getResponse().getContentAsString();
	}

	// This test should pass if vulnerability doesn't exist
	@Test
	public void testPasswordEncoder() throws Exception {

		String result = mockMvc.perform(get("/password_encoder_test"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		Assert.isTrue(result.equals("Success"));
	}
}