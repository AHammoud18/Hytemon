/*     */ package org.bson.types;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
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
/*     */ public final class BSONTimestamp
/*     */   implements Comparable<BSONTimestamp>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3268482672267936464L;
/*     */   private final int inc;
/*     */   private final Date time;
/*     */   
/*     */   public BSONTimestamp() {
/*  39 */     this.inc = 0;
/*  40 */     this.time = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BSONTimestamp(int time, int increment) {
/*  51 */     this.time = new Date(time * 1000L);
/*  52 */     this.inc = increment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTime() {
/*  61 */     if (this.time == null) {
/*  62 */       return 0;
/*     */     }
/*  64 */     return (int)(this.time.getTime() / 1000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInc() {
/*  73 */     return this.inc;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  78 */     return "TS time:" + this.time + " inc:" + this.inc;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(BSONTimestamp ts) {
/*  83 */     if (getTime() != ts.getTime()) {
/*  84 */       return getTime() - ts.getTime();
/*     */     }
/*  86 */     return getInc() - ts.getInc();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  92 */     int prime = 31;
/*  93 */     int result = 1;
/*  94 */     result = prime * result + this.inc;
/*  95 */     result = prime * result + getTime();
/*  96 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 101 */     if (obj == this) {
/* 102 */       return true;
/*     */     }
/* 104 */     if (obj instanceof BSONTimestamp) {
/* 105 */       BSONTimestamp t2 = (BSONTimestamp)obj;
/* 106 */       return (getTime() == t2.getTime() && getInc() == t2.getInc());
/*     */     } 
/* 108 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\types\BSONTimestamp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */