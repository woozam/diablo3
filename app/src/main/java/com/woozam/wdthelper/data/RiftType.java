package com.woozam.wdthelper.data;

import android.content.Context;

import com.woozam.wdthelper.D3Application;
import com.woozam.wdthelper.R;

import java.util.ArrayList;

public enum RiftType {

	RIFT_BB, RIFT_CR, RIFT_DH, RIFT_MK, RIFT_WD, RIFT_WZ, RIFT_NE, RIFT_2, RIFT_3, RIFT_4;

	public String toString(boolean isHardcore) {
		String format;
		switch (this) {
		case RIFT_2:
			format = "rift-%steam-2";
			break;
		case RIFT_3:
			format = "rift-%steam-3";
			break;
		case RIFT_4:
			format = "rift-%steam-4";
			break;
		case RIFT_BB:
			format = "rift-%sbarbarian";
			break;
		case RIFT_CR:
			format = "rift-%scrusader";
			break;
		case RIFT_DH:
			format = "rift-%sdh";
			break;
		case RIFT_MK:
			format = "rift-%smonk";
			break;
		case RIFT_WD:
			format = "rift-%swd";
			break;
		case RIFT_WZ:
			format = "rift-%swizard";
			break;
		case RIFT_NE:
			format = "rift-%snecromancer";
			break;
		default:
			format = "%s";
			break;
		}
		return String.format(format, isHardcore ? "hardcore-" : "");
	}
	
	public String getName() {
		Context context = D3Application.getContext();
		switch (this) {
		case RIFT_2:
			return context.getString(R.string.greater_rift_2_player);
		case RIFT_3:
			return context.getString(R.string.greater_rift_3_player);
		case RIFT_4:
			return context.getString(R.string.greater_rift_4_player);
		case RIFT_BB:
			return context.getString(R.string.greater_rift_barbarian);
		case RIFT_CR:
			return context.getString(R.string.greater_rift_crusader);
		case RIFT_DH:
			return context.getString(R.string.greater_rift_demon_hunter);
		case RIFT_MK:
			return context.getString(R.string.greater_rift_monk);
		case RIFT_WD:
			return context.getString(R.string.greater_rift_witch_doctor);
		case RIFT_WZ:
			return context.getString(R.string.greater_rift_wizard);
		case RIFT_NE:
			return context.getString(R.string.greater_rift_necromancer);
		default:
			return "";
		}
	}
	
	private static ArrayList<RiftType> list = new ArrayList<>();
	
	static {
		list.add(RIFT_BB);
		list.add(RIFT_WZ);
		list.add(RIFT_DH);
		list.add(RIFT_MK);
		list.add(RIFT_WD);
		list.add(RIFT_CR);
		list.add(RIFT_NE);
		list.add(RIFT_2);
		list.add(RIFT_3);
		list.add(RIFT_4);
	}
	
	public static ArrayList<RiftType> toArrayList() {
		return list;
	}
}