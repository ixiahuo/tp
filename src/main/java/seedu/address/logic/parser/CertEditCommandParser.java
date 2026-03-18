package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_EDIT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_EDIT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_EXPIRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_NAME;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CertEditCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cert.CertExpiry;
import seedu.address.model.cert.CertName;
import seedu.address.model.cert.Certificate;

/**
 * Parses input arguments and creates a new CertEditCommand object.
 */
public class CertEditCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the CertEditCommand
     * and returns an CertEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CertEditCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CERT_NAME,
                PREFIX_CERT_EDIT_NAME, PREFIX_CERT_EDIT_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_CERT_NAME)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CertEditCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CERT_NAME, PREFIX_CERT_EXPIRY,
                PREFIX_CERT_EDIT_NAME, PREFIX_CERT_EDIT_DATE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CertEditCommand.MESSAGE_USAGE), pe);
        }

        CertName name = ParserUtil.parseCertName(argMultimap.getValue(PREFIX_CERT_NAME).get());
        Certificate cert = new Certificate(name);
        Optional<CertName> newName = argMultimap.getValue(PREFIX_CERT_EDIT_NAME)
                .map(x -> new CertName(x));
        Optional<CertExpiry> newDate = argMultimap.getValue(PREFIX_CERT_EDIT_DATE)
                .map(x -> new CertExpiry(LocalDate.parse(x)));

        return new CertEditCommand(index, cert, newName, newDate);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
