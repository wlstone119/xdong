package com.xdong.admin.service.activiti;

import org.activiti.engine.repository.Model;

import java.io.InputStream;

public interface ProcessService {

    Model convertToModel(String procDefId) throws Exception;

    InputStream resourceRead(String id, String resType) throws Exception;
}
