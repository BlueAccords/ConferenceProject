package client;

public class Driver {

	public static void main(String[] args) {
		Controller systemController = new Controller();
		UserInterface systemUI = new UserInterface();
		systemController.addObserver(systemUI);
		systemUI.addObserver(systemController);
		systemController.startProgram();
	}

}
