package com.example.proyectofinalpmm

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalpmm.base_de_datos.SQLiteHelper
import com.example.proyectofinalpmm.databinding.ActivityChatBotBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale

class ChatBotActivity : BaseActivity(), TextToSpeech.OnInitListener {
    private lateinit var textToSpeech: TextToSpeech

    private lateinit var binding: ActivityChatBotBinding

    private lateinit var eleccion: LinearLayout
    private lateinit var chat: RelativeLayout

    private lateinit var random: RandomActivity

    private lateinit var humanoImg: ImageView
    private lateinit var elfoImg: ImageView
    private lateinit var enanoImg: ImageView
    private lateinit var malditoImg: ImageView

    private lateinit var msg: EditText
    private lateinit var btnEnviar: Button

    private val messageListHumano = mutableListOf<Mensajes>()
    private val messageListElfo = mutableListOf<Mensajes>()
    private val messageListEnano = mutableListOf<Mensajes>()
    private val messageListMaldito = mutableListOf<Mensajes>()

    private lateinit var messageAdapterHumano: MessageAdapter
    private lateinit var messageAdapterElfo: MessageAdapter
    private lateinit var messageAdapterEnano: MessageAdapter
    private lateinit var messageAdapterMaldito: MessageAdapter

    private var personaje: PersonajeP? = null

    private lateinit var imgChat : ImageView

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombreHumano = "Pepe el humano"
        val nombreElfo = "Francisca la elfa"
        val nombreEnano = "Paco el enano"
        val nombreMaldito = "Lucifer el maldito"

        // text to speech
        textToSpeech = TextToSpeech(this, this)

        // base de datos
        val auth = FirebaseAuth.getInstance()
        val idUser = auth.currentUser?.uid
        val dbHelper = SQLiteHelper(this)
        //dbHelper.borrarBaseDeDatos(this)

        var currentChat = nombreHumano
        val chatHumano = idUser?.let { dbHelper.obtenerChatPorIDPersonaje(nombreHumano, it) }
        val chatElfo = idUser?.let { dbHelper.obtenerChatPorIDPersonaje(nombreElfo, it) }
        val chatEnano = idUser?.let { dbHelper.obtenerChatPorIDPersonaje(nombreEnano, it) }
        val chatMaldito = idUser?.let { dbHelper.obtenerChatPorIDPersonaje(nombreMaldito, it) }
        val chatPersonaHumano = idUser?.let { dbHelper.obtenerChatPorIDPersonaje(it, nombreHumano) }
        val chatPersonaElfo = idUser?.let { dbHelper.obtenerChatPorIDPersonaje(it, nombreElfo) }
        val chatPersonaEnano = idUser?.let { dbHelper.obtenerChatPorIDPersonaje(it, nombreEnano) }
        val chatPersonaMaldito =
            idUser?.let { dbHelper.obtenerChatPorIDPersonaje(it, nombreMaldito) }

        messageListHumano.addAll(juntarListas(listOf(chatHumano, chatPersonaHumano), nombreHumano))

        messageListElfo.addAll(juntarListas(listOf(chatElfo, chatPersonaElfo), nombreElfo))

        messageListEnano.addAll(juntarListas(listOf(chatEnano, chatPersonaEnano), nombreEnano))

        messageListMaldito.addAll(
            juntarListas(
                listOf(chatMaldito, chatPersonaMaldito),
                nombreMaldito
            )
        )

        messageAdapterHumano = ContextCompat.getDrawable(this, R.drawable.iconohumano)
            ?.let { MessageAdapter(messageListHumano, it) }!!
        binding.recyclerViewMessages.adapter = messageAdapterHumano
        binding.recyclerViewMessages.layoutManager = LinearLayoutManager(this)

        // Enlazar vistas
        eleccion = binding.eleccion
        chat = binding.chat
        eleccion.visibility = View.VISIBLE
        chat.visibility = View.GONE

        humanoImg = binding.humano
        elfoImg = binding.elfo
        enanoImg = binding.enano
        malditoImg = binding.maldito

        msg = binding.msg
        btnEnviar = binding.btnEnviar

