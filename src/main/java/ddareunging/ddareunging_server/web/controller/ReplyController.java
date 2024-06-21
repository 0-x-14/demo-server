package ddareunging.ddareunging_server.web.controller;

import ddareunging.ddareunging_server.domain.Reply;
import ddareunging.ddareunging_server.dto.ReplyRequestDto;
import ddareunging.ddareunging_server.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/replies")

public class ReplyController {
    @Autowired
    private ReplyService replyService;

    @GetMapping("/course/{courseId}")
    public List<Reply> getRepliesByCourseId(@PathVariable Long courseId) {
        return replyService.findByCourseId(courseId);
    }

    @PostMapping
    public Reply createReply(@RequestBody ReplyRequestDto replyRequestDto) {
        return replyService.createReply(replyRequestDto);
    }

}
