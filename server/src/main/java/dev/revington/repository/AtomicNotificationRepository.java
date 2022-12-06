package dev.revington.repository;
 
public interface AtomicNotificationRepository {

    void updateUnreadNotifications(String owner);
    
}
