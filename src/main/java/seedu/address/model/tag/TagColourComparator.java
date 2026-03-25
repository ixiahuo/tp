package seedu.address.model.tag;

import java.util.Comparator;

/**
 * Compares Tags by Tag Colour for sorted display
 */
public class TagColourComparator implements Comparator<Tag> {
    @Override
    public int compare(Tag o1, Tag o2) {
        return o1.tagColour.compareTo(o2.tagColour);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof TagColourComparator);
    }
}
