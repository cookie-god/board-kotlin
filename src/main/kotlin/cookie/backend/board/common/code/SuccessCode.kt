package cookie.backend.board.common.code

enum class SuccessCode(
    val status: Int,
    val code: String,
    val message: String,
) {
    READ_SUCCESS(200, "READ_SUCCESS", "SUCCESS"),
    CREATE_SUCCESS(201, "CREATE_SUCCESS", "SUCCESS"),
    UPDATE_SUCCESS(204, "UPDATE_SUCCESS", "SUCCESS"),
    DELETE_SUCCESS(200, "DELETE_SUCCESS", "SUCCESS"),
}