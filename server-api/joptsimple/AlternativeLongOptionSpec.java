/*    */ package joptsimple;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.Locale;
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
/*    */ class AlternativeLongOptionSpec
/*    */   extends ArgumentAcceptingOptionSpec<String>
/*    */ {
/*    */   AlternativeLongOptionSpec() {
/* 43 */     super(Collections.singletonList("W"), true, 
/*    */         
/* 45 */         Messages.message(
/* 46 */           Locale.getDefault(), "joptsimple.HelpFormatterMessages", AlternativeLongOptionSpec.class, "description", new Object[0]));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 51 */     describedAs(Messages.message(
/* 52 */           Locale.getDefault(), "joptsimple.HelpFormatterMessages", AlternativeLongOptionSpec.class, "arg.description", new Object[0]));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void detectOptionArgument(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions) {
/* 60 */     if (!arguments.hasMore()) {
/* 61 */       throw new OptionMissingRequiredArgumentException(this);
/*    */     }
/* 63 */     arguments.treatNextAsLongOption();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\AlternativeLongOptionSpec.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */