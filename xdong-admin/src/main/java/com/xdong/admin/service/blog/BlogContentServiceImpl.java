package com.xdong.admin.service.blog;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.dal.blog.BlogContentMapper;
import com.xdong.model.entity.blog.BlogContentDo;
import com.xdong.spi.admin.blog.IBlogContentService;

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
public class BlogContentServiceImpl extends ServiceImpl<BlogContentMapper, BlogContentDo>
		implements IBlogContentService {

}
