package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Jeweler extends AbsData implements Serializable {

	private static final long serialVersionUID = -1217601861498659713L;

	@SerializedName("slug")
	private String slug;
	@SerializedName("level")
	private int level;
	@SerializedName("stepCurrent")
	private int stepCurrent;
	@SerializedName("stepMax")
	private int stepMax;
	
	public Jeweler() {
		super();
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getStepCurrent() {
		return stepCurrent;
	}

	public void setStepCurrent(int stepCurrent) {
		this.stepCurrent = stepCurrent;
	}

	public int getStepMax() {
		return stepMax;
	}

	public void setStepMax(int stepMax) {
		this.stepMax = stepMax;
	}
}
