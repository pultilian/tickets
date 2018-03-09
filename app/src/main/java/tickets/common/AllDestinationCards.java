package tickets.common;

import java.util.ArrayList;

import java.util.List;

// This is basically a fancy enumeration of all destination
// cards in the game. I'm using a fimal (static) class so that 
// the .equals() method can be overridden to allow X->Y == Y->X
public final class AllDestinationCards {
	public static final DestinationCard AEONTACHT_MAGMARSE =
					new DestinationCard("Aeontacht", "Magmarse", 4);
	public static final DestinationCard ICOCOL_WARFELD =
					new DestinationCard("Ico-Col", "Warfeld", 5);
	public static final DestinationCard SPHERAS_DALLAMAN =
					new DestinationCard("Spheras", "Dallaman", 6);
	public static final DestinationCard NONNOG_KIFLAMAR =
					new DestinationCard("Nonnog", "Kiflamar", 7);
	public static final DestinationCard ORTHOK_CREPUSCULON =
					new DestinationCard("Orthok", "Crepusculon", 7);
	public static final DestinationCard BOISEY_LIN =
					new DestinationCard("Boisey", "Lin", 8);
	public static final DestinationCard AYON_CASTINE =
					new DestinationCard("Ayon", "Castine", 8);
	public static final DestinationCard PARADUS_WARFELD =
					new DestinationCard("Paradus", "Warfeld", 8);
	public static final DestinationCard EXEN_DALLAMAN =
					new DestinationCard("Exen", "Dallaman", 9);
	public static final DestinationCard ZEEATLL_LIN =
					new DestinationCard("Zee-A'tll", "Lin", 9);
	public static final DestinationCard ORTHOK_FRACTINE =
					new DestinationCard("Orthok", "Fractine", 9);
	public static final DestinationCard AYON_AEUONI =
					new DestinationCard("Ayon", "Aeuoni",9);
	public static final DestinationCard ASTERN_ALTIERE =
					new DestinationCard("Astern", "Altiere", 10);
	public static final DestinationCard PARADUS_MAGMARSE =
					new DestinationCard("Paradus", "Magmarse", 10);
	public static final DestinationCard KERRECTICE_LITTLEROCK =
					new DestinationCard("Kerrectice", "Little Rock", 11);
	public static final DestinationCard BYNODIA_SPHERAS =
					new DestinationCard("Bynodia", "Spheras", 11);
	public static final DestinationCard STRATUS_IGIO =
					new DestinationCard("Stratus", "Igio", 11);
	public static final DestinationCard AEONTACHT_PETRAQA =
					new DestinationCard("Aeontacht", "Petraqa", 11);
	public static final DestinationCard WENCE_ALTIERE =
					new DestinationCard("Wence", "Altiere", 12);
	public static final DestinationCard KERRECTICE_WARFELD =
					new DestinationCard("Kerrectice", "Warfeld", 12);
	public static final DestinationCard VERDONA_FRACTINE =
					new DestinationCard("Verdona", "Fractine", 13);
	public static final DestinationCard EXEN_CREPUSCULON =
					new DestinationCard("Exen", "Crepusculon", 13);
	public static final DestinationCard NONNOG_IGIO =
					new DestinationCard("Nonnog", "Igio", 13);
	public static final DestinationCard LIN_ORTHOK =
					new DestinationCard("Lin", "Orthok", 16);
	public static final DestinationCard STRATUS_CASTINE =
					new DestinationCard("Stratus", "Castine", 17);
	public static final DestinationCard JAQUALIND_DALLAMAN =
					new DestinationCard("Jaqualind", "Dallaman", 17);
	public static final DestinationCard VERDONA_EXEN =
					new DestinationCard("Verdona", "Exen", 20);
	public static final DestinationCard LIN_ALTIERE =
					new DestinationCard("Lin", "Altiere", 20);
	public static final DestinationCard LIN_SPHERAS =
					new DestinationCard("Lin", "Spheras", 21);
	public static final DestinationCard ZEEATLL_SPHERAS =
					new DestinationCard("Zee-A'tll", "Spheras", 222);

	// Since all routes are undirected, the
	//	 source-destination distinction is only internal
	/*private final String srcCityName;
	private final String destCityName;
	private final int value;

	private AllDestinationCards() {
		this.srcCityName = srcCityName;
		this.destCityName = destCityName;
		this.value = value;
	}*/

	public static List<DestinationCard> getCards() {

		List<DestinationCard> cards = new ArrayList<>();
		cards.add(AEONTACHT_MAGMARSE);
		cards.add(ICOCOL_WARFELD);
		cards.add(SPHERAS_DALLAMAN);
		cards.add(NONNOG_KIFLAMAR);
		cards.add(ORTHOK_CREPUSCULON);
		cards.add(BOISEY_LIN);
		cards.add(AYON_CASTINE);
		cards.add(PARADUS_WARFELD);
		cards.add(EXEN_DALLAMAN);
		cards.add(ZEEATLL_LIN);
		cards.add(ORTHOK_FRACTINE);
		cards.add(AYON_AEUONI);
		cards.add(ASTERN_ALTIERE);
		cards.add(PARADUS_MAGMARSE);
		cards.add(KERRECTICE_LITTLEROCK);
		cards.add(BYNODIA_SPHERAS);
		cards.add(STRATUS_IGIO);
		cards.add(AEONTACHT_PETRAQA);
		cards.add(WENCE_ALTIERE);
		cards.add(KERRECTICE_WARFELD);
		cards.add(VERDONA_FRACTINE);
		cards.add(EXEN_CREPUSCULON);
		cards.add(NONNOG_IGIO);
		cards.add(LIN_ORTHOK);
		cards.add(STRATUS_CASTINE);
		cards.add(JAQUALIND_DALLAMAN);
		cards.add(VERDONA_EXEN);
		cards.add(LIN_ALTIERE);
		cards.add(LIN_SPHERAS);
		cards.add(ZEEATLL_SPHERAS);
		return cards;
	}
}


/*


ALL DESTINATION CARDS - 30 total cards

 1. Aeontacht, Magmarse, 4
 2. Ico-Col, Warfeld, 5
 3. Spheras, Dallaman, 6
 4. Nonnog, Kiflamar, 7
 5. Orthok, Crepusculon, 7
 6. Boisey, Lin, 8
 7. Ayon, Castine, 8
 8. Paradus, Warfeld, 8
 9. Exen, Dallaman, 9
10. Zee-A'tll, Lin, 9
11. Orthok, Fractine, 9
12. Ayon, Aeuoni,9
13. Astern, Altiere, 10
14. Paradus, Magmarse, 10
15. Kerrectice, Little Rock, 11
16. Bynodia, Spheras, 11
17. Stratus, Igio, 11
18. Aeontacht, Petraqa, 11
19. Wence, Altiere, 12
20. Kerrectice,Warfeld,12
21. Verdona, Fractine, 13
22. Exen, Crepusculon, 13
23. Nonnog, Igio, 13
24. Lin, Orthok, 16
25. Stratus, Castine, 17
26. Jaqualind, Dallaman, 17
27. Verdona, Exen, 20
28. Lin, Altiere, 20
29. Lin, Spheras, 21
30. Zee-A'tll, Spheras, 22

*/