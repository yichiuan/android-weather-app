package com.yichiuan.weatherapp.model;


public class Weather {
    private WeatherCode weatherCode;
    private float temperature; // °F
    private String description;
    private byte humidity;
    private Wind wind;

    public Weather(WeatherCode weatherCode, float temperature, String description, byte humidity, Wind wind) {
        this.weatherCode = weatherCode;
        this.temperature = temperature;
        this.description = description;
        this.humidity = humidity;
        this.wind = wind;
    }

    public WeatherCode getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(WeatherCode weatherCode) {
        this.weatherCode = weatherCode;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte getHumidity() {
        return humidity;
    }

    public void setHumidity(byte humidity) {
        this.humidity = humidity;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public static float convertToFahrenheit(float temperature) {
        return temperature * 1.8f + 32;
    }

    public static float convertToCelsius(float temperature) {
        return (temperature - 32) * 5 / 9;
    }
}
