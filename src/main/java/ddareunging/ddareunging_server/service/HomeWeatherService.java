package ddareunging.ddareunging_server.service;

import ddareunging.ddareunging_server.controller.HomeController;
import ddareunging.ddareunging_server.domain.Region;
import ddareunging.ddareunging_server.domain.Weather;
import ddareunging.ddareunging_server.dto.WeatherResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Transactional
@Service
public class HomeWeatherService {

    // getWeather

    private final EntityManager em;
    private final String weatherApiServiceKey;

    @Autowired
    public HomeWeatherService(EntityManager em, @Value("${weatherApi.serviceKey}") String serviceKey) {
        this.em = em;
        this.weatherApiServiceKey = serviceKey;
    }

    public WeatherResponseDTO getWeatherDataOfRegion(Long regionId) {
        log.info("regionId is : " + regionId);

        // 해당 지역 조회
        Region region = em.find(Region.class, regionId);
        StringBuilder urlBuilderOfWeather = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst");

        // 요청 시각 조회
        LocalDateTime now = LocalDateTime.now(); // 현재 시간을 불러옴
        String yyyyMMdd = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int hour = now.getHour();
        int min = now.getMinute();
        if(min <= 30) { // 30분 전에는 자료가 없으므로 이전 시각을 기준으로 함
            hour -= 1;
        }
        String hourStr = "hourStr";
        if(hour < 10) { // 0900 과 같이 조회해야 함
            hourStr = "0" + hour + "00";
        } else {
            hourStr = hour + "00"; // 정시 기준
        }
        String nx = Integer.toString(region.getNx());
        String ny = Integer.toString(region.getNy());
        String currentChangeTime = now.format(DateTimeFormatter.ofPattern("yy.MM.dd ")) + hour;

        Weather prevWeather = region.getWeather();
        if(prevWeather != null && prevWeather.getLastUpdateTime() != null) {
            if(prevWeather.getLastUpdateTime().equals(currentChangeTime)) {
                // 마지막으로 저장한 시간 이후로 데이터가 업데이트 되지 않았다면 기존의 데이터를 그대로 넘김
                return WeatherResponseDTO.builder()
                        .weather(prevWeather)
                        .message("OK").build();
            }
        }


        log.info("API 요청 발송 >>> 지역: {}, 연월일: {}, 시각: {}", region, yyyyMMdd, hourStr);

        try {
            // 기상청 단기예보 - 초단기 실황 조회에 필요한 정보들 추가
            urlBuilderOfWeather.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + weatherApiServiceKey);
            urlBuilderOfWeather.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilderOfWeather.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilderOfWeather.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
            urlBuilderOfWeather.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(yyyyMMdd, "UTF-8")); /*‘21년 6월 28일 발표*/
            urlBuilderOfWeather.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(hourStr, "UTF-8")); /*06시 발표(정시단위) */
            urlBuilderOfWeather.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /*예보지점의 X 좌표값*/
            urlBuilderOfWeather.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /*예보지점의 Y 좌표값*/

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

            try {
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
            } catch (JSONException e) {
                log.error("날씨 정보 조회에 실패했습니다. 잠시 후 다시 시도해주세요");
                return WeatherResponseDTO.builder().message("날씨 정보 조회에 실패했습니다. 잠시 후 다시 시도해주세요").build();
            }

            int pm10Value = 0, pm25Value = 0, pm10Grade = 0, pm25Grade = 0;
            // 엑셀 파싱 구현 단계에서 임의로 만듦
            // 추후 미세먼지 정보 조회 구현 단계에서 삭제 예정

            // 새로 받아온 정보로 DB 업데이트
            Weather weather = new Weather(temp, rainAmount, currentChangeTime);
            region.updateRegionWeather(weather);
            return WeatherResponseDTO.builder()
                    .weather(weather)
                    .message("OK").build();

        } catch (IOException e) {
            return WeatherResponseDTO.builder()
                    .weather(null)
                    .message("날씨 및 미세먼지 정보 조회에 실패했습니다. 잠시 후 다시 시도해주세요").build();
        }
    }
}
