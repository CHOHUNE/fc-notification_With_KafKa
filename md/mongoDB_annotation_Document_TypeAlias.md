# Spring Data MongoDB의 @Document와 @TypeAlias 어노테이션

## @Document 어노테이션

`@Document` 어노테이션은 Spring Data MongoDB에서 자바 클래스를 MongoDB 문서(Document)에 매핑하기 위해 사용되는 핵심 어노테이션입니다.

### 기본 정보
- **패키지**: `org.springframework.data.mongodb.core.mapping`
- **대상**: 클래스 레벨
- **목적**: 자바 객체를 MongoDB 컬렉션에 저장할 문서로 지정

### 주요 속성
| 속성 | 타입 | 설명 | 기본값 |
|------|------|------|--------|
| `collection` | String | 문서가 저장될 MongoDB 컬렉션 이름 | 클래스 이름을 카멜케이스로 변환한 값 |
| `language` | String | 텍스트 검색에 사용할 언어 | "" |
| `collation` | String | 문서의 콜레이션(정렬 규칙) 설정 | "" |

### 사용 예시
```java
@Document("notifications")
public abstract class Notification {
    // 필드와 메서드
}

// 또는
@Document(collection = "notifications")
public abstract class Notification {
    // 필드와 메서드
}
```

### 작동 방식
1. 해당 클래스의 인스턴스가 MongoDB에 저장될 때 지정된 컬렉션에 문서로 저장됨
2. 특별히 컬렉션 이름을 지정하지 않으면 클래스 이름을 기반으로 자동 생성
    - 예: `UserProfile` 클래스 → `userProfile` 컬렉션에 저장
3. 문서의 ID는 보통 `@Id` 어노테이션으로 지정된 필드에 매핑됨

## @TypeAlias 어노테이션

`@TypeAlias` 어노테이션은 MongoDB에 저장될 때 클래스 타입 정보를 간소화하기 위해 사용되는 어노테이션입니다.

### 기본 정보
- **패키지**: `org.springframework.data.annotation`
- **대상**: 클래스 레벨
- **목적**: 다형성 지원을 위한 타입 식별자 제공

### 주요 속성
| 속성 | 타입 | 설명 | 기본값 |
|------|------|------|--------|
| `value` | String | 클래스의 별칭(alias) | - (필수 값) |

### 사용 예시
```java
@TypeAlias("CommentNotification")
public class CommentNotification extends Notification {
    // 필드와 메서드
}
```

### 작동 방식
1. MongoDB 문서에 클래스 타입 정보를 저장할 때 패키지 경로를 포함한 전체 클래스명 대신 지정된 별칭 사용
2. 일반적으로 MongoDB 문서의 `_class` 필드에 이 값이 저장됨
    - 별칭 없을 경우: `_class: "com.example.CommentNotification"`
    - 별칭 있을 경우: `_class: "CommentNotification"`
3. 문서를 자바 객체로 역직렬화할 때 어떤 구체 클래스로 변환할지 결정하는 데 사용

## 다형성 지원에서의 역할

Spring Data MongoDB에서 상속 구조를 가진 객체를 저장할 때 `@Document`와 `@TypeAlias`는 함께 작동하여 다형성을 지원합니다.

### 동작 예시

주어진 코드 예제에서:

```java
@Document("notifications")
public abstract class Notification { /* ... */ }

@TypeAlias("CommentNotification") 
public class CommentNotification extends Notification { /* ... */ }
```

이 경우 동작 과정은 다음과 같습니다:

1. **저장 시**:
    - `CommentNotification` 인스턴스를 저장하면 "notifications" 컬렉션에 문서로 저장됨
    - 문서에는 `_class: "CommentNotification"`이라는 필드가 포함됨

2. **조회 시**:
    - "notifications" 컬렉션에서 문서를 조회할 때
    - `_class` 필드 값을 확인하여 적절한 자바 클래스(`CommentNotification`)의 인스턴스로 변환
    - 이를 통해 `Notification` 타입의 변수에 다양한 하위 클래스 인스턴스를 할당할 수 있음

### 장점

1. **저장 공간 효율성**: 전체 클래스명 대신 짧은 별칭을 사용하여 저장 공간 절약
2. **가독성**: 데이터베이스 조회 시 더 명확한 타입 식별
3. **리팩토링 안전성**: 패키지 구조 변경 시에도 별칭만 유지하면 기존 데이터 호환

## 사용 시 고려사항

1. **별칭 중복 방지**: 서로 다른 클래스에 동일한 별칭을 사용하지 않도록 주의
2. **리팩토링 시 주의**: 클래스명을 변경하는 경우에도 별칭은 유지하거나 마이그레이션 전략 필요
3. **명명 규칙**: 일관된 별칭 명명 규칙을 사용하여 유지보수성 향상