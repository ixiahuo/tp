package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_NAME;

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
            CertDeleteCommand certDeleteCommand = parser.parse("1 e/2028-03-05");
        } catch (ParseException e) {
            assertEquals(new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CertDeleteCommand.MESSAGE_USAGE)).getMessage(),
                    e.getMessage());
        }
    }

    @Test
    public void parse_noIndex_failure() {
        try {
            CertDeleteCommand certDeleteCommand = parser.parse("n/Accounting");
        } catch (ParseException e) {
            assertEquals(new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CertDeleteCommand.MESSAGE_USAGE)).getMessage(),
                    e.getMessage());
        }
    }

    @Test
    public void parse_duplicateName_failure() {
        try {
            CertDeleteCommand certDeleteCommand = parser.parse("1 n/Accounting n/Marketing");
        } catch (ParseException e) {
            assertEquals(new ParseException(Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CERT_NAME))
                    .getMessage(),
                    e.getMessage());
        }
    }
}
