package sb.rf.generalchat.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sb.rf.generalchat.model.MessageStatisticView;
import sb.rf.generalchat.repository.MessageStatisticRepository;
import sb.rf.generalchat.service.MessageService;
import sb.rf.generalchat.service.MessageStatisticService;

import java.util.List;
@RestController
@RequestMapping("/api")
public class StatisticController {
    @Autowired
    private MessageStatisticService messageService;

    @GetMapping("/message/statistic")
    public ResponseEntity<List<MessageStatisticView>> getStatistic(){
        return ResponseEntity.ok(messageService.getMessageStatistic());
    }
}
