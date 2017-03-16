import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class CZMLBuilder extends Builder {

	private String projectDir;

	/**
	 * 
	 */
	public CZMLBuilder() {
		String home = System.getProperty("user.home");
		File theDir = new File(home + "\\Satellite_Data");
		this.projectDir = theDir.toString();
		System.out.println(projectDir);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + theDir.getName());
			boolean re = false;
			try {
				theDir.mkdir();
				re = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (re) {
				System.out.println("Project directory created");
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public String buildHeader() {
		String header = "{\"id\" : \"document\","
				+ "\"version\" : \"1.0\","
				+ "\"clock\" : {\"interval\" : \"2014-04-06T21:44:00Z/2014-04-11T21:42:00Z\","
				+ "\"currentTime\" : \"2014-04-06T21:44:00Z\","
				+ "\"multiplier\" : 1," + "\"range\" : \"LOOP_STOP\","
				+ "\"step\" : \"SYSTEM_CLOCK_MULTIPLIER\"}}";
		System.out.println(header);
		return header;
	}

	/**
	 * 
	 * @return
	 */
	public String buildVehicle() {
		ArrayList<String> sec, listX, listY, listZ, listR, listT, listN;
		// positions of satellite system
		sec = getLinesOfTxt("txt/seconds.txt");
		listX = getLinesOfTxt("txt/r_I_ECI.txt");
		listY = getLinesOfTxt("txt/r_J_ECI.txt");
		listZ = getLinesOfTxt("txt/r_K_ECI.txt");
		String pos = "";
		for (int i = 0; i < listX.size(); i++) {
			pos += "" + sec.get(i) + ",";
			pos += "" + convertValues(listX).get(i) * 1000 + ",";
			pos += "" + convertValues(listY).get(i) * 1000 + ",";
			pos += "" + convertValues(listZ).get(i) * 1000 + "";
			if (i < listX.size() - 1)
				pos += ",";
		}
		String sampPosPro = "\"position\":{"
				+ "\"interpolationAlgorithm\" : \"LAGRANGE\","
				+"\"referenceFrame\": \"INERTIAL\","
				+ "\"interpolationDegree\" : 1, "
				+ "\"epoch\" : \"2014-04-06T21:44:03Z\","
				+ "\"cartesian\":["
				+ pos + "]}";

		// building the vehicle
		String vehicle = "{\"id\" : \"Vehicle\","
				+ "\"availability\" : \"2014-04-06T21:44:00Z/2014-04-11T21:42:00Z\","
				// billboard
				+ "\"billboard\" : {\"eyeOffset\" : {\"cartesian\" : [0.0, 0.0, 0.0]},\"horizontalOrigin\" : \"CENTER\",\"image\" : \"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAEISURBVEhLvVXBDYQwDOuojHKj8LhBbpTbpBCEkZsmIVTXq1RVQGrHiWlLmTTqPiZBlyLgy/KSZQ5JSHDQ/mCYCsC8106kDU0AdwRnvYZArWRcAl0dcYJq1hWCb3hBrumbDAVMwAC82WoRvgMnVMDBnB0nYZFTbE6BBvdUGqVqCbjBIk3PyFFR/NU7EKzru+qZsau3ryPwwCRLKYOzutZuCL6fUmWeJGzNzL/RxAMrUmASSCkkAayk2IxPlwhAAYGpsiHQjbLccfdOY5gKkCXAMi7SscAwbQpAnKyctWyUZ6z8ja3OGMepwD8asz+9FnSvbhU8uVOHFIwQsI3/p0CfhuqCSQuxLqsN6mu8SS+N42MAAAAASUVORK5CYII=\","
				// pixel offset
				+ "\"pixelOffset\" : {\"cartesian2\" : [0.0, 0.0]},\"scale\" : 0.8,\"show\" : true,\"verticalOrigin\" : \"BOTTOM\"},"
				// path
				+ "\"path\" : {\"material\" : {\"solidColor\" : {\"color\" : {\"rgba\" : [255, 255, 0, 255]}}},\"width\" : 5.0,\"show\" : true},"
				+ sampPosPro + "}";
		return vehicle;
	}

	/**
	 * 
	 */
	void writeCZML() {
		// dimensions of ellipsoid
		// listR = getLinesOfTxt("txt/r_R_RTN.txt");
		// listT = getLinesOfTxt("txt/r_T_RTN.txt");
		// listN = getLinesOfTxt("txt/r_N_RTN.txt");

		// arrange data
		String begin = "[";
		String result = " ";
		String end = "]";

		result = begin + buildHeader() + "," + buildVehicle() + ", "
				+ buildEllipsoid() + end;
		System.out.println(result);
		// writing file to project directory "results" as czml
		try {
			File file = new File(projectDir + "/sat.czml");
			FileOutputStream fooStream = new FileOutputStream(file, false); // true
			// false to overwrite.
			byte[] myBytes = result.getBytes();
			fooStream.write(myBytes);
			fooStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	public void createProjectDir() {
		String home = System.getProperty("user.home");
		File theDir = new File(home + "\\Satellite_Data\\001");
		System.out.println(theDir);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + theDir.getName());
			boolean re = false;
			try {
				theDir.mkdir();
				re = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (re) {
				System.out.println("DIR created");
			}
		}
	}

	void getCartesians() {

	}

	/**
	 * 
	 * @return
	 */
	public String buildEllipsoid() {
		ArrayList<String> sec = null, listN = null, listR = null, listT = null;
		String dim = "";
		sec = getLinesOfTxt("txt/seconds.txt");
		listR = getLinesOfTxt("txt/r_R_RTN.txt");
		listT = getLinesOfTxt("txt/r_T_RTN.txt");
		listN = getLinesOfTxt("txt/r_N_RTN.txt");
		for (int i = 0; i < listN.size(); i++) {
			dim += "" + sec.get(i) + ",";
			dim += "" + convertValues(listR).get(i) * 1000 + ",";
			dim += "" + convertValues(listT).get(i) * 1000 + ",";
			dim += "" + convertValues(listN).get(i) * 1000 + "";
			if (i < listN.size() - 1)
				dim += ",";
		}

		String res = "{"
				+ "\"id\" : \"blueEllipsoid\","
				+ "\"availability\" :\"2014-04-06T21:44:00Z/2014-04-11T21:42:00Z\","
				+ "\"name\" : \"blue ellipsoid\","
				+ "\"position\" : {\"reference\" : \"Vehicle#position\"},"
				// Ellipsoid specs
				+ "\"ellipsoid\" :"
				+ " {\"radii\" : {\"epoch\" : \"2014-04-06T21:44:00Z\",\"cartesian\" : ["
				+ dim + "]},"
				// style
				+ "\"fill\" : true,"
				+ "\"material\" : {\"solidColor\" : {\"color\" : {\"rgba\" : [0, 0, 255, 255]}}}";
		res += "}}";
		System.out.println(res);
		return res;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CZMLBuilder b = new CZMLBuilder();
		b.writeCZML();
	}
}
