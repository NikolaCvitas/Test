package hr.nikola.swingworker;

import hr.nikola.utils.StringUtils;

public class SwingStarter {

	public static void main(String[] args) {
		
		
		RunWorker runWorker = new RunWorker("Pokrenuo run workera", StringUtils.getList());
		
		runWorker.run();
		
		

	}

}
