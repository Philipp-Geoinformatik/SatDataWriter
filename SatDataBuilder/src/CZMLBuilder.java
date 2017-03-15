import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class CZMLBuilder extends Builder {

	public CZMLBuilder(String mje, String i, String j, String k) {
		super(mje, i, j, k);
		// TODO Auto-generated constructor stub
	}

	void writeCZML(String path) {

		ArrayList<String> sec, listX, listY, listZ, listR, listT, listN;
		// positions of satellite system
		sec = getLinesOfTxt("txt/rec_seconds.txt");
		listX = getLinesOfTxt("txt/r_I_ECI.txt");
		listY = getLinesOfTxt("txt/r_J_ECI.txt");
		listZ = getLinesOfTxt("txt/r_K_ECI.txt");
		// dimensions of ellipsoid
		listR = getLinesOfTxt("txt/r_R_RTN.txt");
		listT = getLinesOfTxt("txt/r_T_RTN.txt");
		listN = getLinesOfTxt("txt/r_N_RTN.txt");

		// arrange data
		String result = " ";
		String begin = "[";
		String end = "}]";
		String header = "{\"id\" : \"document\",\"version\" : \"1.0\",\"clock\" : {\"interval\" : \"2014-04-06T21:44:00Z/2014-04-11T21:42:00Z\",\"currentTime\" : \"2014-04-06T21:44:00Z\",\"multiplier\" : 1,\"range\" : \"LOOP_STOP\",\"step\" : \"SYSTEM_CLOCK_MULTIPLIER\"}}, {\"id\" : \"Vehicle\",\"availability\" : \"2014-04-06T21:44:00Z/2014-04-11T21:42:00Z\",\"billboard\" : {\"eyeOffset\" : {\"cartesian\" : [0.0, 0.0, 0.0]},\"horizontalOrigin\" : \"CENTER\",\"image\" : \"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAEISURBVEhLvVXBDYQwDOuojHKj8LhBbpTbpBCEkZsmIVTXq1RVQGrHiWlLmTTqPiZBlyLgy/KSZQ5JSHDQ/mCYCsC8106kDU0AdwRnvYZArWRcAl0dcYJq1hWCb3hBrumbDAVMwAC82WoRvgMnVMDBnB0nYZFTbE6BBvdUGqVqCbjBIk3PyFFR/NU7EKzru+qZsau3ryPwwCRLKYOzutZuCL6fUmWeJGzNzL/RxAMrUmASSCkkAayk2IxPlwhAAYGpsiHQjbLccfdOY5gKkCXAMi7SscAwbQpAnKyctWyUZ6z8ja3OGMepwD8asz+9FnSvbhU8uVOHFIwQsI3/p0CfhuqCSQuxLqsN6mu8SS+N42MAAAAASUVORK5CYII=\",\"pixelOffset\" : {\"cartesian2\" : [0.0, 0.0]},\"scale\" : 0.8,\"show\" : true,\"verticalOrigin\" : \"BOTTOM\"},\"path\" : {\"material\" : {\"solidColor\" : {\"color\" : {\"rgba\" : [255, 255, 0, 255]}}},\"width\" : 5.0,\"show\" : true},";
//		String header = "{    \"id\" : \"SPOOK_CZML\",    \"name\" : \"Visualization of GEO Satelite data\",    \"version\" : \"1.0\"},";

		/*
		 * "position" : { "interpolationAlgorithm" : "LAGRANGE",
		 * "interpolationDegree" : 1, "epoch" : "2012-08-04T16:00:00Z",
		 * "cartesian" : [0.0, 1254962.0093268978, -4732330.528380746,
		 * 4074172.505865612, 120.0, 1256995.7322857284, -4732095.2154790815,
		 * 4073821.2249589274] }
		 * ----------------------------------------------------------------
		 * http://stackoverflow.com/questions/32799323/are-properties-and-
		 * methods-presented-in-reference-documentation-of-cesium-js-de
		 * 
		 */

		String positions = "";
		for (int i = 0; i < listX.size(); i++) {
			positions += "" + sec.get(i) + ",";
			positions += "" + convertValues(listX).get(i)*1000 + ",";
			positions += "" + convertValues(listY).get(i)*1000+ ",";
			positions += "" + convertValues(listZ).get(i)*1000 + "";
			if (i < listX.size() - 1)
				positions += ",";
		}

		String sampPosPro = "\"position\":{\"interpolationAlgorithm\" : \"LAGRANGE\"," 
				+ "\"interpolationDegree\" : 1, " + "\"epoch\" : \"2014-04-06T21:44:03Z\"," + "\"cartesian\":["
				+ positions + "]}";

		String point = " \"point\" : {\"color\" : {\"rgba\" : [255, 255, 255, 128]},\"outlineColor\" : {\"rgba\" : [255, 0, 0, 128]},\"outlineWidth\" : 3,\"pixelSize\" : 15    }}";

		result = begin +header+ sampPosPro + end;

		System.out.println(result);
		// writing file to project directory "results" as czml
		// try {
		// File file = new File(path + "/sat.czml");
		// FileOutputStream fooStream = new FileOutputStream(file, false); //
		// true
		// // false to overwrite.
		// byte[] myBytes = json.getBytes();
		// fooStream.write(myBytes);
		// fooStream.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
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
		String R = "txt/r_R_RTN.txt";
		String T = "txt/r_T_RTN.txt";
		String N = "txt/r_N_RTN.txt";

		CZMLBuilder b = new CZMLBuilder(mje, i, j, k);
		// String json = b.arrangeJsonFromData();
		// System.out.println(System.getProperty("user.home"));
		b.writeCZML("results");
		// System.out.println(json);
	}

}
