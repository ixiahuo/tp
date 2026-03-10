package seedu.address.model.cert;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a certificate that a person can hold.
 */
public class Certificate {
    private CertName name;
    private CertExpiry expiry;

    /**
     * Every field must be present.
     */
    public Certificate(CertName name, CertExpiry expiry) {
        this.name = name;
        this.expiry = expiry;
    }

    CertName getName() {
        return name;
    }

    CertExpiry getExpiry() {
        return expiry;
    }

    /**
     * Returns true if both certificates have the same name.
     * This defines a weaker notion of equality between two certificates.
     */
    public boolean isSameCert(Certificate otherCertificate) {
        if (otherCertificate == this) {
            return true;
        }

        return otherCertificate != null
                && otherCertificate.getName().equals(getName());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Certificate)) {
            return false;
        }

        Certificate otherCert = (Certificate) other;
        return name.equals(otherCert.name)
                && expiry.equals(otherCert.expiry);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, expiry);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("expiry date", expiry)
                .toString();
    }
}
