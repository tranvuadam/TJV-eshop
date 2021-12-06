/*
 * Project Social Network, console client. Created for Java Technology course at Czech Technical University in Prague,
 * Faculty of Information Technology.
 *
 * Author: Ondřej Guth (ondrej.guth@fit.cvut.cz)
 *
 * This code is intended for educational purposes only.
 */

package cz.cvut.fit.tjv.social_network.web.data;

import cz.cvut.fit.tjv.social_network.web.model.ProductDTO;
import cz.cvut.fit.tjv.social_network.web.model.ProductWebModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class ProductClient {
    private static final String ONE_URI = "/{id}";
    private final WebClient productWebClient;

    public ProductClient(@Value("${eshop_backend_url}") String backendUrl) {
        productWebClient = WebClient.create(backendUrl + "/product");
    }

    public Mono<ProductWebModel> create(ProductDTO product) {
        return productWebClient.post() // HTTP POST
                .contentType(MediaType.APPLICATION_JSON) // set HTTP header
                .bodyValue(product) // POST data
                .retrieve() // request specification finished
                .bodyToMono(ProductWebModel.class); // interpret response body as one element
    }

    public Flux<ProductWebModel> readAll() {
        return productWebClient.get() // HTTP GET
                .retrieve() // request specification finished
                .bodyToFlux(ProductWebModel.class); // interpret response body as a collection
    }

    public Flux<ProductWebModel> readAllWithPriceLowerThan(Integer price) {
        return productWebClient.get() // HTTP GET
                .uri("?highest_price=" + price)
                .retrieve() // request specification finished
                .bodyToFlux(ProductWebModel.class); // interpret response body as a collection
    }


    public Mono<ProductWebModel> readById(Long Id) {
        return productWebClient.get()
                .uri(ONE_URI, Id).retrieve()
                .bodyToMono(ProductWebModel.class)
                ;
    }

    public Mono<ProductWebModel> update(ProductDTO product) {
        return productWebClient.post()
                .uri(ONE_URI, product.getId())
                .contentType(MediaType.APPLICATION_JSON) // HTTP header
                .bodyValue(product) // HTTP body
                .retrieve()
                .bodyToMono(ProductWebModel.class)
        ;
    }

    public Mono<Void> delete(Long Id) {
        return productWebClient.delete() // HTTP DELETE
                .uri(ONE_URI, Id.toString()) // URI
                .retrieve() // request specification finished
                .bodyToMono(Void.TYPE)
        ;
    }
}
