package com.example.demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierExample {

    // Define the number of threads that will participate
    private static final int NUMBER_OF_WORKERS = 3;

    public static void main(String[] args) {
        // Create a CyclicBarrier for the defined number of parties (threads)
        // The optional Runnable will execute when the barrier is tripped (all parties arrive)
        CyclicBarrier barrier = new CyclicBarrier(NUMBER_OF_WORKERS, new Runnable() {
            @Override
            public void run() {
                // This code runs after all threads have reached the barrier
                System.out.println("\n--- Barrier Tripped! All workers completed Phase 1. Proceeding to Phase 2 ---");
            }
        });

        // Use an ExecutorService to manage the worker threads
        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_WORKERS);

        System.out.println("Starting workers...");

        // Create and submit worker tasks to the executor
        for (int i = 0; i < NUMBER_OF_WORKERS; i++) {
            executor.submit(new Worker(barrier, i + 1));
        }

        // Shut down the executor after tasks are submitted
        executor.shutdown();

        System.out.println("Main thread finished submitting workers.");
    }

    // A simple worker class that performs work in phases
    static class Worker implements Runnable {
        private final CyclicBarrier barrier;
        private final int workerId;

        public Worker(CyclicBarrier barrier, int workerId) {
            this.barrier = barrier;
            this.workerId = workerId;
        }

        @Override
        public void run() {
            try {
                // --- Phase 1 ---
                System.out.println("Worker " + workerId + " is performing Phase 1 work.");
                Thread.sleep(random.nextInt(2000)); // Simulate work time
                System.out.println("Worker " + workerId + " finished Phase 1.");

                // --- Wait at the barrier ---
                System.out.println("Worker " + workerId + " waiting at the barrier.");
                barrier.await(); // Wait for all other threads to reach the barrier

                // --- Phase 2 ---
                // This code will only execute after the barrier is tripped
                System.out.println("Worker " + workerId + " is performing Phase 2 work.");
                Thread.sleep(random.nextInt(2000)); // Simulate work time
                System.out.println("Worker " + workerId + " finished Phase 2.");

            } catch (InterruptedException | BrokenBarrierException e) {
                // Handle potential interruptions or broken barrier state
                System.err.println("Worker " + workerId + " interrupted or barrier broken: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        private static final java.util.Random random = new java.util.Random();
    }
}