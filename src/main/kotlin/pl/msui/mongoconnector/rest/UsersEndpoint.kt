package pl.msui.mongoconnector.rest;

import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import pl.msui.mongoconnector.repo.User
import pl.msui.mongoconnector.repo.UserRepository
import kotlin.random.Random

@RestController
class UsersEndpoint(
    private val userRepository: UserRepository
) {

    @GetMapping(path = ["/insert/random"])
    fun insertUser(): User = userRepository.insert(rand()) ?: throw ResponseStatusException(CONFLICT)

    @GetMapping(path = ["/delete/random"])
    fun deleteUser() {
        if (!userRepository.delete(rand())) throw ResponseStatusException(NOT_FOUND)
    }

    @GetMapping(path = ["/get/random"])
    fun getUser(): User = userRepository.findById(rand()) ?: throw ResponseStatusException(NOT_FOUND)

    @GetMapping(path = ["/get/1"])
    fun getUser1(): User = userRepository.findById("1") ?: throw ResponseStatusException(NOT_FOUND)

    private fun rand() = "" + Random(System.nanoTime()).nextInt(1000000)
}
