package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.Parser.MESSAGE_PREFIX_MISSING_PRECEEDING_SPACE;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_NO_INDEX;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CertDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cert.CertName;
import seedu.address.model.cert.Certificate;

public class CertDeleteCommandParserTest {
    private CertDeleteCommandParser parser = new CertDeleteCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        CertDeleteCommand certDeleteCommand = parser.parse("1 n/Accounting");
        CertDeleteCommand expectedCommand = new CertDeleteCommand(Index.fromOneBased(1),
                new Certificate(new CertName("Accounting")));
        assertEquals(expectedCommand, certDeleteCommand);
    }

    @Test
    public void parse_noName_failure() {
        try {
            parser.parse("1 e/2028-03-05");
        } catch (ParseException e) {
            assertEquals(new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CertDeleteCommand.MESSAGE_USAGE)).getMessage(),
                    e.getMessage());
        }
    }

    @Test
    public void parse_noIndex_failure() {
        try {
            parser.parse(" n/Accounting"); // needs preceeding space, otherwise different error
        } catch (ParseException e) {
            String expectedErrorMessage = String.format("%s\n\n%s",
                    MESSAGE_NO_INDEX,
                    CertDeleteCommand.MESSAGE_USAGE);
            assertEquals(new ParseException(expectedErrorMessage).getMessage(), e.getMessage());
        }
    }

    @Test
    public void parse_duplicateName_failure() {
        try {
            parser.parse("1 n/Accounting n/Marketing");
        } catch (ParseException e) {
            assertEquals(new ParseException(Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CERT_NAME))
                    .getMessage(),
                    e.getMessage());
        }
    }

    @Test
    public void parse_prefixMissingPreceedingSpace_failure() {
        // cert name
        assertParseFailure(parser,
                "1n/OSCP Plus",
                PREFIX_CERT_NAME + MESSAGE_PREFIX_MISSING_PRECEEDING_SPACE);
    }
}
