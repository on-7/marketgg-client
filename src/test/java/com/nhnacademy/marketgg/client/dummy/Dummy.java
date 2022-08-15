package com.nhnacademy.marketgg.client.dummy;

import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.ProductUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.ReviewCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.ReviewUpdateRequest;
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

    public static ReviewUpdateRequest getDummyReviewUpdateRequest() {
        ReviewUpdateRequest reviewUpdateRequest = new ReviewUpdateRequest();
        ReflectionTestUtils.setField(reviewUpdateRequest, "reviewId", 1L);
        ReflectionTestUtils.setField(reviewUpdateRequest, "assetId", 1L);
        ReflectionTestUtils.setField(reviewUpdateRequest, "content", "테스트용 리뷰 수정 DTO입니다");
        ReflectionTestUtils.setField(reviewUpdateRequest, "rating", 3L);

        return reviewUpdateRequest;
    }

    public static MemberInfo getDummyMemberInfo() {
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

    public static ReviewResponse getDummyReviewResponse() {
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

    public static ProductCreateRequest getDummyProductCreateRequest() {
        ProductCreateRequest productRequest = new ProductCreateRequest();

        ReflectionTestUtils.setField(productRequest, "categoryCode", "001");
        ReflectionTestUtils.setField(productRequest, "labelNo", 1L);
        ReflectionTestUtils.setField(productRequest, "name", "상품 request DTO 이름");
        ReflectionTestUtils.setField(productRequest, "content", "상품 request DTO Content");
        ReflectionTestUtils.setField(productRequest, "totalStock", 100L);
        ReflectionTestUtils.setField(productRequest, "price", 100_000L);
        ReflectionTestUtils.setField(productRequest, "description", "상품 request DTO Description");
        ReflectionTestUtils.setField(productRequest, "unit", "상품 request DTO unit");
        ReflectionTestUtils.setField(productRequest, "deliveryType", "상품 request DTO deliveryType");
        ReflectionTestUtils.setField(productRequest, "origin", "상품 request DTO origin");
        ReflectionTestUtils.setField(productRequest, "packageType", "상품 request DTO packageType");
        ReflectionTestUtils.setField(productRequest, "expirationDate", LocalDate.now());
        ReflectionTestUtils.setField(productRequest, "allergyInfo", "상품 request DTO allergyInfo");
        ReflectionTestUtils.setField(productRequest, "capacity", "상품 request DTO capacity");

        return productRequest;
    }

    public static ProductUpdateRequest getDummyProductUpdateRequest() {
        ProductUpdateRequest productRequest = new ProductUpdateRequest();
        ReflectionTestUtils.setField(productRequest, "categoryCode", "001");
        ReflectionTestUtils.setField(productRequest, "labelNo", 1L);
        ReflectionTestUtils.setField(productRequest, "name", "자몽");
        ReflectionTestUtils.setField(productRequest, "content", "아침에 자몽 쥬스");
        ReflectionTestUtils.setField(productRequest, "totalStock", 100L);
        ReflectionTestUtils.setField(productRequest, "price", 2000L);
        ReflectionTestUtils.setField(productRequest, "description", "자몽주스 설명");
        ReflectionTestUtils.setField(productRequest, "unit", "1박스");
        ReflectionTestUtils.setField(productRequest, "deliveryType", "샛별배송");
        ReflectionTestUtils.setField(productRequest, "origin", "인도네시아");
        ReflectionTestUtils.setField(productRequest, "packageType", "냉장");
        ReflectionTestUtils.setField(productRequest, "allergyInfo", "새우알러지");
        ReflectionTestUtils.setField(productRequest, "expirationDate", LocalDate.now());
        ReflectionTestUtils.setField(productRequest, "capacity", "20개");
        ReflectionTestUtils.setField(productRequest, "assetNo", 1L);

        return productRequest;
    }


}
