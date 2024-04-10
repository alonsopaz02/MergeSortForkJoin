package com.threads;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class MergeSortForkJoin {
    public void sort(int[] a) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new MergeSortTask(a, a.length));
    }

    private class MergeSortTask extends RecursiveAction {
        private static final long serialVersionUID = -749935388568367268L;
        private final int[] a;
        private final int n;

        public MergeSortTask(int[] a, int n) {
            this.a = a;
            this.n = a.length;
        }

        @Override
        protected void compute() {
            if (n < 2) {
                return;
            }

            int mid = n / 2;
            int[] l = new int[mid];
            int[] r = new int[n - mid];

            for (int i = 0; i < mid; i++) {
                l[i] = a[i];
            }
            for (int i = mid; i < n; i++) {
                r[i - mid] = a[i];
            }

            MergeSortTask left = new MergeSortTask(l, mid);
            MergeSortTask right = new MergeSortTask(r, n - mid);
            invokeAll(left, right);

            merge(a, l, r, mid, n - mid);
        }

        public static void merge(
                int[] a, int[] l, int[] r, int left, int right) {

            int i = 0, j = 0, k = 0;
            while (i < left && j < right) {
                if (l[i] <= r[j]) {
                    a[k++] = l[i++];
                } else {
                    a[k++] = r[j++];
                }
            }
            while (i < left) {
                a[k++] = l[i++];
            }
            while (j < right) {
                a[k++] = r[j++];
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    // Método para obtener el número de hilos activos utilizados
    public static int getActiveThreads() {
        return ForkJoinPool.commonPool().getParallelism();
    }
}