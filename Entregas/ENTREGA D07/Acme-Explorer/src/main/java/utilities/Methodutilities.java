
package utilities;

import java.util.Date;

public class Methodutilities {

	public boolean moreThanThirtyDays(Date startdate) {
		boolean res = false;
		Date dNow = new Date(System.currentTimeMillis());
		long uno = dNow.getTime();
		long dos = startdate.getTime();
		double dias = ((dos - uno) * 1.0 / 86400000);
		if (dias > 30)
			res = true;
		return res;
	}

	public boolean finishDateFuture(Date startdate) {
		boolean res = false;
		Date dNow = new Date(System.currentTimeMillis());
		long uno = dNow.getTime();
		long dos = startdate.getTime();
		double dias = ((dos - uno) * 1.0 / 86400000);
		if (dias > 0)
			res = true;
		return res;
	}

	public boolean publicationDate(Date startdate) {
		boolean res = false;
		Date dNow = new Date(System.currentTimeMillis());
		long uno = dNow.getTime();
		long dos = startdate.getTime();
		double dias = ((dos - uno) * 1.0 / 86400000);
		if (dias > 0)
			res = true;
		return res;
	}
}
