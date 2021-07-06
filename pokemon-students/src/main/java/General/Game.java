package General;

import java.util.*;

import Area.Area;
import Area.Pokecenter;
import Battle.Battle;
import Item.ItemType;
import Trainer.Badge;
import Trainer.GymLeader;
import Trainer.Trainer;

public class Game {

	private static final ArrayList<Area> areas = new ArrayList<>();
	private static final Scanner sc = new Scanner(System.in);
	public static Trainer trainer = null;

	// set up the game in this static block

	static {

		// PEWTER City
		Pokecenter pewterCenter = new Pokecenter("Pewter City's Pokecenter");
		Area pewterCity = new Area("Pewter city", null, true, null, pewterCenter);
		pewterCity.setContainsPokemon(
				Arrays.asList(PokemonType.GRASS, PokemonType.FLYING, PokemonType.BUG, PokemonType.GROUND));

		// VIRIDIAN City
		Pokecenter viridianCenter = new Pokecenter("Viridian City's Pokecenter");
		Area viridianCity = new Area("Viridian city", null, true, pewterCity, viridianCenter);
		viridianCity.setContainsPokemon(
				Arrays.asList(PokemonType.GRASS, PokemonType.FLYING, PokemonType.BUG, PokemonType.GROUND));

		// PALLET Town
		Pokecenter palletCenter = new Pokecenter("Pallet Town's Pokecenter");
		Area palletTown = new Area("Pallet town", null, true, viridianCity, palletCenter);
		palletTown.setContainsPokemon(
				Arrays.asList(PokemonType.GRASS, PokemonType.FLYING, PokemonType.BUG, PokemonType.GROUND));

		areas.add(palletTown);
		areas.add(viridianCity);
		areas.add(pewterCity);

		// SETUP gym leaders
		GymLeader pewterLeader = new GymLeader("Bram", new Badge("Boulder Badge"), pewterCity);
		Pokemon p = new Pokemon(PokemonData.ONIX);
		p.setLevel(5);
		p.setOwner(pewterLeader);
		pewterLeader.setActivePokemon(p);
		pewterLeader.getPokemonCollection().add(p);
		pewterCity.setGymLeader(pewterLeader);
	}

	public static void main(String[] args) {
		System.out.println("Welcome new trainer, what's your name?");
		String name = sc.nextLine();
		trainer = new Trainer(name, areas.get(0));
		System.out.println("Hi, " + trainer.getName());

		Pokemon firstPokemon = chooseFirstPokemon(0);
		firstPokemon.setOwner(trainer);
		trainer.getPokemonCollection().add(firstPokemon);
		System.out.println("You now have " + trainer.getPokemonCollection().size() + " pokemon in your collection!");

		// game loop
		while (true) {
			showGameOptions();
		}
	}

	private static void showGameOptions() {
		System.out.println("What do you want to do?");
		System.out.println("1 ) Find Pokemon");
		System.out.println("2 ) My Pokemon");
		System.out.println("3 ) Inventory");
		System.out.println("4 ) Badges");
		System.out.println("5 ) Challenge " + trainer.getCurrentArea().getName() + "'s Gym Leader");
		System.out.println("6 ) Travel");
		System.out.println("7 ) Visit Pokecenter");
		System.out.println("8 ) Exit game");
		System.out.println("9 ) Open PokeDex [W.I.P.]");
		int action = sc.nextInt();
		switch (action) {
		case 1:
			findAndBattlePokemon();
			break;
		case 2:
			trainer.showPokemonColletion();
			break;
		case 3:
			ItemType item = showInventory();
			if (item != null) {
				trainer.useItem(item, null);
			}
			break;
		case 4:
			trainer.showBadges();
			break;
		case 5:
			if (trainer.getCurrentArea().getGymLeader() != null) {
				startGymBattle();
			} else {
				System.out.println("No Gym Leader in this town!");
			}
			break;
		case 6:
			Area area = showTravel();
			if (area != null) {
				trainer.travel(area);
			}
			break;
		case 7:
			trainer.visitPokeCenter(trainer.getCurrentArea().getPokecenter());
			break;
		case 8:
			quit();
			break;
		case 9:
			
			System.out.println("deze functie is nog niet werkent");
			break;
		default:
			System.out.println("Sorry, that's not a valid option");
			break;
		}
	}

