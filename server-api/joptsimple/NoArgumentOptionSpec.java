/*    */ package joptsimple;
/*    */ 
/*    */ import java.util.Collections;
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
/*    */ class NoArgumentOptionSpec
/*    */   extends AbstractOptionSpec<Void>
/*    */ {
/*    */   NoArgumentOptionSpec(String option) {
/* 39 */     this(Collections.singletonList(option), "");
/*    */   }
/*    */   
/*    */   NoArgumentOptionSpec(List<String> options, String description) {
/* 43 */     super(options, description);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void handleOption(OptionParser parser, ArgumentList arguments, OptionSet detectedOptions, String detectedArgument) {
/* 50 */     detectedOptions.add(this);
/*    */   }
/*    */   
/*    */   public boolean acceptsArguments() {
/* 54 */     return false;
/*    */   }
/*    */   
/*    */   public boolean requiresArgument() {
/* 58 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isRequired() {
/* 62 */     return false;
/*    */   }
/*    */   
/*    */   public String argumentDescription() {
/* 66 */     return "";
/*    */   }
/*    */   
/*    */   public String argumentTypeIndicator() {
/* 70 */     return "";
/*    */   }
/*    */ 
/*    */   
/*    */   protected Void convert(String argument) {
/* 75 */     return null;
/*    */   }
/*    */   
/*    */   public List<Void> defaultValues() {
/* 79 */     return Collections.emptyList();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\NoArgumentOptionSpec.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */