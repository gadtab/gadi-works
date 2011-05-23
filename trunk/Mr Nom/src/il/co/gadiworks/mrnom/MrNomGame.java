package il.co.gadiworks.mrnom;

import il.co.gadiworks.mrnom.framework.Screen;
import il.co.gadiworks.mrnom.framework.impl.AndroidGame;

public class MrNomGame extends AndroidGame {

	@Override
	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}
}