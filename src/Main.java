import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		// read file
		List<String> list = new ArrayList<String>();
		try {
			for (String line : Files.readAllLines(Paths.get("C:/Users/Pdo3/Desktop/batch-2016-12-01.0.log"))) {
				if (line.contains("	 :")) {
					list.add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("\nExample 2 - Count all with frequency");
		LinkedHashSet<String> uniqueSet = new LinkedHashSet<String>(list);
		//Focus with
		int ii = 1;
		System.out.println("Size:" + uniqueSet.size());
		for (String temp : uniqueSet) {
			if(Collections.frequency(list, temp) >= ii)
			System.out.println(temp + "loop : " +  Collections.frequency(list, temp)+ " time");
		}
	}

}
