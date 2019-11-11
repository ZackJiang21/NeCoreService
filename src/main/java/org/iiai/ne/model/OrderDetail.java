package org.iiai.ne.model;

import java.util.List;

public class OrderDetail extends Order {
    private boolean isDiscount;

    private Coupon coupon;

    private Address address;

    private String remark;

    private List<Dish> dishes;

    public OrderDetail() {
    }

    public boolean getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(boolean discount) {
        isDiscount = discount;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
