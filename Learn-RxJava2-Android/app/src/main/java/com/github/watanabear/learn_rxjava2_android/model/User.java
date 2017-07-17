package com.github.watanabear.learn_rxjava2_android.model;

/**
 * Created by ryo on 2017/07/17.
 */

public class User {

    public long id;
    public String firstName;
    public String lastName;
    public boolean isFollowing;

    public User() {}

    public User(ApiUser apiUser) {
        this.id = apiUser.id;
        this.firstName = apiUser.firstName;
        this.lastName = apiUser.lastName;
    }
}
