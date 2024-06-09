package ddareunging.ddareunging_server.service;

import ddareunging.ddareunging_server.domain.Notice;
import ddareunging.ddareunging_server.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class MyPageService {
    private final NoticeRepository noticeRepository;

    @Autowired
    public MyPageService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Transactional
    public List<Notice> getNotice() throws Exception {
        // 공지사항 조회

        return noticeRepository.findAll();
    }
}
