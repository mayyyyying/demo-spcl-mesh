package io.alauda.demo.consumer;

import io.alauda.demo.consumer.client.ProviderFeignClient;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

@SpringBootTest
class DemoConsumerApplicationTests {

    @Resource
    WebApplicationContext webApplicationContext;

    @MockBean
    ProviderFeignClient providerFeignClient;

    @Value("${spring.application.name}")
    String appName;

    MockMvc mockMvc;

    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testDemoConsumer() throws Exception {
        Mockito.doReturn("hello").when(providerFeignClient).ping();
        String res = mockMvc.perform(MockMvcRequestBuilders.get("/demo/ping"))
                .andReturn().getResponse().getContentAsString();
        Assert.assertTrue("hello".equals(res));
    }

    @Test
    void testPostConsumer() throws Exception {
        Mockito.doReturn("hello").when(providerFeignClient).postPing();
        String res = mockMvc.perform(MockMvcRequestBuilders.post("/demo/ping"))
                .andReturn().getResponse().getContentAsString();
        Assert.assertTrue("hello".equals(res));
    }

    @Test
    void testHello() throws Exception {
        String res = mockMvc.perform(MockMvcRequestBuilders.get("/demo/hello"))
                .andReturn().getResponse().getContentAsString();
        Assert.assertTrue(appName.equals(res));
    }

}
