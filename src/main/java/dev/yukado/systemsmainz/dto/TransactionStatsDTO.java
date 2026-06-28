package dev.yukado.systemsmainz.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TransactionStatsDTO {

    public double income;
    public double expenses;

    public List<PriceGroup> byPrice;
    public List<CountryGroup> byCountry;
    public List<CityGroup> byCity;

    public static class PriceGroup {
        public double price;
        public long sold;
        public PriceGroup(double price, long sold) { this.price = price; this.sold = sold; }
    }

    public static class CountryGroup {
        public String country;
        public long sold;
        public CountryGroup(String country, long sold) { this.country = country; this.sold = sold; }
    }

    public static class CityGroup {
        public String city;
        public long sold;
        public CityGroup(String city, long sold) { this.city = city; this.sold = sold; }
    }

}



