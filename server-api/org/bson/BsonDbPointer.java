/*     */ package org.bson;
/*     */ 
/*     */ import org.bson.types.ObjectId;
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
/*     */ public class BsonDbPointer
/*     */   extends BsonValue
/*     */ {
/*     */   private final String namespace;
/*     */   private final ObjectId id;
/*     */   
/*     */   public BsonDbPointer(String namespace, ObjectId id) {
/*  38 */     if (namespace == null) {
/*  39 */       throw new IllegalArgumentException("namespace can not be null");
/*     */     }
/*  41 */     if (id == null) {
/*  42 */       throw new IllegalArgumentException("id can not be null");
/*     */     }
/*  44 */     this.namespace = namespace;
/*  45 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   public BsonType getBsonType() {
/*  50 */     return BsonType.DB_POINTER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespace() {
/*  59 */     return this.namespace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectId getId() {
/*  68 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  73 */     if (this == o) {
/*  74 */       return true;
/*     */     }
/*  76 */     if (o == null || getClass() != o.getClass()) {
/*  77 */       return false;
/*     */     }
/*     */     
/*  80 */     BsonDbPointer dbPointer = (BsonDbPointer)o;
/*     */     
/*  82 */     if (!this.id.equals(dbPointer.id)) {
/*  83 */       return false;
/*     */     }
/*  85 */     if (!this.namespace.equals(dbPointer.namespace)) {
/*  86 */       return false;
/*     */     }
/*     */     
/*  89 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  94 */     int result = this.namespace.hashCode();
/*  95 */     result = 31 * result + this.id.hashCode();
/*  96 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 101 */     return "BsonDbPointer{namespace='" + this.namespace + '\'' + ", id=" + this.id + '}';
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BsonDbPointer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */