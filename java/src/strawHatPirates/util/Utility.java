package strawHatPirates.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import strawHatPirates.model.Input;

public class Utility {
	public static List<String> readFileInList(String fileName) {

		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
		}

		catch (IOException e) {
			System.err.println("Unable to read input");
			e.printStackTrace();
		}
		return lines;
	}

	public static Input readInputFile(String filepath) {
		List<String> inputStrList = readFileInList(filepath);
		Input input = new Input();
		String[] line1 = inputStrList.get(0).split("\\s+");

		input.setNumberOfPizzas(Integer.valueOf(line1[0]));
		input.setNumOf2memberTeams(Integer.valueOf(line1[1]));
		input.setNumOf3memberTeams(Integer.valueOf(line1[2]));
		input.setNumOf4memberTeams(Integer.valueOf(line1[3]));

		for (int i = 1; i < inputStrList.size(); i++) {
			input.addPizza(i - 1, inputStrList.get(i).split("\\s+"));
		}
		return input;
	}
}
