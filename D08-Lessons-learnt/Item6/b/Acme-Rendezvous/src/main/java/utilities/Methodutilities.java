
package utilities;

import java.util.Date;

public class Methodutilities {

	public boolean organisedMoment(Date startdate) {
		boolean res = false;
		if (startdate != null) {
			Date dNow = new Date(System.currentTimeMillis());
			long uno = dNow.getTime();
			long dos = startdate.getTime();
			double dias = ((dos - uno) * 1.0 / 86400000);
			if (dias > 0)
				res = true;
		}
		return res;
	}
}
