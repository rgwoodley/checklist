package com.codeapes.checklist.service.user.impl;

public final class UserServiceQueries {

    public static final String FETCH_BY_USERNAME_QUERY = "from User as u where u.username = ?";
    
    private UserServiceQueries() {
        super();
    }
}
