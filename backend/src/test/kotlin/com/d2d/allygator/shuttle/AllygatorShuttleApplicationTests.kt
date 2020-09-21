package com.d2d.allygator.shuttle

import com.d2d.allygator.shuttle.config.PropertiesConfig
import com.d2d.allygator.shuttle.rest.VehicleResource
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest(classes = [AllygatorShuttleApplication::class])
class AllygatorShuttleApplicationTests {

    @Autowired
    private lateinit var vehicleResource: VehicleResource

    @Autowired
    private lateinit var propertiesConfig: PropertiesConfig

    @Test
    fun contextLoads() {
        assertAll("Context",
                {Assertions.assertThat(vehicleResource).isNotNull},
                {Assertions.assertThat(propertiesConfig).isNotNull}
        )
    }

}
