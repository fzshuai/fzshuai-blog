package top.fzshuai.demo.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.fzshuai.common.annotation.Log;
import top.fzshuai.common.annotation.RepeatSubmit;
import top.fzshuai.common.core.controller.BaseController;
import top.fzshuai.common.core.domain.R;
import top.fzshuai.common.core.validate.AddGroup;
import top.fzshuai.common.core.validate.EditGroup;
import top.fzshuai.common.core.validate.QueryGroup;
import top.fzshuai.common.enums.BusinessType;
import top.fzshuai.common.utils.poi.ExcelUtil;
import top.fzshuai.demo.domain.bo.TestTreeBO;
import top.fzshuai.demo.domain.vo.TestTreeVO;
import top.fzshuai.demo.service.ITestTreeService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 测试树表Controller
 *
 * @author Lion Li
 * @date 2021-07-26
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/demo/tree")
public class TestTreeController extends BaseController {

    private final ITestTreeService iTestTreeService;

    /**
     * 查询测试树表列表
     */
    @SaCheckPermission("demo:tree:list")
    @GetMapping("/list")
    public R<List<TestTreeVO>> list(@Validated(QueryGroup.class) TestTreeBO bo) {
        List<TestTreeVO> list = iTestTreeService.queryList(bo);
        return R.ok(list);
    }

    /**
     * 导出测试树表列表
     */
    @SaCheckPermission("demo:tree:export")
    @Log(title = "测试树表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated TestTreeBO bo, HttpServletResponse response) {
        List<TestTreeVO> list = iTestTreeService.queryList(bo);
        ExcelUtil.exportExcel(list, "测试树表", TestTreeVO.class, response);
    }

    /**
     * 获取测试树表详细信息
     *
     * @param id 测试树ID
     */
    @SaCheckPermission("demo:tree:query")
    @GetMapping("/{id}")
    public R<TestTreeVO> getInfo(@NotNull(message = "主键不能为空")
                                 @PathVariable("id") Long id) {
        return R.ok(iTestTreeService.queryById(id));
    }

    /**
     * 新增测试树表
     */
    @SaCheckPermission("demo:tree:add")
    @Log(title = "测试树表", businessType = BusinessType.INSERT)
    @RepeatSubmit
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody TestTreeBO bo) {
        return toAjax(iTestTreeService.insertByBo(bo));
    }

    /**
     * 修改测试树表
     */
    @SaCheckPermission("demo:tree:edit")
    @Log(title = "测试树表", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody TestTreeBO bo) {
        return toAjax(iTestTreeService.updateByBo(bo));
    }

    /**
     * 删除测试树表
     *
     * @param ids 测试树ID串
     */
    @SaCheckPermission("demo:tree:remove")
    @Log(title = "测试树表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iTestTreeService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
