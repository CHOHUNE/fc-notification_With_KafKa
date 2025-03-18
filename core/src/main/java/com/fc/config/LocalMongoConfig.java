package com.fc.config;


import com.mongodb.ConnectionString;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@Configuration
public class LocalMongoConfig {

    private static final String MONGODB_IMAGE_NAME = "mongo:5.0";

    private static final int MONGO_INNER_PORT = 27017;
    private static final String DATABASE_NAME = "notification";
    private static final GenericContainer mongo = createMongoInstant();

    private static GenericContainer createMongoInstant() {
        return new GenericContainer(DockerImageName.parse(MONGODB_IMAGE_NAME))
            .withExposedPorts(MONGO_INNER_PORT)
            .withReuse(true);
    }

    @PostConstruct
    // 컨테이너를 시작하는 역할을 한다.
    // configuration 빈이 주입 -> PostConstruct 가 실행됨 -> 해당 메서드로 초기화
    public void startMongo() {
        try {
            mongo.start();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    @PreDestroy // 종료될 때 딱 한 번 실행 <-> PostConstruct 와 반대
    public void stopMongo() {
        try {
            mongo.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean(name = "notificationMongoFactory")
    public MongoDatabaseFactory notificationMongoFactory() {
        return new SimpleMongoClientDatabaseFactory(connectionString());
    }

    private ConnectionString connectionString() {
        String host = mongo.getHost();
        Integer port = mongo.getMappedPort(MONGO_INNER_PORT);

        return new ConnectionString("mongodb://" + host + ":" + port + "/" + DATABASE_NAME);

    }
}
