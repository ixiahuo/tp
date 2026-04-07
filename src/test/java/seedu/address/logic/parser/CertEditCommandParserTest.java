package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_EDIT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_EDIT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_NAME;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CertEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cert.CertExpiry;
import seedu.address.model.cert.CertName;
import seedu.address.model.cert.Certificate;

public class CertEditCommandParserTest {
    private CertEditCommandParser parser = new CertEditCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        CertEditCommand certEditCommand = parser.parse("1 n/Accounting ne/OSCP ee/2028-03-05");
        CertEditCommand expectedCommand = new CertEditCommand(Index.fromOneBased(1),
                new Certificate(new CertName("Accounting")),
                Optional.<CertName>of(new CertName("OSCP")),
                Optional.<CertExpiry>of(new CertExpiry(LocalDate.parse("2028-03-05"))));
        assertEquals(expectedCommand, certEditCommand);
    }

    @Test
    public void parse_noName_failure() {
        try {
            parser.parse("1 ee/2028-03-05");
        } catch (ParseException e) {
            assertEquals(new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CertEditCommand.MESSAGE_USAGE)).getMessage(),
                    e.getMessage());
        }
    }

    @Test
    public void parse_noIndex_failure() {
        try {
            parser.parse("n/Accounting ee/2028-03-05");
        } catch (ParseException e) {
            assertEquals(new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CertEditCommand.MESSAGE_USAGE)).getMessage(),
                    e.getMessage());
        }
    }

    @Test
    public void parse_badDate_failure() {
        try {
            parser.parse("1 n/Accounting ee/2028-31-05");
        } catch (ParseException e) {
            assertEquals(new ParseException(CertExpiry.MESSAGE_CONSTRAINTS).getMessage(),
                    e.getMessage());
        }
    }

    @Test
    public void parse_duplicateName_failure() {
        try {
            parser.parse("1 n/Accounting n/Marketing ee/2028-31-05");
        } catch (ParseException e) {
            assertEquals(new ParseException(Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CERT_NAME))
                    .getMessage(),
                    e.getMessage());
        }
    }

    @Test
    public void parse_duplicateNewName_failure() {
        try {
            parser.parse("1 n/Accounting ne/Marketing ne/OSCP");
        } catch (ParseException e) {
            assertEquals(new ParseException(Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CERT_EDIT_NAME))
                    .getMessage(),
                    e.getMessage());
        }
    }

    @Test
    public void parse_duplicateNewExpiry_failure() {
        try {
            parser.parse("1 n/Accounting ee/2028-03-05 ee/2028-03-06");
        } catch (ParseException e) {
            assertEquals(new ParseException(Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CERT_EDIT_DATE))
                    .getMessage(),
                    e.getMessage());
        }
    }

    @Test
    public void parse_emptyExpiry_success() throws Exception {
        // performs cert-edit 1 n/Accounting ee/
        CertEditCommand certEditCommand = parser.parse("1 n/Accounting ee/");

        CertEditCommand expectedCommand = new CertEditCommand(
                Index.fromOneBased(1),
                new Certificate(new CertName("Accounting")),
                Optional.empty(),
                Optional.of(new CertExpiry(null)) //expected cert expiry to be "No Expiry"
        );

        assertEquals(expectedCommand, certEditCommand);
    }

    @Test
    public void parse_expiryPrefixAbsent_returnsOptionalEmpty() throws Exception {
        // performs cert-edit 1 n/Accounting ne/Marketing, without a new exp date indicated
        CertEditCommand certEditCommand = parser.parse("1 n/Accounting ne/Marketing");

        CertEditCommand expectedCommand = new CertEditCommand(
                Index.fromOneBased(1),
                new Certificate(new CertName("Accounting")),
                Optional.of(new CertName("Marketing")),
                Optional.empty()
        );

        assertEquals(expectedCommand, certEditCommand);
    }
}
