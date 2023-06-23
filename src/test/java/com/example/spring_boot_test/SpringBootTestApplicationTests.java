package com.example.spring_boot_test;

import com.example.spring_boot_test.controller.ProductsController;
import com.example.spring_boot_test.entity.Products;
import com.example.spring_boot_test.service.ProductService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.slf4j.MDC.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class SpringBootTestApplicationTests {

    private static final String ENDPOINT_URL = "/products";

    @InjectMocks
    ProductsController productsController;


    @MockBean
    ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        // MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productsController).build();
    }

    @Test
    public void testSaveProducts() throws Exception {
        Products products = new Products(1, "test", "test", 1);
        //mock the service method to return the above dummy value
        when(productService.save(any())).thenReturn(products);
        //perform the test by making a get call to the endpoint /products/save
        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT_URL + "/save")
                //set the content type as application/json
                .contentType("application/json")
                //set the accept type as application/json
                .content("{\"id\":1,\"name\":\"test\",\"description\":\"test\",\"price\":1}")
                .contentType(MediaType.APPLICATION_JSON)
                //accept the response in json format
                .accept(MediaType.APPLICATION_JSON))
                //validate the response code received
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                //validate the response body
                .andExpect(status().isOk())
                //validate the response id field exists
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void getProductByName() throws Exception {
        Products products = new Products(1, "test", "test", 1);
        when(productService.findByName(any())).thenReturn(products);
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/byname/test")
                .contentType("application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void ShouldReturnAllProducts() throws Exception {
        when(productService.findall()).thenReturn(Arrays.asList(new Products(1, "test", "test", 1),
                new Products(2, "test2", "test2", 2)));
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/all")
                .contentType("application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").exists());
    }

   @Test
    public void ShouldReturnProductById() throws Exception {
        Products products = new Products(1, "test", "test", 1);
        when(productService.findById(anyInt())).thenReturn(products);
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/byid/1")
                .contentType("application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void ShouldUpdateProduct() throws Exception {
        Products products = new Products(1, "test", "test", 1);
        when(productService.update(any(), anyInt())).thenReturn(products);
        mockMvc.perform(MockMvcRequestBuilders.put(ENDPOINT_URL + "/update/1")
                .contentType("application/json")
                .content("{\"id\":1,\"name\":\"test\",\"description\":\"test\",\"price\":1}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void testDeleteById() throws Exception
    {

        when(productService.delete(anyInt())).thenReturn("Product deleted successfully");
        mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT_URL + "/delete/1")
                .contentType("application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").doesNotExist());
    }

}
