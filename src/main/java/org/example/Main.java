package org.example;

import javax.annotation.processing.Filer;
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        mostrarMenu();

        boolean salir = false;

        while(!salir) {
            try {
                salir = procesarOpcion(sc);
            } catch (InputMismatchException e) {
                System.out.println("Error, debes de ingresar un numero valido!");
                sc.nextLine();
            }
        }
    }

    static File archivo = new File("tareas.txt");

    private static List<Tarea> tareas = new ArrayList<>();

    /*
     - Mostrar Menu: Imprime el listado de opciones para interactuar con el sistema
     - Procesar Opcion: Permitir al usuario ingresar una opcion
     - Agregar tarea: Pedirle al usuario nombre de la tarea y crearla
     - Listar tareas: Imprimir listado de todas las tareas creadas
     - Eliminar tarea: Eliminar tarea via posicion de la tarea en el array
     - Completar tarea: Permite marcar como completada la tarea
     - Filtrar tareas: Permite filtrar tareas por estado
     - Procesar numero: Manejar errores en los numeros
     - Verificar arraylist vacio: Retorna true si el array es vacio
     */

    public static void mostrarMenu() {
        System.out.println("1 - Agregar tareas");
        System.out.println("2 - Listar tareas");
        System.out.println("3 - Marcar como completado tarea");
        System.out.println("4 - Eliminar tarea");
        System.out.println("5 - Buscar tareas por su estado (completadas o pendientes)");
        System.out.println("6 - Salir del menu");
    }

    public static boolean procesarOpcion(Scanner sc) {
        int opcion = sc.nextInt();
        sc.nextLine();
        switch(opcion) {
            case 1:
                agregarTarea(sc);
                return false;
            case 2:
                listarTareas();
                return false;
            case 3:
                completarTarea(sc);
                return false;
            case 4:
                eliminarTarea(sc);
                return false;
            case 5:
                filtrarTareas(sc);
                return false;
            case 6:
                System.out.println("Gracias por usar el sistema de gestión de tareas!");
                return true;
            default:
                System.out.println("Opcion fuera de rango");
                return false;
        }
    }

    public static void agregarTarea(Scanner sc) {
        String tarea = procesarString(sc, "Ingrese el nombre de la tarea a agregar:");

        if (tarea != null & !tarea.isEmpty()) {
            Tarea nuevaTarea = new Tarea(tarea);
            tareas.add(nuevaTarea);
            guardarTarea();

            System.out.println("Tarea agregada correctamente");
        } else {
            System.out.println("Error al agregar la tarea!");
        }
    }

    public static void guardarTarea() {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo))) {
            for (Tarea tarea : tareas) {
                escritor.write("Nombre de la tarea: " + tarea.getNombre() + ", " + "Estado: " + tarea.getEstado());
                escritor.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void listarTareas() {
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public static void cargarTarea() {
        if (archivo.exists()) {
            try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    System.out.println(linea);
                }
            } catch (IOException e) {
                System.out.println("Error al cargar tareas: " + e.getMessage());
            }
        }
    }

    public static void eliminarTarea(Scanner sc) {
        if (verificarArrayListVacio()) {
            System.out.println("No hay ninguna tarea ingresada en este momento!");
        } else {
            System.out.println("Ingrese el indice de tarea a eliminar: (Recuerda que empieza desde 0)");
            int index = procesarNumero(sc);

            System.out.println("Se eliminó la tarea: " + tareas.get(index).getNombre());
            tareas.remove(index);
        }
    }

    public static void completarTarea(Scanner sc) {
        if (verificarArrayListVacio()) {
            System.out.println("No hay ninguna tarea ingresada en este momento!");
        } else {
            System.out.println("Ingrese el num de tarea a cambiar su estado:");
            int index = procesarNumero(sc);

            if (index >= 0 && index < tareas.size()) {
                for (int i = 0; i < tareas.size(); i++) {
                    if (i == index) {
                        System.out.println("Tarea completada: " + tareas.get(i).getNombre());
                        tareas.get(i).marcarCompletada();
                        break;
                    }
                }
            } else {
                System.out.println("Numero fuera de rango. Intentalo de nuevo.");
            }
        }
    }

    public static void filtrarTareas(Scanner sc) {
        if (verificarArrayListVacio()) {
            System.out.println("No hay ninguna tarea ingresada en este momento!");
            return;
        }

        System.out.println("Filtrar tareas por estado");
        System.out.println("1 - Completado");
        System.out.println("2 - Pendiente");

        System.out.println("Por favor, escriba la opcion:");
        int opcion = sc.nextInt();

        for (int i = 0; i < tareas.size(); i++) {
            if (opcion == 1) {
                if (tareas.get(i).getEstado().equals("Completado")) {
                    System.out.println(tareas.get(i).getNombre());
                    System.out.println(tareas.get(i).getEstado());
                }
            } else if (opcion == 2) {
                if (tareas.get(i).getEstado().equals("Pendiente")) {
                    System.out.println(tareas.get(i).getNombre());
                    System.out.println(tareas.get(i).getEstado());
                }
            } else {
                System.out.println("Opcion invalida");
            }
        }
    }

    public static int procesarNumero(Scanner sc) {
        int num = sc.nextInt();
        sc.nextLine();

        while (num < 0 || num >= tareas.size()) {
            System.out.println("El numero ingresado esta fuera de rango, vuelve a reingresar:");
            num = sc.nextInt();
            sc.nextLine();
        }
        return num;
    }

    public static String procesarString(Scanner sc, String mensaje) {
        boolean salir = false;
        String text = "";

        while(!salir) {
            System.out.println(mensaje);
            text = sc.nextLine();

            if (text.isEmpty()) {
                System.out.println("El texto no puede estar vacio, vuelve a re-ingresar porfavor:");
            } else {
                salir = true;
            }
        }
        return text;
    }

    public static boolean verificarArrayListVacio() {
        return tareas.isEmpty();
    }

}