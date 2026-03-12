package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.MULTI_TAG_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MULTI_TAG_DESC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TagCommand;
import seedu.address.model.tag.Tag;

public class TagCommandParserTest {

    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, "1" + MULTI_TAG_DESC_AMY + MULTI_TAG_DESC_BOB,
                new TagCommand(INDEX_FIRST_PERSON,
                        new HashSet<>(Arrays.stream(MULTI_TAG_DESC_AMY
                                .split("\\s+")).map(Tag::new).toList()),
                        new HashSet<>(Arrays.stream(MULTI_TAG_DESC_BOB
                                .split("\\s+")).map(Tag::new).toList())
                        ));
    }

    @Test
    public void parse_someFieldsPresent_success() {
        assertParseSuccess(parser, "1 d/TEST1 TEST2",
                new TagCommand(INDEX_FIRST_PERSON,
                        Set.of(),
                        Set.of(new Tag("TEST1"), new Tag("TEST2"))
                ));

        assertParseSuccess(parser, "1 a/TEST1 TEST2",
                new TagCommand(INDEX_FIRST_PERSON,
                        Set.of(new Tag("TEST1"), new Tag("TEST2")),
                        Set.of()
                ));
    }

    @Test
    public void parse_noFieldsPresent_success() {

    }


    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a" + MULTI_TAG_DESC_AMY + MULTI_TAG_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
    }


}
