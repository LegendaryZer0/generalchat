package sb.rf.generalchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sb.rf.generalchat.model.Document;

import java.util.UUID;


public interface DocumentRepo extends JpaRepository<Document, UUID> {
}
