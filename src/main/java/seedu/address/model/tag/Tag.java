package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tag names can only consist of alphanumeric characters"
            + " and certain special characters such as '!@#$?/|<>_*&:;='";
    public static final String MESSAGE_CONSTRAINTS_LENGTH = "Tags names should be short."
            + "Less than equal to 30 characters";

    // Note: If the below rules are too lax, revert back to "\\p{Alnum}+"
    // i.e. Only alphanumeric characters instead
    public static final String VALIDATION_REGEX = "[\\-\\p{Alnum}!@#$?/|<>_*&:;=]+";
    public static final Integer MAX_LENGTH = 30;
    public final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        checkArgument(isValidTagLength(tagName), MESSAGE_CONSTRAINTS_LENGTH);
        this.tagName = tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string fits the valid tag length.
     */
    public static boolean isValidTagLength(String test) {
        return test.length() <= MAX_LENGTH;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }

        Tag otherTag = (Tag) other;
        return tagName.equals(otherTag.tagName);
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
