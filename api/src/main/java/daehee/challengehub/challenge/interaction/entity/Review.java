package daehee.challengehub.challenge.interaction.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@Builder
@Getter
public class Review {
    @Id
    private String id;
    private String challengeId;
    private String authorId;
    private int rating;
    private String comment;
    private Instant createdDate;
}