        humanoImg.setOnClickListener {
            currentChat = nombreHumano
            val drawable = ContextCompat.getDrawable(this, R.drawable.iconohumano)
            messageAdapterHumano = drawable?.let { it1 -> MessageAdapter(messageListHumano, it1) }!!
            binding.recyclerViewMessages.adapter = messageAdapterHumano
            binding.recyclerViewMessages.layoutManager = LinearLayoutManager(this)
            if (messageListHumano.isEmpty()) messageListHumano.add(Mensajes("Dime Algo", currentChat, "TU"))
            messageAdapterHumano.notifyDataSetChanged()
            personaje = dbHelper.obtenerPersonajePorID(nombreHumano, this)
            Log.i("hola", personaje.toString())
            eleccion.visibility = View.GONE
            chat.visibility = View.VISIBLE
        }

        elfoImg.setOnClickListener {
            currentChat = nombreElfo
            val drawable = ContextCompat.getDrawable(this, R.drawable.iconoelfo)
            messageAdapterElfo = drawable?.let { it1 -> MessageAdapter(messageListElfo, it1) }!!
            binding.recyclerViewMessages.adapter = messageAdapterElfo
            binding.recyclerViewMessages.layoutManager = LinearLayoutManager(this)
            if (messageListElfo.isEmpty()) messageListElfo.add(Mensajes("Dime Algo", currentChat, "TU"))
            messageAdapterElfo.notifyDataSetChanged()
            personaje = dbHelper.obtenerPersonajePorID(nombreElfo, this)
            eleccion.visibility = View.GONE
            chat.visibility = View.VISIBLE
        }

        enanoImg.setOnClickListener {
            currentChat = nombreEnano
            val drawable = ContextCompat.getDrawable(this, R.drawable.iconoenano)
            messageAdapterEnano = drawable?.let { it1 -> MessageAdapter(messageListEnano, it1) }!!
            binding.recyclerViewMessages.adapter = messageAdapterEnano
            binding.recyclerViewMessages.layoutManager = LinearLayoutManager(this)
            if (messageListEnano.isEmpty()) messageListEnano.add(Mensajes("Dime Algo", currentChat, "TU"))
            messageAdapterEnano.notifyDataSetChanged()
            personaje = dbHelper.obtenerPersonajePorID(nombreEnano, this)
            eleccion.visibility = View.GONE
            chat.visibility = View.VISIBLE

        }

        malditoImg.setOnClickListener {
            currentChat = nombreMaldito
            val drawable = ContextCompat.getDrawable(this, R.drawable.iconomaldito)
            messageAdapterMaldito = drawable?.let { it1 -> MessageAdapter(messageListMaldito, it1) }!!
            binding.recyclerViewMessages.adapter = messageAdapterMaldito
            binding.recyclerViewMessages.layoutManager = LinearLayoutManager(this)
            if (messageListMaldito.isEmpty()) messageListMaldito.add(Mensajes("Dime Algo", currentChat, "TU"))
            messageAdapterMaldito.notifyDataSetChanged()
            personaje = dbHelper.obtenerPersonajePorID(nombreMaldito, this)
            eleccion.visibility = View.GONE
            chat.visibility = View.VISIBLE
        }

