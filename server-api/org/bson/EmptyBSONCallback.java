/*     */ package org.bson;
/*     */ 
/*     */ import org.bson.types.Decimal128;
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
/*     */ public class EmptyBSONCallback
/*     */   implements BSONCallback
/*     */ {
/*     */   public void objectStart() {
/*  29 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void objectStart(String name) {
/*  34 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public Object objectDone() {
/*  39 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/*  44 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public Object get() {
/*  49 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public BSONCallback createBSONCallback() {
/*  54 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void arrayStart() {
/*  59 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void arrayStart(String name) {
/*  64 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public Object arrayDone() {
/*  69 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotNull(String name) {
/*  74 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotUndefined(String name) {
/*  79 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotMinKey(String name) {
/*  84 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotMaxKey(String name) {
/*  89 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotBoolean(String name, boolean value) {
/*  94 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotDouble(String name, double value) {
/*  99 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotInt(String name, int value) {
/* 104 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotLong(String name, long value) {
/* 109 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotDecimal128(String name, Decimal128 value) {
/* 114 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotDate(String name, long millis) {
/* 119 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotString(String name, String value) {
/* 124 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotSymbol(String name, String value) {
/* 129 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotRegex(String name, String pattern, String flags) {
/* 134 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotTimestamp(String name, int time, int increment) {
/* 139 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotObjectId(String name, ObjectId id) {
/* 144 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotDBRef(String name, String namespace, ObjectId id) {
/* 149 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotBinary(String name, byte type, byte[] data) {
/* 154 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotUUID(String name, long part1, long part2) {
/* 159 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotCode(String name, String code) {
/* 164 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void gotCodeWScope(String name, String code, Object scope) {
/* 169 */     throw new UnsupportedOperationException("Operation is not supported");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\EmptyBSONCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */