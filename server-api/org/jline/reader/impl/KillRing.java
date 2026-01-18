/*     */ package org.jline.reader.impl;
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
/*     */ public final class KillRing
/*     */ {
/*     */   private static final int DEFAULT_SIZE = 60;
/*     */   private final String[] slots;
/*  29 */   private int head = 0;
/*     */ 
/*     */   
/*     */   private boolean lastKill = false;
/*     */ 
/*     */   
/*     */   private boolean lastYank = false;
/*     */ 
/*     */   
/*     */   public KillRing(int size) {
/*  39 */     this.slots = new String[size];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KillRing() {
/*  46 */     this(60);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetLastYank() {
/*  53 */     this.lastYank = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetLastKill() {
/*  60 */     this.lastKill = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean lastYank() {
/*  68 */     return this.lastYank;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(String str) {
/*  77 */     this.lastYank = false;
/*     */     
/*  79 */     if (this.lastKill && 
/*  80 */       this.slots[this.head] != null) {
/*  81 */       this.slots[this.head] = this.slots[this.head] + str;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  86 */     this.lastKill = true;
/*  87 */     next();
/*  88 */     this.slots[this.head] = str;
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
/*     */   public void addBackwards(String str) {
/* 100 */     this.lastYank = false;
/*     */     
/* 102 */     if (this.lastKill && 
/* 103 */       this.slots[this.head] != null) {
/* 104 */       this.slots[this.head] = str + this.slots[this.head];
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 109 */     this.lastKill = true;
/* 110 */     next();
/* 111 */     this.slots[this.head] = str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String yank() {
/* 120 */     this.lastKill = false;
/* 121 */     this.lastYank = true;
/* 122 */     return this.slots[this.head];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String yankPop() {
/* 132 */     this.lastKill = false;
/* 133 */     if (this.lastYank) {
/* 134 */       prev();
/* 135 */       return this.slots[this.head];
/*     */     } 
/* 137 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void next() {
/* 145 */     if (this.head == 0 && this.slots[0] == null) {
/*     */       return;
/*     */     }
/* 148 */     this.head++;
/* 149 */     if (this.head == this.slots.length) {
/* 150 */       this.head = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void prev() {
/* 160 */     this.head--;
/* 161 */     if (this.head == -1) {
/* 162 */       int x = this.slots.length - 1;
/* 163 */       for (; x >= 0 && 
/* 164 */         this.slots[x] == null; x--);
/*     */ 
/*     */ 
/*     */       
/* 168 */       this.head = x;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\impl\KillRing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */