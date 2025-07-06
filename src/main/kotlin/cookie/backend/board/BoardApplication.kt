package cookie.backend.board

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["cookie.backend.board"])
class BoardApplication

fun main(args: Array<String>) {
	runApplication<BoardApplication>(*args)
}
