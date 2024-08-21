package com.example.demo.infrastructure.clients;

import com.example.demo.domain.services.ExchangeRateService;
import com.example.demo.infrastructure.clients.response.ExchangeRateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Component
public class ExchangeRateClient {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateService.class);
    private final WebClient webClient;

    @Value("${exchangerate.api.key}")
    private String apiKey;

    public ExchangeRateClient(WebClient.Builder webClientBuilder, @Value("${exchangerate.api.url}") String apiUrl) {
        this.webClient = webClientBuilder.baseUrl(apiUrl).build();
    }

    /**
     * Retrieves the exchange rate between the specified base currency and target currency.
     * This method makes an HTTP request to an external exchange rate service API,
     * which operates outside of the application's network. Since this is an external service,
     * be aware that the call may be subject to network latency, timeouts, or worst-case failures.
     * It's recommended to implement fault-tolerance mechanisms such as retries or circuit breakers
     * to handle such cases gracefully.
     *
     * @param baseCurrency The currency to convert from (e.g., "USD").
     * @param targetCurrency The currency to convert to (e.g., "DKK").
     * @return The exchange rate from the base currency to the target currency.
     */
    public BigDecimal getExchangeRate(String baseCurrency, String targetCurrency, BigDecimal amount) {
        logger.info("Fetching exchange rate from {} to {} with amount {}", baseCurrency, targetCurrency, amount);

        try
        {
            ExchangeRateResponse response = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/{apiKey}/pair/{from}/{to}/{amount}")
                            .build(apiKey, baseCurrency, targetCurrency, amount))
                    .retrieve()
                    .bodyToMono(ExchangeRateResponse.class)
                    .block();

            if (response.conversion_result == null)
                throw new RuntimeException("Failed to fetch exchange rate from ExchangeRate-API");

            return response.conversion_result;
        }
        catch (Exception e) {
            logger.error("Error fetching exchange rate: {}", e.getMessage());
            throw new RuntimeException("Unable to retrieve exchange rate, please try again later.", e);
        }
    }
}
