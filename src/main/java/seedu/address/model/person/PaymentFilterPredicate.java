package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;



/**
 * Tests that a {@code Student}'s {@code Payment}  matches any of the keywords given.
 */
public class PaymentFilterPredicate implements Predicate<Person> {

    private final Payment filteredPayment;

    /**
     * Predicate that filters the address book by Payment.
     *
     * @param payment Payment to filter by.
     */
    public PaymentFilterPredicate(Payment payment) {
        this.filteredPayment = payment;
    }

    @Override
    public boolean test(Person person) {
        boolean isPaymentFiltered = true;
        if (!filteredPayment.isEmpty()) {
            isPaymentFiltered = person.getPayment().equals(filteredPayment);
        }
        return isPaymentFiltered;
    }

    /**
     * Returns a String representation of the filter result for GUI display.
     *
     * @return Filter result.
     */
    public String filterResult() {
        return "Payment: " + filteredPayment.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PaymentFilterPredicate)) {
            return false;
        }

        PaymentFilterPredicate otherPaymentFilterPredicate = (PaymentFilterPredicate) other;
        return filteredPayment.equals(otherPaymentFilterPredicate.filteredPayment);

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this.getClass().getSimpleName())
                .add("Payment", filteredPayment)
                .toString();
    }
}
