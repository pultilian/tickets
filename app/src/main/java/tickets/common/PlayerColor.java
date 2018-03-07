package tickets.common;

public enum PlayerColor {
    blue("blue"), green("blue"), yellow("blue"), black("blue"), red("blue");

    private String color;

    private PlayerColor(String color){
        this.color = color;
    }

    public String toString(){
        return this.color;
    }

}
