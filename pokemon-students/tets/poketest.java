import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Area.Area;
import General.Game;
import General.Pokemon;
import General.PokemonData;
import Trainer.Trainer;

class poketest {

	@Test
	void test() {
		fail("Not yet implemented");
	}

	@Test
	void chooseFirstPokemon() {
		Area pewterCity = new Area("Pewter city", null, true, null, null);
		Game.trainer = new Trainer("Test", pewterCity);
		Pokemon p = Game.chooseFirstPokemon(1);
		assertTrue(Game.trainer.getActivePokemon().getPokedata().equals(PokemonData.CHARMANDER));

	}
}
