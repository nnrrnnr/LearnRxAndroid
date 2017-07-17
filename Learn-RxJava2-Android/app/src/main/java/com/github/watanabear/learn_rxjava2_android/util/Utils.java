package com.github.watanabear.learn_rxjava2_android.util;

import android.util.Log;

import com.androidnetworking.error.ANError;
import com.github.watanabear.learn_rxjava2_android.model.ApiUser;
import com.github.watanabear.learn_rxjava2_android.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryo on 2017/07/14.
 */

public class Utils {

    private Utils() {}

    public static void logError(String TAG, Throwable e) {
        if (e instanceof ANError) {
            ANError anError = (ANError) e;
            if (anError.getErrorCode() != 0) {
                // received ANError from server
                // error.getErrorCode() - the ANError code from server
                // error.getErrorBody() - the ANError body from server
                // error.getErrorDetail() - just a ANError detail
                Log.d(TAG, "onError errorCode : " + anError.getErrorCode());
                Log.d(TAG, "onError errorBody : " + anError.getErrorBody());
                Log.d(TAG, "onError errorDetail : " + anError.getErrorDetail());
            } else {
                // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                Log.d(TAG, "onError errorDetail : " + anError.getErrorDetail());
            }
        } else {
            Log.d(TAG, "onError errorMessage : " + e.getMessage());
        }
    }

    public static List<ApiUser> getApiUserList() {
        List<ApiUser> apiUserList = new ArrayList<>();

        ApiUser apiUserOne = new ApiUser();
        apiUserOne.firstName = "Amit";
        apiUserOne.lastName = "Shekhar";
        apiUserList.add(apiUserOne);

        ApiUser apiUserTwo = new ApiUser();
        apiUserTwo.firstName = "Manish";
        apiUserTwo.lastName = "Kumar";
        apiUserList.add(apiUserTwo);

        ApiUser apiUserThree = new ApiUser();
        apiUserThree.firstName = "Sumit";
        apiUserThree.lastName = "Kumar";
        apiUserList.add(apiUserThree);

        return apiUserList;
    }


    public static List<User> convertApiUserListToUserList(List<ApiUser> apiUserList) {

        List<User> userList = new ArrayList<>();

        for (ApiUser apiUser : apiUserList) {
            User user = new User();
            user.firstName = apiUser.firstName;
            user.lastName = apiUser.lastName;
            userList.add(user);
        }

        return userList;
    }

    public static List<User> getUserListWhoLovesCricket() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<User> userList = new ArrayList<>();

        User userOne = new User();
        userOne.id = 1;
        userOne.firstName = "Amit";
        userOne.lastName = "Shekhar";
        userList.add(userOne);

        User userTwo = new User();
        userTwo.id = 2;
        userTwo.firstName = "Manish";
        userTwo.lastName = "Kumar";
        userList.add(userTwo);

        return userList;
    }

    public static List<User> getUserListWhoLovesFootball() {

        List<User> userList = new ArrayList<>();

        User userOne = new User();
        userOne.id = 1;
        userOne.firstName = "Amit";
        userOne.lastName = "Shekhar";
        userList.add(userOne);

        User userTwo = new User();
        userTwo.id = 3;
        userTwo.firstName = "Sumit";
        userTwo.lastName = "Kumar";
        userList.add(userTwo);

        return userList;
    }

    public static List<User> filterUserWhoLovesBoth(List<User> cricketFans, List<User> footballFans) {
        List<User> userWhoLovesBoth = new ArrayList<User>();
        for (User cricketFan : cricketFans) {
            for (User footballFan : footballFans) {
                if (cricketFan.id == footballFan.id) {
                    userWhoLovesBoth.add(cricketFan);
                }
            }
        }
        return userWhoLovesBoth;
    }


}
