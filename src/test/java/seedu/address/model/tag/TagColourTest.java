package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

public class TagColourTest {

    @Test
    public void getTagColourByUserInputName_validColourName_success() {
        assertTrue(TagColour.getTagColourByUserInputName("red")
                .filter(Predicate.isEqual(TagColour.RED)).isPresent());

        assertTrue(TagColour.getTagColourByUserInputName("PURPLE")
                .filter(Predicate.isEqual(TagColour.PURPLE)).isPresent());

        assertTrue(TagColour.getTagColourByUserInputName("defAulT")
                .filter(Predicate.isEqual(TagColour.DEFAULT)).isPresent());
    }

    @Test
    public void getTagColourByUserInputName_invalidColourName_success() {
        /* NOTE: Handling invalid user colours is left to the programmer to decide, either throw error or use default */

        assertTrue(TagColour.getTagColourByUserInputName("redder").isEmpty());

        assertTrue(TagColour.getTagColourByUserInputName("gray").isEmpty());

        assertTrue(TagColour.getTagColourByUserInputName("").isEmpty());

        assertTrue(TagColour.getTagColourByUserInputName("ZZZZ")
                .filter(Predicate.isEqual(TagColour.DEFAULT)).isEmpty());
    }


}
