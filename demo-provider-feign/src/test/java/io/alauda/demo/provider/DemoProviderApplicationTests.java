package io.alauda.demo.provider;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

@SpringBootTest
class DemoProviderApplicationTests {

    @Resource
    private WebApplicationContext webApplicationContext;

    @Value("${spring.application.name}")
    String appName;

    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testDemoPing() throws Exception {
        String res = mockMvc.perform(MockMvcRequestBuilders.get("/demo/ping"))
                .andReturn().getResponse().getContentAsString();
        Assert.assertTrue(appName.equals(res));
    }

}
