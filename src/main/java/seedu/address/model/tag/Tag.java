package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.commons.util.StringUtil;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS =
            "Tag names should consist of only alphanumeric characters,\n"
            + "and these special characters !@#$?/|<>_*&:;=";

    public static final String MESSAGE_CONSTRAINTS_LENGTH =
            "Tags names should be at most 30 characters long.";

    // Note: If the below rules are too lax, revert back to "\\p{Alnum}+"
    // i.e. Only alphanumeric characters instead
    public static final String VALIDATION_REGEX = "[\\-\\p{Alnum}!@#$?/|<>_*&:;=]+";
    public static final Integer MAX_LENGTH = 30;
    public final String tagName;
    public final TagColour tagColour;

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
        this.tagColour = TagColour.DEFAULT;
    }

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     * @param tagColour A valid colour for the Tag.
     */
    public Tag(String tagName, TagColour tagColour) {
        requireNonNull(tagName);
        requireNonNull(tagColour);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        checkArgument(isValidTagLength(tagName), MESSAGE_CONSTRAINTS_LENGTH);
        this.tagColour = tagColour;
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

    public boolean containsTagNameIgnoreCase(Tag otherTag) {
        return StringUtil.containsWordIgnoreCase(tagName, otherTag.tagName);
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
        return tagName.equals(otherTag.tagName) && tagColour.equals(otherTag.tagColour);
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
