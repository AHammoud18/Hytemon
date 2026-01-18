/*    */ package joptsimple.internal;
/*    */ 
/*    */ import java.text.MessageFormat;
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
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
/*    */ public class Messages
/*    */ {
/*    */   private Messages() {
/* 37 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public static String message(Locale locale, String bundleName, Class<?> type, String key, Object... args) {
/* 41 */     ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
/* 42 */     String template = bundle.getString(type.getName() + '.' + key);
/* 43 */     MessageFormat format = new MessageFormat(template);
/* 44 */     format.setLocale(locale);
/* 45 */     return format.format(args);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\internal\Messages.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */