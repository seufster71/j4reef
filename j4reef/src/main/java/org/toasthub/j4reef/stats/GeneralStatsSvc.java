package org.toasthub.j4reef.stats;

import org.toasthub.j4reef.utils.Request;
import org.toasthub.j4reef.utils.Response;

public interface GeneralStatsSvc {

	public void systemStats(Request request, Response response);
	public void prefStats(Request request, Response response);
	public void piStats(Request request, Response response);
}
