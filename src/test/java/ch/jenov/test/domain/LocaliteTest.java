package ch.jenov.test.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.jenov.test.web.rest.TestUtil;

public class LocaliteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Localite.class);
        Localite localite1 = new Localite();
        localite1.setId(1L);
        Localite localite2 = new Localite();
        localite2.setId(localite1.getId());
        assertThat(localite1).isEqualTo(localite2);
        localite2.setId(2L);
        assertThat(localite1).isNotEqualTo(localite2);
        localite1.setId(null);
        assertThat(localite1).isNotEqualTo(localite2);
    }
}
