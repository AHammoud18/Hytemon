/*    */ package io.netty.handler.codec.quic;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import java.security.SecureRandom;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ConnectionIdChannelMap
/*    */ {
/* 32 */   private static final SecureRandom random = new SecureRandom();
/*    */   
/* 34 */   private final Map<ConnectionIdKey, QuicheQuicChannel> channelMap = new HashMap<>();
/*    */   private final SipHash sipHash;
/*    */   
/*    */   ConnectionIdChannelMap() {
/* 38 */     byte[] seed = new byte[16];
/* 39 */     random.nextBytes(seed);
/*    */     
/* 41 */     this.sipHash = new SipHash(1, 3, seed);
/*    */   }
/*    */   
/*    */   private ConnectionIdKey key(ByteBuffer cid) {
/* 45 */     long hash = this.sipHash.macHash(cid);
/* 46 */     return new ConnectionIdKey(hash, cid);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   QuicheQuicChannel put(ByteBuffer cid, QuicheQuicChannel channel) {
/* 51 */     return this.channelMap.put(key(cid), channel);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   QuicheQuicChannel remove(ByteBuffer cid) {
/* 56 */     return this.channelMap.remove(key(cid));
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   QuicheQuicChannel get(ByteBuffer cid) {
/* 61 */     return this.channelMap.get(key(cid));
/*    */   }
/*    */   
/*    */   void clear() {
/* 65 */     this.channelMap.clear();
/*    */   }
/*    */   
/*    */   private static final class ConnectionIdKey implements Comparable<ConnectionIdKey> {
/*    */     private final long hash;
/*    */     private final ByteBuffer key;
/*    */     
/*    */     ConnectionIdKey(long hash, ByteBuffer key) {
/* 73 */       this.hash = hash;
/* 74 */       this.key = key;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean equals(Object o) {
/* 79 */       if (this == o) {
/* 80 */         return true;
/*    */       }
/* 82 */       if (o == null || getClass() != o.getClass()) {
/* 83 */         return false;
/*    */       }
/* 85 */       ConnectionIdKey that = (ConnectionIdKey)o;
/* 86 */       return (this.hash == that.hash && Objects.equals(this.key, that.key));
/*    */     }
/*    */ 
/*    */     
/*    */     public int hashCode() {
/* 91 */       return (int)this.hash;
/*    */     }
/*    */ 
/*    */     
/*    */     public int compareTo(@NotNull ConnectionIdKey o) {
/* 96 */       int result = Long.compare(this.hash, o.hash);
/* 97 */       return (result != 0) ? result : this.key.compareTo(o.key);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\quic\ConnectionIdChannelMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */