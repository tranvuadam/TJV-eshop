/*
 * Project Social Network, console client. Created for Java Technology course at Czech Technical University in Prague,
 * Faculty of Information Technology.
 *
 * Author: Ondřej Guth (ondrej.guth@fit.cvut.cz)
 *
 * This code is intended for educational purposes only.
 */

package cz.cvut.fit.tjv.social_network.web.data;

import cz.cvut.fit.tjv.social_network.web.model.UserDTO;
import cz.cvut.fit.tjv.social_network.web.model.UserWebModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collection;


@Component
public class UserClient {
    private static final String ONE_URI = "/{id}";
    private final WebClient userWebClient;

    public UserClient(@Value("${eshop_backend_url}") String backendUrl) {
        userWebClient = WebClient.create(backendUrl + "/user");
    }

    public Mono<UserWebModel> create(UserDTO user) {
        return userWebClient.post() // HTTP POST
                .contentType(MediaType.APPLICATION_JSON) // set HTTP header
                .bodyValue(user) // POST data
                .retrieve() // request specification finished
                .bodyToMono(UserWebModel.class); // interpret response body as one element
    }

    public Flux<UserWebModel> readAll() {
        return userWebClient.get() // HTTP GET
                .retrieve() // request specification finished
                .bodyToFlux(UserWebModel.class); // interpret response body as a collection
    }

    public Mono<UserWebModel> readById(Long Id) {
        return userWebClient.get()
                .uri(ONE_URI, Id).retrieve()
                .bodyToMono(UserWebModel.class)
                ;
    }

    public Mono<UserWebModel> update(UserDTO user) {
        return userWebClient.post()
                .uri(ONE_URI, user.getId())
                .contentType(MediaType.APPLICATION_JSON) // HTTP header
                .bodyValue(user) // HTTP body
                .retrieve()
                .bodyToMono(UserWebModel.class)
        ;
    }

    public Mono<Void> delete(Long Id) {
        System.out.println(Id.toString());
        return userWebClient.delete() // HTTP DELETE
                .uri(ONE_URI, Id.toString()) // URI
                .retrieve() // request specification finished
                .bodyToMono(Void.TYPE)
        ;
    }
}
