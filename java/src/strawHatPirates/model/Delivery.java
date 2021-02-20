package strawHatPirates.model;

import java.util.HashSet;
import java.util.Set;

public class Delivery {
	int teamSize;
	Set<Pizza> pizzas;

	public Delivery(int pizzasReqd) {
		teamSize = pizzasReqd;
	}

	public int getTeamSize() {
		return teamSize;
	}

	public void setTeamSize(int teamSize) {
		this.teamSize = teamSize;
	}

	public Set<Pizza> getPizzas() {
		return pizzas;
	}

	public void setPizzas(Set<Pizza> pizzas) {
		this.pizzas = new HashSet<>(pizzas);
	}

	@Override
	public String toString() {
		return teamSize + " " + pizzas;
	}

}
