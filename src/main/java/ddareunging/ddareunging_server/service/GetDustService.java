package ddareunging.ddareunging_server.service;

import ddareunging.ddareunging_server.domain.Dust;
import ddareunging.ddareunging_server.domain.Weather;
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
public class GetDustService {
    public Dust fetchDustData(String weatherApiServiceKey, String district) throws IOException, JSONException {
        StringBuilder urlBuilderOfDust = new StringBuilder("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty");

        // 에어코리아 대기 오염 정보 - 측정소별 실시간 측정정보 조회
        urlBuilderOfDust.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + weatherApiServiceKey);
        urlBuilderOfDust.append("&" + URLEncoder.encode("returnType", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
        urlBuilderOfDust.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8"));
        urlBuilderOfDust.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilderOfDust.append("&" + URLEncoder.encode("stationName", "UTF-8") + "=" + URLEncoder.encode(district, "UTF-8"));
        urlBuilderOfDust.append("&" + URLEncoder.encode("dataTerm", "UTF-8") + "=" + URLEncoder.encode("DAILY", "UTF-8"));
        urlBuilderOfDust.append("&" + URLEncoder.encode("ver", "UTF-8") + "=" + URLEncoder.encode("1.0", "UTF-8"));

        URL urlOfDust = new URL(urlBuilderOfDust.toString());
        log.info("request url : {}", urlOfDust);

        HttpURLConnection conn = (HttpURLConnection) urlOfDust.openConnection();
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

        String pm10Value= null;
        String pm25Value = null;
        // 수치는 통신 오류가 발생하면 -로 저장되므로 String으로 설정하였음
        int pm10Grade = -1;
        int pm25Grade = -1;

        JSONObject jObject = new JSONObject(data);
        log.info("jObject is : " + jObject);
        JSONObject response = jObject.getJSONObject("response");
        JSONObject body = response.getJSONObject("body");
        JSONArray items = body.getJSONArray("items");

        JSONObject firstItem = items.getJSONObject(0);

        // JSONObject에서 값을 가져옵니다.
        pm10Value = firstItem.optString("pm10Value", "-"); // pm10Value 값 추출
        pm25Value = firstItem.optString("pm25Value", "-"); // pm25Value 값 추출
        pm10Grade = firstItem.optInt("pm10Grade", -1); // pm10Grade 값 추출
        pm25Grade = firstItem.optInt("pm25Grade", -1); // pm25Grade 값 추출

        // 생성된 Dust 객체 반환
        return new Dust(pm10Value, pm25Value, pm10Grade, pm25Grade);
    }
}
