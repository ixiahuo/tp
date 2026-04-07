package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.cert.CertExpiry;
import seedu.address.model.cert.CertName;
import seedu.address.model.cert.Certificate;
import seedu.address.model.person.CertContainsDatePredicate;
import seedu.address.model.person.CertContainsKeywordsPredicate;
import seedu.address.model.person.CombinedPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNames_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
            new FindCommand(new CombinedPredicate(Arrays.asList(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")))));
        assertParseSuccess(parser, " n/Alice n/Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/  Alice  \n n/  Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_invalidNameFormat_throwsParseException() {
        // Name with invalid / usage
        assertParseFailure(parser, " n/ A/lice", Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validTags_returnsFindCommand() {
        // Multiple /t prefixes (OR Logic)
        FindCommand expectedFindCommand = new FindCommand(new CombinedPredicate(Arrays.asList((
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

    @Test
    public void parse_invalidTagFormat_throwsParseException() {
        // Empty tag value
        assertParseFailure(parser, " t/ ", Tag.MESSAGE_CONSTRAINTS);
        // Tag with invalid characters
        assertParseFailure(parser, " t/friend find ", Tag.MESSAGE_CONSTRAINTS);
    }


    @Test
    public void parse_certWithWhitespaces_returnsFindCommand() {
        FindCommand expectedFindCommand =
            new FindCommand(new CombinedPredicate(Arrays.asList(
                new CertContainsKeywordsPredicate(new ArrayList<Certificate>(
                    Arrays.asList(new Certificate(new CertName("Social Media"))))))));
        // Leading and trailing whitespaces within cert value
        assertParseSuccess(parser, " c/ Social Media ", expectedFindCommand);
    }

    @Test
    public void parse_invalidCertNameFormat_throwsParseException() {
        // Certificate with invalid characters
        assertParseFailure(parser, " c/ Social Media@IBM ", CertName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidCertExpiryDateFormat_throwsParseException() {
        // Invalid date format
        assertParseFailure(parser, " e/ 2028/12/15", CertExpiry.MESSAGE_CONSTRAINTS);
        // NaN date value
        assertParseFailure(parser, " e/ tomorrow", CertExpiry.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(new CombinedPredicate(Arrays.asList(
            new NameContainsKeywordsPredicate(Arrays.asList("Alex")),
            new TagContainsKeywordsPredicate(Arrays.asList(Set.of(new Tag("IT")))),
            new CertContainsKeywordsPredicate(new ArrayList<Certificate>(Arrays.asList(
                new Certificate(new CertName("Social Media"))))),
            new CertContainsDatePredicate(new CertExpiry(LocalDate.of(2028, 12, 15)))
        )));
        assertParseSuccess(parser, " n/Alex t/IT c/Social Media e/2028-12-15", expectedFindCommand);
    }
}
