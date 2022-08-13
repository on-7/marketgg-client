package com.nhnacademy.marketgg.client.dummy;

import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.request.ReviewCreateRequest;
import com.nhnacademy.marketgg.client.dto.response.ReviewResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.util.ReflectionTestUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Dummy {

    public static ReviewCreateRequest getDummyReviewCreateRequest() {
        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest();
        ReflectionTestUtils.setField(reviewCreateRequest, "content", "테스트용 리뷰 생성 DTO 입니다");
        ReflectionTestUtils.setField(reviewCreateRequest, "rating", 5L);

        return reviewCreateRequest;
    }

    public static MemberInfo getMemberInfo() {
        MemberInfo memberInfo = new MemberInfo();

        ReflectionTestUtils.setField(memberInfo, "email", "이메일");
        ReflectionTestUtils.setField(memberInfo, "name", "이름");
        ReflectionTestUtils.setField(memberInfo, "phoneNumber", "폰번호");
        ReflectionTestUtils.setField(memberInfo, "memberGrade", "멤버그레이드");
        ReflectionTestUtils.setField(memberInfo, "gender", 'X');
        ReflectionTestUtils.setField(memberInfo, "birthDay", LocalDate.now());
        ReflectionTestUtils.setField(memberInfo, "ggpassUpdatedAt", LocalDateTime.now());

        return memberInfo;
    }

    public static ReviewResponse getReviewResponse() {
        ReviewResponse reviewResponse = new ReviewResponse();

        ReflectionTestUtils.setField(reviewResponse, "id", 1L);
        ReflectionTestUtils.setField(reviewResponse, "memberId", 1L);
        ReflectionTestUtils.setField(reviewResponse, "assetId", 1L);
        ReflectionTestUtils.setField(reviewResponse, "content", "후기 내용");
        ReflectionTestUtils.setField(reviewResponse, "rating", 5L);
        ReflectionTestUtils.setField(reviewResponse, "isBest", false);
        ReflectionTestUtils.setField(reviewResponse, "createdAt", LocalDateTime.now());
        ReflectionTestUtils.setField(reviewResponse, "updatedAt", LocalDateTime.now());
        ReflectionTestUtils.setField(reviewResponse, "deletedAt", LocalDateTime.now());

        return reviewResponse;
    }
}
