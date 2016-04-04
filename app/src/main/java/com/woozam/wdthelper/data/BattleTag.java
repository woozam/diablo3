package com.woozam.wdthelper.data;

import com.woozam.wdthelper.D3Application;
import com.woozam.wdthelper.R;

/**
 * Created by woozam on 2016-01-31.
 */
public class BattleTag extends AbsData {

    private String battleTag;
    private Server server;
    private boolean add;

    public BattleTag() {
    }

    public BattleTag(String battleTag, Server server) {
        this.battleTag = battleTag;
        this.server = server;
    }

    public String getBattleTag() {
        return battleTag;
    }

    public void setBattleTag(String battleTag) {
        this.battleTag = battleTag;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public String toString() {
        return add ? D3Application.getContext().getString(R.string.add_battle_tag) : String.format("(%s) %s", server.getName(), battleTag);
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o == this) {
            return true;
        } else if (o instanceof BattleTag) {
            if (isAdd() && ((BattleTag) o).isAdd()) {
                return true;
            } else if (isAdd() || ((BattleTag) o).isAdd()) {
                return false;
            } else {
                return ((BattleTag) o).getBattleTag().equals(getBattleTag()) && ((BattleTag) o).getServer().equals(getServer());
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        if (isAdd()) {
            return 0;
        } else {
            return (battleTag + server).hashCode();
        }
    }
}