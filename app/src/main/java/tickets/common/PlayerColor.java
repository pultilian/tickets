package tickets.common;

public enum PlayerColor {
    blue("blue"), green("green"), yellow("yellow"), black("black"), red("red");

    private String color;

    private PlayerColor(String color){
        this.color = color;
    }

    public String toString(){
        return this.color;
    }

}
