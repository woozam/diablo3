package com.woozam.wdthelper.data;

public class Passive extends AbsSkill {

	private static final long serialVersionUID = 3949184985486901362L;
	
	private Skill skill;

	public Passive() {
		super();
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}
}