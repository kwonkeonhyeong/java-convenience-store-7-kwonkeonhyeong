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
                .map(BillingItem::calculatePromotionQuantityAmount)
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
            return 0L;
        }
        long discountAmount = (long) ((totalPurchaseAmount() - totalPromotionQuantityAmount()) * (0.3));
        if (discountAmount > 8000) {
            discountAmount = 8000L;
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
        String format = "%-10s %-5s %-10s\n";
        stringBuilder.append("==============W 편의점================\n");
        stringBuilder.append(String.format(format, "상품명", "수량", "금액"));
        stringBuilder.append(formatOrders());
        return stringBuilder.toString();
    }

    private String formatPromotionArea() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("=============증정================\n");
        stringBuilder.append(formatPromotions());
        return stringBuilder.toString();
    }

    private String formatPaymentArea() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("===================================\n");
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
        String format = "%-10s %-5s %-10s\n";
        String formattedPrice = String.format("%,d", totalPurchaseAmount());
        return String.format(format, "총구매액", totalOrderQuantity(), formattedPrice);
    }

    private String formatTotalPromotionDiscountAmount() {
        String format = "%-10s %-5s %-10s\n";
        String formattedPrice = String.format("-%,d", totalPromotionDiscountAmount());
        return String.format(format, "행사할인", "", formattedPrice);
    }

    private String formatMembershipDiscountAmount() {
        String format = "%-10s %-5s %-10s\n";
        String formattedPrice = String.format("-%,d", totalMembershipDiscountAmount());
        return String.format(format, "멤버십할인", "", formattedPrice);
    }

    private String formatPaymentAmount() {
        String format = "%-10s %-5s %-10s\n";
        String formattedPrice = String.format("%,d", totalPaymentAmount());
        return String.format(format, "내실돈", "", formattedPrice);
    }

    public List<BillingItem> getBillingItems() {
        return billingItems;
    }
}
