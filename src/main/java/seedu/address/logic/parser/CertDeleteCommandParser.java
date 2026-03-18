package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_NAME;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CertDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cert.CertName;
import seedu.address.model.cert.Certificate;

/**
 * Parses input arguments and creates a CertDeleteCommand object.
 */
public class CertDeleteCommandParser implements Parser<CertDeleteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of CertDeleteCommand
     * and returns a CertDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public CertDeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CERT_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_CERT_NAME)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CertDeleteCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CERT_NAME);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, CertDeleteCommand.MESSAGE_USAGE), pe);
        }

        CertName name = ParserUtil.parseCertName(argMultimap.getValue(PREFIX_CERT_NAME).get());

        Certificate cert = new Certificate(name);

        return new CertDeleteCommand(index, cert);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
