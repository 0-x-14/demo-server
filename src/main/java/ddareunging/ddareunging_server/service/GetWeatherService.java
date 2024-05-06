package ddareunging.ddareunging_server.service;

import ddareunging.ddareunging_server.domain.Weather;
import ddareunging.ddareunging_server.dto.WeatherResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Slf4j
@Service
public class GetWeatherService {
    StringBuilder urlBuilderOfWeather = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst");

    public Weather fetchWeatherData(String weatherApiServiceKey, String yyyyMMdd, String hourStr, String nx, String ny, String currentChangeTime) throws IOException, JSONException {
        // 기상청 단기예보 - 초단기 실황 조회에 필요한 정보들 추가
        urlBuilderOfWeather.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + weatherApiServiceKey);
        urlBuilderOfWeather.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilderOfWeather.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilderOfWeather.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilderOfWeather.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(yyyyMMdd, "UTF-8")); /*‘21년 6월 28일 발표*/
        urlBuilderOfWeather.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(hourStr, "UTF-8")); /*06시 발표(정시단위) */
        urlBuilderOfWeather.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilderOfWeather.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /*예보지점의 Y 좌표값*/

        URL urlOfWeather = new URL(urlBuilderOfWeather.toString());
        log.info("request url: {}", urlOfWeather);

        HttpURLConnection conn = (HttpURLConnection) urlOfWeather.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        String data = sb.toString();

        //// 응답 수신 완료 ////
        //// 응답 결과를 JSON 파싱 ////

        Double temp = null;
        Double rainAmount = null;

        JSONObject jObject = new JSONObject(data);
        log.info("jObject is : " + jObject);
        JSONObject response = jObject.getJSONObject("response");
        JSONObject body = response.getJSONObject("body");
        JSONObject items = body.getJSONObject("items");
        JSONArray jArray = items.getJSONArray("item");

        for(int i = 0; i < jArray.length(); i++) {
            JSONObject obj = jArray.getJSONObject(i);
            String category = obj.getString("category");
            double obsrValue = obj.getDouble("obsrValue");

            switch (category) {
                case "T1H": // 기온
                    temp = obsrValue;
                    break;
                case "RN1": // 강수량
                    rainAmount = obsrValue;
                    break;
            }
        }
        // 생성된 Weather 객체 반환
        return new Weather(temp, rainAmount, currentChangeTime);
    }
}
