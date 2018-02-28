# tickets
Ticket to ride by the BYU CS340 Super Group

## branch TODO: vertical-cards ##
	1. finish ServerGame and ServerPlayer interactions
		* between the two classes, all game functionality should be defined
		1. implement DestinationDeck constructor 
			* decide where all destination cards are created (DestinationDeck or ServerGame)
		2. implement TrainCardArea constructor
			- decide where all train cards are created (TrainCardArea or ServerGame)
		3. fill in stub methods on ServerFacade
			1. drawTrainCard
			2. drawFaceUpCard
			3. drawDestinationCard
			4. chooseDestinationCards
	2. begin implementation within the Client