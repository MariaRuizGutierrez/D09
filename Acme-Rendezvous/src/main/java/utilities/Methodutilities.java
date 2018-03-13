
package utilities;

import java.util.Date;

public class Methodutilities {

	public boolean organisedMoment(final Date startdate) {
		boolean res = false;
		if (startdate != null) {
			final Date dNow = new Date(System.currentTimeMillis());
			final long uno = dNow.getTime();
			final long dos = startdate.getTime();
			final double dias = ((dos - uno) * 1.0 / 86400000);
			if (dias > 0)
				res = true;
		}
		return res;
	}
}
