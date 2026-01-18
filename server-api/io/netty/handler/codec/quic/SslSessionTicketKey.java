/*     */ package io.netty.handler.codec.quic;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ public final class SslSessionTicketKey
/*     */ {
/*     */   public static final int NAME_SIZE = 16;
/*     */   public static final int HMAC_KEY_SIZE = 16;
/*     */   public static final int AES_KEY_SIZE = 16;
/*     */   public static final int TICKET_KEY_SIZE = 48;
/*     */   final byte[] name;
/*     */   final byte[] hmacKey;
/*     */   final byte[] aesKey;
/*     */   
/*     */   public SslSessionTicketKey(byte[] name, byte[] hmacKey, byte[] aesKey) {
/*  54 */     if (name == null || name.length != 16) {
/*  55 */       throw new IllegalArgumentException("Length of name must be 16");
/*     */     }
/*  57 */     if (hmacKey == null || hmacKey.length != 16) {
/*  58 */       throw new IllegalArgumentException("Length of hmacKey must be 16");
/*     */     }
/*  60 */     if (aesKey == null || aesKey.length != 16) {
/*  61 */       throw new IllegalArgumentException("Length of aesKey must be 16");
/*     */     }
/*  63 */     this.name = (byte[])name.clone();
/*  64 */     this.hmacKey = (byte[])hmacKey.clone();
/*  65 */     this.aesKey = (byte[])aesKey.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] name() {
/*  74 */     return (byte[])this.name.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] hmacKey() {
/*  82 */     return (byte[])this.hmacKey.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] aesKey() {
/*  90 */     return (byte[])this.aesKey.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  95 */     if (this == o) {
/*  96 */       return true;
/*     */     }
/*  98 */     if (o == null || getClass() != o.getClass()) {
/*  99 */       return false;
/*     */     }
/*     */     
/* 102 */     SslSessionTicketKey that = (SslSessionTicketKey)o;
/*     */     
/* 104 */     if (!Arrays.equals(this.name, that.name)) {
/* 105 */       return false;
/*     */     }
/* 107 */     if (!Arrays.equals(this.hmacKey, that.hmacKey)) {
/* 108 */       return false;
/*     */     }
/* 110 */     return Arrays.equals(this.aesKey, that.aesKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 115 */     int result = Arrays.hashCode(this.name);
/* 116 */     result = 31 * result + Arrays.hashCode(this.hmacKey);
/* 117 */     result = 31 * result + Arrays.hashCode(this.aesKey);
/* 118 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 123 */     return "SessionTicketKey{name=" + 
/* 124 */       Arrays.toString(this.name) + ", hmacKey=" + 
/* 125 */       Arrays.toString(this.hmacKey) + ", aesKey=" + 
/* 126 */       Arrays.toString(this.aesKey) + '}';
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\SslSessionTicketKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */