package com.mycompany.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Task3 {
    private static final String API_URL =
            "https://api.open-meteo.com/v1/forecast"
            + "?latitude=56&longitude=44"
            + "&hourly=temperature_2m,rain"
            + "&current=cloud_cover"
            + "&timezone=Europe%2FMoscow"
            + "&forecast_days=1"
            + "&wind_speed_unit=ms";

    private static final String FORECAST_FILE = "../result/forecast.txt";

    public static void run(WebDriver webDriver) throws Exception {
        webDriver.get(API_URL);

        WebElement body = webDriver.findElement(By.tagName("body"));
        String jsonStr = body.getText();

        JSONParser parser = new JSONParser();
        JSONObject root = (JSONObject) parser.parse(jsonStr);

        JSONObject hourly = (JSONObject) root.get("hourly");
        JSONArray times = (JSONArray) hourly.get("time");
        JSONArray temps = (JSONArray) hourly.get("temperature_2m");
        JSONArray rains = (JSONArray) hourly.get("rain");

        String header = String.format("%-4s | %-19s | %-14s | %-12s",
                "№", "Дата/время", "Температура", "Осадки (мм)");
        String separator = "-".repeat(header.length());

        System.out.println(header);
        System.out.println(separator);

        StringBuilder sb = new StringBuilder();
        sb.append(header).append(System.lineSeparator());
        sb.append(separator).append(System.lineSeparator());

        for (int i = 0; i < times.size(); i++) {
            String row = String.format("%-4d | %-19s | %-14s | %-12s",
                    i + 1,
                    times.get(i),
                    temps.get(i) + " °C",
                    rains.get(i) + " мм");
            System.out.println(row);
            sb.append(row).append(System.lineSeparator());
        }

        try (PrintWriter pw = new PrintWriter(
                new FileWriter(FORECAST_FILE, StandardCharsets.UTF_8))) {
            pw.print(sb.toString());
        }
        System.out.println("\nПрогноз сохранён в " + FORECAST_FILE);
    }
}
