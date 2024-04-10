package com.threads;

import java.util.Random;


public class ForkjoinEjemplo {

    private static final int N = 500;
    private static final int repeticiones = 1;

    private static void inicializa(int[] secuencial, int[] paralelo) {
        Random random = new Random();
        for (int i = 0; i < secuencial.length; i++) {
            secuencial[i] = random.nextInt(100) + 1;
            paralelo[i] = secuencial[i];
        }
    }

    public static void main(String[] args) {
        int[] arregloSec = new int[N];
        int[] arregloPar = new int[N];

        long durSec = 0;
        long durPar = 0;

        int activeThreads=0;

        for (int r = 0; r < repeticiones; r++) {
            inicializa(arregloSec, arregloPar);

            // Tomar el tiempo de inicio
            durSec = 0;

            long iniSec = System.currentTimeMillis();
            MergeSort.sort(arregloSec);
            
            // Tomar el tiempo final y restar el inicio para hallar la duracion
            durSec += (System.currentTimeMillis() - iniSec);

            // hacer lo mismo que el secuencial para el paralelo
            // Tomar el tiempo de inicio
            durPar = 0;

            long iniPar = System.currentTimeMillis();
            MergeSortForkJoin paralelo = new MergeSortForkJoin();
            paralelo.sort(arregloPar);
            
            // Tomar el tiempo final y restar el inicio para hallar la duracion
            durPar += (System.currentTimeMillis() - iniPar);

            // Tomar el tiempo final y restar el inicio para hallar la duracion
        }
        
        activeThreads = MergeSortForkJoin.getActiveThreads();
        durSec = durSec/repeticiones;
        durPar = durPar/repeticiones;

        double acceleration = (double) durSec / durPar;
        double efficiency = acceleration / activeThreads;

        // presentar la tabla

        System.out.println("Tiempo secuencial (ms): " + durSec);
        System.out.println("Tiempo paralelo (ms): " + durPar);
        System.out.println("Hilos activos: "+ activeThreads);
        System.out.println("Aceleracion: "+acceleration);
        System.out.println("Eficiencia(%): "+efficiency*100+"%");

    }
}