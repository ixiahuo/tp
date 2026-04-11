package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's salary in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSalary(String)}
 */
public class Salary {
    public static final String MESSAGE_CONSTRAINTS =
            "Salaries should be an integer and no special characters are allowed.\n\n"
            + "Utility: leading, trailing and internal whitespaces will be trimmed.";

    public static final String VALIDATION_REGEX = "\\d+";
    public final String value;

    /**
     * Constructs a {@code Salary}.
     *
     * @param salary A valid salary.
     */
    public Salary(String salary) {
        requireNonNull(salary);
        checkArgument(isValidSalary(String.valueOf(salary)), MESSAGE_CONSTRAINTS);
        String normalized = salary.replaceFirst("^0+(?!$)", "");
        this.value = normalized.isEmpty() ? salary : normalized;
    }

    /**
     * Returns true if a given salary is a valid salary.
     * The salary field can be empty.
     */
    public static boolean isValidSalary(String test) {
        if (test == null || test.trim().isEmpty()) {
            return true;
        }
        return test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof Salary)) {
            return false;
        }

        Salary otherSalary = (Salary) other;
        return value.equals(otherSalary.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
