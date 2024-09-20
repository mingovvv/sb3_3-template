package demo.template.common.enums;

import lombok.Getter;

import java.util.Arrays;

public class ResultCode {

    @Getter
    public enum Success {
        OK("S200", "Success.");

        private final String code;
        private final String message;

        Success(String code, String message) {
            this.code = code;
            this.message = message;
        }

    }

    @Getter
    public enum Error {
        INVALID_REQUEST("E400", "Required value is invalid."),
        INVALID_VALUE("E401", "Required value is invalid."),
        PATH_NOT_FOUND("E404", "Invalid path. (No URL provided)"),
        METHOD_NOT_ALLOWED("E405", "Method not allowed."),
        INVALID_JSON_FORMAT("E410", "The JSON format is invalid."),
        DATA_NOT_FOUND("E450", "Data not found."),
        INVALID_PARAMETER("E451", "Invalid parameters."),
        INVALID_CHANNEL_ID("E470", "Invalid channel-id."),
        INVALID_CHANNEL_KEY("E471", "Invalid channel-key."),
        TOKEN_EXPIRED("E472", "Token has expired."),
        API_CALL_FAILED("E544", "Internal API call failed."),
        DB_CONNECTION_ERROR("E550", "Database connection error."),
        UNKNOWN("E599", "Unknown error.");

        private final String code;
        private final String message;

        Error(String code, String message) {
            this.code = code;
            this.message = message;
        }

    }

    public static boolean isSuccess(String code) {
        return Arrays.stream(Success.values())
                .anyMatch(success -> success.getCode().equals(code));
    }

}

