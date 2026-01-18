/*     */ package org.bson.json;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import org.bson.BSONException;
/*     */ import org.bson.BsonInvalidOperationException;
/*     */ import org.bson.assertions.Assertions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StrictCharacterStreamJsonWriter
/*     */   implements StrictJsonWriter
/*     */ {
/*     */   private final Writer writer;
/*     */   private final StrictCharacterStreamJsonWriterSettings settings;
/*     */   
/*     */   private enum JsonContextType
/*     */   {
/*  34 */     TOP_LEVEL,
/*  35 */     DOCUMENT,
/*  36 */     ARRAY;
/*     */   }
/*     */   
/*     */   private enum State {
/*  40 */     INITIAL,
/*  41 */     NAME,
/*  42 */     VALUE,
/*  43 */     DONE;
/*     */   }
/*     */   
/*     */   private static class StrictJsonContext {
/*     */     private final StrictJsonContext parentContext;
/*     */     private final StrictCharacterStreamJsonWriter.JsonContextType contextType;
/*     */     private final String indentation;
/*     */     private boolean hasElements;
/*     */     
/*     */     StrictJsonContext(StrictJsonContext parentContext, StrictCharacterStreamJsonWriter.JsonContextType contextType, String indentChars) {
/*  53 */       this.parentContext = parentContext;
/*  54 */       this.contextType = contextType;
/*  55 */       this.indentation = (parentContext == null) ? indentChars : (parentContext.indentation + indentChars);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  61 */   private StrictJsonContext context = new StrictJsonContext(null, JsonContextType.TOP_LEVEL, "");
/*  62 */   private State state = State.INITIAL;
/*     */ 
/*     */   
/*     */   private int curLength;
/*     */ 
/*     */   
/*     */   private boolean isTruncated;
/*     */ 
/*     */ 
/*     */   
/*     */   public StrictCharacterStreamJsonWriter(Writer writer, StrictCharacterStreamJsonWriterSettings settings) {
/*  73 */     this.writer = writer;
/*  74 */     this.settings = settings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentLength() {
/*  83 */     return this.curLength;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeStartObject(String name) {
/*  88 */     writeName(name);
/*  89 */     writeStartObject();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeStartArray(String name) {
/*  94 */     writeName(name);
/*  95 */     writeStartArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeBoolean(String name, boolean value) {
/* 100 */     Assertions.notNull("name", name);
/* 101 */     writeName(name);
/* 102 */     writeBoolean(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeNumber(String name, String value) {
/* 107 */     Assertions.notNull("name", name);
/* 108 */     Assertions.notNull("value", value);
/* 109 */     writeName(name);
/* 110 */     writeNumber(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeString(String name, String value) {
/* 115 */     Assertions.notNull("name", name);
/* 116 */     Assertions.notNull("value", value);
/* 117 */     writeName(name);
/* 118 */     writeString(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeRaw(String name, String value) {
/* 123 */     Assertions.notNull("name", name);
/* 124 */     Assertions.notNull("value", value);
/* 125 */     writeName(name);
/* 126 */     writeRaw(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeNull(String name) {
/* 131 */     writeName(name);
/* 132 */     writeNull();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeName(String name) {
/* 137 */     Assertions.notNull("name", name);
/* 138 */     checkState(State.NAME);
/*     */     
/* 140 */     if (this.context.hasElements) {
/* 141 */       write(",");
/*     */     }
/* 143 */     if (this.settings.isIndent()) {
/* 144 */       write(this.settings.getNewLineCharacters());
/* 145 */       write(this.context.indentation);
/* 146 */     } else if (this.context.hasElements) {
/* 147 */       write(" ");
/*     */     } 
/* 149 */     writeStringHelper(name);
/* 150 */     write(": ");
/*     */     
/* 152 */     this.state = State.VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeBoolean(boolean value) {
/* 157 */     checkState(State.VALUE);
/* 158 */     preWriteValue();
/* 159 */     write(value ? "true" : "false");
/* 160 */     setNextState();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeNumber(String value) {
/* 165 */     Assertions.notNull("value", value);
/* 166 */     checkState(State.VALUE);
/* 167 */     preWriteValue();
/* 168 */     write(value);
/* 169 */     setNextState();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeString(String value) {
/* 174 */     Assertions.notNull("value", value);
/* 175 */     checkState(State.VALUE);
/* 176 */     preWriteValue();
/* 177 */     writeStringHelper(value);
/* 178 */     setNextState();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeRaw(String value) {
/* 183 */     Assertions.notNull("value", value);
/* 184 */     checkState(State.VALUE);
/* 185 */     preWriteValue();
/* 186 */     write(value);
/* 187 */     setNextState();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeNull() {
/* 192 */     checkState(State.VALUE);
/* 193 */     preWriteValue();
/* 194 */     write("null");
/* 195 */     setNextState();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeStartObject() {
/* 200 */     if (this.state != State.INITIAL && this.state != State.VALUE) {
/* 201 */       throw new BsonInvalidOperationException("Invalid state " + this.state);
/*     */     }
/* 203 */     preWriteValue();
/* 204 */     write("{");
/* 205 */     this.context = new StrictJsonContext(this.context, JsonContextType.DOCUMENT, this.settings.getIndentCharacters());
/* 206 */     this.state = State.NAME;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeStartArray() {
/* 211 */     preWriteValue();
/* 212 */     write("[");
/* 213 */     this.context = new StrictJsonContext(this.context, JsonContextType.ARRAY, this.settings.getIndentCharacters());
/* 214 */     this.state = State.VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEndObject() {
/* 219 */     checkState(State.NAME);
/*     */     
/* 221 */     if (this.settings.isIndent() && this.context.hasElements) {
/* 222 */       write(this.settings.getNewLineCharacters());
/* 223 */       write(this.context.parentContext.indentation);
/*     */     } 
/* 225 */     write("}");
/* 226 */     this.context = this.context.parentContext;
/* 227 */     if (this.context.contextType == JsonContextType.TOP_LEVEL) {
/* 228 */       this.state = State.DONE;
/*     */     } else {
/* 230 */       setNextState();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEndArray() {
/* 236 */     checkState(State.VALUE);
/*     */     
/* 238 */     if (this.context.contextType != JsonContextType.ARRAY) {
/* 239 */       throw new BsonInvalidOperationException("Can't end an array if not in an array");
/*     */     }
/*     */     
/* 242 */     if (this.settings.isIndent() && this.context.hasElements) {
/* 243 */       write(this.settings.getNewLineCharacters());
/* 244 */       write(this.context.parentContext.indentation);
/*     */     } 
/* 246 */     write("]");
/* 247 */     this.context = this.context.parentContext;
/* 248 */     if (this.context.contextType == JsonContextType.TOP_LEVEL) {
/* 249 */       this.state = State.DONE;
/*     */     } else {
/* 251 */       setNextState();
/*     */     } 
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
/*     */   public boolean isTruncated() {
/* 264 */     return this.isTruncated;
/*     */   }
/*     */   
/*     */   void flush() {
/*     */     try {
/* 269 */       this.writer.flush();
/* 270 */     } catch (IOException e) {
/* 271 */       throwBSONException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   Writer getWriter() {
/* 276 */     return this.writer;
/*     */   }
/*     */   
/*     */   private void preWriteValue() {
/* 280 */     if (this.context.contextType == JsonContextType.ARRAY) {
/* 281 */       if (this.context.hasElements) {
/* 282 */         write(",");
/*     */       }
/* 284 */       if (this.settings.isIndent()) {
/* 285 */         write(this.settings.getNewLineCharacters());
/* 286 */         write(this.context.indentation);
/* 287 */       } else if (this.context.hasElements) {
/* 288 */         write(" ");
/*     */       } 
/*     */     } 
/* 291 */     this.context.hasElements = true;
/*     */   }
/*     */   
/*     */   private void setNextState() {
/* 295 */     if (this.context.contextType == JsonContextType.ARRAY) {
/* 296 */       this.state = State.VALUE;
/*     */     } else {
/* 298 */       this.state = State.NAME;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeStringHelper(String str) {
/* 303 */     write('"');
/* 304 */     for (int i = 0; i < str.length(); i++) {
/* 305 */       char c = str.charAt(i);
/* 306 */       switch (c) {
/*     */         case '"':
/* 308 */           write("\\\"");
/*     */           break;
/*     */         case '\\':
/* 311 */           write("\\\\");
/*     */           break;
/*     */         case '\b':
/* 314 */           write("\\b");
/*     */           break;
/*     */         case '\f':
/* 317 */           write("\\f");
/*     */           break;
/*     */         case '\n':
/* 320 */           write("\\n");
/*     */           break;
/*     */         case '\r':
/* 323 */           write("\\r");
/*     */           break;
/*     */         case '\t':
/* 326 */           write("\\t");
/*     */           break;
/*     */         default:
/* 329 */           switch (Character.getType(c)) {
/*     */             case 1:
/*     */             case 2:
/*     */             case 3:
/*     */             case 5:
/*     */             case 9:
/*     */             case 10:
/*     */             case 11:
/*     */             case 12:
/*     */             case 20:
/*     */             case 21:
/*     */             case 22:
/*     */             case 23:
/*     */             case 24:
/*     */             case 25:
/*     */             case 26:
/*     */             case 27:
/*     */             case 28:
/*     */             case 29:
/*     */             case 30:
/* 349 */               write(c);
/*     */               break;
/*     */           } 
/* 352 */           write("\\u");
/* 353 */           write(Integer.toHexString((c & 0xF000) >> 12));
/* 354 */           write(Integer.toHexString((c & 0xF00) >> 8));
/* 355 */           write(Integer.toHexString((c & 0xF0) >> 4));
/* 356 */           write(Integer.toHexString(c & 0xF));
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 362 */     write('"');
/*     */   }
/*     */   
/*     */   private void write(String str) {
/*     */     try {
/* 367 */       if (this.settings.getMaxLength() == 0 || str.length() + this.curLength < this.settings.getMaxLength()) {
/* 368 */         this.writer.write(str);
/* 369 */         this.curLength += str.length();
/*     */       } else {
/* 371 */         this.writer.write(str.substring(0, this.settings.getMaxLength() - this.curLength));
/* 372 */         this.curLength = this.settings.getMaxLength();
/* 373 */         this.isTruncated = true;
/*     */       } 
/* 375 */     } catch (IOException e) {
/* 376 */       throwBSONException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void write(char c) {
/*     */     try {
/* 382 */       if (this.settings.getMaxLength() == 0 || this.curLength < this.settings.getMaxLength()) {
/* 383 */         this.writer.write(c);
/* 384 */         this.curLength++;
/*     */       } else {
/* 386 */         this.isTruncated = true;
/*     */       } 
/* 388 */     } catch (IOException e) {
/* 389 */       throwBSONException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkState(State requiredState) {
/* 394 */     if (this.state != requiredState) {
/* 395 */       throw new BsonInvalidOperationException("Invalid state " + this.state);
/*     */     }
/*     */   }
/*     */   
/*     */   private void throwBSONException(IOException e) {
/* 400 */     throw new BSONException("Wrapping IOException", e);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\StrictCharacterStreamJsonWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */