/*     */ package org.jline.terminal.impl;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.terminal.Size;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.terminal.spi.SystemStream;
/*     */ import org.jline.terminal.spi.TerminalProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DumbTerminalProvider
/*     */   implements TerminalProvider
/*     */ {
/*     */   public String name() {
/*  58 */     return "dumb";
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
/*     */   public Terminal sysTerminal(String name, String type, boolean ansiPassThrough, Charset encoding, Charset inputEncoding, Charset outputEncoding, boolean nativeSignals, Terminal.SignalHandler signalHandler, boolean paused, SystemStream systemStream) throws IOException {
/*  74 */     return (Terminal)new DumbTerminal(this, systemStream, name, type, new FileInputStream(FileDescriptor.in), new FileOutputStream(
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  80 */           (systemStream == SystemStream.Error) ? FileDescriptor.err : FileDescriptor.out), encoding, inputEncoding, outputEncoding, signalHandler);
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
/*     */   public Terminal sysTerminal(String name, String type, boolean ansiPassThrough, Charset encoding, Charset stdinEncoding, Charset stdoutEncoding, Charset stderrEncoding, boolean nativeSignals, Terminal.SignalHandler signalHandler, boolean paused, SystemStream systemStream) throws IOException {
/* 103 */     Charset outputEncoding = (systemStream == SystemStream.Error) ? stderrEncoding : stdoutEncoding;
/* 104 */     return sysTerminal(name, type, ansiPassThrough, encoding, stdinEncoding, outputEncoding, nativeSignals, signalHandler, paused, systemStream);
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
/*     */   public Terminal newTerminal(String name, String type, InputStream masterInput, OutputStream masterOutput, Charset encoding, Charset inputEncoding, Charset outputEncoding, Terminal.SignalHandler signalHandler, boolean paused, Attributes attributes, Size size) throws IOException {
/* 131 */     throw new UnsupportedOperationException();
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
/*     */   public Terminal newTerminal(String name, String type, InputStream masterInput, OutputStream masterOutput, Charset encoding, Charset stdinEncoding, Charset stdoutEncoding, Charset stderrEncoding, Terminal.SignalHandler signalHandler, boolean paused, Attributes attributes, Size size) throws IOException {
/* 150 */     return newTerminal(name, type, masterInput, masterOutput, encoding, stdinEncoding, stdoutEncoding, signalHandler, paused, attributes, size);
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
/*     */   public boolean isSystemStream(SystemStream stream) {
/* 166 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String systemStreamName(SystemStream stream) {
/* 171 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int systemStreamWidth(SystemStream stream) {
/* 176 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 181 */     return "TerminalProvider[" + name() + "]";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\DumbTerminalProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */