package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

public class Skill extends AbsData {

	private static final long serialVersionUID = 3837010796068854244L;

	@SerializedName("slug")
	private String slug;
	@SerializedName("name")
	private String name;
	@SerializedName("icon")
	private String icon;
	@SerializedName("level")
	private int level;
	@SerializedName("categorySlug")
	private String categorySlug;
	@SerializedName("tooltipUrl")
	private String tooltipUrl;
	@SerializedName("description")
	private String description;
	@SerializedName("simpleDescription")
	private String simpleDescription;
	@SerializedName("skillCalcId")
	private String skillCalcId;

	public Skill() {
		super();
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getCategorySlug() {
		return categorySlug;
	}

	public void setCategorySlug(String categorySlug) {
		this.categorySlug = categorySlug;
	}

	public String getTooltipUrl() {
		return tooltipUrl;
	}

	public void setTooltipUrl(String tooltipUrl) {
		this.tooltipUrl = tooltipUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSimpleDescription() {
		return simpleDescription;
	}

	public void setSimpleDescription(String simpleDescription) {
		this.simpleDescription = simpleDescription;
	}

	public String getSkillCalcId() {
		return skillCalcId;
	}

	public void setSkillCalcId(String skillCalcId) {
		this.skillCalcId = skillCalcId;
	}

}