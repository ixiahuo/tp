package seedu.address.model.tag;

import java.util.Optional;

/**
 * Maps a Colour to its {@code cssClass}  equivalent.
 *
 * Code written with help from Google Gemini and Dumbo's answer in this StackOverflow post
 * https://stackoverflow.com/questions/48884550/use-enum-class-instead-of-a-map-for-holding-constants
 *
 */
public enum TagColour {
    RED("tag_colour_red", "red"),
    YELLOW("tag_colour_yellow", "yellow"),
    GREEN("tag_colour_green", "green"),
    BLUE("tag_colour_default", "blue"),
    DEFAULT("tag_colour_default", "default"),
    PURPLE("tag_colour_purple", "purple");

    public static final String MESSAGE_INVALID_COLOUR =
            "Tag Colour does not exist. We only have: RED, YELLOW, GREEN, BLUE OR PURPLE";

    public static final String MESSAGE_COLOUR_OPTIONS =
            "Tags can come in the following colours: RED, YELLOW, GREEN, BLUE OR PURPLE";

    // Name of the cssClass that will be used to assign the colour in a css file
    private final String cssClass;

    // Name that the user will refer to this colour by via user input
    private final String userColourName;

    TagColour(String cssClass, String userColourName) {
        this.cssClass = cssClass;
        this.userColourName = userColourName;
    }

    public String getCssClass() {
        return cssClass;
    }

    public static Optional<TagColour> getTagColourByUserInputName(String userColourName) {
        for (TagColour c : TagColour.values()) {
            if (c.userColourName.equalsIgnoreCase(userColourName)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return userColourName;
    }
}
