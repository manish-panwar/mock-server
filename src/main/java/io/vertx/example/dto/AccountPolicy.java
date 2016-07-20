package io.vertx.example.dto;

/**
 * Created by manishk on 7/19/16.
 */
public class AccountPolicy {

    private String userName;
    private boolean allowSync;

    public String getUserName() {
        return userName;
    }

    public AccountPolicy setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public boolean isAllowSync() {
        return allowSync;
    }

    public AccountPolicy setAllowSync(boolean allowSync) {
        this.allowSync = allowSync;
        return this;
    }
}
