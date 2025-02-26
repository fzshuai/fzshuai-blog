package top.fzshuai.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import top.fzshuai.blog.domain.Article;
import top.fzshuai.blog.domain.dto.CategoryDto;
import top.fzshuai.blog.domain.vo.PageResultVo;
import top.fzshuai.blog.mapper.ArticleMapper;
import top.fzshuai.common.exception.ServiceException;
import top.fzshuai.common.utils.StringUtils;
import top.fzshuai.common.core.page.TableDataInfo;
import top.fzshuai.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.fzshuai.blog.domain.bo.CategoryBo;
import top.fzshuai.blog.domain.vo.CategoryVo;
import top.fzshuai.blog.domain.Category;
import top.fzshuai.blog.mapper.CategoryMapper;
import top.fzshuai.blog.service.ICategoryService;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Objects;

/**
 * 文章分类Service业务层处理
 *
 * @author fzshuai
 * @date 2023-05-03
 */
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryMapper baseMapper;
    private final ArticleMapper articleMapper;

    @Override
    public PageResultVo<CategoryDto> queryCategoryList() {
        return new PageResultVo<>(baseMapper.selectCategoryList(), Integer.parseInt(String.valueOf(baseMapper.selectCount(null))));
    }

    /**
     * 查询文章分类
     */
    @Override
    public CategoryVo queryCategoryById(Long categoryId) {
        return baseMapper.selectVoById(categoryId);
    }

    /**
     * 查询文章分类列表
     */
    @Override
    public TableDataInfo<CategoryVo> queryCategoryList(CategoryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Category> lqw = buildQueryWrapper(bo);
        Page<CategoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询文章分类列表
     */
    @Override
    public List<CategoryVo> queryCategoryList(CategoryBo bo) {
        LambdaQueryWrapper<Category> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Category> buildQueryWrapper(CategoryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Category> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getCategoryName()), Category::getCategoryName, bo.getCategoryName());
        return lqw;
    }

    /**
     * 新增文章分类
     */
    @Override
    public Boolean insertByBo(CategoryBo bo) {
        Category add = BeanUtil.toBean(bo, Category.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setCategoryId(add.getCategoryId());
        }
        return flag;
    }

    /**
     * 修改文章分类
     */
    @Override
    public Boolean updateByBo(CategoryBo bo) {
        Category update = BeanUtil.toBean(bo, Category.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Category category) {
        // TODO 做一些数据校验,如唯一约束
        Category existCategory = baseMapper.selectOne(new LambdaQueryWrapper<Category>()
            .select(Category::getCategoryId)
            .eq(Category::getCategoryName, category.getCategoryName()));
        if (Objects.nonNull(existCategory) && !existCategory.getCategoryId().equals(category.getCategoryId())) {
            throw new ServiceException("分类名已存在");
        }
    }

    /**
     * 批量删除文章分类
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> categoryIds, Boolean isValid) {
        if (isValid) {
            // TODO 做一些业务上的校验,判断是否需要校验
        }
        Long count = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
            .in(Article::getCategoryId, categoryIds));
        if (count > 0) {
            throw new ServiceException("删除失败，该分类下存在文章");
        }

        return baseMapper.deleteBatchIds(categoryIds) > 0;
    }

}
