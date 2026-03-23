package seedu.address.ui;

/**
 * Maps a Colour to its {@code cssClass}  equivalent.
 *
 * Code written with help from Google Gemini and Dumbo's answer in this StackOverflow post
 * https://stackoverflow.com/questions/48884550/use-enum-class-instead-of-a-map-for-holding-constants
 *
 */
public enum TagColour {
    RED("tag_colour_red"),
    YELLOW("tag_colour_yellow"),
    GREEN("tag_colour_green"),
    PURPLE("tag_colour_purple"),
    DEFAULT("tag_colour_default");

    private final String cssClass;

    TagColour(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssClass() {
        return cssClass;
    }
}
