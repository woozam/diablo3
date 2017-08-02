package com.woozam.wdthelper.data;

import com.woozam.wdthelper.common.PreferenceUtils;

import java.io.Serializable;

public class RankingParameter implements Serializable {

    public Server server = Server.KR;
    public RiftType type = RiftType.RIFT_4;
    public RiftSeason season = RiftSeason.SEASON_8;
    public boolean isSeason = false;
    public boolean isHardcore = false;

    public static void save(RankingParameter rankingParameter) {
        PreferenceUtils.setValue(PreferenceUtils.KEY_LAST_RANKING_PARAMETER_SERVER, rankingParameter.server.name());
        PreferenceUtils.setValue(PreferenceUtils.KEY_LAST_RANKING_PARAMETER_TYPE, rankingParameter.type.name());
        PreferenceUtils.setValue(PreferenceUtils.KEY_LAST_RANKING_PARAMETER_SEASON, rankingParameter.season.name());
        PreferenceUtils.setValue(PreferenceUtils.KEY_LAST_RANKING_PARAMETER_SEASONAL, rankingParameter.isSeason);
        PreferenceUtils.setValue(PreferenceUtils.KEY_LAST_RANKING_PARAMETER_HARDCORE, rankingParameter.isHardcore);
    }

    public static RankingParameter load() {
        RankingParameter rankingParameter = new RankingParameter();
        rankingParameter.server = Server.valueOf(PreferenceUtils.getStringValue(PreferenceUtils.KEY_LAST_RANKING_PARAMETER_SERVER, Server.KR.name()));
        rankingParameter.type = RiftType.valueOf(PreferenceUtils.getStringValue(PreferenceUtils.KEY_LAST_RANKING_PARAMETER_TYPE, RiftType.RIFT_4.name()));
        rankingParameter.season = RiftSeason.valueOf(PreferenceUtils.getStringValue(PreferenceUtils.KEY_LAST_RANKING_PARAMETER_SEASON, RiftSeason.SEASON_8.name()));
        rankingParameter.isSeason = PreferenceUtils.getBooleanValue(PreferenceUtils.KEY_LAST_RANKING_PARAMETER_SEASONAL, false);
        rankingParameter.isHardcore = PreferenceUtils.getBooleanValue(PreferenceUtils.KEY_LAST_RANKING_PARAMETER_HARDCORE, false);
        return rankingParameter;
    }
}