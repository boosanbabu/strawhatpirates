package strawHatPirates.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import strawHatPirates.model.Delivery;
import strawHatPirates.model.Input;
import strawHatPirates.model.Output;
import strawHatPirates.model.Pizza;
import strawHatPirates.model.Result;
import strawHatPirates.util.Utility;

public class Application {
	public static final String IN_FOLDER = "C:\\Users\\bmbabu\\Desktop\\bmbabu\\!work\\wkspc\\hashcode2021\\strawHatPirates\\resource\\input\\";
	public static final String OUT_FOLDER = "C:\\Users\\bmbabu\\Desktop\\bmbabu\\!work\\wkspc\\hashcode2021\\strawHatPirates\\resource\\output\\";

	public static void main(String args[]) {
		String fileList = "a_example,b_little_bit_of_everything,c_many_ingredients,d_many_pizzas,e_many_teams";
//		String fileList = "b_little_bit_of_everything";
		for (String fileName : fileList.split(",")) {
			Input input = parseInputFile(IN_FOLDER + fileName + ".in");
			input.availablePizzas()
					.sort((p1, p2) -> Integer.compare(p1.getIngredientsCount(), p2.getIngredientsCount()));
			Output output = solveProblemSorted(input);
			writeToFile(output, fileName);
			System.out.println("Completed " + fileName);
		}
	}

	private static Input parseInputFile(String filePath) {
		return Utility.readInputFile(filePath);
	}

	private static void writeToFile(Output output, String fileName) {
		File fout = new File(OUT_FOLDER + fileName + ".out");
		try (FileOutputStream fos = new FileOutputStream(fout);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));) {

			bw.write(String.valueOf(output.getDeliveries().size()));
			bw.newLine();
			for (Delivery delivery : output.getDeliveries()) {
				String pStr = "";
				for (Pizza p : delivery.getPizzas()) {
					pStr += " " + p.getId();
				}
				bw.write(delivery.getTeamSize() + pStr);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static Output solveProblemSorted(Input input) {
		PizzaVendor pv = new PizzaVendor();
		Output out = new Output();
		while (true) {
			if (input.availablePizzas().size() < 2) {
				// If there are less than 2 pizza, we can't deliver to any team
				break;
			}
			if (input.getT2() <= 0 && input.getT3() <= 0 && input.getT4() <= 0) {
				// We all teams have got a pizza
				break;
			}
			Result r2 = null, r3 = null, r4 = null;
			if (input.getT2() > 0) {
				List<Pizza> clonedPizzaList = new ArrayList<>(input.availablePizzas());
				r2 = pv.getHighIngrdnPizzas(2, clonedPizzaList);
			}
			if (input.getT3() > 0) {
				List<Pizza> clonedPizzaList = new ArrayList<>(input.availablePizzas());
				r3 = pv.getHighIngrdnPizzas(3, clonedPizzaList);
			}
			if (input.getT4() > 0) {
				List<Pizza> clonedPizzaList = new ArrayList<>(input.availablePizzas());
				r4 = pv.getHighIngrdnPizzas(4, clonedPizzaList);
			}
			if (r2 != null || r3 != null || r4 != null) {
				selectBestOf3(r2, r3, r4, input, out);
			}

		}
		return out;
	}

	private static void selectBestOf3(Result r2, Result r3, Result r4, Input input, Output out) {
		if (r2 != null && (r2.getScore() >= getScoreNullSafe(r3) && r2.getScore() >= getScoreNullSafe(r4))) {
			deliver(out, r2, input);
			input.setNumOf2memberTeams(input.getT2() - 1);
		} else if (r3 != null && r3.getScore() >= getScoreNullSafe(r2) && r3.getScore() >= getScoreNullSafe(r4)) {
			deliver(out, r3, input);
			input.setNumOf3memberTeams(input.getT3() - 1);
		} else {
			deliver(out, r4, input);
			input.setNumOf4memberTeams(input.getT4() - 1);
		}
	}

	public static int getScoreNullSafe(Result r) {
		if (r == null)
			return 0;
		return r.getScore();
	}

	private static void deliver(Output out, Result r, Input inp) {
		out.getDeliveries().add(r.getDel());
		out.setNumberOfDeliveries(out.getNumberOfDeliveries() + 1);
		for (Pizza p : r.getDel().getPizzas()) {
			inp.availablePizzas().remove(p);
		}
	}

	/**
	 * Combinations attempt - Checks all combinations -backtrack- VERY INEFFICIENT
	 * 
	 * @param input
	 * @return
	 */
	private static Output solveProblem(Input input) {
		PizzaVendor pv = new PizzaVendor();

		Output out = new Output();
		while (true) {
			if (input.availablePizzas().size() < 2)
				break;

			Future<Result> f2 = pv.getHighScorePizzas(2, input.availablePizzas());
			Future<Result> f3 = pv.getHighScorePizzas(3, input.availablePizzas());
			Future<Result> f4 = pv.getHighScorePizzas(4, input.availablePizzas());
			try {
				while (!(f2.isDone() && f3.isDone() && f4.isDone())) {
					System.out.println(String.format("future1 is %s, future2 is %s and future3 is %s",
							f2.isDone() ? "done" : "not done", f3.isDone() ? "done" : "not done",
							f4.isDone() ? "done" : "not done"));
					Thread.sleep(1000);
				}
				Result r2 = f2.get();
				Result r3 = f3.get();
				Result r4 = f4.get();
				selectBestOf3(r2, r3, r4, input, out);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		pv.shutdown();
		return out;
	}

}
