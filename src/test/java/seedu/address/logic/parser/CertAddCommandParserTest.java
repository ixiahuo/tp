package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_NAME;

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
    public void parse_noExpiryDate_failure() {
        try {
            parser.parse("1 n/Accounting");
        } catch (ParseException e) {
            assertEquals(new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CertAddCommand.MESSAGE_USAGE)).getMessage(),
                    e.getMessage());
        }
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
            parser.parse("n/Accounting e/2028-03-05");
        } catch (ParseException e) {
            assertEquals(new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CertAddCommand.MESSAGE_USAGE)).getMessage(),
                    e.getMessage());
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
}
