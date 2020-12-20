package ch.jenov.test.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.jenov.test.web.rest.TestUtil;

public class LotVaccinTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LotVaccin.class);
        LotVaccin lotVaccin1 = new LotVaccin();
        lotVaccin1.setId(1L);
        LotVaccin lotVaccin2 = new LotVaccin();
        lotVaccin2.setId(lotVaccin1.getId());
        assertThat(lotVaccin1).isEqualTo(lotVaccin2);
        lotVaccin2.setId(2L);
        assertThat(lotVaccin1).isNotEqualTo(lotVaccin2);
        lotVaccin1.setId(null);
        assertThat(lotVaccin1).isNotEqualTo(lotVaccin2);
    }
}
