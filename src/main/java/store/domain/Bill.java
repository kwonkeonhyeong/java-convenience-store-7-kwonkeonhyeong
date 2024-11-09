package store.domain;

import java.util.List;

public class Bill {

    private final List<BillingItem> billingItems;
    private final boolean hasMembership;

    private Bill(List<BillingItem> billingItems, boolean hasMembership) {
        this.billingItems = billingItems;
        this.hasMembership = hasMembership;
    }

    public static Bill of(List<BillingItem> billingItems, boolean hasMembership) {
        return new Bill(billingItems, hasMembership);
    }
}
