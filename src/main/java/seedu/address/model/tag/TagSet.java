package seedu.address.model.tag;

import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * Set specifically made to arrange and check Tags by their Names
 */
public class TagSet extends TreeSet<Tag> {

    private static final TagNameComparator defaultTagMameComparator = new TagNameComparator();

    public TagSet() {
        super(defaultTagMameComparator);
    }

    /**
     * Set specifically made to arrange and check Tags by their Names
     */
    public TagSet(Collection<Tag> s) {
        this();
        addAll(s);
    }

    /**
     * Compares Tags by Tag Name so Tags can be identified by name (not by Colour) in Set
     */
    private static class TagNameComparator implements Comparator<Tag> {
        @Override
        public int compare(Tag o1, Tag o2) {
            return o1.tagName.compareTo(o2.tagName);
        }

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof TagNameComparator);
        }
    }

}
