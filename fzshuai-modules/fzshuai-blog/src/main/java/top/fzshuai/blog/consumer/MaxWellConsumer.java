package top.fzshuai.blog.consumer;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import top.fzshuai.blog.domain.Article;
import top.fzshuai.blog.domain.dto.ArticleSearchDto;
import top.fzshuai.blog.domain.dto.MaxwellDataDto;
import top.fzshuai.blog.mapper.ElasticsearchMapper;
import top.fzshuai.common.utils.BeanCopyUtils;

import javax.annotation.Resource;

import static top.fzshuai.blog.constant.MQConstant.MAXWELL_QUEUE;

/**
 * maxwell监听数据
 *
 * @author fzshuai
 * @date 2023/05/03 20:26
 * @since 1.0
 */
@Component
@RabbitListener(queues = MAXWELL_QUEUE)
public class MaxWellConsumer {

    @Resource
    private ElasticsearchMapper elasticsearchMapper;

    @RabbitHandler
    public void process(byte[] data) {
        // 获取监听信息
        MaxwellDataDto maxwellDataDTO = JSON.parseObject(new String(data), MaxwellDataDto.class);
        // 获取文章数据
        Article article = JSON.parseObject(JSON.toJSONString(maxwellDataDTO.getData()), Article.class);
        // 判断操作类型
        switch (maxwellDataDTO.getType()) {
            case "insert":
            case "update":
                // 更新es文章
                elasticsearchMapper.save(BeanCopyUtils.copy(article, ArticleSearchDto.class));
                break;
            case "delete":
                // 删除文章
                elasticsearchMapper.deleteById(article.getArticleId());
                break;
            default:
                break;
        }
    }

}
