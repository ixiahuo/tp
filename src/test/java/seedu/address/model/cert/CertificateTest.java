package seedu.address.model.cert;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class CertificateTest {
    @Test
    public void isSameCert() {
        Certificate cert1 = new Certificate(new CertName("Accounting"),
                new CertExpiry(LocalDate.parse("2026-03-03")));
        Certificate cert2 = new Certificate(new CertName("Accounting"),
                new CertExpiry(LocalDate.parse("2027-03-03")));
        Certificate cert3 = new Certificate(new CertName("Marketing"),
                new CertExpiry(LocalDate.parse("2026-03-03")));
        // same name different dates
        assertTrue(cert1.isSameCert(cert2));
        // same object
        assertTrue(cert1.isSameCert(cert1));
        // same date different name
        assertFalse(cert1.isSameCert(cert3));
    }
}
