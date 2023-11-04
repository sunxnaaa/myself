package com.test.fx.money.model;

import java.math.BigDecimal;
import java.util.List;

public class Enitry {
    private List<MoneyModel> moneyList;

    private BigDecimal tax;

    private BigDecimal taxOfMoney;


    public List<MoneyModel> getMoneyList() {
        return moneyList;
    }

    public void setMoneyList(List<MoneyModel> moneyList) {
        this.moneyList = moneyList;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTaxOfMoney() {
        return taxOfMoney;
    }

    public void setTaxOfMoney(BigDecimal taxOfMoney) {
        this.taxOfMoney = taxOfMoney;
    }
}
