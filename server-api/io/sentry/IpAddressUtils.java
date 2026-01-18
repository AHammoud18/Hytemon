/*    */ package io.sentry;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ @Internal
/*    */ public final class IpAddressUtils
/*    */ {
/*    */   public static final String DEFAULT_IP_ADDRESS = "{{auto}}";
/* 12 */   private static final List<String> DEFAULT_IP_ADDRESS_VALID_VALUES = Arrays.asList(new String[] { "{{auto}}", "{{ auto }}" });
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isDefault(@Nullable String ipAddress) {
/* 17 */     return (ipAddress != null && DEFAULT_IP_ADDRESS_VALID_VALUES.contains(ipAddress));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\IpAddressUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */