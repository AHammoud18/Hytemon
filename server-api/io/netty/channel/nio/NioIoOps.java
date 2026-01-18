/*     */ package io.netty.channel.nio;
/*     */ 
/*     */ import io.netty.channel.IoOps;
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
/*     */ public final class NioIoOps
/*     */   implements IoOps
/*     */ {
/*  30 */   public static final NioIoOps NONE = new NioIoOps(0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  35 */   public static final NioIoOps ACCEPT = new NioIoOps(16);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   public static final NioIoOps CONNECT = new NioIoOps(8);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   public static final NioIoOps WRITE = new NioIoOps(4);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   public static final NioIoOps READ = new NioIoOps(1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public static final NioIoOps READ_AND_ACCEPT = new NioIoOps(17);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public static final NioIoOps READ_AND_WRITE = new NioIoOps(5);
/*     */   
/*     */   private static final NioIoEvent[] EVENTS;
/*     */   final int value;
/*     */   
/*     */   static {
/*  66 */     NioIoOps all = new NioIoOps(NONE.value | ACCEPT.value | CONNECT.value | WRITE.value | READ.value);
/*     */ 
/*     */     
/*  69 */     EVENTS = new NioIoEvent[all.value + 1];
/*  70 */     addToArray(EVENTS, NONE);
/*  71 */     addToArray(EVENTS, ACCEPT);
/*  72 */     addToArray(EVENTS, CONNECT);
/*  73 */     addToArray(EVENTS, WRITE);
/*  74 */     addToArray(EVENTS, READ);
/*  75 */     addToArray(EVENTS, READ_AND_ACCEPT);
/*  76 */     addToArray(EVENTS, READ_AND_WRITE);
/*  77 */     addToArray(EVENTS, all);
/*     */   }
/*     */   
/*     */   private static void addToArray(NioIoEvent[] array, NioIoOps opt) {
/*  81 */     array[opt.value] = new DefaultNioIoEvent(opt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private NioIoOps(int value) {
/*  87 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(NioIoOps ops) {
/*  96 */     return isIncludedIn(ops.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioIoOps with(NioIoOps ops) {
/* 106 */     if (contains(ops)) {
/* 107 */       return this;
/*     */     }
/* 109 */     return valueOf(this.value | ops.value());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioIoOps without(NioIoOps ops) {
/* 119 */     if (!contains(ops)) {
/* 120 */       return this;
/*     */     }
/* 122 */     return valueOf(this.value & (ops.value() ^ 0xFFFFFFFF));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int value() {
/* 131 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 136 */     if (this == o) {
/* 137 */       return true;
/*     */     }
/* 139 */     if (o == null || getClass() != o.getClass()) {
/* 140 */       return false;
/*     */     }
/* 142 */     NioIoOps nioOps = (NioIoOps)o;
/* 143 */     return (this.value == nioOps.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 148 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NioIoOps valueOf(int value) {
/* 158 */     return eventOf(value).ops();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIncludedIn(int ops) {
/* 168 */     return ((ops & this.value) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNotIncludedIn(int ops) {
/* 178 */     return ((ops & this.value) == 0);
/*     */   }
/*     */   
/*     */   static NioIoEvent eventOf(int value) {
/* 182 */     if (value > 0 && value < EVENTS.length) {
/* 183 */       NioIoEvent event = EVENTS[value];
/* 184 */       if (event != null) {
/* 185 */         return event;
/*     */       }
/*     */     } 
/* 188 */     return new DefaultNioIoEvent(new NioIoOps(value));
/*     */   }
/*     */   
/*     */   private static final class DefaultNioIoEvent implements NioIoEvent {
/*     */     private final NioIoOps ops;
/*     */     
/*     */     DefaultNioIoEvent(NioIoOps ops) {
/* 195 */       this.ops = ops;
/*     */     }
/*     */ 
/*     */     
/*     */     public NioIoOps ops() {
/* 200 */       return this.ops;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 205 */       if (this == o) {
/* 206 */         return true;
/*     */       }
/* 208 */       if (o == null || getClass() != o.getClass()) {
/* 209 */         return false;
/*     */       }
/* 211 */       NioIoEvent event = (NioIoEvent)o;
/* 212 */       return event.ops().equals(ops());
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 217 */       return ops().hashCode();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\nio\NioIoOps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */