package cookie.backend.board.common.code

enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String,
) {
    NOT_EXIST_JWT_TOKEN(401, "JWT_TOKEN_001", "JWT Token is not exist"),
    InternalServerError(500, "SERVER_ERROR_001", "Unknown Server Error")
}