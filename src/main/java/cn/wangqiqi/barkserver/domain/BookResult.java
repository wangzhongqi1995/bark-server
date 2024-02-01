package cn.wangqiqi.barkserver.domain;

import lombok.Data;

@Data
public class BookResult {
    /**
     * 章节名
     */
    private String chapterName;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 小说字数
     */
    private String totalWords;
}
