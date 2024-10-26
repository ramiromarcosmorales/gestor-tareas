package org.example;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LecturaYEscritura {
    public static void main(String[] args) {
        ArrayList<String> estudiantes = new ArrayList<>();

        estudiantes.add("Ramiro");
        estudiantes.add("Felipe");
        estudiantes.add("Arturo");
        estudiantes.add("Juan");
        estudiantes.add("Bautista");

        File archivo = new File("datos.txt");


        // escribir
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo))) {
            for (String estudiante : estudiantes) {
                escritor.write(estudiante);
                escritor.newLine();
            }
            System.out.println("Datos escritos correctamente");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage()) ;
        }

        //leer
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            System.out.println("Nombre estudiantes:");
            while ((linea = lector.readLine()) != null) {
                if (linea.length() > 5) {
                    System.out.println(linea);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
