package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_emptyTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "Lets, Test";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }


    @Test
    public void constructor_invalidTagLength_throwsIllegalArgumentException() {
        // Should be less than equal to 30
        String invalidTagName = "thisTagIsWayTooLongOverThirtyCharacters";

        assertFalse(Tag.isValidTagLength(invalidTagName)); // over 30 char
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // invalid tag names
        assertFalse(Tag.isValidTagName("friend,family")); // contains comma
        assertFalse(Tag.isValidTagName("hr team #2")); // contains special char

        // valid tag names
        assertTrue(Tag.isValidTagName("friend")); // alphanumeric
        assertTrue(Tag.isValidTagName("12345")); // numbers
    }

    @Test
    public void equals() {

        Tag tagRedRed = new Tag("RED", TagColour.RED);
        Tag tagRedDefault = new Tag("RED");
        Tag tagRedBlue = new Tag("RED", TagColour.BLUE);
        Tag tagBlueRed = new Tag("BLUE", TagColour.RED);

        // same values -> returns true
        assertTrue(tagRedRed.equals(new Tag("RED", TagColour.RED)));

        // same object -> returns true
        assertTrue(tagRedRed.equals(tagRedRed));

        // null -> returns false
        assertFalse(tagRedRed.equals(null));

        // different types -> returns false
        assertFalse(tagRedRed.equals(5.0f));

        // different values -> returns false
        assertFalse(tagRedRed.equals(new Tag("RED", TagColour.BLUE)));
        assertFalse(tagRedRed.equals(new Tag("BLUE", TagColour.RED)));

        assertFalse(tagRedRed.equals(tagRedDefault));
        assertFalse(tagRedRed.equals(tagRedBlue));
        assertFalse(tagRedRed.equals(tagBlueRed));
    }

}
