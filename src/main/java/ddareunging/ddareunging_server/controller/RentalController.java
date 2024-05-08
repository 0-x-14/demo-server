package ddareunging.ddareunging_server.controller;

import ddareunging.ddareunging_server.domain.Rental;
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

import static org.apache.commons.lang3.StringUtils.length;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RentalController {

    @Value("${resources.location}")
    private String resourceLocation;

    private final EntityManager em;

    @PostMapping("/rental")
    @Transactional
    public ResponseEntity<String> resetRentalList() {
        String fileLocation = resourceLocation + "/rental.csv";
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
                    String[] splits = line.split(",");
                    em.persist(new Rental(Long.parseLong(splits[0]), splits[1], splits[2],
                            Double.parseDouble(splits[3]), Double.parseDouble(splits[4])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류 발생");
        }

        System.out.println("대여소 정보 엑셀 파싱 성공");
        return ResponseEntity.ok("대여소 정보 엑셀 파싱 성공");
    }
}
