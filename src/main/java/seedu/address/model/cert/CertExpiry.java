package seedu.address.model.cert;

import java.time.DateTimeException;
import java.time.LocalDate;

/** 
 * Represents the expiry date of a certificate.
*/
public class CertExpiry {
    public static final String MESSAGE_CONSTRAINTS =
            "Certificate expiry dates should be in the format of yyyy-mm-dd.";
    private LocalDate expiryDate;

    public CertExpiry(LocalDate expiry) {
        expiryDate = expiry;
    }

    /**
     * Returns true if a given string is a valid certificate name.
     */
    public static boolean isValidCertExpiry(String test) {
        try {
            LocalDate.parse(test);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
}
