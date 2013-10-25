package com.codeapes.checklist.service.user;

import java.util.List;

import com.codeapes.checklist.domain.user.OwnerExecutor;
import com.codeapes.checklist.domain.user.User;
import com.codeapes.checklist.domain.user.UserGroup;

public interface UserService {

    User createUser(String username, String firstname, String lastname, boolean active, String password,
            String createdBy);

    UserGroup createUserGroup(final String name, final String createdBy);
    
    User createUser(User user, String createdBy);
    
    UserGroup createUserGroup(UserGroup userGroup, String createdBy);

    User findUserByObjectKey(Long objectId);

    User findUserByUsername(String username);

    User saveOrUpdateUser(User user, String modifiedBy);

    UserGroup saveOrUpdateUserGroup(UserGroup userGroup, String modifiedBy);

    User updateUser(User user, String modifiedBy);

    UserGroup updateUserGroup(UserGroup userGroup, String modifiedBy);

    void deleteUser(User user, String deletedBy);

    void deleteUserGroup(UserGroup userGroup, String deletedBy);
    
    List<OwnerExecutor> getUserAndGroups(Long userObjectKey);
    
}
