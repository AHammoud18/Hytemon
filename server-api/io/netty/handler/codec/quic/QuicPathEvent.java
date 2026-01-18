/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.Objects;
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
/*     */ public abstract class QuicPathEvent
/*     */   implements QuicEvent
/*     */ {
/*     */   private final InetSocketAddress local;
/*     */   private final InetSocketAddress remote;
/*     */   
/*     */   QuicPathEvent(InetSocketAddress local, InetSocketAddress remote) {
/*  32 */     this.local = Objects.<InetSocketAddress>requireNonNull(local, "local");
/*  33 */     this.remote = Objects.<InetSocketAddress>requireNonNull(remote, "remote");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InetSocketAddress local() {
/*  42 */     return this.local;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InetSocketAddress remote() {
/*  51 */     return this.remote;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  56 */     return "QuicPathEvent{local=" + this.local + ", remote=" + this.remote + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  64 */     if (this == o) {
/*  65 */       return true;
/*     */     }
/*  67 */     if (o == null || getClass() != o.getClass()) {
/*  68 */       return false;
/*     */     }
/*     */     
/*  71 */     QuicPathEvent that = (QuicPathEvent)o;
/*  72 */     if (!Objects.equals(this.local, that.local)) {
/*  73 */       return false;
/*     */     }
/*  75 */     return Objects.equals(this.remote, that.remote);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  80 */     int result = (this.local != null) ? this.local.hashCode() : 0;
/*  81 */     result = 31 * result + ((this.remote != null) ? this.remote.hashCode() : 0);
/*  82 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class New
/*     */     extends QuicPathEvent
/*     */   {
/*     */     public New(InetSocketAddress local, InetSocketAddress remote) {
/*  95 */       super(local, remote);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 100 */       return "QuicPathEvent.New{local=" + 
/* 101 */         local() + ", remote=" + 
/* 102 */         remote() + '}';
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Validated
/*     */     extends QuicPathEvent
/*     */   {
/*     */     public Validated(InetSocketAddress local, InetSocketAddress remote) {
/* 115 */       super(local, remote);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 120 */       return "QuicPathEvent.Validated{local=" + 
/* 121 */         local() + ", remote=" + 
/* 122 */         remote() + '}';
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class FailedValidation
/*     */     extends QuicPathEvent
/*     */   {
/*     */     public FailedValidation(InetSocketAddress local, InetSocketAddress remote) {
/* 136 */       super(local, remote);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 141 */       return "QuicPathEvent.FailedValidation{local=" + 
/* 142 */         local() + ", remote=" + 
/* 143 */         remote() + '}';
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Closed
/*     */     extends QuicPathEvent
/*     */   {
/*     */     public Closed(InetSocketAddress local, InetSocketAddress remote) {
/* 157 */       super(local, remote);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 162 */       if (this == o) {
/* 163 */         return true;
/*     */       }
/* 165 */       if (o == null || getClass() != o.getClass()) {
/* 166 */         return false;
/*     */       }
/* 168 */       return super.equals(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 173 */       return 31 * super.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 178 */       return "QuicPathEvent.Closed{local=" + 
/* 179 */         local() + ", remote=" + 
/* 180 */         remote() + '}';
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class ReusedSourceConnectionId
/*     */     extends QuicPathEvent
/*     */   {
/*     */     private final long seq;
/*     */ 
/*     */ 
/*     */     
/*     */     private final InetSocketAddress oldLocal;
/*     */ 
/*     */ 
/*     */     
/*     */     private final InetSocketAddress oldRemote;
/*     */ 
/*     */ 
/*     */     
/*     */     public ReusedSourceConnectionId(long seq, InetSocketAddress oldLocal, InetSocketAddress oldRemote, InetSocketAddress local, InetSocketAddress remote) {
/* 203 */       super(local, remote);
/* 204 */       this.seq = seq;
/* 205 */       this.oldLocal = Objects.<InetSocketAddress>requireNonNull(oldLocal, "oldLocal");
/* 206 */       this.oldRemote = Objects.<InetSocketAddress>requireNonNull(oldRemote, "oldRemote");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long seq() {
/* 215 */       return this.seq;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InetSocketAddress oldLocal() {
/* 224 */       return this.oldLocal;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InetSocketAddress oldRemote() {
/* 233 */       return this.oldRemote;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 238 */       if (this == o) {
/* 239 */         return true;
/*     */       }
/* 241 */       if (o == null || getClass() != o.getClass()) {
/* 242 */         return false;
/*     */       }
/* 244 */       if (!super.equals(o)) {
/* 245 */         return false;
/*     */       }
/*     */       
/* 248 */       ReusedSourceConnectionId that = (ReusedSourceConnectionId)o;
/*     */       
/* 250 */       if (this.seq != that.seq) {
/* 251 */         return false;
/*     */       }
/* 253 */       if (!Objects.equals(this.oldLocal, that.oldLocal)) {
/* 254 */         return false;
/*     */       }
/* 256 */       return Objects.equals(this.oldRemote, that.oldRemote);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 261 */       int result = super.hashCode();
/* 262 */       result = 31 * result + (int)(this.seq ^ this.seq >>> 32L);
/* 263 */       result = 31 * result + ((this.oldLocal != null) ? this.oldLocal.hashCode() : 0);
/* 264 */       result = 31 * result + ((this.oldRemote != null) ? this.oldRemote.hashCode() : 0);
/* 265 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 270 */       return "QuicPathEvent.ReusedSourceConnectionId{seq=" + this.seq + ", oldLocal=" + this.oldLocal + ", oldRemote=" + this.oldRemote + ", local=" + 
/*     */ 
/*     */ 
/*     */         
/* 274 */         local() + ", remote=" + 
/* 275 */         remote() + '}';
/*     */     }
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
/*     */   public static final class PeerMigrated
/*     */     extends QuicPathEvent
/*     */   {
/*     */     public PeerMigrated(InetSocketAddress local, InetSocketAddress remote) {
/* 291 */       super(local, remote);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 296 */       return "QuicPathEvent.PeerMigrated{local=" + 
/* 297 */         local() + ", remote=" + 
/* 298 */         remote() + '}';
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicPathEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */