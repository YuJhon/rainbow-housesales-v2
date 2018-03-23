package com.jhon.rain.api.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>功能描述</br>文件处理的业务逻辑接口定义</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-house-v2
 * @date 2018/3/23 10:18
 */
public interface FileService {
  /**
   * <pre>上传图片</pre>
   *
   * @param avatarFiles
   * @return
   */
  List<String> getImgPaths(List<MultipartFile> avatarFiles);
}
