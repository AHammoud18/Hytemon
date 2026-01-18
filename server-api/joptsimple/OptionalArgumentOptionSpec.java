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
/*    */ class OptionalArgumentOptionSpec<V>
/*    */   extends ArgumentAcceptingOptionSpec<V>
/*    */ {
/*    */   OptionalArgumentOptionSpec(String option) {
/* 38 */     super(option, false);
/*    */   }
/*    */   
/*    */   OptionalArgumentOptionSpec(List<String> options, String description) {
/* 42 */     super(options, false, description);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void detectOptionArgument(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions) {
/* 47 */     if (arguments.hasMore()) {
/* 48 */       String nextArgument = arguments.peek();
/*    */       
/* 50 */       if (!parser.looksLikeAnOption(nextArgument) && canConvertArgument(nextArgument)) {
/* 51 */         handleOptionArgument(parser, detectedOptions, arguments);
/* 52 */       } else if (isArgumentOfNumberType() && canConvertArgument(nextArgument)) {
/* 53 */         addArguments(detectedOptions, arguments.next());
/*    */       } else {
/* 55 */         detectedOptions.add(this);
/*    */       } 
/*    */     } else {
/* 58 */       detectedOptions.add(this);
/*    */     } 
/*    */   }
/*    */   private void handleOptionArgument(OptionParser parser, OptionSet detectedOptions, ArgumentList arguments) {
/* 62 */     if (parser.posixlyCorrect()) {
/* 63 */       detectedOptions.add(this);
/* 64 */       parser.noMoreOptions();
/*    */     } else {
/*    */       
/* 67 */       addArguments(detectedOptions, arguments.next());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\OptionalArgumentOptionSpec.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */