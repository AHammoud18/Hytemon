/*    */ package io.netty.channel.epoll;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.io.IOException;
/*    */ import java.net.InetAddress;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
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
/*    */ 
/*    */ final class TcpMd5Util
/*    */ {
/*    */   static Collection<InetAddress> newTcpMd5Sigs(AbstractEpollChannel channel, Collection<InetAddress> current, Map<InetAddress, byte[]> newKeys) throws IOException {
/* 34 */     ObjectUtil.checkNotNull(channel, "channel");
/* 35 */     ObjectUtil.checkNotNull(current, "current");
/* 36 */     ObjectUtil.checkNotNull(newKeys, "newKeys");
/*    */ 
/*    */     
/* 39 */     for (Map.Entry<InetAddress, byte[]> e : newKeys.entrySet()) {
/* 40 */       byte[] key = e.getValue();
/* 41 */       ObjectUtil.checkNotNullWithIAE(e.getKey(), "e.getKey");
/* 42 */       ObjectUtil.checkNonEmpty(key, ((InetAddress)e.getKey()).toString());
/* 43 */       if (key.length > Native.TCP_MD5SIG_MAXKEYLEN) {
/* 44 */         throw new IllegalArgumentException("newKeys[" + e.getKey() + "] has a key with invalid length; should not exceed the maximum length (" + Native.TCP_MD5SIG_MAXKEYLEN + ')');
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 51 */     for (InetAddress addr : current) {
/* 52 */       if (!newKeys.containsKey(addr)) {
/* 53 */         channel.socket.setTcpMd5Sig(addr, null);
/*    */       }
/*    */     } 
/*    */     
/* 57 */     if (newKeys.isEmpty()) {
/* 58 */       return Collections.emptySet();
/*    */     }
/*    */ 
/*    */     
/* 62 */     Collection<InetAddress> addresses = new ArrayList<>(newKeys.size());
/* 63 */     for (Map.Entry<InetAddress, byte[]> e : newKeys.entrySet()) {
/* 64 */       channel.socket.setTcpMd5Sig(e.getKey(), e.getValue());
/* 65 */       addresses.add(e.getKey());
/*    */     } 
/*    */     
/* 68 */     return addresses;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\epoll\TcpMd5Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */