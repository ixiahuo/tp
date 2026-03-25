package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class TagColourComparatorTest {

    @Test
    public void compare() {
        Tag tagRed = new Tag("TAG1", TagColour.RED);
        Tag tagDefault = new Tag("TAG2", TagColour.DEFAULT);
        Tag tagPurple = new Tag("TAG3", TagColour.PURPLE);

        List<Tag> tags = List.of(tagDefault, tagPurple, tagRed);
        List<Tag> sortedTags = tags.stream().sorted(new TagColourComparator()).toList();

        assertEquals(sortedTags.get(0), tagRed);
        assertEquals(sortedTags.get(1), tagDefault);
        assertEquals(sortedTags.get(2), tagPurple);
    }

}
