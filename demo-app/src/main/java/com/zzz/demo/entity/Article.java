package com.zzz.demo.entity;

import java.io.Serializable;

/**
 * ClassName:Article <br/>
 * Date: 2018年5月8日 上午10:31:03 <br/>
 *
 * @author fenglibin
 * @see
 */
public class Article implements Serializable {

    private String province;
    private String title;
    private String author;
    private String countryCode;
    private double price;

    public Article() {
    }

    public Article(String province, String title, String author, double price) {
        this.province = province;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public Article(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public Article(String title, String author, String countryCode, String province) {
        this.title = title;
        this.author = author;
        this.countryCode = countryCode;
        this.province = province;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", province='" + province + '\'' +
                ", price=" + price +
                '}';
    }
}
