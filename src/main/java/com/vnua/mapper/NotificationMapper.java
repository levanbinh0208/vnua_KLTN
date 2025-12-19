package com.vnua.mapper;

import com.vnua.model.Notification;

import java.util.List;

public interface NotificationMapper {

    List<Notification> getNotificationsByUserId(Integer userId);

    List<Notification> getUnreadNotifications(Integer userId);

    int countUnreadNotifications(Integer userId);

    void insertNotification(Notification notification);

    void markAsRead(Integer notificationId);

    void markAllAsRead(Integer userId);

    void deleteNotification(Integer notificationId);
}