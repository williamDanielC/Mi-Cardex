package com.cardex.micardex.data

/**
 * Kardex de William Daniel Cruz López (23270090), tomado del PDF "Seguimiento del Alumno"
 * impreso el 05/11/2026.
 *
 * Cada periodo del PDF se convierte en un semestre (1 al 7). El periodo VERANO/2024 no es
 * un semestre: es la repetición de Cálculo Vectorial, que no se aprobó en ENERO-JUNIO/2024
 * (N/A), por eso esa materia queda en el semestre 3 con calNormal = null y calRepeticion = 83.
 *
 * La tabla no guarda clave ni créditos (solo nombre, semestre y calificaciones).
 */
object KardexInicial {

    private fun m(nombre: String, semestre: Int, normal: Int?, repeticion: Int? = null, especial: Int? = null) =
        Materia(
            nombre = nombre, semestre = semestre,
            calNormal = normal, calRepeticion = repeticion, calEspecial = especial
        )

    val materias: List<Materia> = listOf(
        // Semestre 1 — Enero-Junio 2023
        m("Cálculo Diferencial", 1, 75),
        m("Fundamentos de Programación", 1, 94),
        m("Taller de Ética", 1, 90),
        m("Matemáticas Discretas", 1, 79),
        m("Taller de Administración", 1, 94),
        m("Fundamentos de Investigación", 1, 100),

        // Semestre 2 — Agosto-Diciembre 2023
        m("Cálculo Integral", 2, 90),
        m("Programación Orientada a Objetos", 2, 86),
        m("Contabilidad Financiera", 2, 95),
        m("Química", 2, 82),
        m("Álgebra Lineal", 2, 100),
        m("Física General", 2, 75),
        m("Desarrollo Sustentable", 2, 100),

        // Semestre 3 — Enero-Junio 2024 (+ Verano 2024 para Cálculo Vectorial)
        m("Cálculo Vectorial", 3, normal = null, repeticion = 83),
        m("Estructura de Datos", 3, 90),
        m("Taller de Sistemas Operativos", 3, 83),
        m("Investigación de Operaciones", 3, 80),
        m("Fundamentos de Bases de Datos", 3, 100),
        m("Principios Eléctricos y Aplicaciones Digitales", 3, 85),
        m("Probabilidad y Estadística", 3, 85),

        // Semestre 4 — Agosto-Diciembre 2024
        m("Simulación", 4, 99),
        m("Métodos Numéricos", 4, 92),
        m("Taller de Bases de Datos", 4, 92),
        m("Arquitectura de Computadoras", 4, 94),
        m("Tópicos Avanzados de Programación", 4, 92),
        m("Fundamentos de Telecomunicaciones", 4, 71),
        m("Ecuaciones Diferenciales", 4, 80),

        // Semestre 5 — Enero-Junio 2025
        m("Programación Web", 5, 98),
        m("Redes de Computadoras", 5, 95),
        m("Sistemas Operativos", 5, 92),
        m("Fundamentos de Ingeniería de Software", 5, 81),
        m("Administración de Bases de Datos", 5, 95),
        m("Lenguajes y Autómatas I", 5, 91),

        // Semestre 6 — Agosto-Diciembre 2025
        m("Actividades Complementarias", 6, 100),
        m("Lenguajes y Autómatas II", 6, 78),
        m("Conmutación y Enrutamiento de Redes de Datos", 6, 75),
        m("Cultura Empresarial", 6, 100),
        m("Ingeniería de Software", 6, 75),
        m("Lenguajes de Interfaz", 6, 85),
        m("Graficación", 6, 98),

        // Semestre 7 — Enero-Junio 2026
        m("Taller de Investigación I", 7, 88),
        m("Administración de Redes", 7, 98),
        m("Programación Lógica y Funcional", 7, 95),
        m("Sistemas Programables", 7, 91),
        m("Gestión de Proyectos de Software", 7, 70),
        m("Pro. Proy. y Plns. Tecnológicos", 7, 83),
        m("E-Business", 7, 80)
    )
}
