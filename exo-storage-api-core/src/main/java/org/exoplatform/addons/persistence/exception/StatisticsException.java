package org.exoplatform.addons.persistence.exception;

/**
 * Created by menzli on 02/05/14.
 */
public class StatisticsException extends Exception {

    public enum Code {
        /** Enable to validate parameters */
        STATISTICS_VALIDATION_PARAMETER,

        /** Enable to load the service */
        STATISTICS_VALIDATION_SERVICES

    }

    /**
     *  Gets the code.
     *
     * @return the code
     */
    private final Code code;

    /**
     *
     * @param code
     */
    public StatisticsException(Code code) {
        this.code = code;
    }

    /**
     *
     * @param code
     * @param message
     * @param cause
     */
    public StatisticsException(Code code, String message, Throwable cause) {
        super(message,cause);
        this.code = code;
    }

    /**
     *
     * @param code
     * @param message
     */
    public StatisticsException(Code code, String message) {
        super(message);
        this.code = code;
    }

    /**
     *
     * @return
     */
    public Code getCode() {
        return this.code;
    }

}
