package com.maxmind.minfraud.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Event {

    @JsonProperty("transaction_id")
    private final String transactionId;
    @JsonProperty("shop_id")
    private final String shopId;
    @JsonProperty("time")
    private final String time;
    @JsonProperty("type")
    private final Type type;

    @JsonIgnore
    private final SimpleDateFormat dateFormat;


    private Event(Event.Builder builder) {
        transactionId = builder.transactionId;
        shopId = builder.shopId;
        time = builder.time;
        type = builder.type;
        dateFormat = builder.dateFormat;
    }

    /**
     * {@code Builder} creates instances of {@code Event}
     * from values set by the builder's methods.
     */
    public static final class Builder {
        String transactionId;
        String shopId;
        String time;
        Type type;
        SimpleDateFormat dateFormat;

        public Builder() {
            TimeZone tz = TimeZone.getTimeZone("UTC");
            this.dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
            this.dateFormat.setTimeZone(tz);
        }

        /**
         * @param id Your internal ID for the transaction. We can use this to
         *           locate a specific transaction in our logs, and it will
         *           also show up in email alerts and notifications from us to
         *           you.
         * @return The builder object.
         */
        public Event.Builder transactionId(String id) {
            this.transactionId = id;
            return this;
        }

        /**
         * @param id Your internal ID for the shop, affiliate, or merchant
         *           this order is coming from. Required for minFraud users
         *           who are resellers, payment providers, gateways and
         *           affiliate networks.
         * @return The builder object.
         */
        public Event.Builder shopId(String id) {
            this.shopId = id;
            return this;
        }

        /**
         * @param date The date and time the event occurred.
         * @return The builder object.
         */
        public Event.Builder time(Date date) {
            time = this.dateFormat.format(date);
            return this;
        }

        /**
         * @param type The type of event being scored.
         * @return The builder object.
         */
        public Event.Builder type(Type type) {
            this.type = type;
            return this;
        }

        /**
         * @return An instance of {@code Event} created from the
         * fields set on this builder.
         */
        public Event build() {
            return new Event(this);
        }
    }

    /**
     * @return The transaction ID.
     */
    public final String getTransactionId() {
        return transactionId;
    }

    /**
     * @return The shop ID.
     */
    public final String getShopId() {
        return shopId;
    }

    /**
     * @return The date and time of the event.
     * @throws ParseException if the time cannot be parsed.
     */
    @JsonIgnore
    public final Date getTime() throws ParseException {
        return time == null ? null : this.dateFormat.parse(time);
    }

    /**
     * @return The type of the event.
     */
    public final Type getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Event{");
        sb.append("transactionId='").append(this.transactionId).append('\'');
        sb.append(", shopId='").append(this.shopId).append('\'');
        sb.append(", time='").append(this.time).append('\'');
        sb.append(", type=").append(this.type);
        sb.append('}');
        return sb.toString();
    }

    /**
     * The enumerated event types.
     */
    public enum Type {
        ACCOUNT_CREATION,
        ACCOUNT_LOGIN,
        PURCHASE,
        RECURRING_PURCHASE,
        REFERRAL,
        SURVEY;

        public String toString() {
            return this.name().toLowerCase();
        }
    }
}
