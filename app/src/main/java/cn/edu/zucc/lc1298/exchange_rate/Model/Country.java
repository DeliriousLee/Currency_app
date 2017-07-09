package cn.edu.zucc.lc1298.exchange_rate.Model;

/**
 * Created by Lee on 2017/7/2.
 */

public class Country {
    public String countryName;
    public String moneyCode;
    private int imageId;
    public Currency currency;
    private String str_currency;
    private int countryId;

    public static String doubleToString(double actor){
        String result=new String();
        result=String.valueOf(actor);
        return result;
    }
    public Country(){

    }
    public Country(int countryId,String countryName,String moneyCode,int image ){
        this.countryId=countryId;
        this.countryName=countryName;
        imageId=image;
        this.moneyCode=moneyCode;
    }
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getMoneyCode() {
        return moneyCode;
    }

    public void setMoneyCode(String moneyCode) {
        this.moneyCode = moneyCode;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getStr_currency() {
        return str_currency;
    }

    public void setStr_currency(String str_currency) {
        this.str_currency = str_currency;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }


}
