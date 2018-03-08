package tickets.common;

public class ClientModelUpdate implements IMessage {

    public enum ModelUpdate{
        updatePlayerCard,
        updateFaceUpCard,
        updatePlayerDestCards,
        updateGameHistory,
        updateChat,
        updateMap
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
