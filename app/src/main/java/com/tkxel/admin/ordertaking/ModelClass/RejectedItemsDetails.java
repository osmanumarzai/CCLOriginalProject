package com.tkxel.admin.ordertaking.ModelClass;

/**
 * Created by admin on 12/21/2017.
 */

import java.io.Serializable;

public class RejectedItemsDetails implements Serializable {
    int DeliveryOrderId = 0, itemId = 0, BalanceQuantity = 0, DeliveryQuantity = 0;

    public int get_DeliveryOrderId() {
        return DeliveryOrderId;
    }

    public void set_DeliveryOrderId(int DeliveryOrderId) {
        this.DeliveryOrderId = DeliveryOrderId;
    }

    public int get_itemId() {
        return itemId;
    }

    public void set_itemId(int itemId) {
        this.itemId = itemId;
    }

    public int get_BalanceQuantity() {
        return BalanceQuantity;
    }

    public void set_BalanceQuantity(int BalanceQuantity) {
        this.BalanceQuantity = BalanceQuantity;
    }

    public int get_DeliveryQuantity() {
        return DeliveryQuantity;
    }

    public void set_DeliveryQuantity(int DeliveryQuantity) {
        this.DeliveryQuantity = DeliveryQuantity;
    }


}