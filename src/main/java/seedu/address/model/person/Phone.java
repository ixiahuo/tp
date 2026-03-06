package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {


    public static final String MESSAGE_CONSTRAINTS =
            "If not blank, phone numbers should adopt the format +<COUNTRY_CODE> <PHONE NUMBER>, "
                    + "with a space between country code and number, followed by min 3 max 15 digits only";
    /* If not blank:
     * (1) Must start with '+' followed by 1-3 digits for country code.
     * (2) Must be followed by a single space.
     * (3) Must then be followed by minimum 3 and maximum 15 digits.
     */
    public static final String VALIDATION_REGEX = "^\\+\\d{1,3} \\d{3,15}$";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     * Field be empty but not null
     */
    public static boolean isValidPhone(String test) {
        return test.isEmpty() || test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
