/*    */ package io.netty.resolver;
/*    */ 
/*    */ import java.net.InetAddress;
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
/*    */ 
/*    */ public interface HostsFileEntriesResolver
/*    */ {
/* 28 */   public static final HostsFileEntriesResolver DEFAULT = new DefaultHostsFileEntriesResolver();
/*    */   
/*    */   InetAddress address(String paramString, ResolvedAddressTypes paramResolvedAddressTypes);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\resolver\HostsFileEntriesResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */