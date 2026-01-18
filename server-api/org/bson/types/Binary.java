/*     */ package org.bson.types;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import org.bson.BsonBinarySubType;
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
/*     */ public class Binary
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7902997490338209467L;
/*     */   private final byte type;
/*     */   private final byte[] data;
/*     */   
/*     */   public Binary(byte[] data) {
/*  39 */     this(BsonBinarySubType.BINARY, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Binary(BsonBinarySubType type, byte[] data) {
/*  49 */     this(type.getValue(), data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Binary(byte type, byte[] data) {
/*  59 */     this.type = type;
/*  60 */     this.data = (byte[])data.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getType() {
/*  69 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getData() {
/*  78 */     return (byte[])this.data.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int length() {
/*  87 */     return this.data.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  92 */     if (this == o) {
/*  93 */       return true;
/*     */     }
/*  95 */     if (o == null || getClass() != o.getClass()) {
/*  96 */       return false;
/*     */     }
/*     */     
/*  99 */     Binary binary = (Binary)o;
/*     */     
/* 101 */     if (this.type != binary.type) {
/* 102 */       return false;
/*     */     }
/* 104 */     if (!Arrays.equals(this.data, binary.data)) {
/* 105 */       return false;
/*     */     }
/*     */     
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     int result = this.type;
/* 114 */     result = 31 * result + Arrays.hashCode(this.data);
/* 115 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\types\Binary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */