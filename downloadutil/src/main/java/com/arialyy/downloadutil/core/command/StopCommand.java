package com.arialyy.downloadutil.core.command;

import android.content.Context;
import com.arialyy.downloadutil.entity.DownloadEntity;

/**
 * Created by lyy on 2016/9/20.
 * 停止命令
 */
public class StopCommand extends IDownloadCommand {

  /**
   * @param context context
   * @param entity 下载实体
   */
  protected StopCommand(Context context, DownloadEntity entity) {
    super(context, entity);
  }

  @Override public void executeComment() {
    target.stopTask(target.getTask(mEntity));
  }
}
