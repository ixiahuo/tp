package seedu.address.storage;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cert.CertExpiry;
import seedu.address.model.cert.CertName;
import seedu.address.model.cert.Certificate;

/**
 * Jackson-friendly version of {@link Certificate}.
 */
class JsonAdaptedCert {

    @JsonProperty("name")
    private final String certName;
    @JsonProperty("expiry date")
    private final String certExpiry;

    /**
     * Constructs a {@code JsonAdaptedCert} with the given {@code certName} and {@code certExpiry}.
     */
    @JsonCreator
    public JsonAdaptedCert(@JsonProperty("name") String certName,
                           @JsonProperty("expiry") String certExpiry) {
        this.certName = certName;
        this.certExpiry = certExpiry;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedCert(Certificate source) {
        certName = source.getName().toString();
        certExpiry = source.getExpiry().toString();
    }

    /**
     * Converts this Jackson-friendly adapted certificate object into the model's {@code Certificate} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Certificate.
     */
    public Certificate toModelType() throws IllegalValueException {
        if (!CertName.isValidCertName(certName)) {
            throw new IllegalValueException(CertName.MESSAGE_CONSTRAINTS);
        }
        CertName modelName = new CertName(certName);
        if (certExpiry == null || certExpiry.equals("No Expiry")) {
            return new Certificate(modelName, new CertExpiry(null));
        }
        if (!CertExpiry.isValidCertExpiry(certExpiry)) {
            throw new IllegalValueException(CertExpiry.MESSAGE_CONSTRAINTS);
        }
        return new Certificate(new CertName(certName), new CertExpiry(LocalDate.parse(certExpiry)));
    }

}
