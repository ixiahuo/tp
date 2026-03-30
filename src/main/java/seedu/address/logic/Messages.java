package seedu.address.logic;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command, use 'help' to view available commands.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_FIELD_COMBI =
            "The following field(s) cannot be used together: ";

    public static final String MESSAGE_ONE_FIELD_REQUIRES_ANOTHER =
            "The following field(s) %1$s must be used in conjunction with other field(s) %2$s: ";

    public static final String MESSAGE_AT_LEAST_ONE_FIELD =
            "At least one of the following fields must be specified: ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toCollection(TreeSet::new));

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Returns an error message indicating a combination of prefixes that cannot be used together.
     */
    public static String getErrorMessageForInvalidPrefixCombination(Prefix... invalidPrefixes) {
        assert invalidPrefixes.length > 1;

        Set<String> invalidFields =
                Stream.of(invalidPrefixes).map(Prefix::toString).collect(Collectors.toCollection(TreeSet::new));

        return MESSAGE_INVALID_FIELD_COMBI + String.join(" ", invalidFields);
    }

    /**
     * Returns an error message indicating that at least one of the following prefixes must be specified
     */
    public static String getErrorMessageForAtLeaseOnePrefixRequired(Prefix... prefixes) {
        assert prefixes.length > 0;

        Set<String> requiredFields =
                Stream.of(prefixes).map(Prefix::toString).collect(Collectors.toCollection(TreeSet::new));

        return MESSAGE_AT_LEAST_ONE_FIELD + String.join(" ", requiredFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Salary: ")
                .append(person.getSalary())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        builder.append("; Certificates: ");
        person.getCertificates().forEach(builder::append);
        return builder.toString();
    }

}
