package tickets.common;

public class ClientModelUpdate implements IMessage {

    public enum ModelUpdate{
        playerTrainHandUpdated,
        faceUpCardUpdated,
        playerDestHandUpdated,
        destCardOptionsUpdated,
        gameHistoryUpdated,
        playerInfoUpdated,
        chatUpdated,
        mapUpdated,
    }

    private ModelUpdate update;

    public ClientModelUpdate(ModelUpdate update) {
        this.update= update;
    }

    @Override
    public Object getMessage() {
        return update;
    }
}
