package com.fc;

import org.bson.types.ObjectId;

public class NotificationIdGenerator {

    public static String generateId() {
        return new ObjectId().toString();
        // ObjectId:  mongoDB 에서 생성하는 uniqueId
    }

}
