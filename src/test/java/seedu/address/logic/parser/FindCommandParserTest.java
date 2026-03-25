package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.CombinedPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    // private static final String MESSAGE_INVALID_FORMAT =
    // String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
            new FindCommand(new CombinedPredicate(Arrays.asList(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")))));
        assertParseSuccess(parser, " n/Alice n/Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/  Alice  \n n/  Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validTags_returnsFindCommand() {
        // Single /t prefix multiple tags (AND Logic)
        FindCommand expectedFindCommand =
                new FindCommand(new CombinedPredicate(Arrays.asList(
                    new TagContainsKeywordsPredicate(Arrays.asList(
                        Set.of(new Tag("friend"), new Tag("Computing")))))));
        assertParseSuccess(parser, " t/friend Computing", expectedFindCommand);

        // Multiple /t prefixes (OR Logic)
        expectedFindCommand = new FindCommand(new CombinedPredicate(Arrays.asList((
                new TagContainsKeywordsPredicate(Arrays.asList(
                        Set.of(new Tag("friend")),
                        Set.of(new Tag("family"))))))));
        assertParseSuccess(parser, " t/friend t/family", expectedFindCommand);
    }

    @Test
    public void parse_tagsWithWhitespaces_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new CombinedPredicate(Arrays.asList(
                    new TagContainsKeywordsPredicate(Arrays.asList(
                        Set.of(new Tag("friends")))))));
        // Leading and trailing whitespaces within the tag value
        assertParseSuccess(parser, " t/  friends  ", expectedFindCommand);
    }

    // @Test
    // public void parse_invalidTagFormat_throwsParseException() {
    //     // Empty tag value
    //     assertParseFailure(parser, " t/ ", MESSAGE_INVALID_FORMAT);

    //     // Tag with invalid characters
    //     assertParseFailure(parser, " t/friend\\find ", Tag.MESSAGE_CONSTRAINTS);
    // }

}
