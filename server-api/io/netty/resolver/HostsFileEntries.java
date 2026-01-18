/*    */ package io.netty.resolver;
/*    */ 
/*    */ import java.net.Inet4Address;
/*    */ import java.net.Inet6Address;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class HostsFileEntries
/*    */ {
/* 34 */   static final HostsFileEntries EMPTY = new HostsFileEntries(
/*    */       
/* 36 */       Collections.emptyMap(), 
/* 37 */       Collections.emptyMap());
/*    */   
/*    */   private final Map<String, Inet4Address> inet4Entries;
/*    */   private final Map<String, Inet6Address> inet6Entries;
/*    */   
/*    */   public HostsFileEntries(Map<String, Inet4Address> inet4Entries, Map<String, Inet6Address> inet6Entries) {
/* 43 */     this.inet4Entries = Collections.unmodifiableMap(new HashMap<>(inet4Entries));
/* 44 */     this.inet6Entries = Collections.unmodifiableMap(new HashMap<>(inet6Entries));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<String, Inet4Address> inet4Entries() {
/* 52 */     return this.inet4Entries;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<String, Inet6Address> inet6Entries() {
/* 60 */     return this.inet6Entries;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\resolver\HostsFileEntries.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */