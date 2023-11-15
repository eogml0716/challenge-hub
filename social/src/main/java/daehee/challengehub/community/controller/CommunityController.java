package daehee.challengehub.community.controller;

import daehee.challengehub.community.model.CommunityPostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/community")
public class CommunityController {
    // 커뮤니티 피드 조회
    @GetMapping("/feed")
    public ResponseEntity<List<CommunityPostDto>> getCommunityFeed() {
        // 임의의 커뮤니티 피드 데이터 생성
        List<CommunityPostDto> communityFeed = Arrays.asList(
                CommunityPostDto.builder()
                        .postId(1L)
                        .authorId(100L)
                        .postContent("첫 번째 커뮤니티 포스트 내용")
                        .postTitle("첫 번째 커뮤니티 포스트")
                        .creationDate("2023-11-10")
                        .lastEdited("2023-11-10")
                        .likeCount(15)
                        .commentCount(4)
                        .build(),
                CommunityPostDto.builder()
                        .postId(2L)
                        .authorId(101L)
                        .postContent("두 번째 커뮤니티 포스트 내용")
                        .postTitle("두 번째 커뮤니티 포스트")
                        .creationDate("2023-11-11")
                        .lastEdited("2023-11-11")
                        .likeCount(10)
                        .commentCount(2)
                        .build()
        );

        return ResponseEntity.ok(communityFeed);
    }


    // 커뮤니티 포스트 작성
    @PostMapping("/posts")
    public ResponseEntity<String> createCommunityPost(@RequestBody CommunityPostDto communityPostDto) {
        CommunityPostDto newPost = CommunityPostDto.builder()
                .postId(1L) // 임의의 게시물 ID
                .authorId(communityPostDto.getAuthorId()) // 작성자 ID
                .postContent(communityPostDto.getPostContent()) // 게시물 내용
                .postTitle(communityPostDto.getPostTitle()) // 게시물 제목
                .creationDate("2023-11-15") // 게시 날짜
                .lastEdited("2023-11-15") // 마지막 수정 날짜
                .likeCount(0) // 좋아요 수
                .commentCount(0) // 댓글 수
                .build();

        String responseMessage = String.format("커뮤니티 포스트 생성 성공: %s", newPost.getPostTitle());
        return ResponseEntity.ok(responseMessage);
    }

    // 커뮤니티 포스트 수정
    @PutMapping("/posts/{postId}")
    public ResponseEntity<CommunityPostDto> updatePost(@PathVariable Long postId, @RequestBody CommunityPostDto postUpdateData) {
        CommunityPostDto updatedPost = CommunityPostDto.builder()
                .postId(1L)
                .authorId(101L)
                .postContent("첫 번째 포스트 내용 수정")
                .postTitle("첫 번째 포스트 수정ㅇ")
                .creationDate("2023-11-12")
                .lastEdited("2023-11-15")
                .likeCount(10)
                .commentCount(5)
                .build();

        return ResponseEntity.ok(updatedPost);
    }

    // 커뮤니티 포스트 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postId = 3L;
        String responseMessage = "포스트 삭제 성공: 포스트 ID " + postId;
        return ResponseEntity.ok(responseMessage);
    }

    // 커뮤니티 포스트 목록 조회
    @GetMapping("/posts")
    public ResponseEntity<List<CommunityPostDto>> getCommunityPosts() {
        List<CommunityPostDto> posts = Arrays.asList(
                CommunityPostDto.builder()
                        .postId(1L)
                        .authorId(101L)
                        .postContent("첫 번째 포스트 내용")
                        .postTitle("첫 번째 포스트")
                        .creationDate("2023-11-15")
                        .lastEdited("2023-11-15")
                        .likeCount(10)
                        .commentCount(5)
                        .build(),
                CommunityPostDto.builder()
                        .postId(2L)
                        .authorId(102L)
                        .postContent("두 번째 포스트 내용")
                        .postTitle("두 번째 포스트")
                        .creationDate("2023-11-14")
                        .lastEdited("2023-11-14")
                        .likeCount(7)
                        .commentCount(3)
                        .build()
        );

        return ResponseEntity.ok(posts);
    }


    // 포스트 좋아요
    // TODO: 좋아요 관련 로직 구현이 단순히 1만 증가한다고 되는건지 다시 검토하기
    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<String> likeCommunityPost(@PathVariable Long postId) {
        CommunityPostDto likedPost = CommunityPostDto.builder()
                .postId(postId)
                .authorId(101L)
                .postContent("이 포스트는 좋아요를 받았습니다.")
                .postTitle("좋아요 받은 포스트")
                .creationDate("2023-11-15")
                .lastEdited("2023-11-15")
                .likeCount(11) // 좋아요 수 증가
                .commentCount(5)
                .build();

        String responseMessage = String.format("포스트 ID %d에 좋아요 성공", postId);
        return ResponseEntity.ok(responseMessage);
    }
}
