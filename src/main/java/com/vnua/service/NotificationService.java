package com.vnua.service;

import com.vnua.mapper.NotificationMapper;
import com.vnua.model.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationMapper notificationMapper;

    public NotificationService(NotificationMapper notificationMapper) {
        this.notificationMapper = notificationMapper;
    }

    public List<Notification> getNotificationsByUserId(Integer userId) {
        return notificationMapper.getNotificationsByUserId(userId);
    }

    public List<Notification> getUnreadNotifications(Integer userId) {
        return notificationMapper.getUnreadNotifications(userId);
    }

    public int countUnreadNotifications(Integer userId) {
        return notificationMapper.countUnreadNotifications(userId);
    }

    public void createNotification(Notification notification) {
        notificationMapper.insertNotification(notification);
    }

    public void markAsRead(Integer notificationId) {
        notificationMapper.markAsRead(notificationId);
    }

    public void markAllAsRead(Integer userId) {
        notificationMapper.markAllAsRead(userId);
    }

    public void deleteNotification(Integer notificationId) {
        notificationMapper.deleteNotification(notificationId);
    }
}