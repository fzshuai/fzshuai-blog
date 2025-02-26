package top.fzshuai.blog.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 归档文章
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
public class ArchiveVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long articleId;

    /**
     * 标题
     */
    private String articleTitle;

    /**
     * 发表时间
     */
    private Date createTime;

}
