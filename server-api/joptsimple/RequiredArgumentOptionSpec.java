/*    */ package joptsimple;
/*    */ 
/*    */ import java.util.List;
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
/*    */ class RequiredArgumentOptionSpec<V>
/*    */   extends ArgumentAcceptingOptionSpec<V>
/*    */ {
/*    */   RequiredArgumentOptionSpec(String option) {
/* 38 */     super(option, true);
/*    */   }
/*    */   
/*    */   RequiredArgumentOptionSpec(List<String> options, String description) {
/* 42 */     super(options, true, description);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void detectOptionArgument(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions) {
/* 47 */     if (!arguments.hasMore()) {
/* 48 */       throw new OptionMissingRequiredArgumentException(this);
/*    */     }
/* 50 */     addArguments(detectedOptions, arguments.next());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\RequiredArgumentOptionSpec.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */