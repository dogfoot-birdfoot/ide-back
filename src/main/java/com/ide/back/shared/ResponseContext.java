package com.ide.back.shared;

public class ResponseContext {

    public static ThreadLocal<Long> requestAt = new ThreadLocal<>();

    public static void clear() {
        requestAt.remove();
    }
}
