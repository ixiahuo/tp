package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new TagCommand object
 */
public class TagCommandParser implements Parser<TagCommand> {

    private static final Logger logger = LogsCenter.getLogger(TagCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns an TagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        logger.finer("Parsing Tag Command");

        checkIfFlagsAreStuck(args, PREFIX_ADD_TAG, PREFIX_DELETE_TAG, PREFIX_COLOUR_TAG);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ADD_TAG, PREFIX_DELETE_TAG, PREFIX_COLOUR_TAG);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException parseException) {
            throw new ParseException(parseException.getMessage() + "\n\n" + TagCommand.MESSAGE_USAGE);
        }

        argMultimap.verifyAtLeastOnePrefixFor(PREFIX_ADD_TAG, PREFIX_DELETE_TAG);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ADD_TAG, PREFIX_DELETE_TAG, PREFIX_COLOUR_TAG);

        argMultimap.verifyNoPrefixCombinationFor(PREFIX_DELETE_TAG, PREFIX_ADD_TAG);
        argMultimap.verifyNoPrefixCombinationFor(PREFIX_DELETE_TAG, PREFIX_COLOUR_TAG);

        Set<Tag> tagsToUpdate;
        Optional<String> tagsAsStringToAdd = argMultimap.getValue(PREFIX_ADD_TAG);
        Optional<String> tagsAsStringToDelete = argMultimap.getValue(PREFIX_DELETE_TAG);
        Optional<String> coloursAsStringToAdd = argMultimap.getValue(PREFIX_COLOUR_TAG);

        if (tagsAsStringToAdd.isPresent()) {
            logger.finest("Preparing to add tags: ");
            if (coloursAsStringToAdd.isPresent()) {
                tagsToUpdate = parseTagsForEdit(List.of(tagsAsStringToAdd.get().split("\\s+")),
                        Arrays.stream(coloursAsStringToAdd.get().split("\\s+")).toList());
            } else {
                tagsToUpdate = parseTagsForEdit(List.of(tagsAsStringToAdd.get().split("\\s+")));
            }
            logger.finer("Adding tags: " + tagsToUpdate.toString());
            return new TagCommand(index, tagsToUpdate, true);
        }

        if (tagsAsStringToDelete.isPresent()) {
            logger.finest("Preparing to delete tags: ");
            tagsToUpdate = parseTagsForEdit(List.of(tagsAsStringToDelete.get().split("\\s+")));
            logger.finer("Tags to delete: " + tagsToUpdate.toString());
            return new TagCommand(index, tagsToUpdate, false);
        }
        logger.finer("ERROR: TagCommandParser has no add or delete any tags fields.");
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Set<Tag> parseTagsForEdit(List<String> tags, List<String> colours) throws ParseException {
        return ParserUtil.parseTags(processTagStrings(tags), colours);
    }

    private Set<Tag> parseTagsForEdit(List<String> tags) throws ParseException {
        return ParserUtil.parseTags(processTagStrings(tags));
    }

    private List<String> processTagStrings(List<String> tags) {
        assert tags != null;

        if (tags.isEmpty()) {
            return List.of();
        }

        return tags.size() == 1 && tags.contains("") ? Collections.emptyList() : tags;
    }
}
