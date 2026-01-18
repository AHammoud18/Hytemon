/*     */ package org.jline.reader.impl.completer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.DirectoryStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.List;
/*     */ import org.jline.reader.Candidate;
/*     */ import org.jline.reader.Completer;
/*     */ import org.jline.reader.LineReader;
/*     */ import org.jline.reader.ParsedLine;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.utils.AttributedStringBuilder;
/*     */ import org.jline.utils.AttributedStyle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class FileNameCompleter
/*     */   implements Completer
/*     */ {
/*     */   public void complete(LineReader reader, ParsedLine commandLine, List<Candidate> candidates) {
/*     */     Path current;
/*     */     String curBuf;
/*  49 */     assert commandLine != null;
/*  50 */     assert candidates != null;
/*     */     
/*  52 */     String buffer = commandLine.word().substring(0, commandLine.wordCursor());
/*     */ 
/*     */ 
/*     */     
/*  56 */     String sep = getUserDir().getFileSystem().getSeparator();
/*  57 */     int lastSep = buffer.lastIndexOf(sep);
/*  58 */     if (lastSep >= 0) {
/*  59 */       curBuf = buffer.substring(0, lastSep + 1);
/*  60 */       if (curBuf.startsWith("~")) {
/*  61 */         if (curBuf.startsWith("~" + sep)) {
/*  62 */           current = getUserHome().resolve(curBuf.substring(2));
/*     */         } else {
/*  64 */           current = getUserHome().getParent().resolve(curBuf.substring(1));
/*     */         } 
/*     */       } else {
/*  67 */         current = getUserDir().resolve(curBuf);
/*     */       } 
/*     */     } else {
/*  70 */       curBuf = "";
/*  71 */       current = getUserDir();
/*     */     }  
/*  73 */     try { DirectoryStream<Path> directoryStream = Files.newDirectoryStream(current, this::accept); 
/*  74 */       try { directoryStream.forEach(p -> {
/*     */               String value = curBuf + p.getFileName().toString();
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               if (Files.isDirectory(p, new java.nio.file.LinkOption[0])) {
/*     */                 candidates.add(new Candidate(value + (reader.isSet(LineReader.Option.AUTO_PARAM_SLASH) ? sep : ""), getDisplay(reader.getTerminal(), p), null, null, reader.isSet(LineReader.Option.AUTO_REMOVE_SLASH) ? sep : null, null, false));
/*     */               } else {
/*     */                 candidates.add(new Candidate(value, getDisplay(reader.getTerminal(), p), null, null, null, null, true));
/*     */               } 
/*     */             });
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  90 */         if (directoryStream != null) directoryStream.close();  } catch (Throwable throwable) { if (directoryStream != null) try { directoryStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean accept(Path path) {
/*     */     try {
/*  97 */       return !Files.isHidden(path);
/*  98 */     } catch (IOException e) {
/*  99 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Path getUserDir() {
/* 104 */     return Paths.get(System.getProperty("user.dir"), new String[0]);
/*     */   }
/*     */   
/*     */   protected Path getUserHome() {
/* 108 */     return Paths.get(System.getProperty("user.home"), new String[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDisplay(Terminal terminal, Path p) {
/* 113 */     String name = p.getFileName().toString();
/* 114 */     if (Files.isDirectory(p, new java.nio.file.LinkOption[0])) {
/* 115 */       AttributedStringBuilder sb = new AttributedStringBuilder();
/* 116 */       sb.styled(AttributedStyle.BOLD.foreground(1), name);
/* 117 */       sb.append("/");
/* 118 */       name = sb.toAnsi(terminal);
/* 119 */     } else if (Files.isSymbolicLink(p)) {
/* 120 */       AttributedStringBuilder sb = new AttributedStringBuilder();
/* 121 */       sb.styled(AttributedStyle.BOLD.foreground(1), name);
/* 122 */       sb.append("@");
/* 123 */       name = sb.toAnsi(terminal);
/*     */     } 
/* 125 */     return name;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\impl\completer\FileNameCompleter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */