package Area;

import java.util.List;

import General.Pokemon;

public class Pokecenter {

    private final String name;

    public Pokecenter(String name) {
        this.name = name;
    }

    //TODO: US-PKM-O-12
    public void healPokemon(List<Pokemon> pokemonToHeal) {
    	for(Pokemon p : pokemonToHeal) {
    		int maxHp = p.getMaxHp();
    		p.setCurrentHp(maxHp);
    	}
    }
}
