/*    */ package com.hypixel.hytale.builtin.hytalegenerator.framework.math;
/*    */ 
/*    */ import java.util.Random;
/*    */ import java.util.stream.IntStream;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CoPrimeGenerator
/*    */ {
/*    */   public static long[] generateCoPrimes(long seed, int bucketSize, int numberOfBuckets, long floor) {
/* 29 */     if (bucketSize < 1 || numberOfBuckets < 1) {
/* 30 */       throw new IllegalArgumentException("invalid sizes");
/*    */     }
/* 32 */     Random rand = new Random(seed);
/* 33 */     int[] primes = new int[bucketSize * numberOfBuckets];
/* 34 */     fillWithPrimes(primes);
/* 35 */     int[][] buckets = new int[numberOfBuckets][bucketSize];
/*    */ 
/*    */     
/* 38 */     long[] output = new long[numberOfBuckets];
/* 39 */     IntStream.range(0, output.length).forEach(i -> output[i] = 1L);
/*    */ 
/*    */ 
/*    */     
/* 43 */     int indexOfBucket = 0, indexOfPrime = 0, indexInsideBucket = 0;
/* 44 */     for (; indexOfPrime < primes.length; 
/* 45 */       indexOfPrime++) {
/* 46 */       buckets[indexOfBucket][indexInsideBucket] = primes[indexOfPrime];
/*    */       
/* 48 */       if (indexOfBucket == numberOfBuckets - 1) {
/* 49 */         indexInsideBucket++;
/*    */       }
/* 51 */       indexOfBucket = (indexOfBucket + 1) % numberOfBuckets;
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 57 */     for (int i = 0; i < numberOfBuckets; i++) {
/* 58 */       while (output[i] < floor)
/* 59 */         output[i] = output[i] * buckets[i][rand.nextInt(bucketSize)]; 
/* 60 */     }  return output;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void fillWithPrimes(@Nonnull int[] bucket) {
/* 70 */     for (int number = 2, index = 0; index < bucket.length; number++) {
/* 71 */       if (isPrime(number)) {
/* 72 */         bucket[index] = number;
/* 73 */         index++;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static boolean isPrime(int number) {
/* 79 */     for (int i = 2; i < number; i++) {
/* 80 */       if (number % i == 0)
/* 81 */         return false; 
/* 82 */     }  return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\math\CoPrimeGenerator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */