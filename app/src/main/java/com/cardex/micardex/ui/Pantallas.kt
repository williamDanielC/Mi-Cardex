@file:OptIn(ExperimentalMaterial3Api::class)

package com.cardex.micardex.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cardex.micardex.data.Materia
import com.cardex.micardex.data.PromedioSemestre
import java.util.Locale

// =====================================================================
// TEMA VISUAL (solo apariencia: no toca datos ni lógica)
// Se aplica dentro de cada pantalla, así MainActivity no necesita cambios.
// =====================================================================
private val EsquemaCardex = lightColorScheme(
    primary = Color(0xFF0B5563),            // verde azulado profundo
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD3EBEF),
    onPrimaryContainer = Color(0xFF06323A),
    background = Color(0xFFF3F6F7),
    surface = Color(0xFFF3F6F7),
    onSurface = Color(0xFF172326),
    surfaceVariant = Color(0xFFE2E8EA),
    onSurfaceVariant = Color(0xFF4A5A5F),
    outline = Color(0xFFB9C4C8),
    outlineVariant = Color(0xFFD5DDE0),
    error = Color(0xFFB3261E)
)

private val Numeros = FontFamily.Serif      // las calificaciones se leen como en un acta
private const val NOTA_MINIMA = 70

@Composable
private fun Marco(contenido: @Composable () -> Unit) {
    MaterialTheme(colorScheme = EsquemaCardex, content = contenido)
}

@Composable
private fun barraSuperior() = TopAppBarDefaults.topAppBarColors(
    containerColor = MaterialTheme.colorScheme.background
)

@Composable
private fun colorTarjeta() = CardDefaults.cardColors(containerColor = Color.White)

@Composable
private fun bordeTarjeta() = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)

private fun formato(valor: Double?): String =
    if (valor == null) "—" else String.format(Locale.US, "%.2f", valor)

private fun iniciales(nombre: String): String =
    nombre.split(" ").filter { it.isNotBlank() }.take(2)
        .joinToString("") { it.first().uppercase() }.ifEmpty { "?" }

