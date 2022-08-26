package com.nhnacademy.marketgg.client.context;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SessionContext {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String getSessionId() {
        return threadLocal.get();
    }

    public static void setSessionId(String sessionId) {
        threadLocal.set(sessionId);
    }

    public static void remove() {
        threadLocal.remove();
    }

}
