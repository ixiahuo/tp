package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CERT_EXPIRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
// import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cert.CertExpiry;
import seedu.address.model.cert.CertName;
import seedu.address.model.cert.Certificate;
import seedu.address.model.person.CertContainsDatePredicate;
import seedu.address.model.person.CertContainsKeywordsPredicate;
import seedu.address.model.person.CombinedPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        List<Predicate<Person>> predicates = new ArrayList<>();
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG, PREFIX_CERT, PREFIX_CERT_EXPIRY);
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TAG, PREFIX_CERT, PREFIX_CERT_EXPIRY)
                || !argMultimap.getPreamble().trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CERT_EXPIRY);

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            predicates.add(getNamePredicate(argMultimap));
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            predicates.add(getTagPredicate(argMultimap));
        }

        if (argMultimap.getValue(PREFIX_CERT).isPresent()) {
            predicates.add(getCertPredicate(argMultimap));
        }

        if (argMultimap.getValue(PREFIX_CERT_EXPIRY).isPresent()) {
            predicates.add(getCertExpPredicate(argMultimap));
        }

        assert predicates != null;
        return new FindCommand(new CombinedPredicate(predicates));
    }

    /**
     * Returns true if at least one of the prefixes contain a value in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private NameContainsKeywordsPredicate getNamePredicate(ArgumentMultimap argMultimap) throws ParseException {
        try {
            List<String> names = argMultimap.getAllValues(PREFIX_NAME)
                .stream().map(String::trim).map(nameStr -> checkValidName(nameStr)).collect(Collectors.toList());
            return new NameContainsKeywordsPredicate(names);
        } catch (RuntimeException re) {
            throw new ParseException(re.getCause().getMessage());
        }
    }

    private TagContainsKeywordsPredicate getTagPredicate(ArgumentMultimap argMultimap) throws ParseException {
        try {
            List<Set<Tag>> tagGroups = argMultimap.getAllValues(PREFIX_TAG)
                .stream().map(this::parseAndGroupTags).collect(Collectors.toList());
            return new TagContainsKeywordsPredicate(tagGroups);
        } catch (RuntimeException re) {
            throw new ParseException(re.getCause().getMessage());
        }

    }

    private CertContainsKeywordsPredicate getCertPredicate(ArgumentMultimap argMultimap) throws ParseException {
        try {
            List<Certificate> certsList = argMultimap.getAllValues(PREFIX_CERT)
                .stream().map(name -> name.trim()).filter(name -> !name.isEmpty())
                .map(name -> new Certificate(checkVaildCertName(name))).collect(Collectors.toList());
            ArrayList<Certificate> certs = new ArrayList<>(certsList);
            return new CertContainsKeywordsPredicate(certs);
        } catch (RuntimeException re) {
            throw new ParseException(re.getCause().getMessage());
        }

    }

    private CertContainsDatePredicate getCertExpPredicate(ArgumentMultimap argMultimap) throws ParseException {
        CertExpiry expiry = ParserUtil.parseCertExpiry(argMultimap.getValue(PREFIX_CERT_EXPIRY).get());
        return new CertContainsDatePredicate(expiry);
    }

    private String checkValidName(String input) {
        try {
            return ParserUtil.parseName(input).fullName;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private Tag checkValidTag(String input) {
        try {
            return new Tag(input);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private CertName checkVaildCertName(String input) {
        try {
            return ParserUtil.parseCertName(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Set<Tag> parseAndGroupTags(String tagEntry) {
        return Arrays.stream(tagEntry.trim().split("\\s+"))
                .map(tagStr -> checkValidTag(tagEntry))
                .collect(Collectors.toSet());
    }

}
