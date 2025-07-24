package cookie.backend.board.common.code

enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String,
) {
    NOT_EXIST_JWT_TOKEN(401, "JWT_TOKEN_001", "JWT Token is not exist"),
    INVALID_JWT_TOKEN(401, "JWT_TOKEN_002", "JWT Token is invalid"),

    NOT_EXIST_USER(404, "USER_001", "User does not exist"),
    NOT_EXIST_USER_ID(401, "USER_002", "User ID is not exist"),

    NOT_EXIST_EMAIL(400, "EMAIL_001", "Email does not exist"),
    INVALID_EMAIL(400, "EMAIL_002", "Email is invalid"),
    DUPLICATE_EMAIL(404, "EMAIL_003", "Email is already in use"),

    NOT_EXIST_PASSWORD(400, "PASSWORD_001", "Password does not exist"),
    INVALID_PASSWORD(400, "PASSWORD_002", "Password is invalid"),

    NOT_EXIST_NICKNAME(400, "NICKNAME_001", "Nickname does not exist"),
    INVALID_NICKNAME(400, "NICKNAME_002", "Nickname is invalid"),
    DUPLICATE_NICKNAME(404, "NICKNAME_003", "Nickname is already in use"),

    INTERNAL_SERVER_ERROR(500, "SERVER_ERROR_001", "Unknown Server Error");

    companion object {
        private val codeMap = entries.associateBy { it.code }

        fun from(code: String?): ErrorCode = codeMap[code] ?: INTERNAL_SERVER_ERROR
    }
}