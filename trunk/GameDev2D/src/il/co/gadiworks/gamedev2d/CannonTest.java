package il.co.gadiworks.gamedev2d;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import il.co.gadiworks.games.framework.Game;
import il.co.gadiworks.games.framework.Input.TouchEvent;
import il.co.gadiworks.games.framework.Screen;
import il.co.gadiworks.games.framework.gl.Vertices;
import il.co.gadiworks.games.framework.impl.GLGame;
import il.co.gadiworks.games.framework.impl.GLGraphics;
import il.co.gadiworks.games.framework.math.Vector2;

public class CannonTest extends GLGame {

	@Override
	public Screen getStartScreen() {
		return new CannonScreen(this);
	}
	
	class CannonScreen extends Screen {
		final float FRUSTUM_WIDTH = 4.8f;
		final float FRUSTUM_HEIGHT = 3.2f;
		
		GLGraphics glGraphics;
		Vertices vertices;
		Vector2 cannonPos = new Vector2(2.4f, 0.5f);
		float cannonAngle = 0;
		Vector2 touchPos = new Vector2();

		public CannonScreen(Game game) {
			super(game);
			
			this.glGraphics = ((GLGame) game).getGLGraphics();
			
			this.vertices = new Vertices(this.glGraphics, 3, 0, false, false);
			this.vertices.setVertices(new float[] {
					-0.5f, -0.5f,
					 0.5f,  0.0f,
					-0.5f,  0.5f
			}, 0, 6);
		}
		
		@Override
		public void update(float deltaTime) {
			List<TouchEvent> touchEvents = GAME.getInput().getTouchEvents();
			GAME.getInput().getKeyEvents();
			
			int len = touchEvents.size();
			
			for (int i = 0; i < len; i++) {
				TouchEvent event = touchEvents.get(i);
				
				this.touchPos.x = (event.x / (float) this.glGraphics.getWidth()) * FRUSTUM_WIDTH;
				this.touchPos.y = (1 - event.y / (float) this.glGraphics.getHeight()) * FRUSTUM_HEIGHT;
				
				this.cannonAngle = this.touchPos.sub(cannonPos).angle();
			}
		}
		
		@Override
		public void present(float arg0) {
			GL10 gl = this.glGraphics.getGL();
			
			gl.glViewport(0, 0, this.glGraphics.getWidth(), this.glGraphics.getHeight());
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(0, FRUSTUM_WIDTH, 0, FRUSTUM_HEIGHT, 1, -1);
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			
			gl.glTranslatef(this.cannonPos.x, this.cannonPos.y, 0);
			gl.glRotatef(this.cannonAngle, 0, 0, 1);
			
			this.vertices.bind();
			this.vertices.draw(GL10.GL_TRIANGLES, 0, 3);
			this.vertices.unbind();
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void pause() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void resume() {
			// TODO Auto-generated method stub
			
		}
	}
}