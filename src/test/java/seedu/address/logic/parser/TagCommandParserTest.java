package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_AT_LEAST_ONE_FIELD;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_FIELD_COMBI;
import static seedu.address.logic.commands.CommandTestUtil.MULTI_TAG_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MULTI_TAG_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TagCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColour;

public class TagCommandParserTest {

    private TagCommandParser parser = new TagCommandParser();

    private String defaultTagFlags = " a/TEST1 d/TEST2"; // needs preceeding space, otherwise different error

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, ParserUtil.MESSAGE_INVALID_INDEX + "\n\n"
                + TagCommand.MESSAGE_USAGE);

        // no field specified
        // needs preceeding space, otherwise different error
        assertParseFailure(parser, " 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_AT_LEAST_ONE_FIELD + "a/ d/"));

        // no index and no field specified
        assertParseFailure(parser, "", ParserUtil.MESSAGE_NO_INDEX + "\n\n"
                + TagCommand.MESSAGE_USAGE);

        // only colour specified
        // needs preceeding space, otherwise different error
        assertParseFailure(parser, " c/blue", ParserUtil.MESSAGE_NO_INDEX + "\n\n"
                + TagCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + defaultTagFlags, ParserUtil.MESSAGE_INVALID_INDEX
                + "\n\n" + TagCommand.MESSAGE_USAGE);

        // zero index
        assertParseFailure(parser, "0" + defaultTagFlags, ParserUtil.MESSAGE_INVALID_INDEX
                + "\n\n" + TagCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "a" + defaultTagFlags, ParserUtil.MESSAGE_INVALID_INDEX
                + "\n\n" + TagCommand.MESSAGE_USAGE);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", ParserUtil.MESSAGE_INVALID_INDEX + "\n\n"
                + TagCommand.MESSAGE_USAGE);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", ParserUtil.MESSAGE_INVALID_INDEX + "\n\n"
                + TagCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_allFieldsPresent_failure() {
        assertParseFailure(parser, "1 a/TEST1 d/TEST2 c/red", MESSAGE_INVALID_FIELD_COMBI + "a/ d/");
    }

    @Test
    public void parse_someFieldsPresent_success() {
        assertParseSuccess(parser, "2 d/friends owesMoney",
                new TagCommand(INDEX_SECOND_PERSON,
                        Set.of(new Tag("friends"), new Tag("owesMoney")),
                        false
                ));


        assertParseSuccess(parser, "1 a/TEST1 TEST2",
                new TagCommand(INDEX_FIRST_PERSON,
                        Set.of(new Tag("TEST1"), new Tag("TEST2")),
                        true
                ));

        assertParseSuccess(parser, "1 a/TEST1 TEST2",
                new TagCommand(INDEX_FIRST_PERSON,
                        Set.of(new Tag("TEST1"), new Tag("TEST2")),
                        true
                ));

        assertParseSuccess(parser, "1 a/TEST1 TEST2 c/purple",
                new TagCommand(INDEX_FIRST_PERSON,
                        Set.of(new Tag("TEST1", TagColour.PURPLE), new Tag("TEST2", TagColour.PURPLE)),
                        true
                ));

        assertParseSuccess(parser, "1 a/TEST1 TEST2 c/purple red",
                new TagCommand(INDEX_FIRST_PERSON,
                        Set.of(new Tag("TEST1", TagColour.PURPLE), new Tag("TEST2", TagColour.RED)),
                        true
                ));
    }

    @Test
    public void parse_invalidColourFieldsPresent_failure() {
        assertParseFailure(parser, "1 d/TEST1 c/GREEN", MESSAGE_INVALID_FIELD_COMBI + "c/ d/");

        assertParseFailure(parser, "1 c/RED", MESSAGE_AT_LEAST_ONE_FIELD + "a/ d/");

        assertParseFailure(parser, "1 a/BOB c/WHITE", TagColour.MESSAGE_INVALID_COLOUR);
    }


    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        assertParseFailure(parser, "a" + MULTI_TAG_DESC_AMY + MULTI_TAG_DESC_BOB,
                ParserUtil.MESSAGE_INVALID_INDEX + "\n\n" + TagCommand.MESSAGE_USAGE);
    }


}