// =====================================================================
// 1) PANTALLA DE DATOS DEL ALUMNO (se guardan en SharedPreferences)
// =====================================================================
@Composable
fun PantallaAlumno(
    controlInicial: String,
    nombreInicial: String,
    puedeRegresar: Boolean,
    onGuardar: (String, String) -> Unit,
    onRegresar: () -> Unit
) = Marco {
    var control by rememberSaveable { mutableStateOf(controlInicial) }
    var nombre by rememberSaveable { mutableStateOf(nombreInicial) }
    val valido = control.isNotBlank() && nombre.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (puedeRegresar) "Mis datos" else "Bienvenido") },
                colors = barraSuperior(),
                navigationIcon = {
                    if (puedeRegresar) IconButton(onClick = onRegresar) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Regresar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier.padding(padding).padding(horizontal = 20.dp, vertical = 12.dp).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                Modifier.size(72.dp).background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (nombre.isBlank()) {
                    Icon(
                        Icons.Filled.Person, null,
                        Modifier.size(36.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                } else {
                    Text(
                        iniciales(nombre),
                        fontFamily = Numeros,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            Text(
                "Escribe tus datos una sola vez. Se guardan en tu teléfono y aparecen " +
                        "en el encabezado de tu cardex.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            OutlinedTextField(
                value = control, onValueChange = { control = it },
                label = { Text("Número de control") }, singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = nombre, onValueChange = { nombre = it },
                label = { Text("Nombre completo") }, singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { onGuardar(control, nombre) },
                enabled = valido,
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) { Text("Guardar mis datos") }
        }
    }
}

// =====================================================================
// 2) PANTALLA PRINCIPAL: LISTA DEL CARDEX
// =====================================================================
@Composable
fun PantallaCardex(
    titulo: String,
    numeroControl: String,
    nombre: String,
    materias: List<Materia>,
    promedioGeneral: Double?,
    onAgregar: () -> Unit,
    onEditar: (Materia) -> Unit,
    onConsultas: () -> Unit,
    onEditarAlumno: () -> Unit
) = Marco {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(titulo, fontWeight = FontWeight.SemiBold) },
                colors = barraSuperior(),
                actions = {
                    TextButton(onClick = onConsultas) { Text("Promedios") }
                    IconButton(onClick = onEditarAlumno) { Icon(Icons.Filled.Person, "Mis datos") }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAgregar,
                icon = { Icon(Icons.Filled.Add, null) },
                text = { Text("Agregar materia") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp, 8.dp, 16.dp, 96.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                EncabezadoAlumno(nombre, numeroControl, materias, promedioGeneral)
            }

            if (materias.isEmpty()) {
                item {
                    Column(Modifier.fillMaxWidth().padding(top = 48.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Tu cardex está vacío", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Toca “Agregar materia” para registrar la primera.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Agrupa visualmente las materias por semestre
            materias.groupBy { it.semestre }.toSortedMap().forEach { (semestre, lista) ->
                item(key = "sem$semestre") {
                    val prom = lista.mapNotNull { it.calificacionFinal }.takeIf { it.isNotEmpty() }?.average()
                    Row(
                        Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Semestre $semestre",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            "Promedio ${formato(prom)}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                }
                items(lista, key = { it.id }) { materia ->
                    TarjetaMateria(materia, onClick = { onEditar(materia) })
                }
            }
        }
    }
}

@Composable
private fun EncabezadoAlumno(
    nombre: String,
    numeroControl: String,
    materias: List<Materia>,
    promedioGeneral: Double?
) {
    val aprobadas = materias.count { (it.calificacionFinal ?: 0) >= NOTA_MINIMA }
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(48.dp).background(Color.White.copy(alpha = 0.18f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        iniciales(nombre),
                        fontFamily = Numeros, fontWeight = FontWeight.Bold,
                        fontSize = 18.sp, color = Color.White
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        nombre, style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold, color = Color.White
                    )
                    Text(
                        "No. de control $numeroControl",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Column(Modifier.weight(1f)) {
                    Text(
                        "Promedio general",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        formato(promedioGeneral),
                        fontFamily = Numeros, fontWeight = FontWeight.Bold,
                        fontSize = 44.sp, color = Color.White
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "${materias.size} materia(s)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        "$aprobadas aprobada(s)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}

@Composable
private fun TarjetaMateria(materia: Materia, onClick: () -> Unit) {
    val calFinal = materia.calificacionFinal
    val aprobada = calFinal != null && calFinal >= NOTA_MINIMA
    val colorFinal = when {
        calFinal == null -> MaterialTheme.colorScheme.onSurfaceVariant
        aprobada -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.error
    }
    Card(
        onClick = onClick,
        colors = colorTarjeta(),
        border = bordeTarjeta(),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(Modifier.padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(materia.nombre, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    materia.calNormal?.let {
                        EtiquetaIntento("Normal $it", cuenta = materia.tipoFinal == "Normal")
                    }
                    materia.calRepeticion?.let {
                        EtiquetaIntento("Repetición $it", cuenta = materia.tipoFinal == "Repetición")
                    }
                    materia.calEspecial?.let {
                        EtiquetaIntento("Especial $it", cuenta = materia.tipoFinal == "Especial")
                    }
                    if (calFinal == null) {
                        Text(
                            "Sin calificación",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            Spacer(Modifier.width(12.dp))
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    calFinal?.toString() ?: "—",
                    fontFamily = Numeros, fontWeight = FontWeight.Bold,
                    fontSize = 30.sp, color = colorFinal
                )
                Text(
                    if (calFinal == null) "" else if (aprobada) "Aprobada" else "No aprobada",
                    style = MaterialTheme.typography.labelSmall,
                    color = colorFinal
                )
            }
        }
    }
}

/** Pequeña etiqueta de un intento; la que cuenta para el promedio se resalta. */
@Composable
private fun EtiquetaIntento(texto: String, cuenta: Boolean) {
    val fondo = if (cuenta) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
    val letra = if (cuenta) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
    Text(
        texto,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = if (cuenta) FontWeight.Bold else FontWeight.Normal,
        color = letra,
        modifier = Modifier
            .background(fondo, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    )
}

// =====================================================================
// 3) FORMULARIO PARA AGREGAR / EDITAR UNA MATERIA
// =====================================================================
@Composable
fun PantallaMateria(
    materia: Materia?,                 // null = materia nueva
    onGuardar: (Materia) -> Unit,
    onEliminar: (Materia) -> Unit,
    onRegresar: () -> Unit
) = Marco {
    var nombre by rememberSaveable { mutableStateOf(materia?.nombre ?: "") }
    var semestre by rememberSaveable { mutableStateOf(materia?.semestre?.toString() ?: "") }
    var normal by rememberSaveable { mutableStateOf(materia?.calNormal?.toString() ?: "") }
    var repeticion by rememberSaveable { mutableStateOf(materia?.calRepeticion?.toString() ?: "") }
    var especial by rememberSaveable { mutableStateOf(materia?.calEspecial?.toString() ?: "") }
    var confirmarBorrado by remember { mutableStateOf(false) }

    // Validaciones (sin cambios)
    fun calValida(t: String) = t.isBlank() || (t.toIntOrNull()?.let { it in 0..100 } ?: false)
    val semestreNum = semestre.toIntOrNull()
    val semestreOk = semestreNum != null && semestreNum in 1..15
    val todoOk = nombre.isNotBlank() && semestreOk &&
            calValida(normal) && calValida(repeticion) && calValida(especial)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (materia == null) "Nueva materia" else "Editar materia") },
                colors = barraSuperior(),
                navigationIcon = {
                    IconButton(onClick = onRegresar) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Regresar") }
                },
                actions = {
                    if (materia != null) IconButton(onClick = { confirmarBorrado = true }) {
                        Icon(Icons.Filled.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier.padding(padding).padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Bloque 1: datos de la materia
            Card(colors = colorTarjeta(), border = bordeTarjeta(), shape = RoundedCornerShape(16.dp)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Materia", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
                    OutlinedTextField(
                        value = nombre, onValueChange = { nombre = it },
                        label = { Text("Nombre de la materia") }, singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    CampoNumero("Semestre (1–15)", semestre, { semestre = it }, error = semestre.isNotBlank() && !semestreOk)
                }
            }

            // Bloque 2: calificaciones
            Card(colors = colorTarjeta(), border = bordeTarjeta(), shape = RoundedCornerShape(16.dp)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Calificaciones", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
                    Text(
                        "De 0 a 100. Deja vacío el intento que no aplique; cuenta el más reciente.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    CampoNumero("Curso normal", normal, { normal = it }, error = !calValida(normal))
                    CampoNumero("Repetición", repeticion, { repeticion = it }, error = !calValida(repeticion))
                    CampoNumero("Especial", especial, { especial = it }, error = !calValida(especial))
                }
            }

            Button(
                onClick = {
                    onGuardar(
                        Materia(
                            id = materia?.id ?: 0,
                            nombre = nombre.trim(),
                            semestre = semestreNum!!,
                            calNormal = normal.toIntOrNull(),
                            calRepeticion = repeticion.toIntOrNull(),
                            calEspecial = especial.toIntOrNull()
                        )
                    )
                },
                enabled = todoOk,
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) { Text(if (materia == null) "Agregar al cardex" else "Guardar cambios") }
        }
    }

    if (confirmarBorrado && materia != null) {
        AlertDialog(
            onDismissRequest = { confirmarBorrado = false },
            title = { Text("Eliminar materia") },
            text = { Text("¿Eliminar \"${materia.nombre}\" del cardex? No se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = { confirmarBorrado = false; onEliminar(materia) }) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = { TextButton(onClick = { confirmarBorrado = false }) { Text("Cancelar") } }
        )
    }
}

@Composable
private fun CampoNumero(etiqueta: String, valor: String, onCambio: (String) -> Unit, error: Boolean) {
    OutlinedTextField(
        value = valor,
        onValueChange = { nuevo -> if (nuevo.all { it.isDigit() } && nuevo.length <= 3) onCambio(nuevo) },
        label = { Text(etiqueta) },
        isError = error,
        supportingText = { if (error) Text("Valor fuera de rango") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}

// =====================================================================
// 4) CONSULTAS: PROMEDIO GENERAL Y POR SEMESTRE
// =====================================================================
@Composable
fun PantallaConsultas(
    promedioGeneral: Double?,
    porSemestre: List<PromedioSemestre>,
    onRegresar: () -> Unit
) = Marco {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Promedios") },
                colors = barraSuperior(),
                navigationIcon = {
                    IconButton(onClick = onRegresar) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Regresar") }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(24.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Promedio general",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                        Text(
                            formato(promedioGeneral),
                            fontFamily = Numeros, fontWeight = FontWeight.Bold,
                            fontSize = 56.sp, color = Color.White
                        )
                    }
                }
            }
            item {
                Text(
                    "Por semestre",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
            if (porSemestre.isEmpty()) {
                item {
                    Text(
                        "Aún no hay calificaciones. Agrega materias en tu cardex para ver tus promedios.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            items(porSemestre, key = { it.semestre }) { fila ->
                Card(
                    colors = colorTarjeta(),
                    border = bordeTarjeta(),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text("Semestre ${fila.semestre}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                                Text(
                                    "${fila.materias} materia(s)",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(
                                formato(fila.promedio),
                                fontFamily = Numeros, fontWeight = FontWeight.Bold, fontSize = 26.sp
                            )
                        }
                        LinearProgressIndicator(
                            progress = { ((fila.promedio ?: 0.0) / 100.0).toFloat().coerceIn(0f, 1f) },
                            modifier = Modifier.fillMaxWidth().height(6.dp),
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                }
            }
            item {
                Text(
                    "Cuenta la calificación del último intento: Especial, si no Repetición, si no Normal.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}