package cookie.backend.board.config.exception

import cookie.backend.board.common.code.ErrorCode

class BoardException(
    val errorCode: ErrorCode
) : RuntimeException(errorCode.message)
