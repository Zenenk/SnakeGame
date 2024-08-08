package com.example.snakegame

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var gameView: GameView
    private lateinit var pauseButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameView = findViewById(R.id.game_view)
        pauseButton = findViewById(R.id.btn_pause)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_left -> gameView.snake.moveTo(Direction.left)
            R.id.btn_right -> gameView.snake.moveTo(Direction.right)
            R.id.btn_up -> gameView.snake.moveTo(Direction.up)
            R.id.btn_down -> gameView.snake.moveTo(Direction.down)
            R.id.btn_pause -> togglePauseResume()
        }
    }

    private fun togglePauseResume() {
        if (gameView.isGamePaused()) {
            gameView.resumeGame()
            pauseButton.text = getString(R.string.pause)
        } else {
            gameView.pauseGame()
            pauseButton.text = getString(R.string.play)
        }
    }
}
