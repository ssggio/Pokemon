package Area;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import General.Pokemon;
import General.PokemonData;
import General.PokemonType;
import Trainer.GymLeader;

public class Area {

	private final String name;
	private final Area nextArea;
	private final Random r = new Random();
	private final Pokecenter pokecenter;
	private List<PokemonType> containsPokemon = new ArrayList<>();
	private boolean isUnlocked;
	private GymLeader gymLeader;

	public Area(String name, GymLeader gymLeader, boolean isUnlocked, Area nextArea, Pokecenter pokecenter) {
		this.name = name;
		this.gymLeader = gymLeader;
		this.isUnlocked = isUnlocked;
		this.nextArea = nextArea;
		this.pokecenter = pokecenter;
	}

	public void setContainsPokemon(List<PokemonType> containsPokemon) {
		this.containsPokemon = containsPokemon;
	}

	public String getName() {
		return name;
	}

	public boolean isUnlocked() {
		return isUnlocked;
	}

	public void setUnlocked(boolean unlocked) {
		isUnlocked = unlocked;
	}

	public GymLeader getGymLeader() {
		return gymLeader;
	}

	public void setGymLeader(GymLeader gymLeader) {
		this.gymLeader = gymLeader;
	}

	public Area getNextArea() {
		return nextArea;
	}

	// TODO: US-PKM-O-6
	public Pokemon getRandomPokemonFromArea(int level) {

		return null;
	}

	public Pokecenter getPokecenter() {
		return pokecenter;
	}
}
