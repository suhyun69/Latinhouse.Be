# Backend Architecture Reference

Spring Boot + Hexagonal Architecture 기반 백엔드 프로젝트 구조 레퍼런스.
새 프로젝트에서 동일한 구조를 사용할 때 이 문서를 참고한다.

---

## Tech Stack

| 항목 | 버전/선택                               |
|------|-------------------------------------|
| Java | 25                                  |
| Spring Boot | 4.0.6                               |
| Build | Gradle                              |
| DB (dev) | H2 in-memory                        |
| ORM | Spring Data JPA + Hibernate         |
| 인증 | JWT (jjwt 0.11.5)                   |
| 유효성 검사 | Spring Validation (`@Valid`)        |
| API 문서 | springdoc-openapi 2.2.0 (Swagger UI) |
| 보일러플레이트 | Lombok                              |

### build.gradle 의존성

```gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '4.0.4'
    id 'io.spring.dependency-management' version '1.1.7'
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    // JWT
    implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
    runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.11.5'
    runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.11.5'

    // Swagger
    implementation group: 'org.springdoc', name: 'springdoc-openapi-starter-webmvc-ui', version: '2.2.0'

    // Spring
    implementation 'org.springframework.boot:spring-boot-h2console'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.springframework.boot:spring-boot-starter-webmvc'

    // Lombok
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'

    // Dev
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    runtimeOnly 'com.h2database:h2'

    // Test
    testImplementation 'org.springframework.boot:spring-boot-starter-data-jpa-test'
    testImplementation 'org.springframework.boot:spring-boot-starter-security-test'
    testImplementation 'org.springframework.boot:spring-boot-starter-validation-test'
    testImplementation 'org.springframework.boot:spring-boot-starter-webmvc-test'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

tasks.named('test') {
    useJUnitPlatform()
}
```

---

## application.yml

```yaml
custom:
  jwt:
    secretKey: ${JWT_SECRET}  # 32자 이상 필수

spring:
  application:
    name: backend
  profiles:
    active: local
    include: secret
  jpa:
    hibernate:
      ddl-auto: create
    properties:
      hibernate:
        show-sql: true
        format_sql: true
        use_sql_comments: true

springdoc:
  packages-to-scan:
    - com.example.{project}.{domain}.adapter.in.web
  default-consumes-media-type: application/json;charset=UTF-8
  default-produces-media-type: application/json;charset=UTF-8
  swagger-ui:
    path: /swagger-ui.html
    tags-sorter: alpha
    operations-sorter: alpha
  api-docs:
    path: /v3/api-docs
    groups:
      enabled: true
  cache:
    disabled: true
```

`application-secret.yml`은 gitignore에 추가하고, `.default` 템플릿 파일을 함께 커밋한다.

---

## 패키지 구조

```
com.example.{project}/
  config/
    SecurityConfig.java          ← PasswordEncoder, AuthenticationManager, base filter chain
    ApiSecurityConfig.java       ← /api/** JWT filter chain
    OpenApiConfig.java           ← Swagger 설정
  global/
    exception/
      ErrorCode.java             ← 에러코드 enum
      CustomException.java       ← 기본 커스텀 예외
      ErrorResponse.java         ← 에러 응답 DTO
      FieldErrorResponse.java    ← 필드 에러 DTO
      GlobalExceptionHandler.java← @RestControllerAdvice
  security/
    JwtUtil.java                 ← 토큰 생성/검증
    JwtRequestFilter.java        ← OncePerRequestFilter
    CustomUserDetailsService.java← UserDetailsService 구현
    JwtAuthenticationEntryPoint.java
  {domain}/
    adapter/
      in/web/
        Api{Version}{Domain}Controller.java
        request/
          Create{Domain}WebRequest.java
          Update{Domain}WebRequest.java
        response/
          {Domain}WebResponse.java
      out/persistence/
        {Domain}PersistenceAdapter.java
        entity/
          {Domain}JpaEntity.java
        mapper/
          {Domain}Mapper.java
        repository/
          {Domain}Repository.java
    application/service/
      {Domain}Service.java
    domain/
      {Domain}.java              ← 순수 도메인 모델
    port/
      in/
        Create{Domain}UseCase.java
        Find{Domain}UseCase.java
        Update{Domain}UseCase.java
        Delete{Domain}UseCase.java
        request/
          Create{Domain}AppRequest.java
          Update{Domain}AppRequest.java
        response/
          {Domain}AppResponse.java
      out/
        Create{Domain}Port.java
        Read{Domain}Port.java
        Update{Domain}Port.java
        Delete{Domain}Port.java
```

