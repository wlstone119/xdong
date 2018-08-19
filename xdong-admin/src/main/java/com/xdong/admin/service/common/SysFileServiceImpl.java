package com.xdong.admin.service.common;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xdong.common.config.XdongConfig;
import com.xdong.dal.common.SysFileMapper;
import com.xdong.model.entity.common.SysFileDo;
import com.xdong.spi.admin.common.ISysFileService;

import java.io.File;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 文件上传 服务实现类
 * </p>
 *
 * @author wanglei
 * @since 2018-04-30
 */
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFileDo> implements ISysFileService {

    @Autowired
    private XdongConfig bootdoConfig;

    @Override
    public boolean batchRemove(Long[] ids) {
        return deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public Boolean isExist(String url) {
        Boolean isExist = false;
        if (!StringUtils.isEmpty(url)) {
            String filePath = url.replace("/files/", "");
            filePath = bootdoConfig.getUploadPath() + filePath;
            File file = new File(filePath);
            if (file.exists()) {
                isExist = true;
            }
        }
        return isExist;
    }

}
