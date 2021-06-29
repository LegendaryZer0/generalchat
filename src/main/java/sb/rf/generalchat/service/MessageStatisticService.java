package sb.rf.generalchat.service;

import sb.rf.generalchat.model.MessageStatisticView;

import java.util.List;

public interface MessageStatisticService {
  List<MessageStatisticView> getMessageStatistic();
}
