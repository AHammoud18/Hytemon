/*     */ package io.netty.handler.ssl.util;
/*     */ 
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ThreadLocalInsecureRandom
/*     */   extends SecureRandom
/*     */ {
/*     */   private static final long serialVersionUID = -8209473337192526191L;
/*  33 */   private static final SecureRandom INSTANCE = new ThreadLocalInsecureRandom();
/*     */   
/*     */   static SecureRandom current() {
/*  36 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAlgorithm() {
/*  43 */     return "insecure";
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSeed(byte[] seed) {}
/*     */ 
/*     */   
/*     */   public void setSeed(long seed) {}
/*     */ 
/*     */   
/*     */   public void nextBytes(byte[] bytes) {
/*  54 */     random().nextBytes(bytes);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] generateSeed(int numBytes) {
/*  59 */     byte[] seed = new byte[numBytes];
/*  60 */     random().nextBytes(seed);
/*  61 */     return seed;
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextInt() {
/*  66 */     return random().nextInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextInt(int n) {
/*  71 */     return random().nextInt(n);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean nextBoolean() {
/*  76 */     return random().nextBoolean();
/*     */   }
/*     */ 
/*     */   
/*     */   public long nextLong() {
/*  81 */     return random().nextLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public float nextFloat() {
/*  86 */     return random().nextFloat();
/*     */   }
/*     */ 
/*     */   
/*     */   public double nextDouble() {
/*  91 */     return random().nextDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   public double nextGaussian() {
/*  96 */     return random().nextGaussian();
/*     */   }
/*     */   
/*     */   private static Random random() {
/* 100 */     return ThreadLocalRandom.current();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ss\\util\ThreadLocalInsecureRandom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */