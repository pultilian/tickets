package tickets.common;

import java.util.List;

public class TrainCardWrapper {
    private List<TrainCard> trainCards;

    public TrainCardWrapper(List<TrainCard> trainCards) {
        this.trainCards = trainCards;
    }

    public List<TrainCard> getTrainCards() {
        return trainCards;
    }
}
