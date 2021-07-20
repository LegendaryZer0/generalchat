package sb.rf.generalchat.util;

public interface MailsGenerator {
  String getEmailforConfirm(String serverUrl, Long userId);
}
