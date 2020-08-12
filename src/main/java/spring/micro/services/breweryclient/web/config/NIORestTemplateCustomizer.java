package spring.micro.services.breweryclient.web.config;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/*
 * Created by Nibir Hossain on 12.08.20
 */

// @Component
public class NIORestTemplateCustomizer implements RestTemplateCustomizer {

    private final Integer maxTotalConnections;
    private final Integer defaultMaxTotalConnections;
    private final Integer connectionRequestTimeout;
    private final Integer socketTimeout;
    private final Integer ioThreadCount;

    public NIORestTemplateCustomizer(@Value("${brewery.apache.maxtotalconnections}") Integer maxTotalConnections,
                                     @Value("${brewery.apache.defaultmaxtotalconnections}") Integer defaultMaxTotalConnections,
                                     @Value("${brewery.apache.connectionrequesttimeout}") Integer connectionRequestTimeout,
                                     @Value("${brewery.apache.sockettimeout}") Integer socketTimeout,
                                     @Value("${brewery.apache.iothreadcount}") Integer ioThreadCount) {
        this.maxTotalConnections = maxTotalConnections;
        this.defaultMaxTotalConnections = defaultMaxTotalConnections;
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.socketTimeout = socketTimeout;
        this.ioThreadCount = ioThreadCount;
    }

    public ClientHttpRequestFactory clientHttpRequestFactory() throws IOReactorException {
        final DefaultConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(
                IOReactorConfig
                .custom()
                .setConnectTimeout(connectionRequestTimeout)
                .setIoThreadCount(ioThreadCount)
                .setSoTimeout(socketTimeout)
                .build()
        );

        final PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(ioReactor);
        connectionManager.setDefaultMaxPerRoute(defaultMaxTotalConnections);
        connectionManager.setMaxTotal(maxTotalConnections);

        CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients
                .custom()
                .setConnectionManager(connectionManager)
                .build();

        return new HttpComponentsAsyncClientHttpRequestFactory(httpAsyncClient);
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        try {
            restTemplate.setRequestFactory(this.clientHttpRequestFactory());
        } catch (IOReactorException e) {
            e.printStackTrace();
        }
    }
}
