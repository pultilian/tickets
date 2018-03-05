package tickets.common;

public enum Faction {
    Player1("Tacht", PlayerColor.red),
    Player2("Altian", PlayerColor.blue),
    Player3("Kit", PlayerColor.yellow),
    Player4("Pathian", PlayerColor.green),
    Player5("Murtoken", PlayerColor.black);
    private final String name;
    private final PlayerColor color;
    private Faction(final String name, final PlayerColor color) {
        this.name = name;
        this.color = color;
    }
    public String getName() {
        return name;
    }
    public PlayerColor getColor(){
        return color;
    }
}
