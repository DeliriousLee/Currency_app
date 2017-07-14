package cn.edu.zucc.lc1298.exchange_rate.Model;

/**
 * Created by Lee on 2017/7/7.
 */

public class Records {
    public int country_id;
    public String time;
    public String moneyCode;
    public String countryName;

    public int getCountry_id() {
        return country_id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMoneyCode() {
        return moneyCode;
    }

    public void setMoneyCode(String moneyCode) {
        this.moneyCode = moneyCode;
    }
}
