/*    */ package org.jline.console;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Function;
/*    */ import org.jline.reader.Completer;
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
/*    */ public class CommandMethods
/*    */ {
/*    */   Function<CommandInput, ?> execute;
/*    */   Function<String, List<Completer>> compileCompleter;
/*    */   
/*    */   public CommandMethods(Function<CommandInput, ?> execute, Function<String, List<Completer>> compileCompleter) {
/* 39 */     this.execute = execute;
/* 40 */     this.compileCompleter = compileCompleter;
/*    */   }
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
/*    */   public CommandMethods(Consumer<CommandInput> execute, Function<String, List<Completer>> compileCompleter) {
/* 53 */     this.execute = (i -> {
/*    */         execute.accept(i);
/*    */         return null;
/*    */       });
/* 57 */     this.compileCompleter = compileCompleter;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Function<CommandInput, ?> execute() {
/* 66 */     return this.execute;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Function<String, List<Completer>> compileCompleter() {
/* 75 */     return this.compileCompleter;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\console\CommandMethods.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */