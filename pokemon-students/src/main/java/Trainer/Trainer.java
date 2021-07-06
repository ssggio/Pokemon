package Trainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import Area.Area;
import Area.Pokecenter;
import Battle.Battle;
import General.Pokemon;
import General.PokemonType;
import Item.Inventory;
import Item.ItemType;

public class Trainer {
	private final String name;
	private final ArrayList<Pokemon> pokemonCollection = new ArrayList<>();
	//
	private final ArrayList<Pokemon> Pokedex = new ArrayList<>();
	//
	private final Inventory inventory = new Inventory();
	private final Random r = new Random();
	private final List<Badge> badges = new ArrayList<>();
	private Pokemon activePokemon;
	private Area currentArea;
	private double money = 100;

	public Trainer(String name, Area startingArea) {
		this.name = name;
		this.inventory.addItem(5, ItemType.POKEBALL);
		this.currentArea = startingArea;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public Pokemon getActivePokemon() {
		return activePokemon;
	}

	public void setActivePokemon(Pokemon activePokemon) {
		this.activePokemon = activePokemon;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Pokemon> getPokemonCollection() {
		return pokemonCollection;
	}
	public ArrayList<Pokemon> getpokedex() {
		return Pokedex;
	}

	public List<Badge> getBadges() {
		return badges;
	}

	public Area getCurrentArea() {
		return currentArea;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	private void setCurrentArea(Area currentArea) {
		this.currentArea = currentArea;
	}

	// TODO: US-PKM-O-5:
	public Battle battle(Pokemon myPokemon, Pokemon otherPokemon) {
		if (myPokemon.getOwner() != null && myPokemon.getOwner().equals(this)) {
			Battle battle = new Battle(myPokemon, otherPokemon, this);
			return battle;
		} else {
			return null;
		}
	}

	// userstory 7 t/m 12 samen met cas niels en bowen gemaakt
	// TODO: US-PKM-O-7
	private boolean catchPokemon(Pokemon pokemon) {
		if (pokemon.getOwner() != null) {
			return false;
		}
		int catchChance = r.nextInt(100);
		if (catchChance > 50) {
			pokemonCollection.add(pokemon);
			pokemon.setOwner(this);
			System.out.println("");
			System.out.println("you've caught: " + pokemon.getPokedata());
			System.out.println("");
			if (activePokemon == null) {
				setActivePokemon(activePokemon);
			}
			return true;
		}
		System.out.println("Awh shoot you didn't press B enough times. ");
		return false;
	}

	public List<Pokemon> getPokemonByType(PokemonType pokemonType) {
		return pokemonCollection.stream().filter(p -> p.getPokedata().pokemonType.equals(pokemonType))
				.collect(Collectors.toList());
	}

	public void useItem(ItemType item, Battle battle) {
		if (battle == null) {
			System.out.println("Used: " + item.name());
			return;
		}
		switch (item) {
		case POKEBALL:
			if (this.catchPokemon(battle.getEnemy())) {
				battle.setBattleComplete(true);
				battle.setWinner(battle.getMyPokemon());
			}
			this.inventory.removeItem(ItemType.POKEBALL);
			break;
		default:
			break;
		}
	}

	// TODO: US-PKM-O-8
	public Battle challengeTrainer(Trainer opponent) {
		battle(getActivePokemon(), opponent.activePokemon);
		Battle battle = new Battle(getActivePokemon(), opponent.activePokemon, this);
		battle.start();
		return battle;
	}

	// TODO: US-PKM-O-11
	public void travel(Area area) {
		this.setCurrentArea(area);
	}

	// TODO: US-PKM-O-3
	public void showPokemonColletion() {
		for (Pokemon p : pokemonCollection) {
			p.status();
		}
	}

	// TODO: US-PKM-O-6
	public Pokemon findPokemon() {
		boolean isSearching = true;
		while (isSearching) {
			int findChance = r.nextInt(100);
			if (findChance > 80) {
				Pokemon foundPokemon = currentArea.getRandomPokemonFromArea(activePokemon.getLevel());
				return foundPokemon;
			} else {
				try {
					Thread.sleep(2000);
				} catch (Exception e) {

				}
			}
		}
		return null;
	}

	// TODO: US-PKM-O-10
	public void showBadges() {
		for (Badge b : badges) {
			b.getName();
	        System.out.println("");
	        if(b != null) {
	        	System.out.println("you don't have any badges");
	        }
		}
	}

	// TODO: US-PKM-O-9
	public void addBadge(Badge newBadge) {
		badges.add(newBadge);
	}

	// TODO: US-PKM-O-5:
	public boolean canBattle() {
		for (Pokemon p : pokemonCollection) {

			if (!p.isKnockout()) {

				return true;
			}
		}
		return false;
	}

	// TODO: US-PKM-O-12
	public void visitPokeCenter(Pokecenter pokecenter) {
		if (pokecenter != null) {
			ArrayList<Pokemon> pokemonToHeal = getPokemonCollection();
			pokecenter.healPokemon(pokemonToHeal);
			System.out.println("nurse joy: your pokemon have been healed");
		}

	}
}