/*     */ package io.netty.channel.kqueue;
/*     */ 
/*     */ import io.netty.channel.IoEvent;
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
/*     */ public final class KQueueIoEvent
/*     */   implements IoEvent
/*     */ {
/*     */   private int ident;
/*     */   private short filter;
/*     */   private short flags;
/*     */   private int fflags;
/*     */   private long data;
/*     */   private long udata;
/*     */   
/*     */   @Deprecated
/*     */   public static KQueueIoEvent newEvent(int ident, short filter, short flags, int fflags) {
/*  43 */     return new KQueueIoEvent(ident, filter, flags, fflags, 0L, 0L);
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
/*     */   public static KQueueIoEvent newEvent(int ident, short filter, short flags, int fflags, long data, long udata) {
/*  58 */     return new KQueueIoEvent(ident, filter, flags, fflags, data, udata);
/*     */   }
/*     */   
/*     */   private KQueueIoEvent(int ident, short filter, short flags, int fflags, long data, long udata) {
/*  62 */     this.ident = ident;
/*  63 */     this.filter = filter;
/*  64 */     this.flags = flags;
/*  65 */     this.fflags = fflags;
/*  66 */     this.data = data;
/*  67 */     this.udata = udata;
/*     */   }
/*     */   
/*     */   KQueueIoEvent() {
/*  71 */     this(0, (short)0, (short)0, 0, 0L, 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   void update(int ident, short filter, short flags, int fflags, long data, long udata) {
/*  76 */     this.ident = ident;
/*  77 */     this.filter = filter;
/*  78 */     this.flags = flags;
/*  79 */     this.fflags = fflags;
/*  80 */     this.data = data;
/*  81 */     this.udata = udata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int ident() {
/*  90 */     return this.ident;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short filter() {
/*  99 */     return this.filter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short flags() {
/* 108 */     return this.flags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int fflags() {
/* 117 */     return this.fflags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long data() {
/* 126 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long udata() {
/* 135 */     return this.udata;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 140 */     return "KQueueIoEvent{ident=" + this.ident + ", filter=" + this.filter + ", flags=" + this.flags + ", fflags=" + this.fflags + ", data=" + this.data + ", udata=" + this.udata + '}';
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\KQueueIoEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */