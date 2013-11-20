package com.codeapes.checklist.service.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codeapes.checklist.dao.persistence.PersistenceDAO;
import com.codeapes.checklist.domain.user.OwnerExecutor;
import com.codeapes.checklist.domain.user.Role;
import com.codeapes.checklist.domain.user.User;
import com.codeapes.checklist.domain.user.UserGroup;
import com.codeapes.checklist.service.user.UserService;
import com.codeapes.checklist.util.AppLogger;

@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserService {

    private static final AppLogger logger = new AppLogger(UserServiceImpl.class); // NOSONAR

    @Autowired
    private PersistenceDAO persistenceDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User findUserByUsername(final String username) {

        logger.debug("looking for user: %s.", username);

        final String userByNameQuery = UserServiceQueries.FETCH_BY_USERNAME_QUERY;
        @SuppressWarnings("unchecked")
        final List<User> users = (List<User>)persistenceDAO.find(userByNameQuery, username);
        if (users != null && !users.isEmpty()) {
            final User user = (User) users.get(0);
            logger.debug("user %s found with object key of %s", username, user.getObjectKey());
            return user;
        }
        logger.debug("the user %s not found.", username);
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User findUserByObjectKey(Long objectKey) {

        return (User) persistenceDAO.findObjectByKey(User.class, objectKey);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User createUser(final String username, final String firstName, final String lastName, final boolean active,
        final String password, final String createdBy) {
        
        final User user = new User();
        user.setActive(active);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(passwordEncoder.encodePassword(password, username));
        user.addUserRole(Role.USER);
        createUser(user, createdBy);
        return user;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserGroup createUserGroup(final String name, final String createdBy) {
        
        final UserGroup userGroup = new UserGroup();
        userGroup.setName(name);
        createUserGroup(userGroup, createdBy);
        return userGroup;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User createUser(final User user, final String createdBy) {
        
        return (User) persistenceDAO.saveObject(user, createdBy);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserGroup createUserGroup(final UserGroup userGroup, final String createdBy) {
        
        return (UserGroup) persistenceDAO.saveObject(userGroup, createdBy);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User saveOrUpdateUser(User user, String modifiedBy) {

        if (user.getObjectKey() == null) {
            createUser(user, modifiedBy);
        }
        persistenceDAO.saveOrUpdate(user, modifiedBy);
        return user;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserGroup saveOrUpdateUserGroup(UserGroup userGroup, String modifiedBy) {

        if (userGroup.getObjectKey() == null) {
            createUserGroup(userGroup, modifiedBy);
        }
        persistenceDAO.saveOrUpdate(userGroup, modifiedBy);
        return userGroup;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User updateUser(User user, String modifiedBy) {

        persistenceDAO.update(user, modifiedBy);
        return user;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserGroup updateUserGroup(UserGroup userGroup, String modifiedBy) {

        persistenceDAO.update(userGroup, modifiedBy);
        return userGroup;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserGroup(UserGroup userGroup, String deletedBy) {

        persistenceDAO.delete(userGroup, deletedBy);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUser(User user, String deletedBy) {

        persistenceDAO.delete(user, deletedBy);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<OwnerExecutor> getUserAndGroups(Long userObjectKey) {
        final User user = findUserByObjectKey(userObjectKey);
        final List<OwnerExecutor> userAndGroups = new ArrayList<OwnerExecutor>();
        userAndGroups.add(user);
        userAndGroups.addAll(user.getGroups());
        return userAndGroups;
    }
    
}
