package com.xdong.admin.service.blog.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.admin.service.blog.IBlogContentService;
import com.xdong.dal.blog.dao.BlogContentMapper;
import com.xdong.dal.blog.domain.BlogContent;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章内容 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
@Service
public class BlogContentServiceImpl extends ServiceImpl<BlogContentMapper, BlogContent> implements IBlogContentService {

}
