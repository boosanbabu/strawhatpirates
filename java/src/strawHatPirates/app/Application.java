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
		String fileName = "b_little_bit_of_everything";
		Input input = parseInputFile(IN_FOLDER + fileName + ".in");
		Output output = solveProblem(input);
		writeToFile(output, fileName);
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

	private static Output solveProblem(Input input) {
		PizzaVendor pv = new PizzaVendor();

		Output out = new Output();
		while (true) {
			if (input.availablePizzas().size() < 2)
				break;

			Future<Result> f2 = pv.getMePizzas(2, input.availablePizzas());
			Future<Result> f3 = pv.getMePizzas(3, input.availablePizzas());
			Future<Result> f4 = pv.getMePizzas(4, input.availablePizzas());
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
				if (r3 == null || (r2.getScore() >= r3.getScore() && r2.getScore() >= r4.getScore())) {
					deliver(out, r2, input);
				} else if (r4 == null || r3.getScore() >= r2.getScore() && r3.getScore() >= r4.getScore()) {
					deliver(out, r3, input);
				} else {
					deliver(out, r4, input);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			Result r2 = getMePizzas(2, input.availablePizzas());
//			Result r3 = getMePizzas(3, input.availablePizzas());
//			Result r4 = getMePizzas(4, input.availablePizzas());
			catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		pv.shutdown();
		return out;
	}

	private static void deliver(Output out, Result r, Input inp) {
		out.getDeliveries().add(r.getDel());
		out.setNumberOfDeliveries(out.getNumberOfDeliveries() + 1);
		for (Pizza p : r.getDel().getPizzas()) {
			inp.availablePizzas().remove(p);
		}
	}

}
