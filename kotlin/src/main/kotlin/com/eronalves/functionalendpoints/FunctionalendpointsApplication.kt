package com.eronalves.functionalendpoints

import com.eronalves.functionalendpoints.user.Fetcher
import com.eronalves.functionalendpoints.user.User
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.function.RouterFunctions
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import java.net.URI

@SpringBootApplication
class FunctionalendpointsApplication(val fetcher: Fetcher){

	@Bean
	fun routeFunctions() = RouterFunctions.route()
		.path("/user") { r: RouterFunctions.Builder -> r
			.GET("/all") { _: ServerRequest -> ServerResponse.ok().body(fetcher.all()) }
			.POST {
				val inserted = fetcher.insert(it.body(User::class.java))
				ServerResponse.created(URI("/" + inserted.id)).body(inserted)
			}
			.path("/{id}") { subpath -> subpath
					.GET { ServerResponse.ok().body(fetcher.getById(it.pathVariable("id"))) }
					.PUT {
						fetcher.update(it.body(User::class.java), it.pathVariable("id"))
						ServerResponse.ok().body("")
					}
					.DELETE {
						fetcher.delete(it.pathVariable("id"))
						ServerResponse.ok().body("")}
					}
					.build()
			}
			.build()
}

fun main(args: Array<String>) {
	runApplication<FunctionalendpointsApplication>(*args)
}