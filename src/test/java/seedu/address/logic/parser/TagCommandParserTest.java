package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.MULTI_TAG_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MULTI_TAG_DESC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TagCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

public class TagCommandParserTest {

    private TagCommandParser parser = new TagCommandParser();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, "1 a/TEST1 d/TEST2",
                new TagCommand(INDEX_FIRST_PERSON,
                        Set.of(new Tag("TEST1")),
                        Set.of(new Tag("TEST2"))
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
        // Note that this is a CommandError not a ParseError
        assertParseSuccess(parser, "1 d/TEST1 TEST2",
                new TagCommand(INDEX_FIRST_PERSON,
                        Set.of(),
                        Set.of()
                ));
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        assertParseFailure(parser, model.getFilteredPersonList().size() + 1 + MULTI_TAG_DESC_AMY + MULTI_TAG_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
    }


}
