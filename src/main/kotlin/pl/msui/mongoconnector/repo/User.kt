package pl.msui.mongoconnector.repo

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@TypeAlias("users")
@Document("users")
data class User(
    @Id val id: String?,
    val name: String
)
