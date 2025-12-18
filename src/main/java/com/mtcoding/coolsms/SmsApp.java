package com.mtcoding.coolsms;

import com.solapi.sdk.SolapiClient;
import com.solapi.sdk.message.exception.SolapiMessageNotReceivedException;
import com.solapi.sdk.message.model.Message;
import com.solapi.sdk.message.service.DefaultMessageService;

public class SmsApp {
    public static void main(String[] args) {
        DefaultMessageService messageService = SolapiClient.INSTANCE.createInstance("NCSLM3CIEOBRHUU1", "UGBL7MXX1WDYK7ZFF1TSQHCIE51KBB1L");
// Message 패키지가 중복될 경우 com.solapi.sdk.message.model.Message로 치환하여 주세요
        Message message = new Message();
        message.setFrom("01034451357");
        message.setTo("01022227460");
        message.setText("김해준 - 안녕하세요 김해준입니다\n강의 잘 듣고있습니다\n감사합니다");

        try {
            // send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
            messageService.send(message);
        } catch (SolapiMessageNotReceivedException exception) {
            // 발송에 실패한 메시지 목록을 확인할 수 있습니다!
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
