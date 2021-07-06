package General;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Battle.Attack;
import Trainer.Trainer;

public class Pokemon implements Serializable  {

	private final int maxPP;
	private final int currentPP;
	private final Random r = new Random();
	private PokemonData pokedata;
	private Trainer owner;
	private int currentHp;
	private int maxHp;
	public  int level;
	private int xpToNext;
	private int currentXp;

	public Pokemon(PokemonData pokedata) {
		this.pokedata = pokedata;
		this.currentHp = pokedata.maxHp;
		this.maxHp = pokedata.maxHp;
		this.currentPP = pokedata.maxPp;
		this.maxPP = pokedata.maxPp;
		this.level = 5;
		this.xpToNext = 8;
		this.currentXp = 0;
	}

	public PokemonData getPokedata() {
		return pokedata;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		this.currentHp = currentHp;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Trainer getOwner() {
		return owner;
	}

	public void setOwner(Trainer owner) {
		this.owner = owner;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	private int getXpToNext() {
		return xpToNext;
	}

	public int getCurrentXp() {
		return currentXp;
	}

	public void setCurrentXp(int currentXp) {
		this.currentXp = currentXp;
	}

	public List<Attack> getAttacks() {
		List<Attack> attacks = new ArrayList<>();
		attacks.addAll(pokedata.specificAttacks);
		attacks.addAll(pokedata.pokemonType.typeAttacks);
		return attacks;
	}

	// TODO: US-PKM-O-4E
	private void evolve() {
    this.pokedata = pokedata.evolvesIn;
	}
	// TODO: US-PKM-O-4D
	public void gainXp(int amount) {
		if (amount + currentXp >= xpToNext) {
			levelUp();
			int remainingXp = (currentXp + amount) - xpToNext;
			gainXp(remainingXp);
		} else {
			this.setCurrentXp(currentXp + amount);
			status();
		}
	}

	// TODO: US-PKM-O-4C
	private void levelUp() {
		int hpIncrease = r.nextInt(100);
		setLevel(level +1);
		setCurrentXp(0);
		setMaxHp(maxHp + hpIncrease);
		setCurrentHp(currentHp + hpIncrease);
		if (evolveCheck() == true) {
			evolve();
		}
	}

	// TODO: US-PKM-O-4E
	private boolean evolveCheck() {
		if (pokedata.evolvesIn != null && level >= pokedata.evolutionLevel) {
			return true;
		} else {
			return false;
		}
	}

	// TODO: US-PKM-O-4
	public void attack(Pokemon otherPokemon, Attack attack) {
		int power = attack.getPower();
		if (otherPokemon.getCurrentHp() - power < 0) {
			otherPokemon.setCurrentHp(0);
		} else {
			setCurrentHp(otherPokemon.getCurrentHp() - power);
		}
	}

	// TODO: US-PKM-O-4B
	public Attack getRandomAttack() {
		List<Attack> attackList = getAttacks();
		int attackSize = attackList.size();
		int randomAttackIndex = r.nextInt(attackSize);
		Attack randomAttack = attackList.get(randomAttackIndex);
		return randomAttack;
	}

	public double getHpPercentage() {
		return currentHp / maxHp * 100;
	}

	public void status() {
		System.out.println("----------------");
		System.out.println(this.getPokedata().name());
		System.out.println("LVL " + this.level);
		System.out.println("HP " + this.currentHp + "/" + this.maxHp);
		System.out.println("PP " + this.currentPP + "/" + this.maxPP);
		System.out.println("XP " + this.currentXp + "/" + this.xpToNext);
	}

	// TODO: US-PKM-O-5
	public boolean isKnockout() {
		if (currentHp <= 0) {
			return true;
		} else {
			return false;
		}
	}

}