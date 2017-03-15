import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Builder {

	ArrayList<Double> mje, I, J, K;
	String json;

	/**
	 * 
	 * @param mje
	 * @param i
	 * @param j
	 * @param k
	 */
	public Builder(String mje, String i, String j, String k) {
		this.mje = convertValues(getLinesOfTxt(mje));
		this.I = convertValues(getLinesOfTxt(i));
		this.J = convertValues(getLinesOfTxt(j));
		this.K = convertValues(getLinesOfTxt(k));
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	ArrayList<String> getLinesOfTxt(String path) {
		Object encoding;
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// lines.forEach(System.out::println);
		return new ArrayList<String>(lines);
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	ArrayList<Double> convertValues(ArrayList<String> list) {

		ArrayList<Double> res = new ArrayList<Double>();
		list.forEach(a -> {
			res.add(Double.parseDouble(a));
		});
		return res;
	}

	/**
	 * 
	 * @return
	 */
	String arrangeJsonFromData() {

		String json = "{\"satellites\":[{\"id\":22068,\"positions\":[";

		for (int i = 0; i < mje.size(); i++) {
			String pos = "";
			pos = "{\"height\": 0, \"objectId\": 123, \"x\": " + I.get(i) *1000 + ",";
			pos += "\"y\": " + J.get(i) *1000+ ",";
			pos += "\"z\": " + K.get(i) *1000+ ",";
			pos += "\"mJE\": " + mje.get(i) + "}\n";
			if (i < mje.size() - 1)
				pos += ",";
			else
				pos += "]";
			json += pos;
		}
		json += "}]}";
		return json;
	}

	void writeSatData(String path, String json) {
		try {
			File file = new File(path + "/sat.json");

			FileOutputStream fooStream = new FileOutputStream(file, false); // true
			// false to overwrite.
			byte[] myBytes = json.getBytes();
			fooStream.write(myBytes);
			fooStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String mje = "txt/mje.txt";
		String i = "txt/r_I_ECI.txt";
		String j = "txt/r_J_ECI.txt";
		String k = "txt/r_K_ECI.txt";

		Builder b = new Builder(mje, i, j, k);
		String json = b.arrangeJsonFromData();
		b.writeSatData("results", json);
		System.out.println(System.getProperty("user.home"));

		System.out.println(json);
	}
}