        btnEnviar.setOnClickListener {
            if (!msg.text.isNullOrEmpty()) {
                val mensaje = msg.text.toString()
                val respuesta = personaje?.comunicacion(msg.text.toString()) ?: "Default"
                when (currentChat) {
                    nombreHumano -> {
                        messageListHumano.add(Mensajes(mensaje, "TU", currentChat))
                        textToSpeech.speak(mensaje, TextToSpeech.QUEUE_ADD, null, null)
                        messageListHumano.add(Mensajes(respuesta, currentChat, "TU"))
                        textToSpeech.speak(respuesta, TextToSpeech.QUEUE_ADD, null, null)
                        messageAdapterHumano.notifyDataSetChanged()
                        binding.recyclerViewMessages.smoothScrollToPosition(messageListHumano.size - 1)
                        if (idUser != null) {
                            dbHelper.insertarMensajeChat(idUser, currentChat, mensaje)
                            dbHelper.insertarMensajeChat(currentChat, idUser, respuesta)
                        }
                    }

                    nombreElfo -> {
                        messageListElfo.add(Mensajes(mensaje, "TU", currentChat))
                        textToSpeech.speak(mensaje, TextToSpeech.QUEUE_ADD, null, null)
                        messageListElfo.add(Mensajes(respuesta, currentChat, "TU"))
                        textToSpeech.speak(respuesta, TextToSpeech.QUEUE_ADD, null, null)
                        messageAdapterElfo.notifyDataSetChanged()
                        binding.recyclerViewMessages.smoothScrollToPosition(messageListElfo.size - 1)
                        if (idUser != null) {
                            dbHelper.insertarMensajeChat(idUser, currentChat, mensaje)
                            dbHelper.insertarMensajeChat(currentChat, idUser, respuesta)
                        }
                    }

                    nombreEnano -> {
                        messageListEnano.add(Mensajes(mensaje, "TU", currentChat))
                        textToSpeech.speak(mensaje, TextToSpeech.QUEUE_ADD, null, null)
                        messageListEnano.add(Mensajes(respuesta, currentChat, "TU"))
                        textToSpeech.speak(respuesta, TextToSpeech.QUEUE_ADD, null, null)
                        messageAdapterEnano.notifyDataSetChanged()
                        binding.recyclerViewMessages.smoothScrollToPosition(messageListEnano.size - 1)
                        if (idUser != null) {
                            dbHelper.insertarMensajeChat(idUser, currentChat, mensaje)
                            dbHelper.insertarMensajeChat(currentChat, idUser, respuesta)
                        }
                    }

                    nombreMaldito -> {
                        messageListMaldito.add(Mensajes(mensaje, "TU", currentChat))
                        textToSpeech.speak(mensaje, TextToSpeech.QUEUE_ADD, null, null)
                        messageListMaldito.add(Mensajes(respuesta, currentChat, "TU"))
                        textToSpeech.speak(respuesta, TextToSpeech.QUEUE_ADD, null, null)
                        messageAdapterMaldito.notifyDataSetChanged()
                        binding.recyclerViewMessages.smoothScrollToPosition(messageListMaldito.size - 1)
                        if (idUser != null) {
                            dbHelper.insertarMensajeChat(idUser, currentChat, mensaje)
                            dbHelper.insertarMensajeChat(currentChat, idUser, respuesta)
                        }
                    }
                }
                msg.text.clear()
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale("es", "ES"))

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                textToSpeech.setLanguage(Locale("en", "US"))
            }
        }
    }

    override fun onDestroy() {
        textToSpeech.stop()
        textToSpeech.shutdown()
        super.onDestroy()
    }
}

private fun juntarListas(lista: List<List<String>?>, nombre: String): MutableList<Mensajes> {
    val listaR = mutableListOf<Mensajes>()

    val chat1 = lista.getOrNull(1) ?: emptyList()
    val chat2 = lista.getOrNull(0) ?: emptyList()

    val mensajes = chat1.zip(chat2) { mensaje1, mensaje2 ->
        mensaje1 to mensaje2
    }

    for ((mensaje1, mensaje2) in mensajes) {
        if (mensaje1.isNotEmpty() || mensaje2.isNotEmpty()) {
            if (mensaje1.isNotEmpty()) {
                listaR.add(Mensajes(mensaje1, "TU", nombre))
            }
            if (mensaje2.isNotEmpty()) {
                listaR.add(Mensajes(mensaje2, nombre, "TU"))
            }
        }
    }

    if (listaR.isEmpty()) {
        println("Ambas listas están vacías.")
    }

    return listaR
}

class MessageAdapter(private val messageList: List<Mensajes>, private val imagenResId: Drawable) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messageList[position]
        holder.bind(message, imagenResId)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textContent: TextView = itemView.findViewById(R.id.textMessageContent)
        private val textSender: TextView = itemView.findViewById(R.id.textMessageSender)
        private val imagen: ImageView = itemView.findViewById(R.id.imageView5)
        private val imagenGif: ImageView = itemView.findViewById(R.id.GifChat)
        // private var animationView: LottieAnimationView = itemView.findViewById(R.id.animationView4)
        private var imageView : ImageView = itemView.findViewById(R.id.imageView5)

        fun bind(mensaje: Mensajes, id:Drawable) {
            animateText(textContent, mensaje.content)
            //animationView.playAnimation()
            textSender.text = mensaje.sender
            imageView.setImageDrawable(id)

            if(mensaje.sender=="Usuario"){
                imagen.visibility = View.GONE
                imagenGif.visibility= View.GONE

            }

        }
    }
}


