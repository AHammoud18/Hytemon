/*    */ package joptsimple.util;
/*    */ 
/*    */ import java.net.InetAddress;
/*    */ import java.net.UnknownHostException;
/*    */ import java.util.Locale;
/*    */ import joptsimple.ValueConversionException;
/*    */ import joptsimple.ValueConverter;
/*    */ import joptsimple.internal.Messages;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InetAddressConverter
/*    */   implements ValueConverter<InetAddress>
/*    */ {
/*    */   public InetAddress convert(String value) {
/*    */     try {
/* 44 */       return InetAddress.getByName(value);
/*    */     }
/* 46 */     catch (UnknownHostException e) {
/* 47 */       throw new ValueConversionException(message(value));
/*    */     } 
/*    */   }
/*    */   
/*    */   public Class<InetAddress> valueType() {
/* 52 */     return InetAddress.class;
/*    */   }
/*    */   
/*    */   public String valuePattern() {
/* 56 */     return null;
/*    */   }
/*    */   
/*    */   private String message(String value) {
/* 60 */     return Messages.message(
/* 61 */         Locale.getDefault(), "joptsimple.ExceptionMessages", InetAddressConverter.class, "message", new Object[] { value });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimpl\\util\InetAddressConverter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */