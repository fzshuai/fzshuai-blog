package top.fzshuai.blog.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 照片
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@Data
public class PhotoInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 照片id
     */
    @NotNull(message = "照片id不能为空")
    private Long photoId;

    /**
     * 照片名
     */
    @NotBlank(message = "照片名不能为空")
    private String photoName;

    /**
     * 照片描述
     */
    private String photoDesc;

}
