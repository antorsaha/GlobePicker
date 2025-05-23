package com.saha.globepickerdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.saha.globepicker.CountryCodePicker
import com.saha.globepicker.models.GlobeCountry
import com.saha.globepickerdemo.ui.theme.GlobePickerDemoTheme

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GlobePickerDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        Greeting(
                            name = "Android", modifier = Modifier.padding(innerPadding)
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {/*Text(
        text = "Hello $name!",
        modifier = modifier
    )*/

    CountryCodePicker {
        Log.d(TAG, "Greeting: selectedCountry ${it.name}")
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GlobePickerDemoTheme {
        Greeting("Android")
    }
}