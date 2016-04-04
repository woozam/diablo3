package com.woozam.wdthelper.data;

public enum ItemType {

	HEAD, TORSO, FEET, HANDS, SHOULDERS, LEGS, BRACERS, MAINHAND, OFFHAND, WAIST, RIGHTFINGER, LEFTFINGER, NECK;

	private static final String S_BRACERS = "bracers";
	private static final String S_FEET = "feet";
	private static final String S_HANDS = "hands";
	private static final String S_HEAD = "head";
	private static final String S_LEFTFINGER = "leftFinger";
	private static final String S_LEGS = "legs";
	private static final String S_MAINHAND = "mainHand";
	private static final String S_NECK = "neck";
	private static final String S_OFFHAND = "offHand";
	private static final String S_RIGHTFINGER = "rightHand";
	private static final String S_SHOULDERS = "shoulders";
	private static final String S_TORSO = "torso";
	private static final String S_WAIST = "waist";

	public static ItemType getItemType(String itemType) {
		if (itemType.equals(S_BRACERS)) {
			return BRACERS;
		} else if (itemType.equals(S_FEET)) {
			return FEET;
		} else if (itemType.equals(S_HANDS)) {
			return HANDS;
		} else if (itemType.equals(S_HEAD)) {
			return HEAD;
		} else if (itemType.equals(S_LEFTFINGER)) {
			return LEFTFINGER;
		} else if (itemType.equals(S_LEGS)) {
			return LEGS;
		} else if (itemType.equals(S_MAINHAND)) {
			return MAINHAND;
		} else if (itemType.equals(S_NECK)) {
			return NECK;
		} else if (itemType.equals(S_OFFHAND)) {
			return OFFHAND;
		} else if (itemType.equals(S_RIGHTFINGER)) {
			return RIGHTFINGER;
		} else if (itemType.equals(S_SHOULDERS)) {
			return SHOULDERS;
		} else if (itemType.equals(S_TORSO)) {
			return TORSO;
		} else if (itemType.equals(S_WAIST)) {
			return WAIST;
		} else {
			return null;
		}
	}

	public String toString() {
		switch (this) {
		case BRACERS:
			return "손목";
		case FEET:
			return "발";
		case HANDS:
			return "장갑";
		case HEAD:
			return "머리";
		case LEFTFINGER:
			return "왼쪽 손가락";
		case LEGS:
			return "다리";
		case MAINHAND:
			return "주무기";
		case NECK:
			return "목";
		case OFFHAND:
			return "보조무기";
		case RIGHTFINGER:
			return "오른쪽 손가락";
		case SHOULDERS:
			return "어깨";
		case TORSO:
			return "몸통";
		case WAIST:
			return "허리";
		default:
			return "";
		}
	}
}