package ru.k2.outstaff

import org.junit.jupiter.api.BeforeAll
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

/*
* Аннотацию @Testcontainers не используем, т.к данная аннотация отвечает за то,
* чтобы контейнер запускался и останавливался после каждого теста.
*
* Нас такой вариант не устраивает.
 */
abstract class AbstractDataBaseContainer {

    companion object {

        private val testContainer = PostgreSQLContainer<Nothing>(DockerImageName.parse("postgres:16.2"))
//                .apply {
//                    withInitScript("init_test_db.sql")
//                    withDatabaseName("k2")
//                }


        @JvmStatic
        @DynamicPropertySource
        fun postgresProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", testContainer::getJdbcUrl)
        }

        @JvmStatic
        @BeforeAll
        internal fun setUp() {
            testContainer.start()
        }
    }

}