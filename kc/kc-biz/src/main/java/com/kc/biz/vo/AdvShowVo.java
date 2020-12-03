package com.kc.biz.vo;

import java.io.Serializable;
import java.util.List;

public class AdvShowVo implements Serializable {
    private static final long serialVersionUID = 4359709211352300084L;

    private AdvVo startPageAdv;
    private List<AdvVo> homeAdvList;
    private AdvVo videoDetailAdv;
    private AdvVo appDownloadAdv;

    public AdvVo getStartPageAdv() {
        return startPageAdv;
    }

    public void setStartPageAdv(AdvVo startPageAdv) {
        this.startPageAdv = startPageAdv;
    }

    public List<AdvVo> getHomeAdvList() {
        return homeAdvList;
    }

    public void setHomeAdvList(List<AdvVo> homeAdvList) {
        this.homeAdvList = homeAdvList;
    }

    public AdvVo getVideoDetailAdv() {
        return videoDetailAdv;
    }

    public void setVideoDetailAdv(AdvVo videoDetailAdv) {
        this.videoDetailAdv = videoDetailAdv;
    }

    public AdvVo getAppDownloadAdv() {
        return appDownloadAdv;
    }

    public void setAppDownloadAdv(AdvVo appDownloadAdv) {
        this.appDownloadAdv = appDownloadAdv;
    }
}
