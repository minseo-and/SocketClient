package com.example.socket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var btn_connect : Button
    lateinit var text : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text = findViewById(R.id.tv_text)
        btn_connect = findViewById(R.id.btn_connect)
        btn_connect.setOnClickListener {
            thread {
                val socket = Socket("192.168.117.15",8080)
                Log.d("test", "$socket")

                val inputString = socket.getInputStream()
                val dis = DataInputStream(inputString)
                val a1 = dis.readInt()
                val a2 = dis.readDouble()
                val a3 = dis.readBoolean()
                val a4= dis.readUTF()

                runOnUiThread {
                    text.text = "a1 : $a1\n"
                    text.append("a2 : $a2\n")
                    text.append("a3 : $a3\n")
                    text.append("a4 : $a4\n")
                }

                val outPutString = socket.getOutputStream()
                val dos = DataOutputStream(outPutString)

                //서버로 데이터 전달
                dos.writeInt(200)
                dos.writeDouble(22.22)
                dos.writeBoolean(false)
                dos.writeUTF("클라이언트가 보낸 문자열")

                socket.close()
            }
        }
    }
}