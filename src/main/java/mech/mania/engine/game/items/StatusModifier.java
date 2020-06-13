package mech.mania.engine.game.items;

public class StatusModifier {
    private int flatSpeedChange;
    private float percentSpeedChange;
    private int flatHealthChange;
    private float percentHealthChange;
    private int flatExperienceChange;
    private float percentExperienceChange;
    private int flatPhysicalDamageChange;
    private float percentPhysicalDamageChange;
    private int flatMagicDamageChange;
    private float percentMagicDamageChange;
    private int flatPhysicalDefenseChange;
    private float percentPhysicalDefenseChange;

    private int flatMagicDefenseChange;
    private float percentMagicDefenseChange;

    public StatusModifier(int flatSpeedChange, float percentSpeedChange,
                          int flatHealthChange, float percentHealthChange,
                          int flatExperienceChange, float percentExperienceChange,
                          int flatPhysicalDamageChange, float percentPhysicalDamageChange,
                          int flatMagicDamageChange, float percentMagicDamageChange,
                          int flatPhysicalDefenseChange, float percentPhysicalDefenseChange,
                          int flatMagicDefenseChange, float percentMagicDefenseChange) {
        this.flatSpeedChange = flatSpeedChange;
        this.percentSpeedChange = percentSpeedChange;
        this.flatHealthChange = flatHealthChange;
        this.percentHealthChange = percentHealthChange;
        this.flatExperienceChange = flatExperienceChange;
        this.percentExperienceChange = percentExperienceChange;
        this.flatPhysicalDamageChange = flatPhysicalDamageChange;
        this.percentPhysicalDamageChange = percentPhysicalDamageChange;
        this.flatMagicDamageChange = flatMagicDamageChange;
        this.percentMagicDamageChange = percentMagicDamageChange;
        this.flatPhysicalDefenseChange = flatPhysicalDefenseChange;
        this.percentPhysicalDefenseChange = percentPhysicalDefenseChange;
        this.flatMagicDefenseChange = flatMagicDefenseChange;
        this.percentMagicDefenseChange = percentMagicDefenseChange;
    }

    public StatusModifier(ItemProtos.StatusModifier statusModifierProto) {
        this.flatSpeedChange = statusModifierProto.getFlatSpeedChange();
        this.percentSpeedChange = statusModifierProto.getPercentSpeedChange();
        this.flatHealthChange = statusModifierProto.getFlatHealthChange();
        this.percentHealthChange = statusModifierProto.getPercentHealthChange();
        this.flatExperienceChange = statusModifierProto.getFlatExperienceChange();
        this.percentExperienceChange = statusModifierProto.getPercentExperienceChange();
        this.flatPhysicalDamageChange = statusModifierProto.getFlatPhysicalDamageChange();
        this.percentPhysicalDamageChange = statusModifierProto.getPercentPhysicalDamageChange();
        this.flatMagicDamageChange = statusModifierProto.getFlatMagicDamageChange();
        this.percentMagicDamageChange = statusModifierProto.getPercentMagicDamageChange();
        this.flatPhysicalDefenseChange = statusModifierProto.getFlatPhysicalDefenseChange();
        this.percentPhysicalDefenseChange = statusModifierProto.getPercentPhysicalDefenseChange();
        this.flatMagicDefenseChange = statusModifierProto.getFlatMagicDefenseChange();
        this.percentMagicDefenseChange = statusModifierProto.getPercentMagicDefenseChange();
    }

