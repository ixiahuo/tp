package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name implements Comparable<Name> {

    public static final String MESSAGE_CONSTRAINTS =
            "Name cannot be empty. It should contain only letters, spaces and forward slashes.\n"
            + "Letters immediately closest to a forward slash must be uppercase. (e.g. S/O)";

    /*
     * (1) Only alphabetical characters, single spaces, and forward slashes.
     * (2) Cannot be blank.
     * (3) Letters immediately beside a forward slash must be uppercase.
     * This regex ensures the first and last characters are not spaces,
     * and only single spaces are allowed between characters.
     */
    public static final String VALIDATION_REGEX = "^[a-zA-Z]+(([ ]|[A-Z]/[A-Z])[a-zA-Z]*)*$";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     * The name field cannot be empty.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public int compareTo(Name otherName) {
        return this.fullName.compareTo(otherName.fullName);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        // Case-insensitive comparison for duplicate checks
        return fullName.equalsIgnoreCase(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
