package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;
public class PaymentPredicateTest {

    @Test
    public void equals() {
        PaymentFilterPredicate firstPredicate = new PaymentFilterPredicate(
                new Payment());

        PaymentFilterPredicate secondPredicate = new PaymentFilterPredicate(
                new Payment("Paid"));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PaymentFilterPredicate firstPredicateCopy = new PaymentFilterPredicate(
                new Payment());
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different filter -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_filterContainsPayment_returnsTrue() {
        PaymentFilterPredicate predicate = new PaymentFilterPredicate(new Payment("Paid"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_filterContainsGradeSubject_returnsFalse() {
        PaymentFilterPredicate predicate = new PaymentFilterPredicate(
                new Payment("Not Paid"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void toStringMethod() {
        String payment = "Paid";
        PaymentFilterPredicate predicate = new PaymentFilterPredicate(
                new Payment(payment));

        String expected = PaymentFilterPredicate.class.getSimpleName() + "{Payment=" + payment + "}";
        assertEquals(expected, predicate.toString());
    }
}