---

## 헥사고날 아키텍처 핵심 원칙

### 의존성 방향

```
Controller (adapter/in)
    ↓ depends on
UseCase Interface (port/in)
    ↑ implements
Service (application/service)
    ↓ depends on
Port Interface (port/out)
    ↑ implements
PersistenceAdapter (adapter/out)
```

- 도메인과 포트는 외부 의존성(JPA, Spring 등)에 의존하지 않는다.
- Service는 절대 JpaEntity를 직접 참조하지 않는다.
- Controller는 절대 JpaEntity를 반환하지 않는다.

### DTO 계층 분리

| 계층 | 클래스 위치 | 역할 |
|------|-------------|------|
| HTTP 입력 | `adapter/in/web/request/` | `@Valid` 형식 검증 |
| 앱 입력 | `port/in/request/` | 타입 변환, 비즈니스 검증 |
| 도메인 | `domain/` | 순수 비즈니스 모델 |
| 영속성 | `adapter/out/persistence/entity/` | `@Entity`, JPA 어노테이션 |
| 앱 응답 | `port/in/response/` | 도메인 → 응답 변환 |
| HTTP 응답 | `adapter/in/web/response/` | JSON 직렬화 최종 형태 |

---

## 각 레이어별 코드 패턴

### Controller

```java
@RestController
@RequestMapping("/api/v1/{domain}")
@Tag(name = "{Domain}", description = "{Domain} API Document")
@RequiredArgsConstructor
public class ApiV1{Domain}Controller {

    private final Create{Domain}UseCase create{Domain}UseCase;
    private final Find{Domain}UseCase find{Domain}UseCase;

    @PostMapping
    @Operation(summary = "Create {Domain}")
    public ResponseEntity<{Domain}WebResponse> add(@Valid @RequestBody Create{Domain}WebRequest webReq) {
        Create{Domain}AppRequest appReq = Create{Domain}AppRequest.from(webReq);
        {Domain}WebResponse response = new {Domain}WebResponse(create{Domain}UseCase.create(appReq));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find {Domain}")
    public ResponseEntity<{Domain}WebResponse> findById(@PathVariable("id") Long id) {
        {Domain}WebResponse response = new {Domain}WebResponse(find{Domain}UseCase.findById(id));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete {Domain}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        delete{Domain}UseCase.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
```

### WebRequest DTO

```java
@Getter
@NoArgsConstructor
public class Create{Domain}WebRequest {

    @NotBlank
    private String title;

    @NotNull
    @Pattern(regexp = "^(A|B)$", message = "must be 'A' or 'B'")
    private String type;

    @NotBlank
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    private String date;  // "yyyy-MM-dd"
}
```

### AppRequest DTO

```java
@Value
@Builder
public class Create{Domain}AppRequest {

    String title;
    String type;
    LocalDate date;

    // WebRequest → AppRequest 변환 + 비즈니스 검증
    public static Create{Domain}AppRequest from(Create{Domain}WebRequest webReq) {
        LocalDate date;
        try {
            date = LocalDate.parse(webReq.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format.");
        }

        return Create{Domain}AppRequest.builder()
                .title(webReq.getTitle())
                .type(webReq.getType())
                .date(date)
                .build();
    }
}
```

### UseCase Interface (port/in)

