package strawHatPirates.app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import strawHatPirates.model.Pizza;
import strawHatPirates.model.Result;

public class PizzaVendor {

	private ExecutorService executor = Executors.newFixedThreadPool(3);

	public Future<Result> getHighScorePizzas(int pizzasReqd, List<Pizza> availablePizzas) {
		return executor.submit(() -> {
			if (pizzasReqd > availablePizzas.size())
				return null;
			Result result = new Result(pizzasReqd);
			checkAllCombination(availablePizzas, 0, result, new ArrayList<Integer>());
			return result;
		});
	}

	public void shutdown() {
		executor.shutdown();
	}

	private void checkAllCombination(List<Pizza> availablePizzas, int start, Result result, List<Integer> temp) {
		if (temp.size() == result.getDel().getTeamSize()) {
			int currentScore = computeScore(temp, availablePizzas);
			if (result.getScore() < currentScore) {
				updateResult(result, currentScore, temp, availablePizzas);
			}
			return;
		}
		for (int i = start; i <= availablePizzas.size() - result.getDel().getTeamSize() + 1; i++) {
			temp.add(i);
			checkAllCombination(availablePizzas, i + 1, result, temp);
			temp.remove(temp.size() - 1);
		}
	}

	public int computeScore(List<Integer> temp, List<Pizza> availablePizzas) {
		Set<String> uniqIngrdnts = new HashSet<>();
		for (int i : temp) {
			uniqIngrdnts.addAll(availablePizzas.get(i).getIngredients());
		}
		return uniqIngrdnts.size() * uniqIngrdnts.size();
	}

	private void updateResult(Result result, int score, List<Integer> pizzas, List<Pizza> availablePizzas) {
		Set<Pizza> selectedPizzas = new HashSet<>();
		for (int p : pizzas) {
			selectedPizzas.add(availablePizzas.get(p));
		}
		result.getDel().setPizzas(selectedPizzas);
		result.setScore(score);
	}

	public Result getHighIngrdnPizzas(int i, List<Pizza> clonedPizzaList) {
		Result result = new Result(i);
		Set<Pizza> selectedPizzas = new HashSet<>();
		Set<String> uniqIngrdnts = new HashSet<>();
		while (i-- > 0) {
			Pizza p = clonedPizzaList.remove(clonedPizzaList.size() - 1);
			selectedPizzas.add(p);
			uniqIngrdnts.addAll(p.getIngredients());
		}
		result.getDel().setPizzas(selectedPizzas);
		result.setScore(uniqIngrdnts.size() * uniqIngrdnts.size());
		return result;
	}
}
