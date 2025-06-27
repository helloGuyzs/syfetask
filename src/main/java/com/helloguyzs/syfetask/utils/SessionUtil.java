package com.helloguyzs.syfetask.utils;

import com.helloguyzs.syfetask.exceptions.UnauthorizedException;
import com.helloguyzs.syfetask.models.Users;
import jakarta.servlet.http.HttpServletRequest;

public class SessionUtil {

    public static Integer getCurrentUserId(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute("user");

        if (userObj == null || !(userObj instanceof Users)) {
            throw new UnauthorizedException("User is not logged in");
        }

        Users user = (Users) userObj;
        return user.getId();
    }
}
