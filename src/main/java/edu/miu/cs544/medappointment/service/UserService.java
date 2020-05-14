package edu.miu.cs544.medappointment.service;

import edu.miu.cs544.medappointment.entity.User;

public interface UserService {
    Long getAuthUserId(String username);
    User getAuthUser();
}
