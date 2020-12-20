package ch.jenov.test.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ch.jenov.test.web.rest.TestUtil;

public class CreneauHoraireTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreneauHoraire.class);
        CreneauHoraire creneauHoraire1 = new CreneauHoraire();
        creneauHoraire1.setId(1L);
        CreneauHoraire creneauHoraire2 = new CreneauHoraire();
        creneauHoraire2.setId(creneauHoraire1.getId());
        assertThat(creneauHoraire1).isEqualTo(creneauHoraire2);
        creneauHoraire2.setId(2L);
        assertThat(creneauHoraire1).isNotEqualTo(creneauHoraire2);
        creneauHoraire1.setId(null);
        assertThat(creneauHoraire1).isNotEqualTo(creneauHoraire2);
    }
}
