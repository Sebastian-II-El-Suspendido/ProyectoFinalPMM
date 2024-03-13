package com.example.proyectofinalpmm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalpmm.databinding.ActivityCiudadBinding

class CiudadActivity : BaseActivity() {
    private lateinit var binding: ActivityCiudadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCiudadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)
        val btnContinuarCiudad = findViewById<Button>(R.id.btnContinuarCiudad)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerCity)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        val ciudadesList: List<Pair<Int, Int>> = listOf(
            R.drawable.casa1 to R.drawable.barrio1,
            R.drawable.casa2 to R.drawable.barrio2,
            R.drawable.casa3 to R.drawable.barrio3,
            R.drawable.casa4 to R.drawable.barrio4,
            R.drawable.casa5 to R.drawable.barrio5,
            R.drawable.casa6 to R.drawable.barrio6,
            R.drawable.casa7 to R.drawable.barrio7,
            R.drawable.casa8 to R.drawable.barrio8,
            R.drawable.casa9 to R.drawable.barrio9,
            R.drawable.casa10 to R.drawable.barrio10,
            R.drawable.casa11 to R.drawable.barrio11,
            R.drawable.casa12 to R.drawable.barrio12,
        )
        val zonasList: List<Pair<String, String>> = listOf(
            "Distrito de las Mil Fuentes" to "El Distrito de las Mil Fuentes, es un barrio encantador que se encuentra en el corazón de una ciudad de un mundo de fantasía. Es famoso por sus múltiples fuentes que adornan las esquinas de sus calles empedradas, cada una con un diseño más intrincado que la otra, desde fuentes ornamentales hasta pequeñas cascadas que parecen danzar con el viento. La vegetación es abundante, con enredaderas florales que se trepan por las fachadas de las acogedoras casas de techo de paja, integrando armoniosamente la naturaleza con la arquitectura.\n" +
                    "\n" +
                    "Las fuentes no son solo una muestra de la artesanía local, sino también un lugar de reunión para las criaturas míticas como hadas y espíritus del agua, que a menudo se pueden vislumbrar jugando entre los chorros de agua o descansando en las sombras de los árboles frondosos. La vida en el Distrito de las Mil Fuentes transcurre al ritmo del murmullo del agua, entre mercados de calle que venden desde frutas mágicas hasta artefactos encantados. Los habitantes del distrito son conocidos por su amor al arte y la artesanía, lo cual se refleja en los detalles cuidados de sus hogares y en las plazas públicas.",
            "Barrio de los Sueños Encantados" to "El Barrio de los Sueños Encantados es un lugar de maravilla, donde la magia y la realidad se entrelazan. Las viviendas y los jardines parecen sacados de un cuento de hadas, y sus habitantes parecen tener la habilidad de dar vida a sus más fantásticos sueños. Se dice que en este barrio, lo imposible puede suceder simplemente con desearlo, y sus residentes son conocidos por su alegría y su creatividad sin límites.",
            "Plaza del Dragón Dorado" to "La Plaza del Dragón Dorado es una zona de esplendor y actividad, conocida en todo el reino por su imponente castillo y su próspera comunidad. Las tiendas lujosas que bordean la plaza lucen toldos de seda de vivos colores, y los comerciantes venden desde gemas encantadas hasta tónicos mágicos bajo los estandartes que ondean al viento. Los adoquines centelleantes de las calles guían a los visitantes a través de una colección de estatuas de dragones, cada una capturando la esencia de estas criaturas místicas en diferentes posturas y expresiones.\n" +
                    "\n" +
                    "El aire vibra con la música de trovadores y el zumbido de conversaciones entre ciudadanos y aventureros. Los alquimistas exhiben sus últimos descubrimientos en puestos repletos de frascos humeantes y pergaminos antiguos. Por encima de todo, criaturas mágicas planean en el cielo, su presencia un recordatorio constante de la magia que impregna este lugar.\n" +
                    "\n" +
                    "El castillo en sí es un prodigio arquitectónico, con torretas que se elevan hacia las nubes y una entrada que promete maravillas en su interior. Es el corazón del barrio, una fortaleza de conocimiento y poder donde se reúnen los sabios y gobernantes. Aquí, en la Plaza del Dragón Dorado, la grandeza y la fantasía no solo son posibles, sino cotidianas, y cada piedra y esquina tiene una historia de valentía y encanto esperando ser descubierta.",
            "Callejón de las Sombras Veladas" to "El Callejón de las Sombras Veladas se sumerge en un halo de misterio y tranquilidad, oculto en las profundidades de una urbe bulliciosa. En este barrio, las casas se asientan sobre los árboles centenarios, conectadas por puentes colgantes que se mecen suavemente con la brisa del bosque encantado. Los senderos son estrechos y serpenteantes, apenas iluminados por orbes de luz que flotan con una suave luminiscencia, proyectando sombras danzantes sobre el musgo y la piedra.\n" +
                    "\n" +
                    "Aquí, lejos del ruido y el ajetreo de la ciudad, los sabios y los guardianes de los secretos antiguos encuentran su santuario. Se dice que los habitantes son custodios de conocimientos arcanos y protectores de la naturaleza, y que en sus hogares se tejen encantamientos tan antiguos como los mismos árboles. El Callejón de las Sombras Veladas no es solo un lugar, sino un susurro de la antigua magia que aún perdura en el mundo, una invitación a perderse y quizás encontrarse en los susurros del viento entre las hojas.",
            "Sector de las Maravillas Arcanas" to "El Sector de las Maravillas Arcanas es una joya en el corazón de una ciudad fantástica, una comunidad vibrante donde la magia y lo cotidiano se entretejen sin esfuerzo. Los edificios, con sus cimientos de piedra robusta y elegantes balcones de madera, están adornados con símbolos místicos y objetos encantados que reflejan la riqueza de los conocimientos arcanos que allí se albergan.\n" +
                    "\n" +
                    "Las calles están llenas de vida: magos paseando con sus familias de criaturas míticas, puestos de mercado donde se venden pociones burbujeantes y pergaminos que contienen secretos antiguos. La energía del lugar es palpable, y se siente en el aire la constante vibración de la magia que impregna cada rincón del sector. Aquí, en el Sector de las Maravillas Arcanas, cada paso puede llevar a un descubrimiento asombroso y cada habitante tiene su propia historia fascinante para contar.",
            "Avenida de los Secretos Antiguos" to "La Avenida de los Secretos Antiguos es como un libro abierto de historia viviente, donde cada piedra y cada edificio cuenta una historia del pasado. Este barrio respira un aire de antigüedad y sabiduría, con sus bibliotecas y tiendas de antigüedades que atraen a aquellos en busca de conocimiento y reliquias del ayer. Aquí, el misterio se entreteje con la arquitectura, y cada paso invita a descubrir los enigmas que el tiempo ha querido ocultar.",
            "Zona de las Estrellas Errantes" to "La Zona de las Estrellas Errantes es un barrio sereno y fascinante, famoso por su cielo nocturno que nunca cesa de asombrar. Aquí, las casas con pináculos altos y tejados de paja se alinean a lo largo de calles adoquinadas, sus fachadas adornadas con detalles que reflejan el amor por lo astronómico.\n" +
                    "\n" +
                    "Los habitantes de este barrio son conocidos por su pasión por la astronomía y la astrología, viviendo en un ritmo que sigue el danzar de las constelaciones. Observatorios públicos y telescopios se alzan en plazas y jardines en los techos, invitando a todos a contemplar el vasto universo. La vida aquí gira en torno al estudio y la apreciación del firmamento, con cada evento celestial marcando momentos de reunión y celebración.\n" +
                    "\n" +
                    "En la Zona de las Estrellas Errantes, la curiosidad por los secretos del cosmos une a sus residentes, creando una comunidad donde cada noche se comparten descubrimientos y se forjan amistades bajo el manto estrellado.",
            "Pasaje de la Luna Plateada" to "\n" +
                    "El Pasaje de la Luna Plateada es conocido por su serenidad envuelta en la luz del satélite nocturno. Las casas de estructura de madera y los senderos de adoquines reflejan el suave resplandor lunar, creando un ambiente de calma y reflexión. Los residentes, a menudo inspirados por el cielo estrellado, tienden a ser pensadores, artistas y poetas, cuyas obras nacen bajo el influjo de la luna.",
            "Barriada de Luna Plateada" to "La Barriada de la Luna Plateada es un vecindario impregnado de un encanto que trasciende lo cotidiano, donde las casas con estructuras de madera y fachadas minuciosamente detalladas capturan la esencia de un mundo encantado. Cada tejado parece estar plateado, reflejando el brillo suave del satélite nocturno, mientras que las farolas lunares proporcionan una luz delicada a los caminos y plazas.\n" +
                    "\n" +
                    "En las noches, los mercados cobran vida bajo la luz argéntea, con comerciantes que venden amuletos y artefactos bañados por el misterio lunar. Los habitantes, amantes de la noche y sus misterios, se reúnen para compartir historias y observar los fenómenos celestes, viviendo en armonía con el ciclo de la luna y sus fases. La Barriada de la Luna Plateada no es solo un hogar, sino también un santuario para aquellos que buscan comprender los secretos más profundos que solo la noche puede revelar.",
            "Calle de los Misterios Olvidados" to "La Calle de los Misterios Olvidados es una arteria antigua de la ciudad, donde cada edificación parece custodiar una historia eterna. Las fachadas de las casas, con sus trabajos en madera y sus piedras milenarias, están grabadas con símbolos cuyo significado se ha perdido en el tiempo, esperando ser redescubiertos.\n" +
                    "\n" +
                    "La neblina se cierne sobre los adoquines y la hiedra trepa libre por las paredes, dando al lugar una atmósfera cargada de enigmas. Las farolas proyectan un resplandor suave que apenas dispersa las sombras, invitando a los transeúntes a imaginar las leyendas y relatos olvidados que susurran entre las grietas.\n" +
                    "\n" +
                    "Este es un vecindario de quietud y emociones contenidas, donde cada puerta podría ser el comienzo de una aventura inesperada o la entrada a un mundo perdido, sumergido en los ecos de una época que sólo la luna ha sido testigo.",
            "Sector de las Mariposas de Cristal" to "El Sector de las Mariposas de Cristal es un barrio donde el arte y la belleza natural confluyen en una sinfonía visual. Las mariposas de cristal, símbolo del barrio, revolotean en jardines meticulosamente cuidados, reflejando la luz y el color en cada vuelo. Los residentes y visitantes disfrutan de un ambiente perpetuo de primavera, con calles que resuenan al sonido de campanillas de viento y rodeadas de flores en constante floración. Los artistas y artesanos del sector exhiben su creatividad en cada rincón, con esculturas y murales que complementan la armonía de la naturaleza y la artesanía. Este sector es un testimonio viviente de la pasión por la estética y el diseño, un lugar donde la inspiración brota tan libremente como las alas de sus mariposas emblemáticas.",
            "Área de la Aurora Brillante" to "En el Área de la Aurora Brillante, el alba es recibida con los brazos abiertos y un espíritu de renovación. Este barrio, con su gloriosa arquitectura gótica y sus ventanas de vitrales que capturan la primera luz del día, es el corazón palpitante de la ciudad al amanecer. Los cafés extienden sus terrazas, los músicos callejeros tocan melodías que acompañan el nuevo día, y los residentes salen a las calles para disfrutar del comienzo de sus rutinas. Aquí, cada edificio parece estar diseñado para saludar al sol, con colores vibrantes que celebran el eterno ciclo del renacimiento. Es un lugar de optimismo, donde cada mañana ofrece la promesa de un nuevo comienzo."
        )
        val adapter = ImageAdapter(this, ciudadesList, zonasList)
        recyclerView.adapter = adapter

        val scrollComprar = findViewById<LinearLayout>(R.id.ScrollComprar)
        scrollComprar.visibility = View.GONE
        binding.btnEntrar2.visibility= View.GONE

        val globalButton = findViewById<ImageView>(R.id.ajustesBoton)
        globalButton.setOnClickListener {
            val intentb = Intent(this,AjustesActivity::class.java)
            startActivity(intentb)
        }


        Log.v("Casas",ciudadesList.size.toString())
        Log.v("Barrios",zonasList.size.toString())

        animateText(binding.textView,"Entras a la ciudad (Espero que con los bolsillos bien cargados de oro) con la necesidad de adquirir una nueva propiedad donde poder descansar de tus aventuras. Se te presentan distintas propiedas que puedes comprar")

        btnEntrar.setOnClickListener {
            val intent = Intent(this,MercaderActivity::class.java)
            startActivity(intent)
        }

        btnContinuarCiudad.setOnClickListener {
            binding.textView.visibility=View.GONE
            scrollComprar.visibility=View.VISIBLE
            binding.btnEntrar.visibility=View.GONE
            binding.btnContinuarCiudad.visibility=View.GONE
        }

        binding.btnEntrar2.setOnClickListener {
            binding.textView.visibility=View.VISIBLE
            scrollComprar.visibility=View.GONE
            binding.btnEntrar.visibility=View.VISIBLE
            binding.btnContinuarCiudad.visibility=View.VISIBLE
            binding.btnEntrar2.visibility=View.GONE

        }
    }


    private fun returnMainActivity() {
        val intent = Intent(this, RandomActivity::class.java)
        startActivity(intent)
    }
}