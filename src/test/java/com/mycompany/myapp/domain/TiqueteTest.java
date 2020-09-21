package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class TiqueteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tiquete.class);
        Tiquete tiquete1 = new Tiquete();
        tiquete1.setId(1L);
        Tiquete tiquete2 = new Tiquete();
        tiquete2.setId(tiquete1.getId());
        assertThat(tiquete1).isEqualTo(tiquete2);
        tiquete2.setId(2L);
        assertThat(tiquete1).isNotEqualTo(tiquete2);
        tiquete1.setId(null);
        assertThat(tiquete1).isNotEqualTo(tiquete2);
    }
}
