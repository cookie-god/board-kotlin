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
    INTERNAL_SERVER_ERROR(500, "SERVER_ERROR_001", "Unknown Server Error")
}