package strawHatPirates.model;

import java.util.Arrays;
import java.util.List;

public class Pizza {
	int id;
	int ingredientsCount;
	List<String> ingredients;

	public Pizza(int id, String[] pizza) {
		this.id = id;
		ingredientsCount = Integer.valueOf(pizza[0]);
		ingredients = Arrays.asList(pizza).subList(1, pizza.length);
	}

	@Override
	public String toString() {
		return "Pizza [id=" + id + ", ingredientsCount=" + ingredientsCount + ", ingredients=" + ingredients + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIngredientsCount() {
		return ingredientsCount;
	}

	public void setIngredientsCount(int ingredientsCount) {
		this.ingredientsCount = ingredientsCount;
	}

	public List<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((ingredients == null) ? 0 : ingredients.hashCode());
		result = prime * result + ingredientsCount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pizza other = (Pizza) obj;
		if (id != other.id)
			return false;

		return true;
	}

}
