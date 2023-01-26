package com.eronalves.functionalendpoints;

import com.eronalves.functionalendpoints.user.Fetcher;
import com.eronalves.functionalendpoints.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.web.servlet.function.RequestPredicates.accept;

@SpringBootApplication
public class FunctionalendpointsApplication {

	@Autowired
	Fetcher fetcher;

	public static void main(String[] args) {
		SpringApplication.run(FunctionalendpointsApplication.class, args);
	}

	@Bean
	public RouterFunction<?> routerFunction(){
		return RouterFunctions.route()
				.path("/user", r -> r
						.POST(accept(MediaType.APPLICATION_JSON), request -> {
							User received = request.body(User.class);
							User inserted = fetcher.insert(received);
							return ServerResponse.created(new URI("/" + received.id())).body(received);
						})
						.GET("/all", request -> ServerResponse.ok().body(this.fetcher.all()))
						.path("/{id}", subpath -> subpath
								.GET(request -> ServerResponse.ok().body(this.fetcher.get(request.pathVariable("id"))))
								.PUT(accept(MediaType.APPLICATION_JSON), request -> {
									User received = request.body(User.class);
									fetcher.update(received, request.pathVariable("id"));
									return ServerResponse.ok().body("");
								})
								.DELETE(request -> {
									fetcher.delete(request.pathVariable("id"));
									return ServerResponse.ok().body("");
								})
								.build()))
				.build();
	}


}
