package com.cogs121.ixd.stores.UserStore;

/**
 * Created by rjw on 5/24/17.
 */

public class ConsumerUserStore {
    private String userName;
    private String password;

    private boolean isEnterprise = false;

    public void createConsumerUser(String username, String password) {
        this.userName = username;
        this.password = password;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}