package com.tweakcart;

/**
 * Created by IntelliJ IDEA.
 * User: Edoxile
 */

/**
 * Voor nu even @Deprecated, misschien later weer introduceren?
 */

@Deprecated
public class TweakCartException extends Exception {
    private final ExceptionCause cause;

    public TweakCartException(int id) {
        cause = ExceptionCause.getEnum(id);
    }

    private enum ExceptionCause {
        ArrayIndexOutOfBounds(0),
        NullPointer(1),
        KeyNotFound(2),
        InvalidConstructionException(3);

        private int id;
        private static final ExceptionCause[] exceptions = new ExceptionCause[4];

        static {
            for (ExceptionCause exceptionCause : values()) exceptions[exceptionCause.id] = exceptionCause;
        }

        private ExceptionCause(int id) {
            this.id = id;
        }

        public static final ExceptionCause getEnum(int id) {
            return exceptions[id];
        }

        public final String getCause() {
            return this.name();
        }
    }
}
