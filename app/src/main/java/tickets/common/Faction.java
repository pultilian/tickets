package tickets.common;

public enum Faction {
    Player1("player1", PlayerColor.red),
    Player2("player2", PlayerColor.blue),
    Player3("player3", PlayerColor.yellow),
    Player4("player4", PlayerColor.green),
    Player5("player5", PlayerColor.black);
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
