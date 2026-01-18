/*    */ package io.netty.util;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public final class DomainMappingBuilder<V>
/*    */ {
/*    */   private final DomainNameMappingBuilder<V> builder;
/*    */   
/*    */   public DomainMappingBuilder(V defaultValue) {
/* 37 */     this.builder = new DomainNameMappingBuilder<>(defaultValue);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DomainMappingBuilder(int initialCapacity, V defaultValue) {
/* 48 */     this.builder = new DomainNameMappingBuilder<>(initialCapacity, defaultValue);
/*    */   }
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
/*    */   public DomainMappingBuilder<V> add(String hostname, V output) {
/* 64 */     this.builder.add(hostname, output);
/* 65 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DomainNameMapping<V> build() {
/* 75 */     return this.builder.build();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\DomainMappingBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */