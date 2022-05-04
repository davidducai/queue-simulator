package main;

import controller.SetupController;
import view.SetupView;

public class Launch {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		SetupView view = new SetupView();
		SetupController controller = new SetupController(view);
	}
}
