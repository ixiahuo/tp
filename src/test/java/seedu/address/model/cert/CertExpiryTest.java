package seedu.address.model.cert;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class CertExpiryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CertExpiry(null));
    }

    @Test
    public void equals() {
        CertExpiry expiry = new CertExpiry(LocalDate.parse("2026-03-17"));
        assertTrue(expiry.equals(new CertExpiry(LocalDate.parse("2026-03-17"))));

        assertTrue(expiry.equals(expiry));

        assertFalse(expiry.equals(5));

        assertFalse(expiry.equals("2026-03-08"));
    }
}