    public ItemProtos.StatusModifier buildProtoClass() {
        ItemProtos.StatusModifier.Builder statusModifierBuilder = ItemProtos.StatusModifier.newBuilder();

        statusModifierBuilder.setFlatSpeedChange(flatSpeedChange);
        statusModifierBuilder.setPercentSpeedChange(percentSpeedChange);
        statusModifierBuilder.setFlatHealthChange(flatHealthChange);
        statusModifierBuilder.setPercentHealthChange(percentHealthChange);
        statusModifierBuilder.setFlatExperienceChange(flatExperienceChange);
        statusModifierBuilder.setPercentExperienceChange(percentExperienceChange);
        statusModifierBuilder.setFlatPhysicalDamageChange(flatPhysicalDamageChange);
        statusModifierBuilder.setPercentPhysicalDamageChange(percentPhysicalDamageChange);
        statusModifierBuilder.setFlatMagicDamageChange(flatMagicDamageChange);
        statusModifierBuilder.setPercentMagicDamageChange(percentMagicDamageChange);
        statusModifierBuilder.setFlatPhysicalDefenseChange(flatPhysicalDefenseChange);
        statusModifierBuilder.setPercentPhysicalDefenseChange(percentPhysicalDefenseChange);
        statusModifierBuilder.setFlatMagicDefenseChange(flatMagicDefenseChange);
        statusModifierBuilder.setPercentMagicDefenseChange(percentMagicDefenseChange);

        return statusModifierBuilder.build();
    }


    public int getFlatSpeedChange() {
        return flatSpeedChange;
    }

    public void setFlatSpeedChange(int flatSpeedChange) {
        this.flatSpeedChange = flatSpeedChange;
    }

    public float getPercentSpeedChange() {
        return percentSpeedChange;
    }

    public void setPercentSpeedChange(float percentSpeedChange) {
        this.percentSpeedChange = percentSpeedChange;
    }

    public int getFlatHealthChange() {
        return flatHealthChange;
    }

    public void setFlatHealthChange(int flatHealthChange) {
        this.flatHealthChange = flatHealthChange;
    }

    public float getPercentHealthChange() {
        return percentHealthChange;
    }

    public void setPercentHealthChange(float percentHealthChange) {
        this.percentHealthChange = percentHealthChange;
    }

    public int getFlatExperienceChange() {
        return flatExperienceChange;
    }

    public void setFlatExperienceChange(int flatExperienceChange) {
        this.flatExperienceChange = flatExperienceChange;
    }

    public float getPercentExperienceChange() {
        return percentExperienceChange;
    }

    public void setPercentExperienceChange(float percentExperienceChange) {
        this.percentExperienceChange = percentExperienceChange;
    }

    public int getFlatPhysicalDamageChange() {
        return flatPhysicalDamageChange;
    }

    public void setFlatPhysicalDamageChange(int flatPhysicalDamageChange) {
        this.flatPhysicalDamageChange = flatPhysicalDamageChange;
    }

    public float getPercentPhysicalDamageChange() {
        return percentPhysicalDamageChange;
    }

    public void setPercentPhysicalDamageChange(float percentPhysicalDamageChange) {
        this.percentPhysicalDamageChange = percentPhysicalDamageChange;
    }

    public int getFlatMagicDamageChange() {
        return flatMagicDamageChange;
    }

    public void setFlatMagicDamageChange(int flatMagicDamageChange) {
        this.flatMagicDamageChange = flatMagicDamageChange;
    }

    public float getPercentMagicDamageChange() {
        return percentMagicDamageChange;
    }

    public void setPercentMagicDamageChange(float percentMagicDamageChange) {
        this.percentMagicDamageChange = percentMagicDamageChange;
    }

    public int getFlatPhysicalDefenseChange() {
        return flatPhysicalDefenseChange;
    }

    public void setFlatPhysicalDefenseChange(int flatPhysicalDefenseChange) {
        this.flatPhysicalDefenseChange = flatPhysicalDefenseChange;
    }

    public float getPercentPhysicalDefenseChange() {
        return percentPhysicalDefenseChange;
    }

    public void setPercentPhysicalDefenseChange(float percentPhysicalDefenseChange) {
        this.percentPhysicalDefenseChange = percentPhysicalDefenseChange;
    }

    public int getFlatMagicDefenseChange() {
        return flatMagicDefenseChange;
    }

    public void setFlatMagicDefenseChange(int flatMagicDefenseChange) {
        this.flatMagicDefenseChange = flatMagicDefenseChange;
    }

    public float getPercentMagicDefenseChange() {
        return percentMagicDefenseChange;
    }

    public void setPercentMagicDefenseChange(float percentMagicDefenseChange) {
        this.percentMagicDefenseChange = percentMagicDefenseChange;
    }
    
}
