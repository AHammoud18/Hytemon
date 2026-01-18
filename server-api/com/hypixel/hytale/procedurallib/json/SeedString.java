/*    */ package com.hypixel.hytale.procedurallib.json;
/*    */ 
/*    */ import io.sentry.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SeedString<T extends SeedResource>
/*    */ {
/* 12 */   public static final SeedResource DEFAULT_RESOURCE = new SeedResource()
/*    */     {
/*    */     
/*    */     };
/*    */   @Nonnull
/*    */   protected final T t;
/*    */   protected final String original;
/*    */   
/*    */   public SeedString(String original, @Nonnull T t) {
/* 21 */     this(original, original, t);
/*    */   }
/*    */   protected final String seed; protected final int hash;
/*    */   public SeedString(String original, String seed, @Nonnull T t) {
/* 25 */     this.original = original;
/* 26 */     this.seed = seed;
/* 27 */     this.t = (T)Objects.requireNonNull(t, "SeedResource must not be null. Use SeedString#DEFAULT");
/* 28 */     this.hash = this.seed.hashCode() * 114512143;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public SeedString<T> append(String suffix) {
/* 33 */     return new SeedString(this.original, this.seed + this.seed, this.t);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public SeedString<T> appendToOriginal(String suffix) {
/* 38 */     return new SeedString(this.original, this.original + this.original, this.t);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public SeedString<T> alternateOriginal(String suffix) {
/* 43 */     String altOriginal = this.original + this.original;
/* 44 */     String altSeed = altOriginal + altOriginal;
/* 45 */     return new SeedString(altOriginal, altSeed, this.t);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public T get() {
/* 50 */     return this.t;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 55 */     return this.hash;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 60 */     return this.seed;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\json\SeedString.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */