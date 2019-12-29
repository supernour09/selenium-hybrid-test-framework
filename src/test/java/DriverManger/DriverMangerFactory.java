package DriverManger;

public class DriverMangerFactory {

	/**
	 * all the currently available browsers
	 * 
	 */
	public enum DriverType {
		CHROME, FIREFOX;
	}

	/**
	 * Factory design pattern to make web drivers with and browser so if you want to
	 * add new browser just extend DriverManger and add case in here and in the enum
	 * 
	 * @param type
	 * @return
	 */
	public static DriverManger getDriverManger(DriverType type) {
		DriverManger driverManger;
		switch (type) {
		case CHROME:
			driverManger = new ChromeDriverManger();
			break;
		case FIREFOX:
			driverManger = new FireFoxDriverManger();
			break;
		default:
			driverManger = new FireFoxDriverManger();
			break;

		}
		return driverManger;

	}
}
