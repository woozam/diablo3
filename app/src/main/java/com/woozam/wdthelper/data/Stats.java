package com.woozam.wdthelper.data;

public class Stats extends AbsData {

    private static final long serialVersionUID = -2422240928705388757L;

    private int life;

    private int strength;
    private int dexterity;
    private int vitality;
    private int intelligence;

    private double damage;
    private double toughness;
    private double healing;

    private float damageIncrease;
    private float attackSpeed;
    private float critChance;
    private float critDamage;
    private double thorns;

    private long primaryResource;
    private long secondaryResource;

    private int armor;
    private float damageReduction;
    private float blockChance;
    private long blockAmountMin;
    private long blockAmountMax;

    private int physicalResist;
    private int fireResist;
    private int coldResist;
    private int lightningResist;
    private int poisonResist;
    private int arcaneResist;

    private double lifeOnHit;
    private double lifePerKill;
    private double lifeSteal;

    private float magicFind;
    private float goldFind;

    private int experienceBonus;

    public Stats() {
        super();
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getVitality() {
        return vitality;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getToughness() {
        return toughness;
    }

    public void setToughness(double toughness) {
        this.toughness = toughness;
    }

    public double getHealing() {
        return healing;
    }

    public void setHealing(double healing) {
        this.healing = healing;
    }

    public float getDamageIncrease() {
        return damageIncrease;
    }

    public void setDamageIncrease(float damageIncrease) {
        this.damageIncrease = damageIncrease;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public float getCritChance() {
        return critChance;
    }

    public void setCritChance(float critChance) {
        this.critChance = critChance;
    }

    public float getCritDamage() {
        return critDamage;
    }

    public void setCritDamage(float critDamage) {
        this.critDamage = critDamage;
    }

    public double getThorns() {
        return thorns;
    }

    public void setThorns(double thorns) {
        this.thorns = thorns;
    }

    public long getPrimaryResource() {
        return primaryResource;
    }

    public void setPrimaryResource(long primaryResource) {
        this.primaryResource = primaryResource;
    }

    public long getSecondaryResource() {
        return secondaryResource;
    }

    public void setSecondaryResource(long secondaryResource) {
        this.secondaryResource = secondaryResource;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public float getDamageReduction() {
        return damageReduction;
    }

    public void setDamageReduction(float damageReduction) {
        this.damageReduction = damageReduction;
    }

    public float getBlockChance() {
        return blockChance;
    }

    public void setBlockChance(float blockChance) {
        this.blockChance = blockChance;
    }

    public long getBlockAmountMin() {
        return blockAmountMin;
    }

    public void setBlockAmountMin(long blockAmountMin) {
        this.blockAmountMin = blockAmountMin;
    }

    public long getBlockAmountMax() {
        return blockAmountMax;
    }

    public void setBlockAmountMax(long blockAmountMax) {
        this.blockAmountMax = blockAmountMax;
    }

    public int getPhysicalResist() {
        return physicalResist;
    }

    public void setPhysicalResist(int physicalResist) {
        this.physicalResist = physicalResist;
    }

    public int getFireResist() {
        return fireResist;
    }

    public void setFireResist(int fireResist) {
        this.fireResist = fireResist;
    }

    public int getColdResist() {
        return coldResist;
    }

    public void setColdResist(int coldResist) {
        this.coldResist = coldResist;
    }

    public int getLightningResist() {
        return lightningResist;
    }

    public void setLightningResist(int lightningResist) {
        this.lightningResist = lightningResist;
    }

    public int getPoisonResist() {
        return poisonResist;
    }

    public void setPoisonResist(int poisonResist) {
        this.poisonResist = poisonResist;
    }

    public int getArcaneResist() {
        return arcaneResist;
    }

    public void setArcaneResist(int arcaneResist) {
        this.arcaneResist = arcaneResist;
    }

    public double getLifeOnHit() {
        return lifeOnHit;
    }

    public void setLifeOnHit(double lifeOnHit) {
        this.lifeOnHit = lifeOnHit;
    }

    public double getLifePerKill() {
        return lifePerKill;
    }

    public void setLifePerKill(double lifePerKill) {
        this.lifePerKill = lifePerKill;
    }

    public double getLifeSteal() {
        return lifeSteal;
    }

    public void setLifeSteal(double lifeSteal) {
        this.lifeSteal = lifeSteal;
    }

    public float getMagicFind() {
        return magicFind;
    }

    public void setMagicFind(float magicFind) {
        this.magicFind = magicFind;
    }

    public float getGoldFind() {
        return goldFind;
    }

    public void setGoldFind(float goldFind) {
        this.goldFind = goldFind;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getExperienceBonus() {
        return experienceBonus;
    }

    public void setExperienceBonus(int experienceBonus) {
        this.experienceBonus = experienceBonus;
    }
}