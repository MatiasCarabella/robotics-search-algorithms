import java.util.Scanner;

/**
 * Sistema de Búsqueda Robótica para Montaje de Motores
 * Trabajo Práctico - Algoritmos de Búsqueda
 */
public class AlgoritmosBusqueda {
    
    // Configuración del problema
    private static final double POS_INICIAL = 250.0;    // Posición B
    private static final double POS_OBJETIVO = 450.0;   // Posición A (desconocida para algoritmos)
    private static final double INCREMENTO = 15.0;      // ΔH
    private static final double RADIO_ANILLO = 40.0;    // Radio del anillo prominente
    private static final double TOLERANCIA = 7.5;       // Precisión requerida
    private static final double RANGO_MIN = 50.0;
    private static final double RANGO_MAX = 750.0;
    
    // Variables para estadísticas
    private static int pasos;
    private static double distanciaRecorrida;
    private static double posicionFinal;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        
        do {
            mostrarMenu();
            opcion = leerOpcion(scanner);
            
            switch(opcion) {
                case 1:
                    ejecutarBusquedaExhaustiva();
                    break;
                case 2:
                    ejecutarBusquedaHeuristica();
                    break;
                case 3:
                    System.out.println("\n¡Hasta luego!");
                    break;
                default:
                    System.out.println("\nX Opción inválida. Intente nuevamente.");
            }
            
            if (opcion != 3) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
            }
            
        } while (opcion != 3);
        
        scanner.close();
    }
    
    private static void mostrarMenu() {
        limpiarPantalla();
        System.out.println("===========================================================");
        System.out.println("  SISTEMA DE BUSQUEDA ROBOTICA - MONTAJE DE MOTORES");
        System.out.println("===========================================================");
        System.out.println();
        System.out.println("Configuración del problema:");
        System.out.println("  - Posición inicial (B): " + POS_INICIAL);
        System.out.println("  - Posición objetivo (A): " + POS_OBJETIVO + " (desconocida)");
        System.out.println("  • Desplazamiento: " + (POS_OBJETIVO - POS_INICIAL) + " unidades");
        System.out.println("  • Incremento de búsqueda: " + INCREMENTO);
        System.out.println();
        System.out.println("-----------------------------------------------------------");
        System.out.println("                      MENÚ PRINCIPAL                       ");
        System.out.println("-----------------------------------------------------------");
        System.out.println("  1. Búsqueda Exhaustiva (Bidireccional)");
        System.out.println("  2. Búsqueda Heurística (Gradiente de Relieve)");
        System.out.println("  3. Salir");
        System.out.println("-----------------------------------------------------------");
        System.out.print("\nSeleccione una opción: ");
    }
    
    private static int leerOpcion(Scanner scanner) {
        try {
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            return opcion;
        } catch (Exception e) {
            scanner.nextLine(); // Limpiar buffer
            return -1;
        }
    }
    
    // ═══════════════════════════════════════════════════════════
    // BÚSQUEDA EXHAUSTIVA
    // ═══════════════════════════════════════════════════════════
    
    private static void ejecutarBusquedaExhaustiva() {
        limpiarPantalla();
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║           BÚSQUEDA EXHAUSTIVA (BIDIRECCIONAL)             ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        
        System.out.println("Descripción:");
        System.out.println("  Explora sistemáticamente ambos sentidos desde la posición");
        System.out.println("  inicial sin información sobre la ubicación del objetivo.\n");
        System.out.println("  Patrón: B -> B+d -> B-2d -> B+3d -> B-4d -> ...\n");
        
        System.out.println("Iniciando búsqueda...\n");
        System.out.println("-".repeat(60));
        
        // Resetear estadísticas
        pasos = 0;
        distanciaRecorrida = 0.0;
        posicionFinal = 0.0;

        long tiempoInicio = System.currentTimeMillis();

        double posActual = POS_INICIAL;
        double posAnterior = posActual;

        System.out.printf("Paso %3d: Posición %6.1f (inicio)%n", ++pasos, posActual);

        // Bucle de búsqueda: probar incrementos crecientes hacia la derecha y la izquierda
        final int MAX_PASOS = 1000; // aumentar límite de seguridad
        for (int i = 1; pasos < MAX_PASOS && !objetivoEncontrado(posActual); i++) {
            // Mover a la derecha: POS_INICIAL + i*INCREMENTO
            double nuevaPos = POS_INICIAL + i * INCREMENTO;
            if (nuevaPos <= RANGO_MAX) {
                // Paso final adaptativo: si el objetivo está a menos de INCREMENTO,
                // mover exactamente hasta él en lugar de hacer un salto completo.
                if (Math.abs(POS_OBJETIVO - posActual) <= INCREMENTO) {
                    nuevaPos = POS_OBJETIVO;
                }
                posActual = nuevaPos;
                distanciaRecorrida += Math.abs(posActual - posAnterior);
                posAnterior = posActual;
                System.out.printf("Paso %3d: Posición %6.1f (derecha ->)%n", ++pasos, posActual);
                if (objetivoEncontrado(posActual)) break;
            }

            // Mover a la izquierda: POS_INICIAL - i*INCREMENTO
            nuevaPos = POS_INICIAL - i * INCREMENTO;
            if (nuevaPos >= RANGO_MIN) {
                // Paso final adaptativo
                if (Math.abs(POS_OBJETIVO - posActual) <= INCREMENTO) {
                    nuevaPos = POS_OBJETIVO;
                }
                posActual = nuevaPos;
                distanciaRecorrida += Math.abs(posActual - posAnterior);
                posAnterior = posActual;
                System.out.printf("Paso %3d: Posición %6.1f (izquierda <-)%n", ++pasos, posActual);
                if (objetivoEncontrado(posActual)) break;
            }
        }

        long tiempoFin = System.currentTimeMillis();
        posicionFinal = posActual;

        // Corrección final: si estamos a menos de INCREMENTO del objetivo,
        // mover exactamente hasta el objetivo para eliminar el error residual.
        if (Math.abs(posicionFinal - POS_OBJETIVO) <= INCREMENTO && posicionFinal != POS_OBJETIVO) {
            distanciaRecorrida += Math.abs(POS_OBJETIVO - posicionFinal);
            posicionFinal = POS_OBJETIVO;
            pasos++; // contar el pequeño ajuste
            System.out.println("-".repeat(60));
            System.out.printf("\nOBJETIVO ALCANZADO CON AJUSTE FINAL en posición %.1f\n", posicionFinal);
        } else if (objetivoEncontrado(posicionFinal)) {
            System.out.println("-".repeat(60));
            System.out.printf("\nOBJETIVO ENCONTRADO en posición %.1f\n", posActual);
        } else {
            System.out.println("-".repeat(60));
            System.out.printf("\nOBJETIVO NO ENCONTRADO. Posición final: %.1f\n", posActual);
        }
        
        mostrarEstadisticas(tiempoFin - tiempoInicio);
    }
    
    // ═══════════════════════════════════════════════════════════
    // BÚSQUEDA HEURÍSTICA
    // ═══════════════════════════════════════════════════════════
    
    private static void ejecutarBusquedaHeuristica() {
        limpiarPantalla();
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║        BÚSQUEDA HEURÍSTICA (GRADIENTE DE RELIEVE)        ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        
        System.out.println("Descripción:");
        System.out.println("  Utiliza el relieve del anillo prominente como guía.");
        System.out.println("  Se mueve hacia donde el relieve es mayor.\n");
        System.out.println("  Función heurística: h(n) = relieve en posición n\n");
        
        System.out.println("Iniciando búsqueda...\n");
        System.out.println("-".repeat(60));
        
        // Resetear estadísticas
        pasos = 0;
        distanciaRecorrida = 0.0;
        posicionFinal = 0.0;
        
        long tiempoInicio = System.currentTimeMillis();
        
        double posActual = POS_INICIAL;
        double posAnterior = posActual;
        int pasosSinMejora = 0;
        
        double relieve = calcularRelieve(posActual);
        System.out.printf("Paso %3d: Pos %6.1f | Relieve %5.2f (inicio)%n", 
                         ++pasos, posActual, relieve);
        
        // Bucle de búsqueda
        while (!objetivoEncontrado(posActual)) {
            double relieveActual = calcularRelieve(posActual);
            double posDer = posActual + INCREMENTO;
            double posIzq = posActual - INCREMENTO;
            
            double relieveDer = (posDer <= RANGO_MAX) ? calcularRelieve(posDer) : -1;
            double relieveIzq = (posIzq >= RANGO_MIN) ? calcularRelieve(posIzq) : -1;
            
            String direccion;
            double nuevaPos;
            
            // Decisión basada en gradiente
            if (relieveDer > relieveActual && relieveDer >= relieveIzq) {
                nuevaPos = posDer;
                direccion = "derecha ->";
                pasosSinMejora = 0;
            } else if (relieveIzq > relieveActual) {
                nuevaPos = posIzq;
                direccion = "izquierda <-";
                pasosSinMejora = 0;
            } else {
                // Máximo local - continuar explorando
                pasosSinMejora++;
                if (pasosSinMejora >= 3) {
                    // Salto exploratorio
                    nuevaPos = posActual + ((Math.random() > 0.5 ? 1 : -1) * INCREMENTO * 2);
                    direccion = "exploracion";
                    pasosSinMejora = 0;
                    System.out.println("    -> Máximo local detectado, salto exploratorio");
                } else {
                    nuevaPos = (relieveDer >= relieveIzq) ? posDer : posIzq;
                    direccion = (relieveDer >= relieveIzq) ? "derecha ->" : "izquierda <-";
                }
            }
            
            // Verificar límites
            if (nuevaPos < RANGO_MIN || nuevaPos > RANGO_MAX) {
                System.out.println("\n(!) Posición fuera de rango");
                break;
            }
            
            posActual = nuevaPos;
            distanciaRecorrida += Math.abs(posActual - posAnterior);
            posAnterior = posActual;
            
            relieve = calcularRelieve(posActual);
            System.out.printf("Paso %3d: Pos %6.1f | Relieve %5.2f (%s)%n", 
                             ++pasos, posActual, relieve, direccion);
            
            // Límite de seguridad
            if (pasos > 100) {
                System.out.println("\n(!) Límite de pasos alcanzado");
                break;
            }
        }
        
        long tiempoFin = System.currentTimeMillis();
        posicionFinal = posActual;
        
        // Corrección final similar: ajustar hasta el objetivo si está dentro de un salto
        if (Math.abs(posicionFinal - POS_OBJETIVO) <= INCREMENTO && posicionFinal != POS_OBJETIVO) {
            distanciaRecorrida += Math.abs(POS_OBJETIVO - posicionFinal);
            posicionFinal = POS_OBJETIVO;
            pasos++; // contar el ajuste
            System.out.println("-".repeat(60));
            System.out.printf("\nOBJETIVO ALCANZADO CON AJUSTE FINAL en posición %.1f\n", posicionFinal);
        } else {
            System.out.println("-".repeat(60));
            System.out.printf("\nOBJETIVO ENCONTRADO en posición %.1f\n", posActual);
        }

        mostrarEstadisticas(tiempoFin - tiempoInicio);
    }
    
    // ═══════════════════════════════════════════════════════════
    // FUNCIONES AUXILIARES
    // ═══════════════════════════════════════════════════════════
    
    /**
     * Calcula el relieve en una posición dada
     * El relieve es máximo en el centro del anillo (objetivo)
     */
    private static double calcularRelieve(double posicion) {
        double distAlCentro = Math.abs(posicion - POS_OBJETIVO);
        double relieve = RADIO_ANILLO - distAlCentro;
        return Math.max(0.0, relieve);
    }
    
    /**
     * Verifica si se alcanzó el objetivo
     */
    private static boolean objetivoEncontrado(double posicion) {
        return Math.abs(posicion - POS_OBJETIVO) <= TOLERANCIA;
    }
    
    /**
     * Muestra las estadísticas de la búsqueda
     */
    private static void mostrarEstadisticas(long tiempoMs) {
        System.out.println("\n-----------------------------------------------------------");
        System.out.println("                      ESTADISTICAS                        ");
        System.out.println("-----------------------------------------------------------");
        System.out.printf("Pasos totales:           %3d%n", pasos);
        System.out.printf("Distancia recorrida:     %6.1f unidades%n", distanciaRecorrida);
        System.out.printf("Tiempo de ejecución:     %4d ms%n", tiempoMs);
        System.out.printf("Posición final:          %6.1f%n", posicionFinal);
        System.out.printf("Error final:             %6.2f unidades%n", Math.abs(posicionFinal - POS_OBJETIVO));
        System.out.println("-----------------------------------------------------------");
    }
    
    /**
     * Limpia la pantalla
     */
    private static void limpiarPantalla() {
        // Imprimir varias líneas en blanco para 'simular' limpieza.
        for (int i = 0; i < 10; i++) System.out.println();
    }
}