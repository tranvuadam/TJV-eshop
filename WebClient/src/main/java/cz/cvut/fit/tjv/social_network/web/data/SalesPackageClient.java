/*
 * Project Social Network, console client. Created for Java Technology course at Czech Technical University in Prague,
 * Faculty of Information Technology.
 *
 * Author: Ondřej Guth (ondrej.guth@fit.cvut.cz)
 *
 * This code is intended for educational purposes only.
 */

package cz.cvut.fit.tjv.social_network.web.data;

import cz.cvut.fit.tjv.social_network.web.model.*;
import io.netty.handler.codec.http2.Http2Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
public class SalesPackageClient {
    private static final String ONE_URI = "/{id}";
    private final WebClient salesPackageWebClient;

    public SalesPackageClient(@Value("${eshop_backend_url}") String backendUrl) {
        salesPackageWebClient = WebClient.create(backendUrl + "/package");
    }

    public Mono<SalesPackageWebModel> create(SalesPackageDTO salesPackageDTO) {
        return salesPackageWebClient.post() // HTTP POST
                .contentType(MediaType.APPLICATION_JSON) // set HTTP header
                .bodyValue(salesPackageDTO) // POST data
                .retrieve()
                .bodyToMono(SalesPackageWebModel.class); // interpret response body as one element
    }

    public Flux<SalesPackageWebModel> readAll() {
        return salesPackageWebClient.get() // HTTP GET
                .retrieve() // request specification finished
                .bodyToFlux(SalesPackageWebModel.class); // interpret response body as a collection
    }

    public Flux<SalesPackageWebModel> readAllWithPriceLowerThan(Integer price) {
        return salesPackageWebClient.get() // HTTP GET
                .uri("?highest_price=" + price)
                .retrieve() // request specification finished
                .bodyToFlux(SalesPackageWebModel.class); // interpret response body as a collection
    }


    public Mono<SalesPackageWebModel> readById(Long Id) {
        return salesPackageWebClient.get()
                .uri(ONE_URI, Id).retrieve()
                .bodyToMono(SalesPackageWebModel.class)
                ;
    }

    public Mono<SalesPackageWebModel> update(SalesPackageDTO salesPackageDTO, Boolean mergeProducts) {

        return salesPackageWebClient.post()
                .uri("/" + salesPackageDTO.getId() + "?merge_products=" + mergeProducts)
                .contentType(MediaType.APPLICATION_JSON) // HTTP header
                .bodyValue(salesPackageDTO) // HTTP body
                .retrieve()
                .bodyToMono(SalesPackageWebModel.class)
        ;
    }

    public Mono<Void> delete(Long Id) {
        return salesPackageWebClient.delete() // HTTP DELETE
                .uri(ONE_URI, Id.toString()) // URI
                .retrieve() // request specification finished
                .bodyToMono(Void.TYPE)
        ;
    }
}
