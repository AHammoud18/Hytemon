/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
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
/*     */ public class LazyField
/*     */   extends LazyFieldLite
/*     */ {
/*     */   private final MessageLite defaultInstance;
/*     */   
/*     */   public LazyField(MessageLite defaultInstance, ExtensionRegistryLite extensionRegistry, ByteString bytes) {
/*  33 */     super(extensionRegistry, bytes);
/*     */     
/*  35 */     this.defaultInstance = defaultInstance;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsDefaultInstance() {
/*  40 */     return (super.containsDefaultInstance() || this.value == this.defaultInstance);
/*     */   }
/*     */   
/*     */   public MessageLite getValue() {
/*  44 */     return getValue(this.defaultInstance);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  49 */     return getValue().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  55 */     return getValue().equals(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  60 */     return getValue().toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class LazyEntry<K>
/*     */     implements Map.Entry<K, Object>
/*     */   {
/*     */     private Map.Entry<K, LazyField> entry;
/*     */ 
/*     */ 
/*     */     
/*     */     private LazyEntry(Map.Entry<K, LazyField> entry) {
/*  73 */       this.entry = entry;
/*     */     }
/*     */ 
/*     */     
/*     */     public K getKey() {
/*  78 */       return this.entry.getKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getValue() {
/*  83 */       LazyField field = this.entry.getValue();
/*  84 */       if (field == null) {
/*  85 */         return null;
/*     */       }
/*  87 */       return field.getValue();
/*     */     }
/*     */     
/*     */     public LazyField getField() {
/*  91 */       return this.entry.getValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public Object setValue(Object value) {
/*  96 */       if (!(value instanceof MessageLite)) {
/*  97 */         throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
/*     */       }
/*     */ 
/*     */       
/* 101 */       return ((LazyField)this.entry.getValue()).setValue((MessageLite)value);
/*     */     }
/*     */   }
/*     */   
/*     */   static class LazyIterator<K> implements Iterator<Map.Entry<K, Object>> {
/*     */     private Iterator<Map.Entry<K, Object>> iterator;
/*     */     
/*     */     public LazyIterator(Iterator<Map.Entry<K, Object>> iterator) {
/* 109 */       this.iterator = iterator;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 114 */       return this.iterator.hasNext();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Map.Entry<K, Object> next() {
/* 120 */       Map.Entry<K, ?> entry = this.iterator.next();
/* 121 */       if (entry.getValue() instanceof LazyField) {
/* 122 */         return new LazyField.LazyEntry<>(entry);
/*     */       }
/* 124 */       return (Map.Entry)entry;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 129 */       this.iterator.remove();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\LazyField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */