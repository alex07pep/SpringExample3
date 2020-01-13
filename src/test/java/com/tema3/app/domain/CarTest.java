package com.tema3.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tema3.app.web.rest.TestUtil;

public class CarTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Car.class);
        Car car1 = new Car();
        car1.setId(1L);
        Car car2 = new Car();
        car2.setId(car1.getId());
        assertThat(car1).isEqualTo(car2);
        car2.setId(2L);
        assertThat(car1).isNotEqualTo(car2);
        car1.setId(null);
        assertThat(car1).isNotEqualTo(car2);
    }
}
