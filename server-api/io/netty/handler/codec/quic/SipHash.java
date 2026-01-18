/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
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
/*     */ final class SipHash
/*     */ {
/*     */   static final int SEED_LENGTH = 16;
/*     */   private final int compressionRounds;
/*     */   private final int finalizationRounds;
/*     */   private static final long INITIAL_STATE_V0 = 8317987319222330741L;
/*     */   private static final long INITIAL_STATE_V1 = 7237128888997146477L;
/*     */   private static final long INITIAL_STATE_V2 = 7816392313619706465L;
/*     */   private static final long INITIAL_STATE_V3 = 8387220255154660723L;
/*     */   private final long initialStateV0;
/*     */   private final long initialStateV1;
/*     */   private final long initialStateV2;
/*     */   private final long initialStateV3;
/*     */   private long v0;
/*     */   private long v1;
/*     */   private long v2;
/*     */   private long v3;
/*     */   
/*     */   SipHash(int compressionRounds, int finalizationRounds, byte[] seed) {
/*  51 */     if (seed.length != 16) {
/*  52 */       throw new IllegalArgumentException("seed must be of length 16");
/*     */     }
/*  54 */     this.compressionRounds = ObjectUtil.checkPositive(compressionRounds, "compressionRounds");
/*  55 */     this.finalizationRounds = ObjectUtil.checkPositive(finalizationRounds, "finalizationRounds");
/*     */ 
/*     */ 
/*     */     
/*  59 */     ByteBuffer keyBuffer = ByteBuffer.wrap(seed).order(ByteOrder.LITTLE_ENDIAN);
/*  60 */     long k0 = keyBuffer.getLong();
/*  61 */     long k1 = keyBuffer.getLong();
/*     */     
/*  63 */     this.initialStateV0 = 0x736F6D6570736575L ^ k0;
/*  64 */     this.initialStateV1 = 0x646F72616E646F6DL ^ k1;
/*  65 */     this.initialStateV2 = 0x6C7967656E657261L ^ k0;
/*  66 */     this.initialStateV3 = 0x7465646279746573L ^ k1;
/*     */   }
/*     */   
/*     */   long macHash(ByteBuffer input) {
/*  70 */     this.v0 = this.initialStateV0;
/*  71 */     this.v1 = this.initialStateV1;
/*  72 */     this.v2 = this.initialStateV2;
/*  73 */     this.v3 = this.initialStateV3;
/*  74 */     int remaining = input.remaining();
/*  75 */     int position = input.position();
/*  76 */     int len = remaining - remaining % 8;
/*  77 */     boolean needsReverse = (input.order() == ByteOrder.BIG_ENDIAN);
/*  78 */     for (int offset = position; offset < len; offset += 8) {
/*  79 */       long m = input.getLong(offset);
/*  80 */       if (needsReverse)
/*     */       {
/*  82 */         m = Long.reverseBytes(m);
/*     */       }
/*  84 */       this.v3 ^= m;
/*  85 */       for (int j = 0; j < this.compressionRounds; j++) {
/*  86 */         sipround();
/*     */       }
/*  88 */       this.v0 ^= m;
/*     */     } 
/*     */ 
/*     */     
/*  92 */     int left = remaining & 0x7;
/*  93 */     long b = remaining << 56L;
/*  94 */     assert left < 8;
/*  95 */     switch (left) {
/*     */       case 7:
/*  97 */         b |= input.get(position + len + 6) << 48L;
/*     */       case 6:
/*  99 */         b |= input.get(position + len + 5) << 40L;
/*     */       case 5:
/* 101 */         b |= input.get(position + len + 4) << 32L;
/*     */       case 4:
/* 103 */         b |= input.get(position + len + 3) << 24L;
/*     */       case 3:
/* 105 */         b |= input.get(position + len + 2) << 16L;
/*     */       case 2:
/* 107 */         b |= input.get(position + len + 1) << 8L;
/*     */       case 1:
/* 109 */         b |= input.get(position + len);
/*     */         break;
/*     */       case 0:
/*     */         break;
/*     */       default:
/* 114 */         throw new IllegalStateException("Unexpected value: " + left);
/*     */     } 
/*     */     
/* 117 */     this.v3 ^= b; int i;
/* 118 */     for (i = 0; i < this.compressionRounds; i++) {
/* 119 */       sipround();
/*     */     }
/*     */     
/* 122 */     this.v0 ^= b;
/* 123 */     this.v2 ^= 0xFFL;
/* 124 */     for (i = 0; i < this.finalizationRounds; i++) {
/* 125 */       sipround();
/*     */     }
/*     */     
/* 128 */     return this.v0 ^ this.v1 ^ this.v2 ^ this.v3;
/*     */   }
/*     */   
/*     */   private void sipround() {
/* 132 */     this.v0 += this.v1;
/* 133 */     this.v2 += this.v3;
/* 134 */     this.v1 = Long.rotateLeft(this.v1, 13);
/* 135 */     this.v3 = Long.rotateLeft(this.v3, 16);
/* 136 */     this.v1 ^= this.v0;
/* 137 */     this.v3 ^= this.v2;
/*     */     
/* 139 */     this.v0 = Long.rotateLeft(this.v0, 32);
/*     */     
/* 141 */     this.v2 += this.v1;
/* 142 */     this.v0 += this.v3;
/* 143 */     this.v1 = Long.rotateLeft(this.v1, 17);
/* 144 */     this.v3 = Long.rotateLeft(this.v3, 21);
/* 145 */     this.v1 ^= this.v2;
/* 146 */     this.v3 ^= this.v0;
/*     */     
/* 148 */     this.v2 = Long.rotateLeft(this.v2, 32);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\SipHash.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */