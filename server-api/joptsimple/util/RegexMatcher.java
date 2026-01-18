/*    */ package joptsimple.util;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.regex.Pattern;
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
/*    */ public class RegexMatcher
/*    */   implements ValueConverter<String>
/*    */ {
/*    */   private final Pattern pattern;
/*    */   
/*    */   public RegexMatcher(String pattern, int flags) {
/* 55 */     this.pattern = Pattern.compile(pattern, flags);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ValueConverter<String> regex(String pattern) {
/* 66 */     return new RegexMatcher(pattern, 0);
/*    */   }
/*    */   
/*    */   public String convert(String value) {
/* 70 */     if (!this.pattern.matcher(value).matches()) {
/* 71 */       raiseValueConversionFailure(value);
/*    */     }
/*    */     
/* 74 */     return value;
/*    */   }
/*    */   
/*    */   public Class<String> valueType() {
/* 78 */     return String.class;
/*    */   }
/*    */   
/*    */   public String valuePattern() {
/* 82 */     return this.pattern.pattern();
/*    */   }
/*    */   
/*    */   private void raiseValueConversionFailure(String value) {
/* 86 */     String message = Messages.message(
/* 87 */         Locale.getDefault(), "joptsimple.ExceptionMessages", RegexMatcher.class, "message", new Object[] { value, this.pattern
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 92 */           .pattern() });
/* 93 */     throw new ValueConversionException(message);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimpl\\util\RegexMatcher.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */