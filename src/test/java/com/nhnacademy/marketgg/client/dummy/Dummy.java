package com.nhnacademy.marketgg.client.dummy;

import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.PageResult;
import com.nhnacademy.marketgg.client.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.ProductUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.ReviewCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.ReviewUpdateRequest;
import com.nhnacademy.marketgg.client.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.response.ImageResponse;
import com.nhnacademy.marketgg.client.dto.response.LabelRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.response.ProductResponse;
import com.nhnacademy.marketgg.client.dto.response.ReviewResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.util.ReflectionTestUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Dummy {

    public static ReviewCreateRequest getDummyReviewCreateRequest() {
        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest("테스트용 리뷰 생성 DTO 입니다", 5L);

        return reviewCreateRequest;
    }

    public static ReviewUpdateRequest getDummyReviewUpdateRequest() {
        ReviewUpdateRequest reviewUpdateRequest = new ReviewUpdateRequest(1L, 1L, "테스트용 리뷰 수정 DTO입니다", 3L);

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
        ProductCreateRequest productRequest =
            new ProductCreateRequest("001", 1L, "자몽", "자몽 맛있음", 100L,
                                     100000L, "자몽설명", "1박스", "냉장", "미국",
                                     "냉장", LocalDate.now(), "없음", "1개");

        return productRequest;
    }

    public static String getDummyModelAttributeProductCreateRequest() {
        return "categoryCode=001&labelNo=1&name=자몽자몽&content=자몽컨텐트&totalStock=100&price=100000&description=자몽설명&" +
            "unit=1박스&deliveryType=냉장&origin=미국&packageType=일반&expirationDate=2022-01-02&allergyInfo=없음&capacity=20개";
    }

    public static ProductUpdateRequest getDummyProductUpdateRequest() {
        ProductUpdateRequest productRequest = new ProductUpdateRequest(1L, "001", 1L, "자몽", "자몽 맛있음", 100L,
                                                                       100000L, "자몽설명", "1박스", "냉장", "미국",
                                                                       "냉장", LocalDate.now(), "없음", "1개");

        return productRequest;
    }

    public static String getDummyModelAttributeProductUpdateRequest() {
        return "assetNo=1&categoryCode=001&labelNo=1&name=자몽자몽&content=자몽컨텐트&totalStock=100&price=100000&description=자몽설명&" +
            "unit=1박스&deliveryType=냉장&origin=미국&packageType=일반&expirationDate=2022-01-02&allergyInfo=없음&capacity=20개";
    }

    public static ProductResponse getDummyProductResponse() {
        ProductResponse productResponse = new ProductResponse();

        ReflectionTestUtils.setField(productResponse, "id", 1L);
        ReflectionTestUtils.setField(productResponse, "assetNo", 1L);
        ReflectionTestUtils.setField(productResponse, "categoryCode", "001");
        ReflectionTestUtils.setField(productResponse, "categoryName", "상품 Response DTO 카테고리 이름");
        ReflectionTestUtils.setField(productResponse, "name", "상품 Response DTO name");
        ReflectionTestUtils.setField(productResponse, "content", "상품 Response DTO content");
        ReflectionTestUtils.setField(productResponse, "totalStock", 100L);
        ReflectionTestUtils.setField(productResponse, "price", 100_000L);
        ReflectionTestUtils.setField(productResponse, "description", "상품 Response DTO description");
        ReflectionTestUtils.setField(productResponse, "unit", "상품 Response DTO unit");
        ReflectionTestUtils.setField(productResponse, "deliveryType", "상품 Response DTO deliveryType");
        ReflectionTestUtils.setField(productResponse, "origin", "상품 Response DTO origin");
        ReflectionTestUtils.setField(productResponse, "packageType", "상품 Response DTO packageType");
        ReflectionTestUtils.setField(productResponse, "expirationDate", LocalDate.now());
        ReflectionTestUtils.setField(productResponse, "allergyInfo", "상품 Response DTO allergyInfo");
        ReflectionTestUtils.setField(productResponse, "capacity", "상품 Response DTO capacity");
        ReflectionTestUtils.setField(productResponse, "createdAt", LocalDateTime.now());
        ReflectionTestUtils.setField(productResponse, "updatedAt", LocalDateTime.now());

        return productResponse;
    }

    public static ImageResponse getDummyImageResponse() {
        ImageResponse imageResponse = new ImageResponse();

        ReflectionTestUtils.setField(imageResponse, "name", "name");
        ReflectionTestUtils.setField(imageResponse, "length", 100L);
        ReflectionTestUtils.setField(imageResponse, "imageAddress", "imageAddress");
        ReflectionTestUtils.setField(imageResponse, "imageSequence", 1);

        return imageResponse;
    }

    public static CategoryRetrieveResponse getDummyCategoryResponse() {
        CategoryRetrieveResponse categoryResponse = new CategoryRetrieveResponse();

        ReflectionTestUtils.setField(categoryResponse, "categoryCode", "001");
        ReflectionTestUtils.setField(categoryResponse, "categorizationName", "제품");
        ReflectionTestUtils.setField(categoryResponse, "categoryName", "친환경");
        ReflectionTestUtils.setField(categoryResponse, "sequence", 1);

        return categoryResponse;
    }

    public static LabelRetrieveResponse getDummyLabelResponse() {
        LabelRetrieveResponse labelRetrieveResponse = new LabelRetrieveResponse();

        ReflectionTestUtils.setField(labelRetrieveResponse, "labelNo", 1L);
        ReflectionTestUtils.setField(labelRetrieveResponse, "name", "친환경");

        return labelRetrieveResponse;
    }

    public static PageResult getDummyPageResult() {
        PageResult pageResult = new PageResult();
        ReflectionTestUtils.setField(pageResult, "pageNumber", 0);
        ReflectionTestUtils.setField(pageResult, "pageSize", 1);
        ReflectionTestUtils.setField(pageResult, "totalPages", 1);
        ReflectionTestUtils.setField(pageResult, "data", List.of(getDummyProductResponse()));

        return pageResult;
    }
}
