# Market GG Client

Market GG 클라이언트는 애플리케이션 이용에 필요한 정보를 각 서버에 요청하고, 응답받은 정보를 사용자 인터페이스로 제공합니다.

# Getting Started

해당 프로젝트를 다운로드하거나 `git clone` 을 통해 실행 환경을 구성한 뒤, 다음과 같은 명령어를 실행합니다.

```
./gradlew bootRun
```

# Project Architecture

![marketgg-architecture-v1-0-3](https://user-images.githubusercontent.com/38161720/183289639-452bfe2d-792e-4260-a10a-652a87b62928.png)

# Features

### [@김정민](https://github.com/Jungmin-Kim228)

- **카테고리 관리** (Co-authored-by: [@박세완](https://github.com/psw4887))
    - 관리자의 카테고리 등록
    - 관리자의 카테고리 전체 목록 조회
    - 관리자의 카테고리 수정
    - 관리자의 카테고리 삭제
- **쿠폰 관리**
    - 관리자의 쿠폰 목록 조회
    - 관리자의 쿠폰 등록
    - 관리자의 쿠폰 수정
    - MY GG에서 보유 쿠폰 목록 조회
    - MY GG에서 쿠폰 등록

### [@김훈민](https://github.com/bunsung92)

- **사용자 관리**
    - 회원가입
    - 회원정보 수정
    - 회원탈퇴
- **배송 관리**
    - 배송지 추가
    - 배송지 수정
    - 배송지 삭제
    - 배송지 전체 조회

### [@민아영](https://github.com/coalong)

- **상품 관리**
    - 상품 등록 (Co-authored-by: [@조현진](https://github.com/Com-Sun))
    - 상품 등록 시 Toast editor Api 연동 및 사진을 MultipartForm 으로 통신 구현
    - 상품 조회 시 Toast viewer Api 연동
- **상품 문의 관리**
    - 상품 상세 페이지 내 상품 문의 작성
    - 상품 상세 페이지 내 상품 문의 수정
    - 상품 상세 페이지 내 상품 문의 삭제
    - 상품 상세 페이지 내 상품 문의 조회
    - 관리자가 상품 문의에 대한 답글 작성
    - MY GG 의 상품 문의에서 회원이 작성한 모든 문의 조회

- **쿠폰 관리**

### [@박세완](https://github.com/psw4887)

- **카테고리 관리** (Co-authored-by: [@김정민](https://github.com/Jungmin-Kim228))
    - 관리자의 카테고리 등록
    - 관리자의 카테고리 전체 목록 조회
    - 관리자의 카테고리 수정
    - 관리자의 카테고리 삭제
- **GG 패스**
    - GG 패스 회원의 갱신일 확인
    - 회원의 GG 패스 구독 신청
    - 회원의 GG 패스 해지 신청
- **라벨 관리**
    - 관리자의 라벨 등록
    - 관리자의 라벨 삭제
    - 관리자의 라벨 전체 목록 조회
- **찜 관리**
    - 상품 상세 페이지 내 찜 등록 시 해당 상품 위시리스트 이동
    - 상품 상세 페이지 내 찜 삭제 시 해당 상품 위시리스트 제외
    - MY GG의 찜 목록에서 찜 삭제 시 해당 상품 위시리스트 제외
- **고객센터 관리**
    1. 공지사항
        - 관리자의 공지사항 등록
        - 회원의 공지사항 전체 목록 조회
        - 회원의 공지사항 상세 조회
        - 관리자의 공지사항 수정
        - 관리자의 공지사항 삭제
    2. 1:1문의
        - 회원의 1:1 문의 등록
        - 관리자의 1:1 문의 전체 조회
        - 회원의 자신이 등록한 1:1 문의 조회
        - 회원의 자신이 등록한 1:1 문의 삭제
    3. FAQ
        - 관리자의 FAQ 등록
        - 회원의 FAQ 전체 목록 조회
        - 회원의 FAQ 상세 조회
        - 관리자의 FAQ 수정
        - 관리자의 FAQ 삭제

### [@윤동열](https://github.com/eastheat10)

- **사용자 관리**
    - 로그인
    - 권한 관리
    - 모든 요청에 대해 JWT 관리
- **장바구니 관리**
    - 장바구니 상품 추가, 조회, 수량 수정, 상품 삭제

### [@이제훈](https://github.com/corock)

- **공통**
    - UI 공통 컴포넌트 작성
    - 레이아웃 디자인
- **상품 관리**
    - 관리자 상품 문의 답변 페이지 작성
    - 관리자 상품 재고 전체 조회 페이지 작성
    - 관리자 상품 재고 수정 폼 페이지 작성
    - 관리자 상품 매출 조회 페이지 작성
- **주문 관리**
    - 주문서 작성 및 페이지 추가
    - 주문 내역 조회
    - 주문 내역 상세 조회
    - 관리자 주문 전체 조회
- **결제 관리**
    - 결제 진행 및 페이지 작성
    - 결제 완료 및 페이지 작성
    - 결제(거래) 내역 조회

### [@조현진](https://github.com/Com-Sun)

- **상품 관리**
    - 상품 등록 (Co-authored-by: [@민아영](https://github.com/coalong))
    - 상품 조회
    - 상품 수정
    - 상품 삭제
- **포인트 관리**
    - 관리자의 포인트 내역 조회
    - 회원의 포인트 내역 조회
- **후기 관리**
    - 후기 등록
    - 후기 조회
    - 후기 수정
    - 후기 삭제

## Technical Issue

### 상품 관리

상품 등록 시 데이터베이스에 어떻게 저장할 것인가?   
→ `MultipartForm` + Toast UI Editor 를 사용한 상품 등록 구현 ([@민아영](https://github.com/coalong))

에디터를 사용하여 사진 첨부를 하면 Blob 형식이다.  
여러 장의 사진 + 글이 섞여 있는 경우 이걸 서버에서 어떻게 처리할까? (순서, 해상도, 용량, etc…)  
→ Toast Editor 기능 고도화 ([@조현진](https://github.com/Com-Sun)) + 스토리지 연동

TUI 내의 hook 옵션을 사용하여 비동기로 Shop 서버 요청 → 클라우드에 사진 저장 → 해당 사진의 URL 만 반환  
즉, 데이터베이스의 `content` 컬럼엔 `String` 형태의 text + URL 만 저장된다.

![file-upload-sequence](https://user-images.githubusercontent.com/38161720/183850177-712bf2d5-0197-434c-beea-f316b324dd1c.png)

### 상품 문의 관리

상품 문의에 대하여 관리자가 답글 등록 시 비동기로 처리

### 찜 관리

찜 등록/제거 시 매번 페이지를 새로 불러올 것인가?  
→ 찜 등록/제거 AJAX 를 통한 비동기 처리 완료

## Tech Stack

### Build Tools

![Gradle](https://img.shields.io/badge/Gradle-02303A?style=flat&logo=Gradle&logoColor=white)

### Datebases

![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat&logo=MySQL&logoColor=white)

### DevOps

![NHN Cloud](https://img.shields.io/badge/-NHN%20Cloud-blue?style=flat&logo=iCloud&logoColor=white)
![GitHubActions](https://img.shields.io/badge/GitHub%20Actions-2088FF?style=flat&logo=GitHubActions&logoColor=white)
![SonarQube](https://img.shields.io/badge/SonarQube-4E98CD?style=flat&logo=SonarQube&logoColor=white)

### Frameworks

![SpringBoot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=SpringBoot&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=flat&logo=Swagger&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-7952B3?style=flat&logo=Bootstrap&logoColor=white)

### Languages

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white&style=flat)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=CSS3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=JavaScript&logoColor=white)

### Prototyping

![Figma](https://img.shields.io/badge/Figma-F24E1E?style=flat&logo=Figma&logoColor=white)

### Template Engine

![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=flat&logo=Thymeleaf&logoColor=white)

### Libraries

![FontAwesome](https://img.shields.io/badge/Font%20Awesome-528DD7?style=flat&logo=FontAwesome&logoColor=white)
![jQuery](https://img.shields.io/badge/jQuery-0769AD?style=flat&logo=jQuery&logoColor=white)
![Bootstrap](https://img.shields.io/badge/BootStrap-7952B3?style=flat&logo=Bootstrap&logoColor=white)

### Testing Tools

![Junit5](https://img.shields.io/badge/Junit5-25A162?style=flat&logo=Junit5&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=flat&logo=Postman&logoColor=white)

### Version Control System

![Git](https://img.shields.io/badge/Git-F05032?style=flat&logo=Git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=white)
![Sourcetree](https://img.shields.io/badge/Sourcetree-0052CC?style=flat&logo=Sourcetree&logoColor=white)

**Git Flow 전략 채용**

![gitflow-workflow](https://user-images.githubusercontent.com/54662174/183854876-aa8c7e55-ce19-4cbf-ba7c-8921ab7a8ab8.png)

**브랜치 구분**

- `main`: 배포 시 사용
- `develop`: 개발 단계가 끝난 부분에 대해 Merge 내용 포함
- `feature/xxx`: 기능 개발 단계
- `hotfix/xxx`: Merge 후 발생한 버그 및 수정사항 반영 시 사용

**`git rebase` 활용**

작업 중인 `feature/xxx` 브랜치와 지속적 통합으로 인한 `develop` 브랜치 동기화를 효과적으로 하기 위함

![git-rebase](https://user-images.githubusercontent.com/54662174/183855384-0dd2d1ee-0e77-4f23-84af-d845a0fb3ecc.png)

**칸반 보드 활용**

- 협업 도구 *Dooray*! 적극 활용
- *GitHub* 에서 제공하는 *Issues* 와 *Pull Requests* 활용하여 커밋 메시지 컨벤션 확립

*Dooray! Planning*

![dooray-scrum-planning](https://user-images.githubusercontent.com/54662174/183858820-1872e8d9-a254-4a47-8f54-c5f7636fb540.png)

*GitHub Projects*

![marketgg-projects](https://user-images.githubusercontent.com/54662174/183858484-7ff5c9f9-c5c9-4937-9e6f-d4efdacb0e26.png)

## Contributors

<a href="https://github.com/nhn-on7/marketgg-shop/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=nhn-on7/marketgg-shop"  alt="marketgg-client-contributors" />
</a>

## License

Market GG is released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).
