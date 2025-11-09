package app;

public class Row {
    public final String country;
    public final String region;
    public final int year;
    public final String disease;
    public final Double vaccinationRate;

    public Row(String country, String region, int year, String disease, Double vaccinationRate) {
        this.country = country;
        this.region = region;
        this.year = year;
        this.disease = disease;
        this.vaccinationRate = vaccinationRate;
    }
}
