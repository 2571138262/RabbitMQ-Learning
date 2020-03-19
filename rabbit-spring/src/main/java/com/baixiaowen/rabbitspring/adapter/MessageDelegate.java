package com.baixiaowen.rabbitspring.adapter;

public class MessageDelegate {

    public void handleMessage(byte[] bytes) {
        System.out.println("默认方法，消息内容：" + new String(bytes));
    }

    public void consumerMessage(byte[] messageBody) {
        System.out.println("默认方法，消息内容：" + new String(messageBody));
    }

    public void consumerMessage(String messageBody) {
        System.out.println("默认方法，消息内容：" + messageBody);
    }

    public void method1(String messageBody) {
        System.out.println("默认方法，消息内容：" + messageBody);
    }

    public void method2(String messageBody) {
        System.out.println("默认方法，消息内容：" + messageBody);
    }

}
