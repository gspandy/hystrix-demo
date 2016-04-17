package cn.eakay.models;

import lombok.Data;

/**
 * Created by xialei on 16/4/15.
 */

@Data
public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