```java
public interface Create{Domain}UseCase {
    {Domain}AppResponse create(Create{Domain}AppRequest appReq);
}

public interface Find{Domain}UseCase {
    {Domain}AppResponse findById(Long id);
    List<{Domain}AppResponse> findAll();
}
```

### Output Port Interface (port/out)

```java
public interface Create{Domain}Port {
    {Domain} create({Domain} domain);
}

public interface Read{Domain}Port {
    Optional<{Domain}> findById(Long id);
    List<{Domain}> findAll();
}
```

### Domain 모델

```java
@Getter
@Builder
public class {Domain} {
    private final Long id;
    private final String title;
    // JPA 어노테이션, Spring 어노테이션 일체 없음
    // 순수 Java 객체
}
```

### Service

```java
@Service
@RequiredArgsConstructor
public class {Domain}Service implements
        Create{Domain}UseCase,
        Find{Domain}UseCase,
        Update{Domain}UseCase,
        Delete{Domain}UseCase {

    private final Create{Domain}Port create{Domain}Port;
    private final Read{Domain}Port read{Domain}Port;

    @Override
    public {Domain}AppResponse create(Create{Domain}AppRequest appReq) {
        // 현재 인증된 사용자 이메일
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        {Domain} domain = {Domain}.builder()
                .title(appReq.getTitle())
                .build();

        return new {Domain}AppResponse(create{Domain}Port.create(domain));
    }

    @Override
    public {Domain}AppResponse findById(Long id) {
        return read{Domain}Port.findById(id)
                .map({Domain}AppResponse::new)
                .orElseThrow(() -> new CustomException(ErrorCode.{DOMAIN}_NOT_FOUND));
    }
}
```

### AppResponse DTO

```java
@Getter
public class {Domain}AppResponse {
    private final Long id;
    private final String title;

    public {Domain}AppResponse({Domain} domain) {
        this.id = domain.getId();
        this.title = domain.getTitle();
    }
}
```

### WebResponse DTO

```java
@Getter
public class {Domain}WebResponse {
    private final Long id;
    private final String title;

    public {Domain}WebResponse({Domain}AppResponse appResponse) {
        this.id = appResponse.getId();
        this.title = appResponse.getTitle();
    }
}
```

### JpaEntity

```java
@Entity
@Table(name = "{domain}")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class {Domain}JpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private SomeEnum type;

    // 1:N 관계
    @OneToMany(mappedBy = "{domain}", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<{Domain}ChildJpaEntity> children;

    // 단순 컬렉션
    @ElementCollection
    @CollectionTable(name = "{domain}_items", joinColumns = @JoinColumn(name = "{domain}_id"))
    @Column(name = "item")
    private List<String> items;
}
```

### Mapper

```java
@Component
public class {Domain}Mapper {

    public {Domain}JpaEntity mapToJpaEntity({Domain} domain) {
        return {Domain}JpaEntity.builder()
                .id(domain.getId())
                .title(domain.getTitle())
                .build();
    }

    public {Domain} mapToDomainEntity({Domain}JpaEntity jpaEntity) {
        return {Domain}.builder()
                .id(jpaEntity.getId())
                .title(jpaEntity.getTitle())
                .build();
    }
}
```

### PersistenceAdapter

```java
@Component
@RequiredArgsConstructor
public class {Domain}PersistenceAdapter implements
        Create{Domain}Port,
        Read{Domain}Port,
        Update{Domain}Port,
        Delete{Domain}Port {

    private final {Domain}Mapper {domain}Mapper;
    private final {Domain}Repository {domain}Repository;

    @Override
    public {Domain} create({Domain} domain) {
        return {domain}Mapper.mapToDomainEntity(
                {domain}Repository.save({domain}Mapper.mapToJpaEntity(domain)));
    }

    @Override
    public Optional<{Domain}> findById(Long id) {
        return {domain}Repository.findById(id)
                .map({domain}Mapper::mapToDomainEntity);
    }

    @Override
    public List<{Domain}> findAll() {
        return {domain}Repository.findAll().stream()
                .map({domain}Mapper::mapToDomainEntity)
                .toList();
    }

    @Override
    public {Domain} update({Domain} domain) {
        return {domain}Mapper.mapToDomainEntity(
                {domain}Repository.save({domain}Mapper.mapToJpaEntity(domain)));
    }

    @Override
    public void delete(Long id) {
        {domain}Repository.deleteById(id);
    }
}
```

