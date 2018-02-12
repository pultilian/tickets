
package tickets.common.response.data;


//represents a change to a client's location.
//this includes new lobbies added to the lobby list,
//  old lobbies removed from the lobby list,
//  new entries added to a lobby's history,
//  and a lobby being turned into a game.
//    - granted, those will probably need subclasses.
//
//Having a single parent class allows the server to maintain
//  a queue of updates for each client; clients calling
//	getClientUpdate() will receive the first update on the queue
//		- I don't really know if that's te best way to do it in all cases,
//		  especially updates for lobby list data. It'd be fancy if somehow
//			a single, compound update could be sent with all of the
//			new information (and marking which values are outdated and
//			need to be removed). So maybe a single update could be created
//			from all of the changes recorded in the client's queue.
public abstract class ClientUpdate {
	//
}