/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package indy;

/**
 *
 * @author strif
 */
public class IndyWinner {
    private String year;
    private String driver;
    private float averageSpeed;
    private String country;

    public String getYear() {
        return year;
    }

    public String getDriver() {
        return driver;
    }

    public float getAverageSpeed() {
        return averageSpeed;
    }

    public String getCountry() {
        return country;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setAverageSpeed(float averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "IndyWinner{" + "year=" + year + ", driver=" + driver + ", averageSpeed=" + averageSpeed + ", country=" + country + '}';
    }
}
