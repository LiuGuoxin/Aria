/*
 * Copyright (C) 2016 AriaLyy(https://github.com/AriaLyy/Aria)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arialyy.aria.core.download;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import com.arialyy.aria.core.common.AbsStartTarget;
import com.arialyy.aria.core.common.Suggest;
import com.arialyy.aria.core.common.ftp.FtpDelegate;
import com.arialyy.aria.core.inf.AbsTaskWrapper;
import com.arialyy.aria.util.CommonUtil;

/**
 * 处理ftp第一次下载等逻辑
 */
public class FtpStartTarget extends AbsStartTarget<FtpStartTarget> {
  private DNormalConfigHandler<FtpStartTarget> mConfigHandler;

  FtpStartTarget(String url, String targetName) {
    mConfigHandler = new DNormalConfigHandler<>(this, -1, targetName);
    mConfigHandler.setUrl(url);
    getTaskWrapper().asFtp().setUrlEntity(CommonUtil.getFtpUrlInfo(url));
    getTaskWrapper().setRequestType(AbsTaskWrapper.D_FTP);
  }

  /**
   * 设置登陆、字符串编码、ftps等参数
   */
  @CheckResult(suggest = Suggest.TASK_CONTROLLER)
  public FtpDelegate<FtpStartTarget> option() {
    return new FtpDelegate<>(this, getTaskWrapper());
  }

  /**
   * 设置文件保存文件夹路径
   * 关于文件名：
   * 1、如果保存路径是该文件的保存路径，如：/mnt/sdcard/file.zip，则使用路径中的文件名file.zip
   * 2、如果保存路径是文件夹路径，如：/mnt/sdcard/，则使用FTP服务器该文件的文件名
   */
  @CheckResult(suggest = Suggest.TASK_CONTROLLER)
  public FtpStartTarget setFilePath(@NonNull String filePath) {
    int lastIndex = mConfigHandler.getUrl().lastIndexOf("/");
    getEntity().setFileName(mConfigHandler.getUrl().substring(lastIndex + 1));
    mConfigHandler.setTempFilePath(filePath);
    return this;
  }

  /**
   * 设置文件存储路径，如果需要修改新的文件名，修改路径便可。
   * 如：原文件路径 /mnt/sdcard/test.zip
   * 如果需要将test.zip改为game.zip，只需要重新设置文件路径为：/mnt/sdcard/game.zip
   *
   * @param filePath 路径必须为文件路径，不能为文件夹路径
   * @param forceDownload {@code true}强制下载，不考虑文件路径是否被占用
   */
  @CheckResult(suggest = Suggest.TASK_CONTROLLER)
  public FtpStartTarget setFilePath(@NonNull String filePath, boolean forceDownload) {
    mConfigHandler.setTempFilePath(filePath);
    mConfigHandler.setForceDownload(forceDownload);
    return this;
  }

  @Override public DownloadEntity getEntity() {
    return (DownloadEntity) super.getEntity();
  }
}