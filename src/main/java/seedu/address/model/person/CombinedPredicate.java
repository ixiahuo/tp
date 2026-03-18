package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests a combination of one or more predicates.
 */
public class CombinedPredicate implements Predicate<Person> {
    private final List<Predicate<Person>> predicates;

    public CombinedPredicate(List<Predicate<Person>> predicates) {
        this.predicates = predicates;
    }

    @Override
    public boolean test(Person person) {
        return predicates.stream().allMatch(p -> p.test(person));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CombinedPredicate)) {
            return false;
        }

        CombinedPredicate otherCombinedPredicate = (CombinedPredicate) other;
        return predicates.equals(otherCombinedPredicate.predicates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", predicates).toString();
    }
}
