package com.banu.rabbitmq.consumer;

import com.banu.dto.request.UserProfileSaveRequestDto;
import com.banu.rabbitmq.model.CreateUser;
import com.banu.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserConsumer {

    private final UserProfileService userProfileService;

    @RabbitListener(queues = "queue-auth-create-user")
    public void createUserFromQueue(CreateUser createUser) { //her zaman donus tipi void
        System.out.println("Kuyruk dinlendi gelen kayıt -> " + createUser);
        userProfileService.save(UserProfileSaveRequestDto.builder()
                .userName(createUser.getUserName())
                .authId(createUser.getAuthId())
                .build());
    }
}
