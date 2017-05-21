package client;

public class Driver {

	public static void main(String[] args) {
		Controller systemController = new Controller();
		UI_ParentFrame_View systemUI = new UI_ParentFrame_View("Main Window", 400, 600);
		systemController.addObserver(systemUI);
		systemUI.addObserver(systemController);
		
		systemUI.getJFrame().show();
		systemController.startProgram();
	}

}
