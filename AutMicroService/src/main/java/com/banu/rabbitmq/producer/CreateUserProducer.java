package com.banu.rabbitmq.producer;

import com.banu.rabbitmq.model.CreateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserProducer {

    private final RabbitTemplate rabbitTemplate; //mesajı paketler
   /*
        Java'da serileştirme işlemi var, bir datayı serileştirecek Base64 formatına çevirebilir ve bunu
        iletebilirisniz.
        Burada sınıfı göndermek istiyoruz. Gönderdiğimiz yer gönderdiğimiz sınıfı algılayamaz.
    */
    public void convertAndSendMessage(CreateUser createUser){
        rabbitTemplate.convertAndSend("exchange-auth","key-auth",createUser);
    }


}
