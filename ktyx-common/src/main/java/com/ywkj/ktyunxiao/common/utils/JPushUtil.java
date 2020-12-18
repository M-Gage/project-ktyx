package com.ywkj.ktyunxiao.common.utils;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.ywkj.ktyunxiao.common.config.JPushProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class JPushUtil {

    @Autowired
    private JPushProperties jPushProperties;

    /**
     * 发送
     * @param payload
     * @return
     */
    public PushResult send (PushPayload payload) {
        log.info("发送推送！");
        JPushClient jPushClient = new JPushClient(jPushProperties.getSecret(), jPushProperties.getAppKey());
        try {
            PushResult result = jPushClient.sendPush(payload);
            log.info("Got result - " + result);
            return result;
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            log.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            log.error("Should review the error, and fix the request", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
        }
        return null;
    }

    /**
     * 标签推送
     * @param tag
     * @return
     */
    public PushPayload tag(String message, String tag) {
        return PushPayload.newBuilder()
                //推送平台
                .setPlatform(Platform.android_ios())
                //推送目标
                .setAudience(Audience.tag(tag))
                //通知
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(
                                IosNotification.newBuilder()
                                        .setAlert(message)
                                        .build()
                        )
                        .addPlatformNotification(
                                AndroidNotification.newBuilder()
                                        .setAlert(message)
                                        .build()
                        )
                        .build())
                //参数
                .setOptions(Options.newBuilder()
                        //过时时间
                        .setTimeToLive(jPushProperties.getTimeToLive())
                        .build())
                .build();
    }

    /**
     * 别名推送
     * @param ails
     * @return
     */
    public PushPayload alis(String message, String... ails) {
        return PushPayload.newBuilder()
                //推送平台
                .setPlatform(Platform.android_ios())
                //推送目标
                .setAudience(Audience.alias(ails))
                //通知
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(
                                IosNotification.newBuilder()
                                        .setAlert(message)
                                        .build()
                        )
                        .addPlatformNotification(
                                AndroidNotification.newBuilder()
                                        .setAlert(message)
                                        .build()
                        )
                        .build())
                //参数
                .setOptions(Options.newBuilder()
                        //过时时间
                        .setTimeToLive(jPushProperties.getTimeToLive())
                        .build())
                .build();
    }

    /**
     * 静默推送
     * @param title
     * @param jsonData
     * @param tag
     * @return
     */
    public PushPayload message(String title, String jsonData,String tag) {
        return PushPayload.newBuilder()
                //推送平台
                .setPlatform(Platform.android_ios())
                //推送目标
                .setAudience(Audience.tag(tag))
                .setMessage(Message.newBuilder()
                        .setMsgContent(title)
                        .addExtra("data",jsonData)
                        .build())
                //参数
                .setOptions(Options.newBuilder()
                        .build())
                .build();
    }

    /**
     * 推送所有
     * @param message
     * @return
     */
    public static PushPayload all(String message) {
        return PushPayload.alertAll(message);
    }

}
