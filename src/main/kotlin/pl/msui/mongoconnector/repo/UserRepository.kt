package pl.msui.mongoconnector.repo;

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val mongoTemplate: MongoTemplate
) {

    fun findById(id: String): User? = mongoTemplate.findById(id, User::class.java)
    fun insert(name: String): User? = mongoTemplate.insert(User(null, name))
    fun delete(id: String): Boolean = mongoTemplate.remove(Query(where("id").`is`(id)), User::class.java).wasAcknowledged()
}
