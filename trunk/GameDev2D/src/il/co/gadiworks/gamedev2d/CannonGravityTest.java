package il.co.gadiworks.gamedev2d;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.util.FloatMath;

import il.co.gadiworks.games.framework.Game;
import il.co.gadiworks.games.framework.Screen;
import il.co.gadiworks.games.framework.Input.TouchEvent;
import il.co.gadiworks.games.framework.gl.Vertices;
import il.co.gadiworks.games.framework.impl.GLGame;
import il.co.gadiworks.games.framework.impl.GLGraphics;
import il.co.gadiworks.games.framework.math.Vector2;

public class CannonGravityTest extends GLGame {

	@Override
	public Screen getStartScreen() {
		return new CannonGravityScreen(this);
	}
	
	class CannonGravityScreen extends Screen {
		final float FRUSTUM_WIDTH = 9.6f;
		final float FRUSTUM_HEIGHT = 6.4f;
		
		GLGraphics glGraphics;
		Vertices cannonVertices;
		Vertices ballVertices;
		Vector2 cannonPos = new Vector2();
		float cannonAngle = 0;
		Vector2 touchPos = new Vector2();
		Vector2 ballPos = new Vector2(0, 0);
		Vector2 ballVelocity = new Vector2(0, 0);
		Vector2 gravity = new Vector2(0, -10);

		public CannonGravityScreen(Game game) {
			super(game);
			
			this.glGraphics = ((GLGame) game).getGLGraphics();
			
			this.cannonVertices = new Vertices(this.glGraphics, 3, 0, false, false);
			this.cannonVertices.setVertices(new float[] {
					-0.5f, -0.5f,
					 0.5f,  0.0f,
					-0.5f,  0.5f
			}, 0, 6);
			
			this.ballVertices = new Vertices(this.glGraphics, 4, 6, false, false);
			this.ballVertices.setVertices(new float[] {
					-0.1f, -0.1f,
					 0.1f, -0.1f,
					 0.1f,  0.1f,
					-0.1f,  0.1f
			}, 0, 8);
			this.ballVertices.setIndices(new short[] {0, 1, 2, 2, 3, 0}, 0, 6);
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
				
				if (event.type == TouchEvent.TOUCH_UP) {
					float radians = this.cannonAngle * Vector2.TO_RADIANS;
					float ballSpeed = this.touchPos.len();
					
					this.ballPos.set(this.cannonPos);
					
					this.ballVelocity.x = FloatMath.cos(radians) * ballSpeed;
					this.ballVelocity.y = FloatMath.sin(radians) * ballSpeed;
				}
			}
			
			this.ballVelocity.add(this.gravity.x * deltaTime, this.gravity.y * deltaTime);
			this.ballPos.add(this.ballVelocity.x * deltaTime, this.ballVelocity.y * deltaTime);
		}
		
		@Override
		public void present(float deltaTime) {
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
			gl.glColor4f(1, 1, 1, 1);
			
			this.cannonVertices.bind();
			this.cannonVertices.draw(GL10.GL_TRIANGLES, 0, 3);
			this.cannonVertices.unbind();
			
			gl.glLoadIdentity();
			gl.glTranslatef(this.ballPos.x, this.ballPos.y, 0);
			gl.glColor4f(1, 0, 0, 1);
			
			this.ballVertices.bind();
			this.ballVertices.draw(GL10.GL_TRIANGLES, 0, 6);
			this.ballVertices.unbind();
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