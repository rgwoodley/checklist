package com.codeapes.checklist.service.user;

import com.codeapes.checklist.domain.user.User;

public interface UserService {

    User createUser(String username, String firstname, String lastname, boolean active, String password,
            String createdBy);

    User createUser(User user, String createdBy);

    User findUserByObjectKey(Long objectId);

    User findUserByUsername(String username);

    User saveOrUpdateUser(User user, String modifiedBy);
    
    User updateUser(User user, String modifiedBy);

    void deleteUser(User user);

}
