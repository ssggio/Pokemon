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
		List<PokemonData> pokeData = Arrays.asList(PokemonData.values());
		ArrayList<PokemonData> collect = new ArrayList<PokemonData>();
		for (PokemonData p : pokeData) {
			if (containsPokemon.contains(p.pokemonType)) {
				collect.add(p);
			}
			}
		int randomPokemonIndex = r.nextInt(collect.size());
		PokemonData randomPokemonData = collect.get(randomPokemonIndex);
		Pokemon randomPokemon = new Pokemon(randomPokemonData);
		int lowestLevel = level - 5 > 0? level - 5 : level;
		int highestLevel = lowestLevel + level;
		int randomlevel = r.nextInt(highestLevel - lowestLevel);
		randomPokemon.setLevel(randomlevel);
		randomPokemon.setMaxHp(randomPokemon.getLevel()*10);
		randomPokemon.setCurrentHp(randomPokemon.getMaxHp());
		return randomPokemon;
	}

	public Pokecenter getPokecenter() {
		return pokecenter;
	}
}
