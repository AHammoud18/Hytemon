/*     */ package com.google.gson.internal;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonIOException;
/*     */ import com.google.gson.JsonNull;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import com.google.gson.internal.bind.TypeAdapters;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonToken;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import com.google.gson.stream.MalformedJsonException;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Streams
/*     */ {
/*     */   private Streams() {
/*  37 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public static JsonElement parse(JsonReader reader) throws JsonParseException {
/*  42 */     boolean isEmpty = true;
/*     */     try {
/*  44 */       JsonToken unused = reader.peek();
/*  45 */       isEmpty = false;
/*  46 */       return (JsonElement)TypeAdapters.JSON_ELEMENT.read(reader);
/*  47 */     } catch (EOFException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  52 */       if (isEmpty) {
/*  53 */         return (JsonElement)JsonNull.INSTANCE;
/*     */       }
/*     */       
/*  56 */       throw new JsonSyntaxException(e);
/*  57 */     } catch (MalformedJsonException e) {
/*  58 */       throw new JsonSyntaxException(e);
/*  59 */     } catch (IOException e) {
/*  60 */       throw new JsonIOException(e);
/*  61 */     } catch (NumberFormatException e) {
/*  62 */       throw new JsonSyntaxException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void write(JsonElement element, JsonWriter writer) throws IOException {
/*  68 */     TypeAdapters.JSON_ELEMENT.write(writer, element);
/*     */   }
/*     */   
/*     */   public static Writer writerForAppendable(Appendable appendable) {
/*  72 */     return (appendable instanceof Writer) ? (Writer)appendable : new AppendableWriter(appendable);
/*     */   }
/*     */   
/*     */   private static final class AppendableWriter
/*     */     extends Writer {
/*     */     private final Appendable appendable;
/*  78 */     private final CurrentWrite currentWrite = new CurrentWrite();
/*     */     
/*     */     AppendableWriter(Appendable appendable) {
/*  81 */       this.appendable = appendable;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void write(char[] chars, int offset, int length) throws IOException {
/*  87 */       this.currentWrite.setChars(chars);
/*  88 */       this.appendable.append(this.currentWrite, offset, offset + length);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void flush() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void write(int i) throws IOException {
/* 102 */       this.appendable.append((char)i);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void write(String str, int off, int len) throws IOException {
/* 108 */       Objects.requireNonNull(str);
/* 109 */       this.appendable.append(str, off, off + len);
/*     */     }
/*     */ 
/*     */     
/*     */     public Writer append(CharSequence csq) throws IOException {
/* 114 */       this.appendable.append(csq);
/* 115 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Writer append(CharSequence csq, int start, int end) throws IOException {
/* 120 */       this.appendable.append(csq, start, end);
/* 121 */       return this;
/*     */     }
/*     */     
/*     */     private static class CurrentWrite
/*     */       implements CharSequence
/*     */     {
/*     */       private char[] chars;
/*     */       
/*     */       void setChars(char[] chars) {
/* 130 */         this.chars = chars;
/* 131 */         this.cachedString = null;
/*     */       }
/*     */       private String cachedString;
/*     */       private CurrentWrite() {}
/*     */       public int length() {
/* 136 */         return this.chars.length;
/*     */       }
/*     */ 
/*     */       
/*     */       public char charAt(int i) {
/* 141 */         return this.chars[i];
/*     */       }
/*     */ 
/*     */       
/*     */       public CharSequence subSequence(int start, int end) {
/* 146 */         return new String(this.chars, start, end - start);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public String toString() {
/* 152 */         if (this.cachedString == null) {
/* 153 */           this.cachedString = new String(this.chars);
/*     */         }
/* 155 */         return this.cachedString;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\Streams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */