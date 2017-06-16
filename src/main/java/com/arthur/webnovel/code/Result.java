package com.arthur.webnovel.code;

public class Result<T> {
    private final boolean success;
    private final T payload;

    private Result(boolean success, T payload) {
        this.success = success;
        this.payload = payload;
    }

    public static <T> Result<T> success() {
        return new Result<T>(true, null);
    }

    public static <T> Result<T> success(T payload) {
        return new Result<T>(true, payload);
    }

    public static <T> Result<T> error() {
        return new Result<T>(false, null);
    }

    public static <T> Result<T> error(T payload) {
        return new Result<T>(false, payload);
    }

    public boolean isError() {
        return ! success;
    }

    public boolean isSuccess() {
        return success;
    }

    public T payload() {
        return payload;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Result[")
                .append(success)
                .append(",")
                .append(payload)
                .append("]").toString();
    }
}
