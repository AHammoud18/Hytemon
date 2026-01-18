/*    */ package org.jline.console.impl;
/*    */ 
/*    */ import java.nio.file.Path;
/*    */ import java.util.function.Supplier;
/*    */ import org.jline.builtins.ConfigurationPath;
/*    */ import org.jline.reader.LineReader;
/*    */ import org.jline.reader.Parser;
/*    */ import org.jline.terminal.Terminal;
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
/*    */ public class SimpleSystemRegistryImpl
/*    */   extends SystemRegistryImpl
/*    */ {
/*    */   private LineReader lineReader;
/*    */   
/*    */   public SimpleSystemRegistryImpl(Parser parser, Terminal terminal, Supplier<Path> workDir, ConfigurationPath configPath) {
/* 27 */     super(parser, terminal, workDir, configPath);
/*    */   }
/*    */   
/*    */   public void setLineReader(LineReader lineReader) {
/* 31 */     this.lineReader = lineReader;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> T consoleOption(String name, T defVal) {
/* 37 */     return (T)this.lineReader.getVariables().getOrDefault(name, defVal);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setConsoleOption(String name, Object value) {
/* 42 */     this.lineReader.setVariable(name, value);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\console\impl\SimpleSystemRegistryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */