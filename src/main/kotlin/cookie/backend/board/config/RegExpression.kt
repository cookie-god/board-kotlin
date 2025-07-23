package cookie.backend.board.config

object RegExpression {
    const val EMAIL_PATTERN: String = "^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*\\.[a-zA-Z.]*$"
    const val PASSWORD_PATTERN: String = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$"
}