package spring.micro.services.breweryclient.web.client;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.micro.services.breweryclient.web.model.CustomerDto;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class CustomerClientTest {
    @Autowired
    CustomerClient customerClient;

    @Test
    void testGetCustomerById() {
        CustomerDto customerDto = this.customerClient.getCustomerById(UUID.randomUUID());
        assertNotNull(customerDto);
    }

    @Test
    void testSaveNewCustomer() {
        CustomerDto customerDto = CustomerDto.builder().firstName("Nibir").lastName("Hossain").build();
        URI uri = customerClient.saveNewCustomer(customerDto);
        assertNotNull(uri);
        log.info(uri.toString());
    }

    @Test
    void testUpdateCustomer() {
        CustomerDto customerDto = CustomerDto.builder().firstName("Nibir").lastName("Hossain").build();
        this.customerClient.updateCustomer(UUID.randomUUID(), customerDto);
    }
}