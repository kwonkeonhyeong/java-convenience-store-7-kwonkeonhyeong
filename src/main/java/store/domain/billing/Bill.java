package store.domain.billing;

import static store.domain.constant.BillComponent.*;
import static store.domain.constant.BillPattern.ITEM_FORMAT;

import java.util.List;
import store.domain.discount.DiscountPolicy;

public class Bill {

    private final List<BillingItem> billingItems;
    private final DiscountPolicy discountPolicy;

    private Bill(List<BillingItem> billingItems, DiscountPolicy discountPolicy) {
        this.billingItems = billingItems;
        this.discountPolicy = discountPolicy;
    }

    public static Bill of(List<BillingItem> billingItems, DiscountPolicy discountPolicy) {
        return new Bill(billingItems, discountPolicy);
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
        long discountableAmount = totalPurchaseAmount() - totalPromotionQuantityAmount();
        return discountPolicy.getDiscountAmount(discountableAmount);
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
