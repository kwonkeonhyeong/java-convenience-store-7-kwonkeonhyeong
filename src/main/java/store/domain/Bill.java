package store.domain;

import static store.domain.constant.BillComponent.*;
import static store.domain.constant.BillPattern.ITEM_FORMAT;

import java.util.List;

public class Bill {

    private static final Long MEMBERSHIP_MAX_AMOUNT = 8000L;
    private static final Long NON_MEMBERSHIP_AMOUNT = 0L;
    private static final double MEMBERSHIP_DISCOUNT_RATE = 0.3;

    private final List<BillingItem> billingItems;
    private final boolean hasMembership;

    private Bill(List<BillingItem> billingItems, boolean hasMembership) {
        this.billingItems = billingItems;
        this.hasMembership = hasMembership;
    }

    public static Bill of(List<BillingItem> billingItems, boolean hasMembership) {
        return new Bill(billingItems, hasMembership);
    }

    public Long totalPurchaseAmount() {
        return billingItems.stream()
                .map(BillingItem::calculatePurchaseAmount)
                .mapToLong(Long::longValue)
                .sum();
    }

    public Long totalOrderQuantity() {
        return billingItems.stream()
                .map(BillingItem::getOrderQuantity)
                .mapToLong(Long::longValue)
                .sum();
    }

    public Long totalPromotionQuantityAmount() {
        return billingItems.stream()
                .map(BillingItem::calculatePromotionAmount)
                .mapToLong(Long::longValue)
                .sum();
    }

    public Long totalPromotionDiscountAmount() {
        return billingItems.stream()
                .map(BillingItem::calculatePromotionDiscountAmount)
                .mapToLong(Long::longValue)
                .sum();
    }

    public Long totalMembershipDiscountAmount() {
        if (!hasMembership) {
            return NON_MEMBERSHIP_AMOUNT;
        }
        long discountableAmount = totalPurchaseAmount() - totalPromotionQuantityAmount();
        long discountAmount = (long) ((discountableAmount) * (MEMBERSHIP_DISCOUNT_RATE));
        if (discountAmount > MEMBERSHIP_MAX_AMOUNT) {
            discountAmount = MEMBERSHIP_MAX_AMOUNT;
        }
        return discountAmount;
    }

    public Long totalPaymentAmount() {
        return totalPurchaseAmount() - totalPromotionDiscountAmount() - totalMembershipDiscountAmount();
    }

    public String formatBill() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formatOrderArea());
        if (totalPromotionDiscountAmount() != 0) {
            stringBuilder.append(formatPromotionArea());
        }
        stringBuilder.append(formatPaymentArea());
        return stringBuilder.toString();
    }

    private String formatOrderArea() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ORDER_HEADER.getComponent());
        String orderHeader = ITEM_FORMAT.formatHeader(
                PRODUCT_HEADER.getComponent(),
                QUANTITY_HEADER.getComponent(),
                AMOUNT_HEADER.getComponent()
        );
        stringBuilder.append(orderHeader);
        stringBuilder.append(formatOrders());
        return stringBuilder.toString();
    }

    private String formatPromotionArea() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PROMOTION_HEADER.getComponent());
        stringBuilder.append(formatPromotions());
        return stringBuilder.toString();
    }

    private String formatPaymentArea() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SUMMARY_HEADER.getComponent());
        stringBuilder.append(formatTotalPurchaseAmount());
        stringBuilder.append(formatTotalPromotionDiscountAmount());
        stringBuilder.append(formatMembershipDiscountAmount());
        stringBuilder.append(formatPaymentAmount());
        return stringBuilder.toString();
    }


    private String formatOrders() {
        StringBuilder stringBuilder = new StringBuilder();
        for (BillingItem billingItem : billingItems) {
            if (billingItem.getOrderQuantity() != 0) {
                stringBuilder.append(billingItem.formatOrder());
            }
        }
        return stringBuilder.toString();
    }

    private String formatPromotions() {
        StringBuilder stringBuilder = new StringBuilder();
        for (BillingItem billingItem : billingItems) {
            if (billingItem.hasPromotion() && billingItem.checkPromotionGiftQuantity() != 0) {
                stringBuilder.append(billingItem.formatPromotion());
            }
        }
        return stringBuilder.toString();
    }

    private String formatTotalPurchaseAmount() {
        return ITEM_FORMAT.formatPaymentSummaryItem(
                TOTAL_PURCHASE_AMOUNT_LABEL,
                totalOrderQuantity(),
                totalPurchaseAmount()
        );
    }

    private String formatTotalPromotionDiscountAmount() {
        return ITEM_FORMAT.formatPaymentSummaryItem(
                TOTAL_PROMOTION_DISCOUNT_AMOUNT_LABEL,
                null,
                totalPromotionDiscountAmount()
        );
    }

    private String formatMembershipDiscountAmount() {
        return ITEM_FORMAT.formatPaymentSummaryItem(
                MEMBERSHIP_DISCOUNT_LABEL,
                null,
                totalMembershipDiscountAmount()
        );
    }

    private String formatPaymentAmount() {
        return ITEM_FORMAT.formatPaymentAmountItem(
                PAYMENT_AMOUNT_LABEL,
                totalPaymentAmount()
        );
    }

    public List<BillingItem> getBillingItems() {
        return billingItems;
    }
}
