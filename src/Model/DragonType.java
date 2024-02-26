package Model;

public enum DragonType {
    WATER,
    UNDERGROUND,
    AIR,
    FIRE;

    public static DragonType parseTypeFromStr(String strType) {
        return switch (strType) {
            case "WATER" -> DragonType.WATER;
            case "AIR" -> DragonType.AIR;
            case "UNDERGROUND" -> DragonType.UNDERGROUND;
            case "FIRE" -> DragonType.FIRE;
            default -> null;
        };
    }

}