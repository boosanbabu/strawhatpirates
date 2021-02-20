package strawHatPirates.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Input {

	int numberOfPizzas;
	int t2;
	int t3;
	int t4;
	List<Pizza> availablePizzas = new ArrayList<>();

	public List<Pizza> availablePizzas() {
		return availablePizzas;
	}

	public void setPizzas(List<Pizza> pizzas) {
		this.availablePizzas = pizzas;
	}

	public int getNumberOfPizzas() {
		return numberOfPizzas;
	}

	public void setNumberOfPizzas(int numberOfPizzas) {
		this.numberOfPizzas = numberOfPizzas;
	}

	public int getNumOf2memberTeams() {
		return t2;
	}

	public void setNumOf2memberTeams(int numOf2memberTeams) {
		this.t2 = numOf2memberTeams;
	}

	public int getNumOf3memberTeams() {
		return t3;
	}

	public void setNumOf3memberTeams(int numOf3memberTeams) {
		this.t3 = numOf3memberTeams;
	}

	public int getNumOf4memberTeams() {
		return t4;
	}

	public void setNumOf4memberTeams(int numOf4memberTeams) {
		this.t4 = numOf4memberTeams;
	}

	public void addPizza(int id, String[] pizza) {
		availablePizzas.add(new Pizza(id, pizza));
	}

	@Override
	public String toString() {
		return "Input [numberOfPizzas=" + numberOfPizzas + ", t2=" + t2 + ", t3=" + t3 + ", t4=" + t4 + ", pizzas="
				+ availablePizzas + "]";
	}

}
