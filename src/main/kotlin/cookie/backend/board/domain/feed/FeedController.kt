package cookie.backend.board.domain.feed

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/feeds")
class FeedController (private val feedService: FeedService) {
    @GetMapping()
    fun getFeeds(): String {
        return "피드 목록"
    }
}