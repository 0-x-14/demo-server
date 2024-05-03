package ddareunging.ddareunging_server.controller;

import ddareunging.ddareunging_server.domain.Region;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.apache.commons.lang3.StringUtils.length;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RegionController {

    @Value("${resources.location}")
    private String resourceLocation;

    private final EntityManager em;

    @PostMapping("/region")
    @Transactional
    public ResponseEntity<String> resetRegionList() {
        String fileLocation = resourceLocation + "/regionList.csv";
        Path path = Paths.get(fileLocation);
        URI uri = path.toUri();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new UrlResource(uri).getInputStream()))
        ) {
            String line = br.readLine(); // head 떼기
            while ((line = br.readLine()) != null) {
                System.out.println("line is : " + line);
                if (length(line) == 5) { // line 값이 ,,,,,일 경우, 즉 데이터가 없을 경우 while문 종료
                    System.out.println("while문 종료");
                } else {
                    String[] splits = line.split(","); // 오류 발생 지점
                    em.persist(new Region(Long.parseLong(splits[0]), splits[1], splits[2],
                            Integer.parseInt(splits[3]), Integer.parseInt(splits[4])));
                }
//                if (length(splits[0]) == 0 || splits[0].isEmpty()) {
//                    System.out.println("while문 종료");
//                    break;
//                } else {
//                    System.out.println("splits[0] is : " + splits[0]);
//                    System.out.println("splits[0]'s length is : " + length(splits[0]));
//                    em.persist(new Region(Long.parseLong(splits[0]), splits[1], splits[2],
//                            Integer.parseInt(splits[3]), Integer.parseInt(splits[4])));
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류 발생");
        }

        return ResponseEntity.ok("엑셀 파싱 성공");
    }
}
