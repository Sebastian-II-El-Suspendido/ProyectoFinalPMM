package com.example.proyectofinalpmm

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalpmm.databinding.ActivityChatBotBinding
import java.util.Locale

class ChatBotActivity : BaseActivity(), TextToSpeech.OnInitListener {
    private lateinit var textToSpeech: TextToSpeech

    private lateinit var binding: ActivityChatBotBinding

    private lateinit var eleccion: LinearLayout
    private lateinit var chat: RelativeLayout

    private lateinit var humanoImg: ImageView
    private lateinit var elfoImg: ImageView
    private lateinit var enanoImg: ImageView
    private lateinit var malditoImg: ImageView

    private lateinit var msg: EditText
    private lateinit var btnEnviar: Button

    private val messageList = mutableListOf<Mensajes>()
    private lateinit var messageAdapter: MessageAdapter

    private var personaje: PersonajeP? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // text to speech
        textToSpeech = TextToSpeech(this, this)

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
            personaje = PersonajeP(
                "Pepe el humano",
                PersonajeP.Raza.Humano,
                PersonajeP.Clase.entries.random(),
                PersonajeP.EstadoVital.entries.random(),
                this
            )
            eleccion.visibility = View.GONE
            chat.visibility = View.VISIBLE
        }

        elfoImg.setOnClickListener {
            personaje = PersonajeP(
                "Francisdo el elfo",
                PersonajeP.Raza.Elfo,
                PersonajeP.Clase.entries.random(),
                PersonajeP.EstadoVital.entries.random(),
                this
            )
            eleccion.visibility = View.GONE
            chat.visibility = View.VISIBLE
        }

        enanoImg.setOnClickListener {
            personaje = PersonajeP(
                "Paco el enano",
                PersonajeP.Raza.Enano,
                PersonajeP.Clase.entries.random(),
                PersonajeP.EstadoVital.entries.random(),
                this
            )
            eleccion.visibility = View.GONE
            chat.visibility = View.VISIBLE
        }

        malditoImg.setOnClickListener {
            personaje = PersonajeP(
                "Lucifer el maldito",
                PersonajeP.Raza.Maldito,
                PersonajeP.Clase.entries.random(),
                PersonajeP.EstadoVital.entries.random(),
                this
            )
            eleccion.visibility = View.GONE
            chat.visibility = View.VISIBLE
        }

        btnEnviar.setOnClickListener {
            if (!msg.text.isNullOrEmpty()) {
                val mensaje = msg.text.toString()
                val respuesta = personaje?.comunicacion(msg.text.toString()) ?: "Default"
                messageList.add(Mensajes(mensaje, "Usuario"))
                textToSpeech.speak(mensaje, TextToSpeech.QUEUE_ADD, null, null)
                messageList.add(Mensajes( respuesta, personaje?.getNombre() ?: "Default"))
                textToSpeech.speak(respuesta, TextToSpeech.QUEUE_ADD, null, null)
                messageAdapter.notifyDataSetChanged()
                binding.recyclerViewMessages.smoothScrollToPosition(messageList.size - 1)
            }
        }

        // Configurar RecyclerView
        messageAdapter = MessageAdapter(messageList)
        binding.recyclerViewMessages.adapter = messageAdapter
        binding.recyclerViewMessages.layoutManager = LinearLayoutManager(this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale("es", "ES"))

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                textToSpeech.setLanguage(Locale("es", "ES"))
            }
        }
    }

    override fun onDestroy() {
        textToSpeech.stop()
        textToSpeech.shutdown()
        super.onDestroy()
    }
}

class MessageAdapter(private val messageList: List<Mensajes>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messageList[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textContent: TextView = itemView.findViewById(R.id.textMessageContent)
        private val textSender: TextView = itemView.findViewById(R.id.textMessageSender)

        fun bind(mensaje: Mensajes) {
            textContent.text = mensaje.content
            textSender.text = mensaje.sender
        }
    }
}