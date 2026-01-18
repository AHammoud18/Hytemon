/*     */ package org.jline.terminal.spi;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Properties;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.terminal.Size;
/*     */ import org.jline.terminal.Terminal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface TerminalProvider
/*     */ {
/*     */   String name();
/*     */   
/*     */   @Deprecated
/*     */   default Terminal sysTerminal(String name, String type, boolean ansiPassThrough, Charset encoding, boolean nativeSignals, Terminal.SignalHandler signalHandler, boolean paused, SystemStream systemStream) throws IOException {
/*  99 */     return sysTerminal(name, type, ansiPassThrough, encoding, encoding, encoding, encoding, nativeSignals, signalHandler, paused, systemStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default Terminal sysTerminal(String name, String type, boolean ansiPassThrough, Charset encoding, Charset inputEncoding, Charset outputEncoding, boolean nativeSignals, Terminal.SignalHandler signalHandler, boolean paused, SystemStream systemStream) throws IOException {
/* 149 */     return sysTerminal(name, type, ansiPassThrough, encoding, inputEncoding, outputEncoding, outputEncoding, nativeSignals, signalHandler, paused, systemStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   Terminal sysTerminal(String paramString1, String paramString2, boolean paramBoolean1, Charset paramCharset1, Charset paramCharset2, Charset paramCharset3, Charset paramCharset4, boolean paramBoolean2, Terminal.SignalHandler paramSignalHandler, boolean paramBoolean3, SystemStream paramSystemStream) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default Terminal newTerminal(String name, String type, InputStream masterInput, OutputStream masterOutput, Charset encoding, Terminal.SignalHandler signalHandler, boolean paused, Attributes attributes, Size size) throws IOException {
/* 238 */     return newTerminal(name, type, masterInput, masterOutput, encoding, encoding, encoding, encoding, signalHandler, paused, attributes, size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default Terminal newTerminal(String name, String type, InputStream masterInput, OutputStream masterOutput, Charset encoding, Charset inputEncoding, Charset outputEncoding, Terminal.SignalHandler signalHandler, boolean paused, Attributes attributes, Size size) throws IOException {
/* 291 */     return newTerminal(name, type, masterInput, masterOutput, encoding, inputEncoding, outputEncoding, outputEncoding, signalHandler, paused, attributes, size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   Terminal newTerminal(String paramString1, String paramString2, InputStream paramInputStream, OutputStream paramOutputStream, Charset paramCharset1, Charset paramCharset2, Charset paramCharset3, Charset paramCharset4, Terminal.SignalHandler paramSignalHandler, boolean paramBoolean, Attributes paramAttributes, Size paramSize) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isSystemStream(SystemStream paramSystemStream);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String systemStreamName(SystemStream paramSystemStream);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int systemStreamWidth(SystemStream paramSystemStream);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static TerminalProvider load(String name) throws IOException {
/* 411 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/* 412 */     if (cl == null) {
/* 413 */       cl = TerminalProvider.class.getClassLoader();
/*     */     }
/* 415 */     InputStream is = cl.getResourceAsStream("META-INF/services/org/jline/terminal/provider/" + name);
/* 416 */     if (is != null) {
/* 417 */       Properties props = new Properties();
/*     */       try {
/* 419 */         props.load(is);
/* 420 */         String className = props.getProperty("class");
/* 421 */         if (className == null) {
/* 422 */           throw new IOException("No class defined in terminal provider file " + name);
/*     */         }
/* 424 */         Class<?> clazz = cl.loadClass(className);
/* 425 */         return clazz.getConstructor(new Class[0]).newInstance(new Object[0]);
/* 426 */       } catch (Exception e) {
/* 427 */         throw new IOException("Unable to load terminal provider " + name + ": " + e.getMessage(), e);
/*     */       } 
/*     */     } 
/* 430 */     throw new IOException("Unable to find terminal provider " + name);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\spi\TerminalProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */