package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;

import java.util.Collection;
import java.util.Collections;
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
    public static final String MESSAGE_USELESS_COLOUR = "Colours are invalid when not creating Tags.\n\n"
            + TagCommand.MESSAGE_USAGE;

    private static final Logger logger = LogsCenter.getLogger(TagCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns an TagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        logger.finer("Parsing Tag Commands");

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
        Optional<String> colourGiven = argMultimap.getValue(PREFIX_COLOUR_TAG);

        if (tagsAsStringToAdd.isPresent()) {
            if (colourGiven.isPresent()) {
                tagsToUpdate = parseTagsForEdit(Set.of(tagsAsStringToAdd.get().split("\\s+")),
                        colourGiven.get());
            } else {
                tagsToUpdate = parseTagsForEdit(Set.of(tagsAsStringToAdd.get().split("\\s+")));
            }
            logger.finest("Adding tags: " + tagsToUpdate.toString());
            return new TagCommand(index, tagsToUpdate, true);
        }

        if (tagsAsStringToDelete.isPresent()) {
            tagsToUpdate = parseTagsForEdit(Set.of(tagsAsStringToDelete.get().split("\\s+")));
            logger.finest("Tags to delete: " + tagsToUpdate.toString());
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
    private Set<Tag> parseTagsForEdit(Collection<String> tags, String colour) throws ParseException {
        return ParserUtil.parseTags(processTagStrings(tags), colour);
    }

    private Set<Tag> parseTagsForEdit(Collection<String> tags) throws ParseException {
        return ParserUtil.parseTags(processTagStrings(tags));
    }

    private Collection<String> processTagStrings(Collection<String> tags) {
        assert tags != null;

        if (tags.isEmpty()) {
            return Set.of();
        }

        return tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
    }
}