---

## 보안 설정

### SecurityConfig.java (전역)

```java
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**")
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin) // H2 콘솔용
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/h2-console/**"
                        ).permitAll()
                        .anyRequest().denyAll() // 나머지 전부 차단, ApiSecurityConfig가 /api/**를 처리
                );

        return http.build();
    }
}
```

### ApiSecurityConfig.java (/api/**)

```java
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApiSecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/*/auth/**").permitAll()
                        .requestMatchers("/api/*/signup/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/*/profile/**", "/api/*/profile").permitAll()
                        // 도메인별 공개 엔드포인트 추가 가능
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
```

### JwtUtil.java

```java
@Component
public class JwtUtil {

    @Value("${custom.jwt.secretKey}")
    private String SECRET_KEY;  // application.yml에서 주입, 32자 이상 필수

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10시간
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7)) // 7일
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
```

### JwtRequestFilter.java

```java
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt = extractBearerToken(request);
        String username = null;

        if (jwt != null) {
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractBearerToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
```

---

## 예외 처리

### ErrorCode.java

```java
@Getter
public enum ErrorCode {
    // 도메인별 에러 코드 추가
    USER_NOT_FOUND("사용자를 찾을 수 없습니다", HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_EXISTS("이미 존재하는 이메일입니다", HttpStatus.CONFLICT),
    PROFILE_NOT_FOUND("프로필을 찾을 수 없습니다", HttpStatus.NOT_FOUND),
    PROFILE_ALREADY_EXISTS("이미 프로필이 존재합니다", HttpStatus.CONFLICT),
    LESSON_NOT_FOUND("레슨을 찾을 수 없습니다", HttpStatus.NOT_FOUND),
    INSTRUCTOR_ONLY("강사만 레슨을 등록할 수 있습니다", HttpStatus.FORBIDDEN),

    // 공통
    INVALID_REQUEST("잘못된 요청입니다", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("", HttpStatus.UNAUTHORIZED);

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
```

### CustomException.java

```java
@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
```

### ErrorResponse.java

```java
@Getter
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String message;
    private final List<FieldErrorResponse> fieldErrors;

    public ErrorResponse(ErrorCode code) {
        this.status = code.getStatus().value();
        this.error = code.getStatus().name();
        this.message = code.getMessage();
        this.fieldErrors = null;
    }

    public ErrorResponse(ErrorCode code, List<FieldErrorResponse> fieldErrors) {
        this.status = code.getStatus().value();
        this.error = code.getStatus().name();
        this.message = code.getMessage();
        this.fieldErrors = fieldErrors;
    }
}
```

### FieldErrorResponse.java

```java
@Getter
@AllArgsConstructor
public class FieldErrorResponse {
    private String field;
    private String message;
}
```

### GlobalExceptionHandler.java

```java
@Hidden  // Swagger에서 숨김
@RestControllerAdvice
public class GlobalExceptionHandler {

    // @Valid 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        List<FieldErrorResponse> fieldErrors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new FieldErrorResponse(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ErrorCode.INVALID_REQUEST, fieldErrors));
    }

    // 커스텀 비즈니스 예외
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorCode code = e.getErrorCode();
        return ResponseEntity
                .status(code.getStatus())
                .body(new ErrorResponse(code));
    }

    // 그 외 예상치 못한 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ErrorCode.INVALID_REQUEST));
    }
}
```

---

## 에러 응답 포맷

