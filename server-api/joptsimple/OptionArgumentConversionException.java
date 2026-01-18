/*    */ package joptsimple;
/*    */ 
/*    */ import java.util.Collections;
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
/*    */ class OptionArgumentConversionException
/*    */   extends OptionException
/*    */ {
/*    */   private static final long serialVersionUID = -1L;
/*    */   private final String argument;
/*    */   
/*    */   OptionArgumentConversionException(OptionSpec<?> options, String argument, Throwable cause) {
/* 41 */     super(Collections.singleton(options), cause);
/*    */     
/* 43 */     this.argument = argument;
/*    */   }
/*    */ 
/*    */   
/*    */   Object[] messageArguments() {
/* 48 */     return new Object[] { this.argument, singleOptionString() };
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\OptionArgumentConversionException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */