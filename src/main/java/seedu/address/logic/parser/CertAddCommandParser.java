package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_EXPIRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_NAME;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CertAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cert.CertExpiry;
import seedu.address.model.cert.CertName;
import seedu.address.model.cert.Certificate;

/**
 * Parses input arguments and creates a new CertAddCommand object.
 */
public class CertAddCommandParser implements Parser<CertAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CertAddCommand
     * and returns an CertAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CertAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CERT_NAME, PREFIX_CERT_EXPIRY);

        if (!arePrefixesPresent(argMultimap, PREFIX_CERT_NAME, PREFIX_CERT_EXPIRY)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CertAddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CERT_NAME, PREFIX_CERT_EXPIRY);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException parseException) {
            throw new ParseException(parseException.getMessage() + "\n\n" + CertAddCommand.MESSAGE_USAGE);
        }

        CertName name = ParserUtil.parseCertName(argMultimap.getValue(PREFIX_CERT_NAME).get());
        CertExpiry expiry = ParserUtil.parseCertExpiry(argMultimap.getValue(PREFIX_CERT_EXPIRY).get());
        Certificate cert = new Certificate(name, expiry);

        return new CertAddCommand(index, cert);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
