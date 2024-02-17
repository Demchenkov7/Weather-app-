package sample.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;


public class HelloController {
    private String currentCity;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text error;

    @FXML
    private Button getDataEkb;

    @FXML
    private Button getDataKz;

    @FXML
    private Button getDataMsk;

    @FXML
    private Button getDataSch;

    @FXML
    private Button getDataSpb;

    @FXML
    private Button getDataVor;

    @FXML
    private Text pressure;

    @FXML
    private Text temp_feels;

    @FXML
    private Text temp_ifo;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

    @FXML
    void initialize() {

        getData.setOnAction(actionEvent -> {
            // Получаем данные из текстового поля
            currentCity = city.getText().trim();
            loadData();
        });

        getDataSpb.setOnAction(spbEvent -> {
            currentCity = "Санкт-Петербург";
            loadData();
        });

        getDataEkb.setOnAction(ekbEvent -> {
            currentCity = "Екатеринбург";
            loadData();
        });

        getDataKz.setOnAction(kzEvent -> {
            currentCity = "Казань";
            loadData();
        });

        getDataMsk.setOnAction(mskEvent -> {
            currentCity = "Москва";
            loadData();
        });

        getDataSch.setOnAction(schEvent -> {
            currentCity = "Сочи";
            loadData();
        });

        getDataVor.setOnAction(vorEvent -> {
            currentCity = "Воронеж";
            loadData();
        });

    }

    private void loadData() {
        if(!currentCity.equals("")) {
            // Получаем данные о погоде с сайта openweathermap
            String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + currentCity + "&appid=d1b3d8279bac0f9fd27cba26b533e760&units=metric");


            if (!output.isEmpty()) {
                JSONObject obj = new JSONObject(output);
                error.setText("");
                temp_ifo.setText("Температура: " + obj.getJSONObject("main").getDouble("temp"));
                temp_feels.setText("Ощущается: " + obj.getJSONObject("main").getDouble("feels_like"));
                temp_max.setText("Максимум: " + obj.getJSONObject("main").getDouble("temp_max"));
                temp_min.setText("Минимум: " + obj.getJSONObject("main").getDouble("temp_min"));
                pressure.setText("Давление: " + obj.getJSONObject("main").getDouble("pressure"));
                // Обрабатываем JSON и устанавливаем данные в текстовые надписи
            }
        }
    }

    private String getUrlContent(String urlAdress) {
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAdress);
            URLConnection urlConnection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            temp_ifo.setText("Температура: ");
            temp_feels.setText("Ощущается: ");
            temp_max.setText("Максимум: ");
            temp_min.setText("Минимум: ");
            pressure.setText("Давление: ");
            error.setText("Город не найден");
        }
        return content.toString();
    }

}
