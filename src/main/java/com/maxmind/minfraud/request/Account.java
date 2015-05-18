package com.maxmind.minfraud.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Account related data for the minFraud request
 */
public final class Account {
    @JsonProperty("user_id")
    private final String userId;

    @JsonProperty("username_md5")
    private final String usernameMd5;

    private Account(Account.Builder builder) {
        userId = builder.userId;
        usernameMd5 = builder.usernameMd5;
    }

    /**
     * {@code Builder} creates instances of {@code Account}
     * from values set by the builder's methods.
     */
    public static final class Builder {
        String userId;
        String usernameMd5;

        /**
         * @param id A unique user ID associated with the end-user in your
         *           system. If your system allows the login name for the
         *           account to be changed, this should not be the login
         *           name for the account, but rather should be an internal
         *           ID that does not change. This is not your MaxMind user
         *           ID.
         * @return The builder object.
         */
        public Account.Builder userId(String id) {
            this.userId = id;
            return this;
        }

        /**
         * @param username The username associated with the account. This is
         *                 <em>not</em> the MD5 of username. This method
         *                 automatically runs {@code DigestUtils.md5Hex}
         *                 on the string passed to it.
         * @return The builder object.
         */
        public Account.Builder username(String username) {
            this.usernameMd5 = DigestUtils.md5Hex(username);
            return this;
        }

        /**
         * @return An instance of {@code Account} created from the
         * fields set on this builder.
         */
        public Account build() {
            return new Account(this);
        }
    }

    /**
     * @return The user ID.
     */
    public final String getUserId() {
        return userId;
    }

    /**
     * @return The MD5 of the username passed to the builder.
     */
    public final String getUsernameMd5() {
        return usernameMd5;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Account{");
        sb.append("userId='").append(this.userId).append('\'');
        sb.append(", usernameMd5='").append(this.usernameMd5).append('\'');
        sb.append('}');
        return sb.toString();
    }
}