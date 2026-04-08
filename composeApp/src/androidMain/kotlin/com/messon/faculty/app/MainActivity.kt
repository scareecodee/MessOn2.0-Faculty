package com.messon.faculty.app

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.messon.faculty.app.core.auth.GoogleAuth
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.init
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        FileKit.init(this)
        setContent {
            val activity = LocalActivity.current as Activity
            val googleAuth: GoogleAuth = koinInject { parametersOf(activity) }
            App(
                googleAuth=googleAuth,
            )
        }
    }
}


