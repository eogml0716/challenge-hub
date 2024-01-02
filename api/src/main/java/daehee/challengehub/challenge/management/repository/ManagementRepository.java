package daehee.challengehub.challenge.management.repository;

import daehee.challengehub.challenge.management.entity.Challenge;
import daehee.challengehub.challenge.management.entity.Participant;
import daehee.challengehub.challenge.management.model.ChallengeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public class ManagementRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ManagementRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    // 챌린지 생성
    public Challenge createChallenge(ChallengeDto challengeDto) {
        Challenge challenge = Challenge.builder()
                .title(challengeDto.getTitle())
                .frequency(challengeDto.getFrequency())
                .duration(challengeDto.getDuration())
                .startTime(challengeDto.getStartTime())
                .endTime(challengeDto.getEndTime())
                .startDate(challengeDto.getStartDate())
                .verificationMethod(challengeDto.getVerificationMethod())
                .verificationExampleUrls(challengeDto.getVerificationExampleUrls())
                .isCameraOnly(challengeDto.isCameraOnly())
                .description(challengeDto.getDescription())
                .category(challengeDto.getCategory())
                .coverImageUrl(challengeDto.getCoverImageUrl())
                .keywords(challengeDto.getKeywords())
                .isPublic(challengeDto.isPublic())
                .createdBy(challengeDto.getCreatedBy())
                .createdAt(Instant.now())
                .lastModified(Instant.now())
                .build();

        return mongoTemplate.save(challenge, "challenges");
    }


    // 전체 챌린지 목록 조회
    public List<Challenge> getAllChallenges() {
        return mongoTemplate.findAll(Challenge.class, "challenges");
    }

    // 특정 챌린지 상세 조회
    public Challenge getChallengeById(String challengeId) {
        return mongoTemplate.findById(challengeId, Challenge.class, "challenges");
    }

    // 특정 챌린지 수정
    public Challenge updateChallenge(String challengeId, ChallengeDto updatedChallengeDto) {
        // 주어진 challengeId로 기존 Challenge를 찾아 업데이트
        Query query = new Query(Criteria.where("id").is(challengeId));
        Update update = new Update();

        // Update 객체에 필드별 변경 사항을 설정
        update.set("title", updatedChallengeDto.getTitle());
        update.set("frequency", updatedChallengeDto.getFrequency());
        update.set("duration", updatedChallengeDto.getDuration());
        update.set("startTime", updatedChallengeDto.getStartTime());
        update.set("endTime", updatedChallengeDto.getEndTime());
        update.set("startDate", updatedChallengeDto.getStartDate());
        update.set("verificationMethod", updatedChallengeDto.getVerificationMethod());
        update.set("verificationExampleUrls", updatedChallengeDto.getVerificationExampleUrls());
        update.set("isCameraOnly", updatedChallengeDto.isCameraOnly());
        update.set("description", updatedChallengeDto.getDescription());
        update.set("category", updatedChallengeDto.getCategory());
        update.set("coverImageUrl", updatedChallengeDto.getCoverImageUrl());
        update.set("keywords", updatedChallengeDto.getKeywords());
        update.set("isPublic", updatedChallengeDto.isPublic());
        update.set("createdBy", updatedChallengeDto.getCreatedBy());
        update.set("createdAt", updatedChallengeDto.getCreatedAt());
        update.set("lastModified", Instant.now()); // 마지막 수정 시간을 현재 시간으로 설정

        // 기존 문서를 업데이트하고 결과 반환
        return mongoTemplate.findAndModify(query, update, Challenge.class, "challenges");
    }

    // 챌린지 삭제
    public boolean deleteChallenge(String challengeId) {
        Query query = new Query(Criteria.where("id").is(challengeId));
        return mongoTemplate.remove(query, Challenge.class, "challenges").getDeletedCount() > 0;
    }

    // 챌린지 참여 신청
    public Participant participateInChallenge(String challengeId, String userId) {
        Participant participant = Participant.builder()
                .challengeId(challengeId)
                .userId(userId)
                .joinDate(Instant.now())
                .achievementRate(0.0)
                .build();

        return mongoTemplate.save(participant, "participants");
    }

    // 챌린지 참여 취소
    public boolean cancelParticipation(String challengeId, String userId) {
        Query query = new Query(Criteria.where("challengeId").is(challengeId)
                .andOperator(Criteria.where("userId").is(userId)));
        return mongoTemplate.remove(query, Participant.class, "participants").getDeletedCount() > 0;
    }

    // 챌린지 참가자 목록 조회
    public List<Participant> getChallengeParticipants(String challengeId) {
        Query query = new Query(Criteria.where("challengeId").is(challengeId));
        return mongoTemplate.find(query, Participant.class, "participants");
    }
}