```json
// 비즈니스 예외 (CustomException)
{
    "timestamp": "2026-04-21T10:30:45.123",
    "status": 404,
    "error": "NOT_FOUND",
    "message": "레슨을 찾을 수 없습니다",
    "fieldErrors": null
}

// 유효성 검증 예외 (@Valid 실패)
{
    "timestamp": "2026-04-21T10:30:45.123",
    "status": 400,
    "error": "BAD_REQUEST",
    "message": "잘못된 요청입니다",
    "fieldErrors": [
        { "field": "title", "message": "must not be blank" },
        { "field": "genre", "message": "must match 'S|B'" }
    ]
}
```

---

## 인증 흐름 요약

```
# 로그인
POST /api/v1/auth/login
→ BCrypt 비밀번호 검증
→ JWT generateToken(email)         → Access Token (10시간)
→ JWT generateRefreshToken(email)  → Refresh Token (7일)
→ Refresh Token을 DB에 저장
→ 클라이언트에 { accessToken, refreshToken } 반환

# 인증 요청
Authorization: Bearer <accessToken>
→ JwtRequestFilter
  → extractUsername() → email
  → loadUserByUsername(email)
  → validateToken()
  → SecurityContextHolder에 Authentication 설정
→ Service에서: SecurityContextHolder.getContext().getAuthentication().getName() → email

# 토큰 갱신
POST /api/v1/auth/refresh  (refresh token 전달)
→ DB 저장된 token과 대조
→ 새 Access Token 발급

# 로그아웃
POST /api/v1/auth/logout
→ DB에서 Refresh Token 삭제
→ Access Token은 자연 만료 (클라이언트 측 폐기)
```

---

## 새 도메인 추가 체크리스트

새 도메인 (예: `payment`) 추가 시 생성 순서:

1. `domain/Payment.java` — `@Getter @Builder`, 순수 모델
2. `port/out/` — `CreatePaymentPort`, `ReadPaymentPort` 등 인터페이스
3. `port/in/` — `CreatePaymentUseCase` 등 인터페이스
4. `port/in/request/` — `CreatePaymentAppRequest` (`@Value @Builder`, `from()` 정적 팩토리)
5. `port/in/response/` — `PaymentAppResponse` (도메인 받는 생성자)
6. `adapter/out/persistence/entity/` — `PaymentJpaEntity` (`@Entity`, `@Data @Builder`)
7. `adapter/out/persistence/mapper/` — `PaymentMapper` (양방향 변환)
8. `adapter/out/persistence/repository/` — `PaymentRepository extends JpaRepository`
9. `adapter/out/persistence/PaymentPersistenceAdapter` — 포트 구현체
10. `application/service/PaymentService` — UseCase 구현체
11. `adapter/in/web/request/` — `CreatePaymentWebRequest` (`@Valid` 어노테이션)
12. `adapter/in/web/response/` — `PaymentWebResponse`
13. `adapter/in/web/ApiV1PaymentController` — `@RestController`
14. `global/exception/ErrorCode`에 에러 코드 추가
15. `ApiSecurityConfig`에 공개 엔드포인트 필요 시 추가
16. `application.yml` springdoc `packages-to-scan`에 컨트롤러 패키지 추가

---

## 코딩 컨벤션

- Lombok: 도메인은 `@Getter @Builder`, JPA Entity는 `@Data @Builder @NoArgsConstructor @AllArgsConstructor`, 서비스/어댑터는 `@RequiredArgsConstructor`
- AppRequest는 `@Value @Builder` (Lombok) — 불변 객체
- 생성자 주입만 사용 (`@RequiredArgsConstructor` + `private final`)
- Controller URL 패턴: `/api/v{version}/{domain}` (소문자)
- 리소스 식별자: `no` (Long, auto increment PK)
- 날짜 입력 포맷: `yyyy-MM-dd` (String), 시간: `HH:mm` (String) → AppRequest에서 `LocalDateTime`으로 변환
- Enum 코드값: 짧은 대문자 코드 사용 (예: `S`/`B`, `GN`/`HD`), `of(String code)` 팩토리 메서드로 파싱
- 랜덤 ID가 필요한 자식 엔티티: `SecureRandom` + 8자 alphanumeric (Service에서 생성)
