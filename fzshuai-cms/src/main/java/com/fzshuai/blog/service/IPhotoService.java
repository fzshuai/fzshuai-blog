package com.fzshuai.blog.service;

import com.fzshuai.blog.domain.bo.PhotoBo;
import com.fzshuai.blog.domain.dto.FrontPhotoDto;
import com.fzshuai.blog.domain.dto.PhotoDTO;
import com.fzshuai.blog.domain.dto.UpdateAlbumDto;
import com.fzshuai.blog.domain.vo.PhotoVo;
import com.fzshuai.common.core.domain.PageQuery;
import com.fzshuai.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 照片Service接口
 *
 * @author fzshuai
 * @date 2023-05-03
 */
public interface IPhotoService {

    /**
     * 前台根据相册id查看照片列表
     *
     * @param albumId 相册id
     * @return {@link List<FrontPhotoDto>} 照片列表
     */
    FrontPhotoDto listPhotosByAlbumId(Long albumId, PageQuery pageQuery);

    /**
     * 查询照片
     */
    PhotoVo queryById(Long photoId);

    /**
     * 查询照片列表
     */
    TableDataInfo<PhotoVo> queryPageList(PhotoBo bo, PageQuery pageQuery);

    /**
     * 查询照片列表
     */
    List<PhotoVo> queryList(PhotoBo bo);

    /**
     * 新增照片
     */
    Boolean insertByBo(PhotoDTO bo);

    /**
     * 修改照片
     */
    Boolean updateByBo(PhotoBo bo);

    /**
     * 修改照片相册
     */
    Boolean updateByBo(UpdateAlbumDto updateAlbumDto);

    /**
     * 校验并批量删除照片信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
