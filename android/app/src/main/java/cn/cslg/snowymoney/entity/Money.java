package cn.cslg.snowymoney.entity;

public class Money {
    private String money;
    private boolean isSelected;

    public Money(String money, boolean isSelected) {
        this.money = money;
        this.isSelected = isSelected;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
