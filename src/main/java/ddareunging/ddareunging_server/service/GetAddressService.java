package ddareunging.ddareunging_server.service;

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
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class GetAddressService {
    public List<String> getAddress(String kakaoApiServiceKey, String latitude, String longitude) throws IOException, JSONException  {
        StringBuilder urlBuilderOfAddress = new StringBuilder("https://dapi.kakao.com/v2/local/geo/coord2regioncode.json");
        urlBuilderOfAddress.append("?" + URLEncoder.encode("x", "UTF-8") + "=" + longitude); // 경도
        urlBuilderOfAddress.append("&" + URLEncoder.encode("y", "UTF-8") + "=" + latitude); // 위도

        URL urlOfAddress = new URL(urlBuilderOfAddress.toString());
        log.info("request url : {}", urlOfAddress);

        HttpURLConnection conn = (HttpURLConnection) urlOfAddress.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Authorization", "KakaoAK " + kakaoApiServiceKey);  // API 키를 Authorization 헤더에 추가


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

        String district = null;
        String dong = null;

        JSONObject jObject = new JSONObject(data);
        log.info("jObject is : " + jObject);
        JSONArray documents = jObject.getJSONArray("documents");

        for (int i = 0; i < documents.length(); i++) {
            JSONObject document = documents.getJSONObject(i);

            String regionType = document.getString("region_type");
            if ("H".equals(regionType)) {
                district = document.getString("region_2depth_name");
                dong = document.getString("region_3depth_name");

                log.info("district Name: " + district);
                log.info("dong Name: " + dong);
                break;  // H 타입을 찾았으니 더 이상의 반복은 필요 없음
            }
        }

        return Arrays.asList(district, dong);
    }
}
