# Kafka 부트스트랩 서버의 기초 개념

## 1. 부트스트랩 서버의 정의

**부트스트랩 서버(Bootstrap Servers)**는 Kafka 클러스터에 최초로 연결하기 위한 진입점 역할을 하는 브로커 목록입니다. 클라이언트가 Kafka 클러스터에 접속하기 위해 가장 먼저 연결해야 하는 서버들을 지정합니다.

## 2. 부트스트랩 서버의 역할

### 2.1 클러스터 연결의 초기 접점

- Kafka 클라이언트(프로듀서, 컨슈머, 관리 도구)가 클러스터에 연결하는 첫 번째 접촉점
- 클라이언트는 반드시 최소 하나 이상의 부트스트랩 서버 주소를 알고 있어야 함
- 모든 Kafka 브로커가 부트스트랩 서버 역할을 할 수 있음

### 2.2 메타데이터 제공

- 클러스터의 전체 구조(토폴로지)에 대한 정보 제공
- 토픽, 파티션, 브로커 분포 등의 메타데이터 전달
- 클라이언트가 어떤 브로커와 통신해야 하는지 결정하는 데 필요한 정보 제공

### 2.3 클라이언트 부하 분산 시작점

- 초기 연결 이후 클라이언트는 필요한 특정 브로커와 직접 통신
- 클러스터의 변화(브로커 추가/제거)에 대응할 수 있는 기반 제공

## 3. 부트스트랩 서버 작동 메커니즘

### 3.1 연결 및 메타데이터 요청 프로세스

1. **초기 연결**: 클라이언트는 설정된 부트스트랩 서버 목록 중 하나에 연결 시도
2. **메타데이터 요청**: 연결된 부트스트랩 서버에 메타데이터 요청 전송
3. **메타데이터 수신**: 클러스터 구성, 토픽, 파티션, 리더 브로커 정보 등 수신
4. **정보 캐싱**: 클라이언트는 이 메타데이터를 캐시하고 주기적으로 갱신
5. **적절한 브로커 연결**: 메타데이터를 기반으로 필요한 브로커에 직접 연결

### 3.2 메타데이터 갱신 프로세스

- 클라이언트는 주기적으로 메타데이터를 갱신 (`metadata.max.age.ms` 설정으로 제어)
- 연결 오류나 토픽/파티션 정보 불일치 시 자동으로 메타데이터 갱신 시도
- 클러스터 변경 사항(브로커 추가/제거, 파티션 재할당 등)을 감지하여 반영

## 4. 부트스트랩 서버 설정 방법

### 4.1 클라이언트 설정

**Java Producer/Consumer API 설정**:
```java
Properties props = new Properties();
props.put("bootstrap.servers", "broker1:9092,broker2:9092,broker3:9092");
// 기타 설정...
```

**Kafka Connect 설정**:
```properties
bootstrap.servers=broker1:9092,broker2:9092,broker3:9092
```

**Spring Boot 애플리케이션 설정**:
```yaml
spring:
  kafka:
    bootstrap-servers: broker1:9092,broker2:9092,broker3:9092
```

**Kafka Streams 설정**:
```java
Properties streamsConfig = new Properties();
streamsConfig.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "broker1:9092,broker2:9092");
```

### 4.2 Docker 환경 설정

**Docker Compose 예시**:
```yaml
services:
  kafka:
    image: confluentinc/cp-kafka:latest
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:9092
      # 기타 설정...
```

### 4.3 브로커 설정 (서버 측)

**server.properties 파일**:
```properties
# 브로커가 클라이언트에게 알릴 리스너 주소
advertised.listeners=PLAINTEXT://broker1:9092
# 실제 바인딩할 리스너 주소
listeners=PLAINTEXT://0.0.0.0:9092
```

## 5. 부트스트랩 서버 관련 핵심 개념

### 5.1 리스너와 어드버타이즈드 리스너

- **리스너(Listeners)**: 브로커가 실제로 바인딩되는 네트워크 인터페이스와 포트
- **어드버타이즈드 리스너(Advertised Listeners)**: 클라이언트에게 알려주는 브로커 주소
- 특히 컨테이너나 클라우드 환경에서 내부/외부 주소가 다를 때 중요

### 5.2 브로커 ID

- 클러스터 내 각 브로커의 고유 식별자
- 메타데이터의 일부로 클라이언트에게 전달됨
- 브로커 장애 복구와 클러스터 관리에 중요

### 5.3 컨트롤러 브로커

- 클러스터 내 한 브로커가 컨트롤러 역할 수행
- 파티션 리더 선출, 브로커 실패 감지 등 관리 작업 담당
- 부트스트랩 서버와는 별개 개념이지만 메타데이터 관리와 관련

## 6. 실제 시나리오와 예시

### 6.1 단일 브로커 설정

가장 간단한 형태의 설정으로, 개발 환경이나 테스트에 적합합니다.

```properties
bootstrap.servers=localhost:9092
```

