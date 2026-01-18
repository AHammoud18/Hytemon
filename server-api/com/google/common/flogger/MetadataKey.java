/*     */ package com.google.common.flogger;
/*     */ 
/*     */ import com.google.common.flogger.util.Checks;
/*     */ import java.util.Iterator;
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
/*     */ public class MetadataKey<T>
/*     */ {
/*     */   private final String label;
/*     */   private final Class<? extends T> clazz;
/*     */   private final boolean canRepeat;
/*     */   private final long bloomFilterMask;
/*     */   
/*     */   public static <T> MetadataKey<T> single(String label, Class<? extends T> clazz) {
/* 103 */     return new MetadataKey<T>(label, clazz, false);
/*     */   }
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
/*     */   public static <T> MetadataKey<T> repeated(String label, Class<T> clazz) {
/* 116 */     return new MetadataKey<T>(label, clazz, true);
/*     */   }
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
/*     */   protected MetadataKey(String label, Class<? extends T> clazz, boolean canRepeat) {
/* 130 */     this.label = Checks.checkMetadataIdentifier(label);
/* 131 */     this.clazz = (Class<? extends T>)Checks.checkNotNull(clazz, "class");
/* 132 */     this.canRepeat = canRepeat;
/* 133 */     this.bloomFilterMask = createBloomFilterMaskFromSystemHashcode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getLabel() {
/* 141 */     return this.label;
/*     */   }
/*     */ 
/*     */   
/*     */   public final T cast(Object value) {
/* 146 */     return this.clazz.cast(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean canRepeat() {
/* 151 */     return this.canRepeat;
/*     */   }
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
/*     */   public void emit(T value, KeyValueHandler out) {
/* 187 */     out.handle(getLabel(), value);
/*     */   }
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
/*     */   public void emitRepeated(Iterator<T> values, KeyValueHandler out) {
/* 201 */     Checks.checkState(this.canRepeat, "non repeating key");
/* 202 */     while (values.hasNext()) {
/* 203 */       emit(values.next(), out);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long getBloomFilterMask() {
/* 215 */     return this.bloomFilterMask;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/* 221 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean equals(Object obj) {
/* 226 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final String toString() {
/* 232 */     return getClass().getName() + "/" + this.label + "[" + this.clazz.getName() + "]";
/*     */   }
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
/*     */   private long createBloomFilterMaskFromSystemHashcode() {
/* 248 */     int hash = System.identityHashCode(this);
/* 249 */     long bloom = 0L;
/*     */ 
/*     */     
/* 252 */     for (int n = 0; n < 5; n++) {
/* 253 */       bloom |= 1L << (hash & 0x3F);
/* 254 */       hash >>>= 6;
/*     */     } 
/* 256 */     return bloom;
/*     */   }
/*     */   
/*     */   public static interface KeyValueHandler {
/*     */     void handle(String param1String, Object param1Object);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\MetadataKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */