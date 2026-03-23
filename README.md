# Spring Boot JWT 인증 템플릿

Spring Boot 3.4.3 기반의 JWT 인증/인가 템플릿 프로젝트입니다.

## 기술 스택

- **Java 21** / **Spring Boot 3.4.3**
- **Spring Security** — JWT 기반 Stateless 인증
- **Spring Data JPA** / **MySQL**
- **JJWT 0.12.5** — JWT 토큰 생성 및 검증
- **Springdoc OpenAPI** — Swagger UI 자동 생성
- **Lombok**
- **Gradle**

## 템플릿 사용 방법

### 1. 레포지토리 복제

```bash
git clone https://github.com/your-username/template.git my-project
cd my-project
```

### 2. 패키지명 변경

`com.gbsw.template`을 본인의 프로젝트 패키지명으로 변경합니다:

- `build.gradle`의 `group` 수정
- `src/main/java/com/gbsw/template/` 디렉토리를 본인의 패키지 경로로 이동
- 모든 Java 파일의 `package` 선언과 `import` 경로 수정
- `application.yml` 등 설정 파일에서 패키지 참조 수정

### 3. 환경 설정

```yaml
DB_NAME: { your_database }
DB_USERNAME: { your_username }
DB_PASSWORD: { your_password }
JWT_SECRET: { your-base64-encoded-secret-key }
JWT_ACCESS_EXPIRATION: 1800000    # 30분
JWT_REFRESH_EXPIRATION: 604800000 # 7일
```

### 4. 새 도메인 추가 예시

`domain/` 패키지 아래에 기능별 모듈을 추가합니다:

```
domain/
└── post/
    ├── controller/
    │   └── PostController.java
    ├── service/
    │   └── PostService.java
    ├── repository/
    │   └── PostRepository.java
    ├── entity/
    │   └── PostEntity.java
    └── dto/
        ├── PostRequest.java
        └── PostResponse.java
```

- 모든 API 응답은 `ApiResponse<T>`로 래핑합니다
- 에러는 `ErrorCode` enum에 추가 후 `CustomException`으로 throw합니다
- 인증이 필요 없는 엔드포인트는 `SecurityConfig`의 `PUBLIC_URLS`에 추가합니다

## 패키지 구조

```
com.gbsw.template/
├── domain/
│   ├── auth/          # 인증 (회원가입, 로그인, 토큰 갱신)
│   ├── health/        # 헬스 체크
│   └── user/          # 사용자 엔티티 및 리포지토리
└── global/
    ├── common/        # ApiResponse<T> 공통 응답 래퍼
    ├── exception/     # ErrorCode, CustomException, GlobalExceptionHandler
    └── security/      # JWT 필터, SecurityConfig, UserDetailsService
```

## API 엔드포인트

### 인증 (`/api/auth`)

| 메서드 | 경로 | 설명 |
|--------|------|------|
| POST | `/api/auth/signup` | 회원가입 |
| POST | `/api/auth/login` | 로그인 (AccessToken + RefreshToken 발급) |
| POST | `/api/auth/refresh` | 토큰 갱신 |

### 기타

| 메서드 | 경로 | 설명 |
|--------|------|------|
| GET | `/api/health` | 서버 상태 확인 |

## 빌드 및 실행

```bash
# 빌드 (테스트 제외)
./gradlew build -x test

# 앱 실행
./gradlew bootRun

# 전체 테스트
./gradlew test

# 클린 빌드
./gradlew clean build
```

## 인증 흐름

1. `/api/auth/signup`으로 회원가입
2. `/api/auth/login`으로 로그인하여 AccessToken과 RefreshToken 발급
3. 인증이 필요한 API 호출 시 `Authorization: Bearer <accessToken>` 헤더 포함
4. AccessToken 만료 시 `/api/auth/refresh`로 갱신

## API 문서

앱 실행 후 Swagger UI에서 API를 확인할 수 있습니다:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API Docs: `http://localhost:8080/v3/api-docs`
