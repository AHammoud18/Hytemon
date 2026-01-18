/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
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
/*     */ public final class QuicConnectionAddress
/*     */   extends SocketAddress
/*     */ {
/*  32 */   static final QuicConnectionAddress NULL_LEN = new QuicConnectionAddress(EmptyArrays.EMPTY_BYTES);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   public static final QuicConnectionAddress EPHEMERAL = new QuicConnectionAddress(null, false);
/*     */ 
/*     */ 
/*     */   
/*     */   private final String toStr;
/*     */ 
/*     */   
/*     */   private final ByteBuffer connId;
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicConnectionAddress(byte[] connId) {
/*  50 */     this(ByteBuffer.wrap((byte[])connId.clone()), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuicConnectionAddress(ByteBuffer connId) {
/*  59 */     this(connId.duplicate(), true);
/*     */   }
/*     */   
/*     */   private QuicConnectionAddress(ByteBuffer connId, boolean validate) {
/*  63 */     Quic.ensureAvailability();
/*  64 */     if (validate && connId.remaining() > Quiche.QUICHE_MAX_CONN_ID_LEN) {
/*  65 */       throw new IllegalArgumentException("Connection ID can only be of max length " + Quiche.QUICHE_MAX_CONN_ID_LEN);
/*     */     }
/*     */     
/*  68 */     if (connId == null) {
/*  69 */       this.connId = null;
/*  70 */       this.toStr = "QuicConnectionAddress{EPHEMERAL}";
/*     */     } else {
/*  72 */       this.connId = connId.asReadOnlyBuffer().duplicate();
/*  73 */       ByteBuf buffer = Unpooled.wrappedBuffer(connId);
/*     */       try {
/*  75 */         this
/*  76 */           .toStr = "QuicConnectionAddress{connId=" + ByteBufUtil.hexDump(buffer) + '}';
/*     */       } finally {
/*  78 */         buffer.release();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  85 */     return this.toStr;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  90 */     if (this == EPHEMERAL) {
/*  91 */       return System.identityHashCode(EPHEMERAL);
/*     */     }
/*  93 */     return Objects.hash(new Object[] { this.connId });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  98 */     if (!(obj instanceof QuicConnectionAddress)) {
/*  99 */       return false;
/*     */     }
/* 101 */     QuicConnectionAddress address = (QuicConnectionAddress)obj;
/* 102 */     if (obj == this) {
/* 103 */       return true;
/*     */     }
/* 105 */     return this.connId.equals(address.connId);
/*     */   }
/*     */   
/*     */   ByteBuffer id() {
/* 109 */     if (this.connId == null) {
/* 110 */       return ByteBuffer.allocate(0);
/*     */     }
/* 112 */     return this.connId.duplicate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static QuicConnectionAddress random(int length) {
/* 123 */     return new QuicConnectionAddress(QuicConnectionIdGenerator.randomGenerator().newId(length));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static QuicConnectionAddress random() {
/* 133 */     return random(Quiche.QUICHE_MAX_CONN_ID_LEN);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\QuicConnectionAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */