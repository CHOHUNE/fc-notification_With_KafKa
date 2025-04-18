
# Spring Cloud Stream 주요 개념

- 이벤트 기반 마이크로서비스 개발을 단순화하는 프레임워크
- 메시징 인프라 대신 비즈니스 로직 작성에 집중할 수 있도록 Kafka, RabbitMQ와 같은 메시징 시스템을 추상화
- 주요 특징
  - 다양한 메시지 브로커 (Kafka, RabbitMQ)와 통합을 추상화
  - 일관된 프로그래밍 모델 제공
  - 메시지 생상잔와 소비자를 쉽게 구현할 수 있는 기능 제공 
  

## 주요 개념

- Binder 
  - 메시징 시스템 <-> Application 연결을 추상화 레이어
  - 메시지 프로듀서와 컨슈머 사이 데이저터 전송을 원활하게 함
- Binding
  - Input Binding, output Binding
    -  application 의 특정 함수와 메시징 시스템 간의 연결을 설정
    - 이를 통해 입력 채널과 출력 채널을 정의하며, 애플리케이션이 메시지를 읽고 쓸수 있도록 함 
- Message
  - 생산자와 소비자간 통신에 사용되는 데이터 구조
- Function
  - 입력 챈러에서는 수신된 메세지를 처리하는 비즈니스 로직을 담고있는 함수
  - 이 함수는 메시질르 받아 필요한 처리를 수행하고 결과를 반환한다. 