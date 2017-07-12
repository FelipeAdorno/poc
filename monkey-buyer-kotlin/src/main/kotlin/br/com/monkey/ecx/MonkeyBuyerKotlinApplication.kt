package br.com.monkey.ecx

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MonkeyBuyerKotlinApplication

fun main(args: Array<String>) {
    SpringApplication.run(MonkeyBuyerKotlinApplication::class.java, *args)
}
