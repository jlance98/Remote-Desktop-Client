package com.example.remotedesktopclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.util.Scanner

class MainActivity : AppCompatActivity(), View.OnClickListener {

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnConnecting = findViewById<Button>(R.id.BtnConnect)
        val btnSend = findViewById<Button>(R.id.BtnSend)

        btnConnecting.setOnClickListener(this)
        btnSend.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.BtnConnect -> {
                CoroutineScope(IO).launch {
                    clientConnect("192.168.1.24", 6666)
                }
            }
            R.id.BtnSend -> {
                CoroutineScope(IO).launch {
                    clientSend("Test send.")
                }
            }
        }
    }

    lateinit var output: PrintWriter
    lateinit var input: Scanner
    lateinit var clientSocket: Socket

    private suspend fun clientConnect(address : String, port: Int) {
        clientSocket = Socket(address, port)
        output = PrintWriter(clientSocket.getOutputStream(), true)
        input = Scanner(clientSocket.getInputStream())
    }

    private suspend fun clientSend(message : String) {
        output.println(message)
        Log.d("MainActivity", input.nextLine())
    }

}