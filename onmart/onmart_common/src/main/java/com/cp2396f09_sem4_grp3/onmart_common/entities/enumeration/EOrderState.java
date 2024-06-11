package com.cp2396f09_sem4_grp3.onmart_common.entities.enumeration;

public enum EOrderState {
    PLACED, // The order has been placed
    CONFIRMED, // OnMart has confirmed the order
    PREPARING, // OnMart is preparing the order
    DELIVERING, // The order is being delivered to you
    DELIVERY_FAILED, // Delivery was unsuccessful
    DELIVERED, // Delivery was successful
    RECEIVED, // The order has been received
    CANCELED // The order was canceled
}
