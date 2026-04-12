package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_EXPIRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.Parser.MESSAGE_PREFIX_MISSING_PRECEEDING_SPACE;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_NO_INDEX;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CertAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cert.CertExpiry;
import seedu.address.model.cert.CertName;
import seedu.address.model.cert.Certificate;

public class CertAddCommandParserTest {
    private CertAddCommandParser parser = new CertAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        CertAddCommand certAddCommand = parser.parse("1 n/Accounting e/2028-03-05");
        CertAddCommand expectedCommand = new CertAddCommand(Index.fromOneBased(1),
                new Certificate(new CertName("Accounting"),
                new CertExpiry(LocalDate.parse("2028-03-05"))));
        assertEquals(expectedCommand, certAddCommand);
    }

    @Test
    public void parse_noExpiryDate_success() throws Exception {
        CertAddCommand certAddCommand = parser.parse("1 n/Accounting");
        CertAddCommand expectedCommand = new CertAddCommand(Index.fromOneBased(1),
                new Certificate(new CertName("Accounting"),
                        new CertExpiry(null)));
        assertEquals(expectedCommand, certAddCommand);
    }

    @Test
    public void parse_noName_failure() {
        try {
            parser.parse("1 e/2028-03-05");
        } catch (ParseException e) {
            assertEquals(new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CertAddCommand.MESSAGE_USAGE)).getMessage(),
                    e.getMessage());
        }
    }

    @Test
    public void parse_noIndex_failure() {
        try {
            parser.parse(" n/Accounting e/2028-03-05"); // needs preceeding space, otherwise different error
        } catch (ParseException e) {
            String expectedErrorMessage = String.format("%s\n\n%s",
                    MESSAGE_NO_INDEX,
                    CertAddCommand.MESSAGE_USAGE);
            assertEquals(new ParseException(expectedErrorMessage).getMessage(), e.getMessage());
        }
    }

    @Test
    public void parse_badDate_failure() {
        try {
            parser.parse("1 n/Accounting e/2028-31-05");
        } catch (ParseException e) {
            assertEquals(new ParseException(CertExpiry.MESSAGE_CONSTRAINTS).getMessage(),
                    e.getMessage());
        }
    }

    @Test
    public void parse_duplicateName_failure() {
        try {
            parser.parse("1 n/Accounting n/Marketing e/2028-31-05");
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
                "1n/OSCP Plus e/2028-01-01",
                PREFIX_CERT_NAME + MESSAGE_PREFIX_MISSING_PRECEEDING_SPACE);

        // cert expiry
        assertParseFailure(parser,
                "1 n/OSCP Pluse/2028-01-01",
                PREFIX_CERT_EXPIRY + MESSAGE_PREFIX_MISSING_PRECEEDING_SPACE);
    }
}
