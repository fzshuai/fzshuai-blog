package top.fzshuai.blog.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import top.fzshuai.blog.domain.vo.BlogHomeInfoVo;
import top.fzshuai.blog.domain.vo.BlogInfoVo;
import top.fzshuai.blog.service.IBlogInfoService;
import top.fzshuai.common.core.controller.BaseController;
import top.fzshuai.common.core.domain.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 博客信息控制器
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@RestController
@RequestMapping("/blog/blogInfo")
public class BlogInfoController extends BaseController {

    @Resource
    private IBlogInfoService blogInfoService;

    /**
     * 查看博客信息
     */
    @SaIgnore
    @GetMapping()
    public R<BlogHomeInfoVo> getBlogHomeInfo() {
        return R.ok(blogInfoService.queryBlogHomeInfo());
    }

    /**
     * 查看关于我信息
     *
     * @return 关于我信息
     */
    @SaIgnore
    @GetMapping("/about")
    public R<String> getAbout() {
        return R.ok("操作成功", blogInfoService.queryAbout());
    }

    /**
     * 修改关于我信息
     *
     * @param blogInfoVO 博客信息
     * @return R
     */
    @SaIgnore
    @PutMapping("/admin/about")
    public R<Void> updateAbout(@Valid @RequestBody BlogInfoVo blogInfoVO) {
        blogInfoService.updateAbout(blogInfoVO);
        return R.ok();
    }

    /**
     * 上传访客信息
     *
     * @return R
     */
    @SaIgnore
    @PostMapping("/report")
    public R<Void> report() {
        blogInfoService.reportVisitor();
        return R.ok();
    }

}
