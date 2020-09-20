package com.d2d.allygator.shuttle

import com.d2d.allygator.shuttle.rest.VehicleResource
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest(classes = [AllygatorShuttleApplication::class])
class AllygatorShuttleApplicationTests {

    @Autowired
    private val vehicleResource: VehicleResource? = null

    @Test
    fun contextLoads() {
        Assertions.assertThat(vehicleResource).isNotNull
    }

}
