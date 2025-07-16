package com.zeno.core

import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private lateinit var textOutput: TextView
    private lateinit var btnSpeak: Button
    private lateinit var btnGenerate: Button
    private var voiceText = "Bienvenue dans ZENO Core, cerveau autonome IA activé."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init Timber Logger
        Timber.plant(Timber.DebugTree())
        Timber.tag("ZENO_REFLEX").d("Démarrage de l'app...")

        textOutput = findViewById(R.id.zenoOutput)
        btnSpeak = findViewById(R.id.buttonSpeak)
        btnGenerate = findViewById(R.id.buttonGenerate)

        // Init synthèse vocale
        tts = TextToSpeech(this, this)

        btnSpeak.setOnClickListener {
            speakText(voiceText)
        }

        btnGenerate.setOnClickListener {
            voiceText = "Réponse IA générée : L’avenir appartient à ceux qui codent tard la nuit."
            textOutput.text = voiceText
            Timber.i("Réponse IA générée.")
        }
    }

    private fun speakText(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.FRENCH)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("ZENO_TTS", "Langue non supportée.")
            } else {
                Timber.i("Synthèse vocale prête.")
            }
        } else {
            Timber.e("Échec initialisation TTS.")
        }
    }

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}
