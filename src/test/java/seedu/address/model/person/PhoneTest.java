package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "911";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 3 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits
        assertFalse(Phone.isValidPhone("93121534")); // no country code
        assertFalse(Phone.isValidPhone("+65 9")); // less than 3 digits for phone number
        assertFalse(Phone.isValidPhone("+18 124293842033123038424")); // phone numbers more than 15 digits

        // valid phone numbers
        assertTrue(Phone.isValidPhone("")); // empty string
        assertTrue(Phone.isValidPhone("+65 911")); // exactly 3 numbers
        assertTrue(Phone.isValidPhone("+60 93121534"));
        assertTrue(Phone.isValidPhone("+18 124293842033123")); // phone number within 15 digits limit
    }

    @Test
    public void equals() {
        Phone phone = new Phone("+65 999");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("+65 999")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("+65 995")));
    }
}
