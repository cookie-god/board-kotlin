package kisung.template.board.config.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import kotlin.text.append

class LoggingFilter : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(this::class.java)
    private val enabled = true

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (isAsyncDispatch(request)) {
            filterChain.doFilter(request, response)
        } else {
            doFilterWrapper(wrapRequest(request), wrapResponse(response), filterChain)
        }
    }

    @Throws(IOException::class, ServletException::class)
    protected fun doFilterWrapper(
        request: ContentCachingRequestWrapper,
        response: ContentCachingResponseWrapper,
        filterChain: FilterChain
    ) {
        val msg = StringBuilder()
        try {
            beforeRequest(request, response, msg)
            filterChain.doFilter(request, response)
        } finally {
            response.copyBodyToResponse()
            afterRequest(request, response, msg)
            if (log.isInfoEnabled) {
                log.info(msg.toString())
            }
        }
    }

    protected fun beforeRequest(
        request: ContentCachingRequestWrapper,
        response: ContentCachingResponseWrapper,
        msg: StringBuilder
    ) {
        if (enabled && log.isInfoEnabled) {
            msg.append("\n-- REQUEST --\n")
            logRequestHeader(request, "${request.remoteAddr}|>", msg)
        }
    }

    protected fun afterRequest(
        request: ContentCachingRequestWrapper,
        response: ContentCachingResponseWrapper,
        msg: StringBuilder
    ) {
        if (enabled && log.isInfoEnabled) {
            logRequestBody(request, "${request.remoteAddr}|>", msg)
            msg.append("\n-- RESPONSE --\n")
            logResponse(response, "${request.remoteAddr}|<", msg)
        }
    }

    companion object {
        private val VISIBLE_TYPES = listOf(
            MediaType.valueOf("text/*"),
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            MediaType.valueOf("application/*+json"),
            MediaType.valueOf("application/*+xml"),
            MediaType.MULTIPART_FORM_DATA
        )

        private fun logRequestHeader(
            request: ContentCachingRequestWrapper,
            prefix: String,
            msg: StringBuilder
        ) {
            val queryString = request.queryString
            if (queryString == null) {
                msg.append("$prefix ${request.method} ${request.requestURI}\n")
            } else {
                msg.append("$prefix ${request.method} ${request.requestURI}?$queryString\n")
            }
        }

        private fun logRequestBody(
            request: ContentCachingRequestWrapper,
            prefix: String,
            msg: StringBuilder
        ) {
            val content = request.contentAsByteArray
            if (content.isNotEmpty()) {
                logContent(content, request.contentType, request.characterEncoding, prefix, msg)
            }
        }

        private fun logResponse(
            response: ContentCachingResponseWrapper,
            prefix: String,
            msg: StringBuilder
        ) {
            val status = response.status
            msg.append("$prefix $status ${HttpStatus.valueOf(status).reasonPhrase}\n")
            val content = response.contentAsByteArray
            if (content.isNotEmpty()) {
                logContent(content, response.contentType, response.characterEncoding, prefix, msg)
            }
        }

        private fun logContent(
            content: ByteArray,
            contentType: String?,
            contentEncoding: String?,
            prefix: String,
            msg: StringBuilder
        ) {
            val mediaType = contentType?.let { MediaType.valueOf(it) }
            val visible = mediaType != null && VISIBLE_TYPES.any { it.includes(mediaType) }
            if (visible) {
                try {
                    val charset = contentEncoding?.let { Charset.forName(it) } ?: Charsets.UTF_8
                    val contentString = String(content, charset)
                    contentString.split("\r\n", "\r", "\n").forEach { line ->
                        msg.append("$prefix $line\n")
                    }
                } catch (e: UnsupportedEncodingException) {
                    msg.append("$prefix [${content.size} bytes content]\n")
                }
            } else {
                msg.append("$prefix [${content.size} bytes content]\n")
            }
        }

        private fun wrapRequest(request: HttpServletRequest): ContentCachingRequestWrapper {
            return if (request is ContentCachingRequestWrapper) {
                request
            } else {
                ContentCachingRequestWrapper(request)
            }
        }

        private fun wrapResponse(response: HttpServletResponse): ContentCachingResponseWrapper {
            return if (response is ContentCachingResponseWrapper) {
                response
            } else {
                ContentCachingResponseWrapper(response)
            }
        }
    }
}