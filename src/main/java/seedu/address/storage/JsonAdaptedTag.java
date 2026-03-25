package seedu.address.storage;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColour;


/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedTag {

    @JsonProperty("name")
    private final String tagName;
    @JsonProperty("colour")
    private final String tagColourString;

    @JsonCreator
    public JsonAdaptedTag(@JsonProperty("name") String tagName,
                          @JsonProperty("colour") String tagColourString) {
        this.tagName = tagName;
        this.tagColourString = tagColourString;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(Tag source) {
        tagName = source.tagName;
        tagColourString = source.tagColour.toString();
    }

    public String getTagName() {
        return tagName;
    }

    public String getTagColour() {
        return tagColourString;
    }
    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {

        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }

        if (!Tag.isValidTagLength(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS_LENGTH);
        }

        Optional<TagColour> tagColour = TagColour.getTagColourByUserInputName(tagColourString);
        return tagColour.map(colour -> new Tag(tagName, colour)).orElseGet(() ->
                new Tag(tagName, TagColour.DEFAULT));

    }

}
