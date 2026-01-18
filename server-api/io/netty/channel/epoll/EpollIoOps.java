/*     */ package io.netty.channel.epoll;
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
/*     */ public final class EpollIoOps
/*     */   implements IoOps
/*     */ {
/*     */   static {
/*  28 */     Epoll.ensureAvailability();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  35 */   public static final EpollIoOps EPOLLOUT = new EpollIoOps(Native.EPOLLOUT);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   public static final EpollIoOps EPOLLIN = new EpollIoOps(Native.EPOLLIN);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   public static final EpollIoOps EPOLLERR = new EpollIoOps(Native.EPOLLERR);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   public static final EpollIoOps EPOLLRDHUP = new EpollIoOps(Native.EPOLLRDHUP);
/*     */   
/*  52 */   public static final EpollIoOps EPOLLET = new EpollIoOps(Native.EPOLLET);
/*     */   
/*  54 */   static final int EPOLL_ERR_OUT_MASK = EPOLLERR.value | EPOLLOUT.value;
/*  55 */   static final int EPOLL_ERR_IN_MASK = EPOLLERR.value | EPOLLIN.value;
/*  56 */   static final int EPOLL_RDHUP_MASK = EPOLLRDHUP.value;
/*     */   
/*     */   private static final EpollIoEvent[] EVENTS;
/*     */   final int value;
/*     */   
/*     */   static {
/*  62 */     EpollIoOps all = new EpollIoOps(EPOLLOUT.value | EPOLLIN.value | EPOLLERR.value | EPOLLRDHUP.value);
/*  63 */     EVENTS = new EpollIoEvent[all.value + 1];
/*  64 */     addToArray(EVENTS, EPOLLOUT);
/*  65 */     addToArray(EVENTS, EPOLLIN);
/*  66 */     addToArray(EVENTS, EPOLLERR);
/*  67 */     addToArray(EVENTS, EPOLLRDHUP);
/*  68 */     addToArray(EVENTS, all);
/*     */   }
/*     */   
/*     */   private static void addToArray(EpollIoEvent[] array, EpollIoOps ops) {
/*  72 */     array[ops.value] = new DefaultEpollIoEvent(ops);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private EpollIoOps(int value) {
/*  78 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(EpollIoOps ops) {
/*  87 */     return ((this.value & ops.value) != 0);
/*     */   }
/*     */   
/*     */   boolean contains(int value) {
/*  91 */     return ((this.value & value) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollIoOps with(EpollIoOps ops) {
/* 101 */     if (contains(ops)) {
/* 102 */       return this;
/*     */     }
/* 104 */     return valueOf(this.value | ops.value());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EpollIoOps without(EpollIoOps ops) {
/* 114 */     if (!contains(ops)) {
/* 115 */       return this;
/*     */     }
/* 117 */     return valueOf(this.value & (ops.value() ^ 0xFFFFFFFF));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int value() {
/* 126 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 131 */     if (this == o) {
/* 132 */       return true;
/*     */     }
/* 134 */     if (o == null || getClass() != o.getClass()) {
/* 135 */       return false;
/*     */     }
/* 137 */     EpollIoOps nioOps = (EpollIoOps)o;
/* 138 */     return (this.value == nioOps.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 143 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EpollIoOps valueOf(int value) {
/* 153 */     return eventOf(value).ops();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 158 */     return "EpollIoOps{value=" + this.value + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static EpollIoEvent eventOf(int value) {
/* 164 */     if (value > 0 && value < EVENTS.length) {
/* 165 */       EpollIoEvent event = EVENTS[value];
/* 166 */       if (event != null) {
/* 167 */         return event;
/*     */       }
/*     */     } 
/* 170 */     return new DefaultEpollIoEvent(new EpollIoOps(value));
/*     */   }
/*     */   
/*     */   private static final class DefaultEpollIoEvent implements EpollIoEvent {
/*     */     private final EpollIoOps ops;
/*     */     
/*     */     DefaultEpollIoEvent(EpollIoOps ops) {
/* 177 */       this.ops = ops;
/*     */     }
/*     */ 
/*     */     
/*     */     public EpollIoOps ops() {
/* 182 */       return this.ops;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 187 */       if (this == o) {
/* 188 */         return true;
/*     */       }
/* 190 */       if (o == null || getClass() != o.getClass()) {
/* 191 */         return false;
/*     */       }
/* 193 */       EpollIoEvent event = (EpollIoEvent)o;
/* 194 */       return event.ops().equals(ops());
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 199 */       return ops().hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 204 */       return "DefaultEpollIoEvent{ops=" + this.ops + '}';
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\EpollIoOps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */