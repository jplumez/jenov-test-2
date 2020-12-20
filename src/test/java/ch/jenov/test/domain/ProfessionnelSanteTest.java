package ch.jenov.test.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.jenov.test.web.rest.TestUtil;

public class ProfessionnelSanteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfessionnelSante.class);
        ProfessionnelSante professionnelSante1 = new ProfessionnelSante();
        professionnelSante1.setId(1L);
        ProfessionnelSante professionnelSante2 = new ProfessionnelSante();
        professionnelSante2.setId(professionnelSante1.getId());
        assertThat(professionnelSante1).isEqualTo(professionnelSante2);
        professionnelSante2.setId(2L);
        assertThat(professionnelSante1).isNotEqualTo(professionnelSante2);
        professionnelSante1.setId(null);
        assertThat(professionnelSante1).isNotEqualTo(professionnelSante2);
    }
}
