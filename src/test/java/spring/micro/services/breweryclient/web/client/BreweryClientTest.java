package spring.micro.services.breweryclient.web.client;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.micro.services.breweryclient.web.model.BeerDto;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class BreweryClientTest {

    @Autowired
    BreweryClient breweryClient;

    @Test
    void testGetBeerById() {
        BeerDto beerDto = breweryClient.getBeerById(UUID.randomUUID());

        assertNotNull(beerDto);
    }

    @Test
    void testSaveNewBeer() {
        // given
        BeerDto beerDto = BeerDto.builder().name("Pilsner").build();

        URI uri = breweryClient.saveNewBeer(beerDto);
        assertNotNull(uri);
        log.info(uri.toString());
    }

    @Test
    void testUpdateBeer() {
        // given
        BeerDto beerDto = BeerDto.builder().name("Pilsner").build();
        breweryClient.updateBeer(UUID.randomUUID(), beerDto);
    }

    @Test
    void testDeleteBeer() {
        breweryClient.deleteBeer(UUID.randomUUID());
    }

}