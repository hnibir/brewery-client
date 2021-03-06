package spring.micro.services.breweryclient.web.client;

/*
 * Created by Nibir Hossain on 12.08.20
 */

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import spring.micro.services.breweryclient.web.model.CustomerDto;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(value = "service.brewery", ignoreUnknownFields = false)
public class CustomerClient {
    public final String CUSTOMER_PATH_V1 = "/api/v1/customer/";
    private String apihost;
    private RestTemplate restTemplate;

    public CustomerClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public CustomerDto getCustomerById(UUID customerId) {
        return this.restTemplate.getForObject(apihost + CUSTOMER_PATH_V1 + customerId, CustomerDto.class);
    }

    public URI saveNewCustomer(CustomerDto customerDto) {
        return this.restTemplate.postForLocation(apihost + CUSTOMER_PATH_V1, customerDto);
    }

    public void updateCustomer(UUID customerId, CustomerDto customerDto) {
        this.restTemplate.put(apihost + CUSTOMER_PATH_V1 + customerId.toString(), customerDto);
    }

    public void deleteCustomer(UUID customerID) {
        this.restTemplate.delete(apihost + CUSTOMER_PATH_V1 + customerID.toString());
    }

    public void setApihost(String apihost) {
        this.apihost = apihost;
    }
}
