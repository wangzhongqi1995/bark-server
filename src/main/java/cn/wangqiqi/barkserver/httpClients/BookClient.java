package cn.wangqiqi.barkserver.httpClients;


import cn.wangqiqi.barkserver.constants.CommonConstant;
import cn.wangqiqi.barkserver.domain.BookRes;
import com.dtflys.forest.annotation.Body;
import com.dtflys.forest.annotation.Post;
import org.springframework.stereotype.Component;

@Component
public interface BookClient {
    @Post(CommonConstant.ZONGHENG_BASE_URL)
    BookRes getLastSimpleChapterInfo(@Body("bookId") String bookId);
}
