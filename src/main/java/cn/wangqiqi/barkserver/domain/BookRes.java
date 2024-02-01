package cn.wangqiqi.barkserver.domain;

import lombok.Data;

import java.util.List;

@Data
public class BookRes {
    /**
     * 返回值编码 0成功
     */
    private Integer code;
    /**
     * 返回消息
     */
    private String message;
    /**
     * 章节信息
     */
    private List<BookResult> result;
}
