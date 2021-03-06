package spring.micro.services.breweryclient.web.client;

/*
 * Created by Nibir Hossain on 12.08.20
 */

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import spring.micro.services.breweryclient.web.model.BeerDto;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(value = "service.brewery", ignoreUnknownFields = false)
public class BreweryClient {
    public final String BEER_PATH_V1 = "/api/v1/beer/";
    private String apihost;

    private final RestTemplate restTemplate;

    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    public BeerDto getBeerById(UUID beerId) {
        return this.restTemplate.getForObject(apihost + BEER_PATH_V1 + beerId.toString(), BeerDto.class);
    }

    public URI saveNewBeer(BeerDto beerDto) {
        return this.restTemplate.postForLocation(apihost + BEER_PATH_V1, beerDto);
    }

    public void updateBeer(UUID beerId, BeerDto beerDto) {
        this.restTemplate.put(apihost + BEER_PATH_V1 + beerId.toString(), beerDto);
    }

    public void deleteBeer(UUID beerId) {
        this.restTemplate.delete(apihost + BEER_PATH_V1 + beerId); // Java compiler will automatically add the toString method
    }

    public void setApihost(String apihost) {
        this.apihost = apihost;
    }
}
