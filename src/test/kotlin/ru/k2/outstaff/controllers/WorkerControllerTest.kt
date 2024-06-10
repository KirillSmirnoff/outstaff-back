package ru.k2.outstaff.controllers

import org.junit.jupiter.api.Test
import org.mockito.Mockito.doReturn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.k2.outstaff.dto.worker.WorkerDto
import ru.k2.outstaff.service.WorkerService
import ru.k2.outstaff.utils.WorkerType
import java.time.LocalDate

@WebMvcTest(value = [WorkerController::class])
internal class WorkerControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var workerService: WorkerService

    @Test
    fun listWorkers() {
        //Given
        doReturn(listOf(
                WorkerDto().apply {
                    id = 123
                    bithday = LocalDate.parse("1994-12-03")
                    name = "Yargi"
                    phone = "+9936287653"
                    mail = "yari@mail.ru"
                    status = null
                    type = WorkerType.RVP.name
                    company = "OOO GOGO"

                },
                WorkerDto().apply {
                    id = 134
                    bithday = LocalDate.parse("1974-03-10")
                    name = "DXXDLY"
                    phone = "+9936253453"
                    mail = "syd@mail.ru"
                    status = null
                    type = WorkerType.RVP.name
                    company = "OOO TODO"

                })
        ).`when`(workerService).getWorkers()
        //When

        mvc.perform(MockMvcRequestBuilders.get("/worker").accept(MediaType.APPLICATION_JSON_VALUE))
                //Then

                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string("[{\"id\":123,\"name\":\"Yargi\",\"bithday\":\"1994-12-03\",\"phone\":\"+9936287653\",\"mail\":\"yari@mail.ru\",\"status\":null,\"type\":\"RVP\",\"company\":\"OOO GOGO\"},{\"id\":134,\"name\":\"DXXDLY\",\"bithday\":\"1974-03-10\",\"phone\":\"+9936253453\",\"mail\":\"syd@mail.ru\",\"status\":null,\"type\":\"RVP\",\"company\":\"OOO TODO\"}]"))


    }
}