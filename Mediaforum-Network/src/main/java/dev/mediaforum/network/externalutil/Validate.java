package dev.mediaforum.network.externalutil;

import dev.mediaforum.network.conf.RestException;

public final class Validate {

    public static void isTrue(boolean expression, String message, Object... args) {
        if (!expression) {
            throw new RestException(String.format(message, args));
        }
    }

    public static <T> T notNull(T object, String message, Object... args) {
        if (object == null) {
            throw new RestException(String.format(message, args));
        } else {
            return object;
        }
    }

    public static void validState(boolean expression, String message, Object... args) {
        if (!expression) {
            throw new RestException(String.format(message, args));
        }
    }

}
