package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cert.CertName;
import seedu.address.model.cert.Certificate;
import seedu.address.testutil.Assert;

public class JsonAdaptedCertTest {
    @Test
    public void toModelType_noExpiryString_returnsCertificateWithNullExpiry() throws Exception {
        // Test the branch: if (certExpiry == null || certExpiry.equals("No Expiry"))
        JsonAdaptedCert cert = new JsonAdaptedCert("Marketing", "No Expiry");
        Certificate modelCert = cert.toModelType();

        assertFalse(modelCert.getExpiry().hasExpiry());
        assertEquals("Marketing", modelCert.getName().toString());
    }

    @Test
    public void toModelType_nullExpiryString_returnsCertificateWithNullExpiry() throws Exception {
        // Test the null branch of the same if-statement
        JsonAdaptedCert cert = new JsonAdaptedCert("Marketing", null);
        Certificate modelCert = cert.toModelType();

        assertFalse(modelCert.getExpiry().hasExpiry());
        assertEquals("Marketing", modelCert.getName().toString());
    }

    @Test
    public void toModelType_invalidCertName_throwsIllegalValueException() {
        JsonAdaptedCert cert = new JsonAdaptedCert(" ", "2029-01-01");
        String expectedMessage = CertName.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, cert::toModelType);
    }
}
