package seedu.address.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface Parser<T extends Command> {
    public static String MESSAGE_PREFIX_MISSING_PRECEEDING_SPACE = " is missing a preceeding space.";

    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    T parse(String userInput) throws ParseException;

    /**
     * Parses {@code args} to check if {@code prefixes} is present, it has whitespace immediately preceeding.
     * @throws ParseException if {@code args} does not conform the expected format
     */
    default void checkIfFlagsAreStuck(String args, Prefix... prefixes) throws ParseException {
        for (Prefix pre : prefixes) {
            Pattern p = Pattern.compile("(?<!\\s)" + pre); // pattern for no space before prefix
            Matcher m = p.matcher(args);
            if (m.find()) {
                throw new ParseException(pre + MESSAGE_PREFIX_MISSING_PRECEEDING_SPACE);
            }
        }
    }
}
