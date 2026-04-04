package seedu.address.model.cert;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Represents the expiry date of a certificate.
 */
public class CertExpiry {
    public static final String MESSAGE_CONSTRAINTS =
            "Certificate expiry dates should be in the format of yyyy-mm-dd.\n"
            + "The date provided must be a valid calendar date.\n"
            + "Tip: check if it is a leap year or if the day exists in that month.\n\n"
            + "Utility: leading and trailing whitespaces are trimmed.";
    private LocalDate expiryDate;

    /**
     * Constructs a valid CertExpiry.
     * @param expiry Expiry date, can be null for no expiry.
     */
    public CertExpiry(LocalDate expiry) {
        expiryDate = expiry;
    }

    /**
     * Returns true if there is an expiry date.
     */
    public boolean hasExpiry() {
        return expiryDate != null;
    }

    /**
     * Returns true if a given string is a valid certificate expiry date.
     */
    public static boolean isValidCertExpiry(String test) {
        try {
            LocalDate.parse(test);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * Returns true if this expiry date is strictly before the {@code otherCertExpiry} date.
     */
    public boolean isBefore(CertExpiry otherCertExpiry) {
        //if this cert has no expiry, it is never before another date
        if (this.expiryDate == null) {
            return false;
        }
        //if other date is null, a specific date is logically before forever
        if (otherCertExpiry.expiryDate == null) {
            return true;
        }
        return expiryDate.isBefore(otherCertExpiry.expiryDate);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CertExpiry)) { //instanceof handles null
            return false;
        }
        CertExpiry otherCertExpiry = (CertExpiry) other;
        return java.util.Objects.equals(this.expiryDate, otherCertExpiry.expiryDate);
    }

    @Override
    public int hashCode() {
        return expiryDate.hashCode();
    }

    @Override
    public String toString() {
        return hasExpiry() ? this.expiryDate.toString() : "No Expiry";
    }

    public String getDisplayDateString() {
        if (expiryDate == null) {
            return "No Expiry";
        }
        return expiryDate.toString();
    }
}
