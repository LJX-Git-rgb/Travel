package com.travel.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Base64;

/**
 * @ClassName SMTPClient
 * @description:
 * @author: LJX
 * @time: 2019/12/31 17:41
 * @version: 1.0
 */
public class SMTPClient {

    private String msg = "\r\n ";
    private String  endmsg = "\r\n.\r\n";
    private String mailServer = "smtp.qq.com";
    private Socket socket = new Socket(mailServer,25);
    private InputStream inputStream = socket.getInputStream();
    private OutputStream outputStream = socket.getOutputStream();
    private String userQQ = "1612253221";
    private String receiveQQ = "2024284385";
    private String authorizationCode = "aGZka25wdXRtd3h3YmViZg==";

    public SMTPClient(String message) throws Exception {
        this.userQQ = userQQ + "@qq.com";
        msg = msg + message;
    }

    private String receiveFromServer() throws IOException {
        byte[] value = new byte[1024];
        int i = inputStream.read(value);
        String str = new String(value);
        System.out.println("S: " + str);
        return str;
    }
    private void sendToServer(String response) throws IOException {
        outputStream.write(response.getBytes());
        System.out.println(response);
    }

    public boolean sendMessage() throws IOException {
        /**
         * @Features: 使用SMTP给特定QQ号发送QQ邮件
         * @description:
         * @param
         * @return: boolean
         * @author: LJX
         * @time: 2020/1/1 13:55
         *
         */
        String recv;
        Base64.Encoder encoder = Base64.getEncoder();
        String EuserQQ = new String(encoder.encode(userQQ.getBytes()));
        /**
         * 接收发送连接后服务器响应的字符串并判断是否连接成功
         */
        recv = receiveFromServer();
        if(recv.substring(0,3) == "220") {
            System.out.println("220 reply not received from server.");
            return false;
        }

        /**
         * 向服务器打招呼并接受服务器的响应报文并判断是否连接成功
         */
        String heloCommand =  "HELO 192.168.1.105\r\n";
        sendToServer(heloCommand);
        recv = receiveFromServer();
        if(recv.substring(0,3) == "250") {
            System.out.println("250 reply not received from server.");
            return false;
        }

        /**
         * 向服务器发送登录请求,等待响应
         */
        String loginCommand = "auth login\r\n";
        sendToServer(loginCommand);
        receiveFromServer();

        /**
         * 发送加密的账号
         */
        String userName =  EuserQQ + "\r\n";
        sendToServer(userName);
        receiveFromServer();

        /**
         * 发送加密的密码/授权码
         */
        String passWord =  this.authorizationCode + "\r\n";
        sendToServer(passWord);
        receiveFromServer();

        /**
         * 发送本地邮箱号
         */
        String mailCommand = "mail from: " + "<" + this.userQQ + ">\r\n";
        sendToServer(mailCommand);
        receiveFromServer();

        /**
         * 发送对方(接收者)邮箱号
         */
        String rcpt_toCommand = "rcpt to: " + "<" + receiveQQ + "@qq.com>\r\n";
        sendToServer(rcpt_toCommand);
        receiveFromServer();

        /**
         * 发送data字符表示邮件内容的开始
         */
        String data = "data\r\n\r\n";
        sendToServer(data);
        receiveFromServer();

        /**
         * 发送获取到的邮件内容和发送.来表示发送信息的结束
         */
        sendToServer(msg);
        sendToServer(endmsg);
        recv = receiveFromServer();
        if(recv.substring(0,3) == "250"){
            System.out.println("250 reply not received from server.");
            return false;
        }

        /**
         * 发送终止指令
         */
        String Quit = "quit\r\n";
        sendToServer(Quit);
        receiveFromServer();

        /**
         * 断开连接
         */
        outputStream.close();
        inputStream.close();
        return true;
    }
}