	// TODO: US-PKM-O-6
	private static void findAndBattlePokemon() {
		Pokemon randomPokemon = trainer.findPokemon();
		Battle battle = trainer.battle(trainer.getActivePokemon(), randomPokemon);
		battle.start();
	}

	private static Area showTravel() {
		Area travelTo = null;
		int index = 1;
		List<Area> travelToAreas = new ArrayList<>();

		for (Area area : areas) {
			if (!area.equals(trainer.getCurrentArea()) && area.isUnlocked()
					&& ((area.getNextArea() != null && area.getNextArea().equals(trainer.getCurrentArea()))
							|| trainer.getCurrentArea().getNextArea() != null
									&& trainer.getCurrentArea().getNextArea().equals(area))) {
				travelToAreas.add(area);
			}
		}
		for (Area a : travelToAreas) {
			System.out.println(index + ") " + a.getName());
			index++;
		}
		System.out.println(index + ") Back");
		int choice = sc.nextInt();
		if (choice != index) {
			travelTo = travelToAreas.get(choice - 1);
		}
		return travelTo;
	}

	private static ItemType showInventory() {
		HashMap<ItemType, Integer> items = trainer.getInventory().getItems();
		Set<Map.Entry<ItemType, Integer>> entries = items.entrySet();
		int index = 1;
		for (Map.Entry<ItemType, Integer> entry : entries) {
			System.out.println(index + ") " + entry.getKey() + " " + entry.getValue());
			index++;
		}
		System.out.println(index + ") Back");
		int choice = sc.nextInt();
		if (choice != index) {
			return ItemType.values()[choice - 1];
		}
		return null;
	}

	// TODO: US-PKM-O-1
	public static Pokemon chooseFirstPokemon(int choice) {
		if (choice == 0) {
			System.out.println("Please choose one of these three pokemon");
			System.out.println("1 ) Charmander");
			System.out.println("2 ) Bulbasaur");
			System.out.println("3 ) Squirtle");
			choice = sc.nextInt();
		}
		if (choice == 1) {
			Pokemon chosenPokemon = new Pokemon(PokemonData.CHARMANDER);
			chosenPokemon.setLevel(5);
			trainer.setActivePokemon(chosenPokemon);
			System.out.println(PokemonData.CHARMANDER.art);
			System.out.println("");
			return chosenPokemon;

		} else if (choice == 2) {
			Pokemon chosenPokemon = new Pokemon(PokemonData.BULBASAUR);
			chosenPokemon.setLevel(5);
			trainer.setActivePokemon(chosenPokemon);
			System.out.println(PokemonData.BULBASAUR.art);
			System.out.println("");
			return chosenPokemon;

		} else if (choice == 3) {
			Pokemon chosenPokemon = new Pokemon(PokemonData.SQUIRTLE);
			chosenPokemon.setLevel(5);
			trainer.setActivePokemon(chosenPokemon);
			System.out.println(PokemonData.SQUIRTLE.art);
			System.out.println("");
			return chosenPokemon;

		} else {

		}
		return null;

	}
    //casten want dat was de oplossing
	//public class Cast{
		
		//	GymLeader Gymleader = new GymLeader(String, Badge, areas);
		//      Trainer sup = (Trainer) Gymleader; 
		// }
	

	// TODO: US-PKM-O-8
	private static void startGymBattle() {
		Battle trainerBattle = trainer.challengeTrainer(Game.trainer.getCurrentArea().getGymLeader());
		if (trainerBattle != null && trainerBattle.getWinner().getOwner().equals(trainer)) {
			if (trainerBattle.getEnemy().getOwner().getClass().equals(GymLeader.class)) {
				Pokemon enemyPokemon = trainerBattle.getEnemy();
				Trainer gymLeader = enemyPokemon.getOwner();
				((GymLeader)gymLeader).setDefeated(true);
				awardBadge(((GymLeader)gymLeader).getBadge().getName());
				Area gymLeaderArea = gymLeader.getCurrentArea();
				Area nextArea = gymLeaderArea.getNextArea();
				if (nextArea != null) {
					nextArea.setUnlocked(true);
				}
			}
		}

	}

	// TODO: US-PKM-O-9
	public static void awardBadge(String badgeName) {
		Badge badgename = new Badge(badgeName);
		trainer.addBadge(badgename);
	}

	public static void gameOver(String message) {
		System.out.println(message);
		System.out.println("Game over");
		quit();
	}

	// TODO: US-PKM-O-2:
	private static void quit() {
		System.exit(0);
	}
}
