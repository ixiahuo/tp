package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {

    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should adopt the format: +<COUNTRY_CODE> <PHONE_NUMBER>\n"
            + "- COUNTRY_CODE: 1 to 3 digits after a '+' "
            + "followed by space, then\n"
            + "- PHONE_NUMBER: 3 to 15 digits (excluding spaces)\n\n"
            + "Utility:\nLeading and trailing whitespaces will be trimmed.\n"
            + "Whitespaces between `+` and COUNTRY_CODE will be trimmed.\n"
            + "Internal whitespaces in PHONE_NUMBER will be trimmed to 1.\n"
            + "e.g. `  +    33  1  62  31  23    45   ` "
            + "will be trimmed to `+33 1 62 31 23 45`";

    /* If not blank:
     * (1) Must start with '+' followed by 1-3 digits for country code.
     * (2) Must be followed by a single space.
     * (3) Must then be followed by a sequence of digits and single spaces.
     */
    public static final String VALIDATION_REGEX = "^\\+\\d{1,3} (\\d+ ?)*\\d+$";
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
     * The phone field can be empty.
     */
    public static boolean isValidPhone(String test) {
        if (test == null || test.trim().isEmpty()) {
            return true;
        }
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        String[] parts = test.split(" ", 2);
        if (parts.length < 2) {
            return false;
        }
        String phoneNumberPart = parts[1];
        String digitsOnly = phoneNumberPart.replaceAll("[^0-9]", "");
        return digitsOnly.length() >= 3 && digitsOnly.length() <= 15;
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
