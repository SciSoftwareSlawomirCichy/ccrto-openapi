package org.ccrto.openapi.core.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CcrtoJAXBContextSecretary implements Callable<String> {

	private static ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>());

	static {
		Runtime.getRuntime().addShutdownHook(new ExecutorServiceCloser());
	}

	@Override
	public String call() throws Exception {
		CcrtoJAXBContextUtils.setJaxbContext(CcrtoJAXBContextUtils.buildContext());
		return "SUCCESS";
	}

	static void wakeUp() {
		executorService.submit(new CcrtoJAXBContextSecretary());
	}

	/**
	 * 
	 * ThreadPoolCloser - Watek zamykający pule wątków
	 *
	 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
	 * @version $Revision: 1.1 $
	 *
	 */
	private static class ExecutorServiceCloser extends Thread {
		@Override
		public void run() {
			executorService.shutdownNow();
		}
	}

}
