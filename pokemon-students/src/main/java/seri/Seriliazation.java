package seri;
import java.io.*;

import General.Pokemon;
import General.PokemonData;
public class Seriliazation {

	    public static void main(String[] args) {
	        Pokemon p = new Pokemon(PokemonData.CHARMANDER);
	        p.gainXp(100);

	        // Serialize
	        String filename = "C:\\Users\\giova\\OneDrive\\java.pokemon.serialization";
	        serializePokemon(filename, p);

	        // Deserialize
	        Pokemon deserialized = deserializePokemon(filename);
	        deserialized.status();
	    }

	    private static Pokemon deserializePokemon(String filename) {
	        try {
	            FileInputStream file = new FileInputStream(filename);
	            ObjectInputStream in = new ObjectInputStream(file);

	            Pokemon d = (Pokemon) in.readObject();

	            in.close();
	            file.close();

	            System.out.println("Object has been deserialized");
	            return d;
	        } catch (IOException ex) {
	            System.out.println("IOException is caught");
	        } catch (ClassNotFoundException ex) {
	            System.out.println("ClassNotFoundException is caught");
	        }
	        return null;
	    }

	    private static void serializePokemon(String filename, Pokemon p) {
	        try {
	            FileOutputStream file = new FileOutputStream(filename);
	            ObjectOutputStream out = new ObjectOutputStream(file);

	            out.writeObject(p);

	            out.close();
	            file.close();

	            System.out.println("Object has been serialized");
	        } catch (IOException ex) {
	            System.out.println("IOException is caught");
	        }
	    }
	}
	

