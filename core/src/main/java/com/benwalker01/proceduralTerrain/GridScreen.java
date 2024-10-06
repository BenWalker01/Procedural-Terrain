package com.benwalker01.proceduralTerrain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GridScreen extends InputAdapter implements Screen {
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private int tileSize = 30;
    private int offset = 0;
    private int gridHeight;
    private int gridWidth;

    private boolean dragging;
    private float lastX, lastY;

    public GridScreen() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer = new ShapeRenderer();
        dragging = false;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        gridHeight = Gdx.graphics.getHeight() / tileSize;
        gridWidth = Gdx.graphics.getWidth() / tileSize;

        for (int x = 0; x <= gridWidth; x++) {
            for (int y = 0; y <= gridHeight; y++) {
                shapeRenderer.setColor(0, 1, 0, 1);
                shapeRenderer.rect(x * tileSize + offset, y * tileSize + offset, tileSize, tileSize);
            }
        }
        shapeRenderer.end();

    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Buttons.LEFT) {
            dragging = true;
            lastX = screenX;
            lastY = screenY;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (dragging) {
            float deltaX = screenX - lastX;
            float deltaY = screenY - lastY;
            camera.translate(-deltaX, deltaY);
            camera.update();
            lastX = screenX;
            lastY = screenY;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Buttons.LEFT) {
            dragging = false;
        }
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (amountY > 0) {
            tileSize *= 0.5;
        } else if (amountY < 0) {
            tileSize *= 2;
        }
        tileSize = Math.min(Math.max(tileSize, 5), 100);
        return true;
    }
}