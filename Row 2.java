package app;
public class Row {
    public final String country;
    public final String region;
    public final int year;
    public final String disease;
    public final Double vaccinationRate;
    public Row(String c, String r, int y, String d, Double v){
        country=c; region=r; year=y; disease=d; vaccinationRate=v;
    }
}
