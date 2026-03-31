package seedu.address.model.cert;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the name of a certificate.
 */
public class CertName {

    public static final String MESSAGE_CONSTRAINTS =
            "Certificate names should consist of only alphanumeric characters and spaces.";

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String certificateName;

    /**
     * Constructs a {@code CertName}.
     *
     * @param name A valid certificate name.
     */
    public CertName(String name) {
        requireNonNull(name);
        checkArgument(isValidCertName(name), MESSAGE_CONSTRAINTS);
        certificateName = name;
    }

    /**
     * Returns true if a given string is a valid certificate name.
     */
    public static boolean isValidCertName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return certificateName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CertName)) {
            return false;
        }

        CertName otherName = (CertName) other;
        return certificateName.toLowerCase()
                .equals(otherName.certificateName.toLowerCase());
    }

    @Override
    public int hashCode() {
        return certificateName.hashCode();
    }
}
