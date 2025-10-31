import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class WeatherForecast {

    // Replace with your own API key from https://openweathermap.org/
    private static final String API_KEY = "YOUR_API_KEY_HERE";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("🌦️ Welcome to the Weather Forecast App");
        System.out.print("Enter city name: ");
        String city = scanner.nextLine();
        getWeather(city);
        scanner.close();
    }

    public static void getWeather(String city) {
        try {
            String urlString = String.format(
                    "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric",
                    city, API_KEY);

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject obj = new JSONObject(response.toString());

            String cityName = obj.getString("name");
            String country = obj.getJSONObject("sys").getString("country");
            double temp = obj.getJSONObject("main").getDouble("temp");
            int humidity = obj.getJSONObject("main").getInt("humidity");
            String weather = obj.getJSONArray("weather").getJSONObject(0).getString("description");

            System.out.println("\n📍 City: " + cityName + ", " + country);
            System.out.println("🌡️ Temperature: " + temp + " °C");
            System.out.println("💧 Humidity: " + humidity + "%");
            System.out.println("☁️ Condition: " + weather);

        } catch (Exception e) {
            System.out.println("❌ Error fetching weather data. Check city name or internet connection.");
        }
    }
}