### 6.2 다중 브로커 클러스터 설정

고가용성을 위한 프로덕션 환경 설정:

```properties
bootstrap.servers=broker1:9092,broker2:9092,broker3:9092
```

이 설정은 첫 번째 브로커에 연결할 수 없는 경우 두 번째, 세 번째 브로커로 자동으로 연결을 시도합니다.

### 6.3 서로 다른 네트워크 환경 설정

내부 네트워크와 외부 네트워크 모두에서 접근 가능한 설정:

**브로커 설정 (server.properties)**:
```properties
listeners=INTERNAL://0.0.0.0:9092,EXTERNAL://0.0.0.0:9093
advertised.listeners=INTERNAL://kafka1.internal:9092,EXTERNAL://kafka1.public:9093
listener.security.protocol.map=INTERNAL:PLAINTEXT,EXTERNAL:PLAINTEXT
inter.broker.listener.name=INTERNAL
```

**내부 클라이언트 설정**:
```properties
bootstrap.servers=kafka1.internal:9092,kafka2.internal:9092
```

**외부 클라이언트 설정**:
```properties
bootstrap.servers=kafka1.public:9093,kafka2.public:9093
```

## 7. 부트스트랩 서버 관련 모범 사례

### 7.1 고가용성을 위한 권장사항

- **여러 브로커 지정**: 하나의 브로커만 지정하면 단일 장애점이 됨
- **전체 브로커 목록 포함**: 가능하면 모든 브로커를 부트스트랩 서버 목록에 포함
- **주기적 메타데이터 갱신**: 적절한 `metadata.max.age.ms` 값 설정 (기본값 300000ms = 5분)

### 7.2 네트워크 고려사항

- **적절한 타임아웃 설정**: 네트워크 환경에 맞는 `request.timeout.ms` 설정
- **연결 재시도 설정**: `reconnect.backoff.ms`, `reconnect.backoff.max.ms` 적절히 설정
- **리스너 구성**: 내부/외부 네트워크 경계를 명확히 정의

### 7.3 보안 고려사항

- **보안 프로토콜 적용**: 프로덕션 환경에서는 SSL/TLS 또는 SASL 적용 권장
- **인증 설정**: 필요시 클라이언트 인증 메커니즘 구성
- **네트워크 분리**: 내부 트래픽과 외부 트래픽 분리

## 8. 흔한 문제와 해결 방법

### 8.1 연결 문제

**증상**: `Connection refused` 또는 `Connection timed out` 오류

**해결 방법**:
- 브로커 실행 여부 확인
- 네트워크 연결성 확인 (방화벽, 보안 그룹 설정)
- 리스너 설정 확인 (`advertised.listeners` 설정이 클라이언트에서 접근 가능한지)

### 8.2 메타데이터 관련 문제

**증상**: `Leader not available` 또는 `Topic not found` 오류

**해결 방법**:
- 토픽 존재 여부 확인
- 브로커 클러스터 상태 확인
- 메타데이터 갱신 주기 확인 및 수동 갱신 시도

### 8.3 컨테이너/클라우드 환경 문제

**증상**: 컨테이너 내부에서는 연결되지만 외부에서 연결 실패

**해결 방법**:
- `advertised.listeners` 설정이 외부에서 접근 가능한 주소인지 확인
- 네트워크 인터페이스와 포트 매핑 확인
- Docker, Kubernetes의 경우 서비스 설정 확인

## 9. 부트스트랩 서버와 관련된 고급 개념

### 9.1 메타데이터 갱신 메커니즘

- 토픽 생성, 삭제 및 변경 시 메타데이터 업데이트 흐름
- 메타데이터 전파 지연 및 영향
- ZooKeeper와 메타데이터 관리 관계 (KRaft 모드에서는 변경됨)

### 9.2 KRaft 모드 (ZooKeeper 없는 Kafka)

- Kafka 2.8 이상에서 지원되는 ZooKeeper 없는 모드
- 메타데이터 관리 방식 변화
- 부트스트랩 서버의 역할 변화

### 9.3 동적 브로커 추가/제거

- 브로커 추가/제거 시 부트스트랩 서버 설정 관리
- 롤링 재시작 전략
- 무중단 클러스터 확장 전략

## 10. 결론

부트스트랩 서버는 Kafka 클러스터 연결의 출발점으로, 클라이언트가 클러스터 구조를 파악하고 적절한 브로커와 통신할 수 있도록 하는 필수적인 요소입니다. 안정적인 Kafka 시스템을 구축하기 위해서는 부트스트랩 서버의 개념을 정확히 이해하고 적절하게 설정하는 것이 중요합니다.

여러 브로커를 부트스트랩 서버로 지정하여 고가용성을 확보하고, 네트워크 환경에 맞게 리스너를 구성하며, 보안 요구사항을 고려한 설정을 통해 안정적인 Kafka 클러스터를 운영할 수 있습니다.