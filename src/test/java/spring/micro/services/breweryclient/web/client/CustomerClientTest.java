package spring.micro.services.breweryclient.web.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.micro.services.breweryclient.web.model.CustomerDto;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerClientTest {
    @Autowired
    CustomerClient customerClient;

    @Test
    void testGetCustomerById() {
        CustomerDto customerDto = this.customerClient.getCustomerById(UUID.randomUUID());
        assertNotNull(customerDto);
    }
}