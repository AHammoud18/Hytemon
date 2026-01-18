/*      */ package com.google.protobuf;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.math.BigInteger;
/*      */ import java.nio.CharBuffer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.logging.Logger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class TextFormat
/*      */ {
/*   39 */   private static final Logger logger = Logger.getLogger(TextFormat.class.getName());
/*      */   
/*      */   private static final String DEBUG_STRING_SILENT_MARKER = " \t ";
/*      */   
/*      */   private static final String ENABLE_INSERT_SILENT_MARKER_ENV_NAME = "SILENT_MARKER_INSERTION_ENABLED";
/*      */   
/*   45 */   private static final boolean ENABLE_INSERT_SILENT_MARKER = ((String)System.getenv().getOrDefault("SILENT_MARKER_INSERTION_ENABLED", "false")).equals("true");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String REDACTED_MARKER = "[REDACTED]";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static String shortDebugString(MessageOrBuilder message) {
/*   58 */     return printer()
/*   59 */       .emittingSingleLine(true)
/*   60 */       .printToString(message, Printer.FieldReporterLevel.SHORT_DEBUG_STRING);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void printUnknownFieldValue(int tag, Object value, Appendable output) throws IOException {
/*   74 */     printUnknownFieldValue(tag, value, setSingleLineOutput(output, false), false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void printUnknownFieldValue(int tag, Object value, TextGenerator generator, boolean redact) throws IOException {
/*   80 */     switch (WireFormat.getTagWireType(tag)) {
/*      */       case 0:
/*   82 */         generator.print(unsignedToString(((Long)value).longValue()));
/*      */         return;
/*      */       case 5:
/*   85 */         generator.print(String.format((Locale)null, "0x%08x", new Object[] { value }));
/*      */         return;
/*      */       case 1:
/*   88 */         generator.print(String.format((Locale)null, "0x%016x", new Object[] { value }));
/*      */         return;
/*      */       
/*      */       case 2:
/*      */         try {
/*   93 */           UnknownFieldSet message = UnknownFieldSet.parseFrom((ByteString)value);
/*   94 */           generator.print("{");
/*   95 */           generator.eol();
/*   96 */           generator.indent();
/*   97 */           Printer.printUnknownFields(message, generator, redact);
/*   98 */           generator.outdent();
/*   99 */           generator.print("}");
/*  100 */         } catch (InvalidProtocolBufferException e) {
/*      */           
/*  102 */           generator.print("\"");
/*  103 */           generator.print(escapeBytes((ByteString)value));
/*  104 */           generator.print("\"");
/*      */         } 
/*      */         return;
/*      */       case 3:
/*  108 */         Printer.printUnknownFields((UnknownFieldSet)value, generator, redact);
/*      */         return;
/*      */     } 
/*  111 */     throw new IllegalArgumentException("Bad tag: " + tag);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Printer printer() {
/*  117 */     return Printer.DEFAULT_TEXT_FORMAT;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Printer debugFormatPrinter() {
/*  122 */     return Printer.DEFAULT_DEBUG_FORMAT;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Printer defaultFormatPrinter() {
/*  127 */     return Printer.DEFAULT_FORMAT;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class Printer
/*      */   {
/*  134 */     private static final Printer DEFAULT_TEXT_FORMAT = new Printer(true, false, 
/*      */ 
/*      */ 
/*      */         
/*  138 */         TypeRegistry.getEmptyTypeRegistry(), 
/*  139 */         ExtensionRegistryLite.getEmptyRegistry(), false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  144 */     private static final Printer DEFAULT_DEBUG_FORMAT = new Printer(true, false, 
/*      */ 
/*      */ 
/*      */         
/*  148 */         TypeRegistry.getEmptyTypeRegistry(), 
/*  149 */         ExtensionRegistryLite.getEmptyRegistry(), true, false);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  154 */     private static final Printer DEFAULT_FORMAT = (new Printer(true, false, 
/*      */ 
/*      */ 
/*      */         
/*  158 */         TypeRegistry.getEmptyTypeRegistry(), 
/*  159 */         ExtensionRegistryLite.getEmptyRegistry(), false, false))
/*      */ 
/*      */       
/*  162 */       .setInsertSilentMarker(TextFormat.ENABLE_INSERT_SILENT_MARKER); private final boolean escapeNonAscii; private final boolean useShortRepeatedPrimitives; private final TypeRegistry typeRegistry; private final ExtensionRegistryLite extensionRegistry; private final boolean enablingSafeDebugFormat; private final boolean singleLine; private boolean insertSilentMarker;
/*      */     
/*      */     static Printer getOutputModePrinter() {
/*  165 */       if (ProtobufToStringOutput.isDefaultFormat())
/*  166 */         return TextFormat.defaultFormatPrinter(); 
/*  167 */       if (ProtobufToStringOutput.shouldOutputDebugFormat()) {
/*  168 */         return TextFormat.debugFormatPrinter();
/*      */       }
/*  170 */       return TextFormat.printer();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     enum FieldReporterLevel
/*      */     {
/*  180 */       REPORT_ALL(0),
/*  181 */       TEXT_GENERATOR(1),
/*  182 */       PRINT(2),
/*  183 */       PRINTER_PRINT_TO_STRING(3),
/*  184 */       TEXTFORMAT_PRINT_TO_STRING(4),
/*  185 */       PRINT_UNICODE(5),
/*  186 */       SHORT_DEBUG_STRING(6),
/*  187 */       LEGACY_MULTILINE(7),
/*  188 */       LEGACY_SINGLE_LINE(8),
/*  189 */       DEBUG_MULTILINE(9),
/*  190 */       DEBUG_SINGLE_LINE(10),
/*  191 */       ABSTRACT_TO_STRING(11),
/*  192 */       ABSTRACT_BUILDER_TO_STRING(12),
/*  193 */       ABSTRACT_MUTABLE_TO_STRING(13),
/*  194 */       REPORT_NONE(14);
/*      */       private final int index;
/*      */       
/*      */       FieldReporterLevel(int index) {
/*  198 */         this.index = index;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @CanIgnoreReturnValue
/*      */     private Printer setInsertSilentMarker(boolean insertSilentMarker) {
/*  223 */       this.insertSilentMarker = insertSilentMarker;
/*  224 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  229 */     private static final ThreadLocal<FieldReporterLevel> sensitiveFieldReportingLevel = new ThreadLocal<FieldReporterLevel>()
/*      */       {
/*      */         protected TextFormat.Printer.FieldReporterLevel initialValue()
/*      */         {
/*  233 */           return TextFormat.Printer.FieldReporterLevel.ABSTRACT_TO_STRING;
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Printer(boolean escapeNonAscii, boolean useShortRepeatedPrimitives, TypeRegistry typeRegistry, ExtensionRegistryLite extensionRegistry, boolean enablingSafeDebugFormat, boolean singleLine) {
/*  244 */       this.escapeNonAscii = escapeNonAscii;
/*  245 */       this.useShortRepeatedPrimitives = useShortRepeatedPrimitives;
/*  246 */       this.typeRegistry = typeRegistry;
/*  247 */       this.extensionRegistry = extensionRegistry;
/*  248 */       this.enablingSafeDebugFormat = enablingSafeDebugFormat;
/*  249 */       this.singleLine = singleLine;
/*  250 */       this.insertSilentMarker = false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Printer escapingNonAscii(boolean escapeNonAscii) {
/*  263 */       return new Printer(escapeNonAscii, this.useShortRepeatedPrimitives, this.typeRegistry, this.extensionRegistry, this.enablingSafeDebugFormat, this.singleLine);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Printer usingTypeRegistry(TypeRegistry typeRegistry) {
/*  279 */       if (this.typeRegistry != TypeRegistry.getEmptyTypeRegistry()) {
/*  280 */         throw new IllegalArgumentException("Only one typeRegistry is allowed.");
/*      */       }
/*  282 */       return new Printer(this.escapeNonAscii, this.useShortRepeatedPrimitives, typeRegistry, this.extensionRegistry, this.enablingSafeDebugFormat, this.singleLine);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Printer usingExtensionRegistry(ExtensionRegistryLite extensionRegistry) {
/*  298 */       if (this.extensionRegistry != ExtensionRegistryLite.getEmptyRegistry()) {
/*  299 */         throw new IllegalArgumentException("Only one extensionRegistry is allowed.");
/*      */       }
/*  301 */       return new Printer(this.escapeNonAscii, this.useShortRepeatedPrimitives, this.typeRegistry, extensionRegistry, this.enablingSafeDebugFormat, this.singleLine);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Printer enablingSafeDebugFormat(boolean enablingSafeDebugFormat) {
/*  320 */       return new Printer(this.escapeNonAscii, this.useShortRepeatedPrimitives, this.typeRegistry, this.extensionRegistry, enablingSafeDebugFormat, this.singleLine);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Printer usingShortRepeatedPrimitives(boolean useShortRepeatedPrimitives) {
/*  338 */       return new Printer(this.escapeNonAscii, useShortRepeatedPrimitives, this.typeRegistry, this.extensionRegistry, this.enablingSafeDebugFormat, this.singleLine);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Printer emittingSingleLine(boolean singleLine) {
/*  355 */       return new Printer(this.escapeNonAscii, this.useShortRepeatedPrimitives, this.typeRegistry, this.extensionRegistry, this.enablingSafeDebugFormat, singleLine);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void setSensitiveFieldReportingLevel(FieldReporterLevel level) {
/*  365 */       sensitiveFieldReportingLevel.set(level);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void print(MessageOrBuilder message, Appendable output) throws IOException {
/*  374 */       print(message, output, FieldReporterLevel.PRINT);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void print(MessageOrBuilder message, Appendable output, FieldReporterLevel level) throws IOException {
/*  380 */       TextFormat.TextGenerator generator = TextFormat.setSingleLineOutput(output, this.singleLine, message
/*      */ 
/*      */           
/*  383 */           .getDescriptorForType(), level, this.insertSilentMarker);
/*      */ 
/*      */       
/*  386 */       print(message, generator);
/*      */     }
/*      */ 
/*      */     
/*      */     public void print(UnknownFieldSet fields, Appendable output) throws IOException {
/*  391 */       printUnknownFields(fields, TextFormat
/*  392 */           .setSingleLineOutput(output, this.singleLine), this.enablingSafeDebugFormat);
/*      */     }
/*      */ 
/*      */     
/*      */     private void print(MessageOrBuilder message, TextFormat.TextGenerator generator) throws IOException {
/*  397 */       if (message.getDescriptorForType().getFullName().equals("google.protobuf.Any") && 
/*  398 */         printAny(message, generator)) {
/*      */         return;
/*      */       }
/*  401 */       printMessage(message, generator);
/*      */     }
/*      */     
/*      */     private void applyUnstablePrefix(Appendable output) {
/*      */       try {
/*  406 */         output.append("");
/*  407 */       } catch (IOException e) {
/*  408 */         throw new IllegalStateException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean printAny(MessageOrBuilder message, TextFormat.TextGenerator generator) throws IOException {
/*  419 */       Descriptors.Descriptor messageType = message.getDescriptorForType();
/*  420 */       Descriptors.FieldDescriptor typeUrlField = messageType.findFieldByNumber(1);
/*  421 */       Descriptors.FieldDescriptor valueField = messageType.findFieldByNumber(2);
/*  422 */       if (typeUrlField == null || typeUrlField
/*  423 */         .getType() != Descriptors.FieldDescriptor.Type.STRING || valueField == null || valueField
/*      */         
/*  425 */         .getType() != Descriptors.FieldDescriptor.Type.BYTES)
/*      */       {
/*      */         
/*  428 */         return false;
/*      */       }
/*  430 */       String typeUrl = (String)message.getField(typeUrlField);
/*      */ 
/*      */       
/*  433 */       if (typeUrl.isEmpty()) {
/*  434 */         return false;
/*      */       }
/*  436 */       Object value = message.getField(valueField);
/*      */       
/*  438 */       Message.Builder contentBuilder = null;
/*      */       try {
/*  440 */         Descriptors.Descriptor contentType = this.typeRegistry.getDescriptorForTypeUrl(typeUrl);
/*  441 */         if (contentType == null) {
/*  442 */           return false;
/*      */         }
/*  444 */         contentBuilder = DynamicMessage.getDefaultInstance(contentType).newBuilderForType();
/*  445 */         contentBuilder.mergeFrom((ByteString)value, this.extensionRegistry);
/*  446 */       } catch (InvalidProtocolBufferException e) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  451 */         return false;
/*      */       } 
/*  453 */       generator.print("[");
/*  454 */       generator.print(typeUrl);
/*  455 */       generator.print("]");
/*  456 */       generator.maybePrintSilentMarker();
/*  457 */       generator.print("{");
/*  458 */       generator.eol();
/*  459 */       generator.indent();
/*  460 */       print(contentBuilder, generator);
/*  461 */       generator.outdent();
/*  462 */       generator.print("}");
/*  463 */       generator.eol();
/*  464 */       return true;
/*      */     }
/*      */     
/*      */     public String printFieldToString(Descriptors.FieldDescriptor field, Object value) {
/*      */       try {
/*  469 */         StringBuilder text = new StringBuilder();
/*  470 */         if (this.enablingSafeDebugFormat) {
/*  471 */           applyUnstablePrefix(text);
/*      */         }
/*  473 */         printField(field, value, text);
/*  474 */         return text.toString();
/*  475 */       } catch (IOException e) {
/*  476 */         throw new IllegalStateException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void printField(Descriptors.FieldDescriptor field, Object value, Appendable output) throws IOException {
/*  482 */       printField(field, value, TextFormat.setSingleLineOutput(output, this.singleLine));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void printField(Descriptors.FieldDescriptor field, Object value, TextFormat.TextGenerator generator) throws IOException {
/*  489 */       if (field.isMapField()) {
/*  490 */         List<MapEntryAdapter> adapters = new ArrayList<>();
/*  491 */         for (Object entry : value) {
/*  492 */           adapters.add(new MapEntryAdapter(entry, field));
/*      */         }
/*  494 */         Collections.sort(adapters);
/*  495 */         for (MapEntryAdapter adapter : adapters) {
/*  496 */           printSingleField(field, adapter.getEntry(), generator);
/*      */         }
/*  498 */       } else if (field.isRepeated()) {
/*  499 */         if (this.useShortRepeatedPrimitives && field.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/*  500 */           printShortRepeatedField(field, value, generator);
/*      */         } else {
/*  502 */           for (Object element : value) {
/*  503 */             printSingleField(field, element, generator);
/*      */           }
/*      */         } 
/*      */       } else {
/*  507 */         printSingleField(field, value, generator);
/*      */       } 
/*      */     }
/*      */     
/*      */     static class MapEntryAdapter
/*      */       implements Comparable<MapEntryAdapter> {
/*      */       private Object entry;
/*      */       private Message messageEntry;
/*      */       private final Descriptors.FieldDescriptor keyField;
/*      */       
/*      */       MapEntryAdapter(Object entry, Descriptors.FieldDescriptor fieldDescriptor) {
/*  518 */         if (entry instanceof Message) {
/*  519 */           this.messageEntry = (Message)entry;
/*      */         } else {
/*  521 */           this.entry = entry;
/*      */         } 
/*  523 */         this.keyField = fieldDescriptor.getMessageType().findFieldByName("key");
/*      */       }
/*      */       
/*      */       Object getKey() {
/*  527 */         if (this.messageEntry != null && this.keyField != null) {
/*  528 */           return this.messageEntry.getField(this.keyField);
/*      */         }
/*  530 */         return null;
/*      */       }
/*      */       
/*      */       Object getEntry() {
/*  534 */         if (this.messageEntry != null) {
/*  535 */           return this.messageEntry;
/*      */         }
/*  537 */         return this.entry;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int compareTo(MapEntryAdapter b) {
/*      */         // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: invokevirtual getKey : ()Ljava/lang/Object;
/*      */         //   4: astore_2
/*      */         //   5: aload_1
/*      */         //   6: invokevirtual getKey : ()Ljava/lang/Object;
/*      */         //   9: astore_3
/*      */         //   10: aload_2
/*      */         //   11: ifnonnull -> 20
/*      */         //   14: aload_3
/*      */         //   15: ifnonnull -> 20
/*      */         //   18: iconst_0
/*      */         //   19: ireturn
/*      */         //   20: aload_2
/*      */         //   21: ifnonnull -> 26
/*      */         //   24: iconst_m1
/*      */         //   25: ireturn
/*      */         //   26: aload_3
/*      */         //   27: ifnonnull -> 32
/*      */         //   30: iconst_1
/*      */         //   31: ireturn
/*      */         //   32: getstatic com/google/protobuf/TextFormat$1.$SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$JavaType : [I
/*      */         //   35: aload_0
/*      */         //   36: getfield keyField : Lcom/google/protobuf/Descriptors$FieldDescriptor;
/*      */         //   39: invokevirtual getJavaType : ()Lcom/google/protobuf/Descriptors$FieldDescriptor$JavaType;
/*      */         //   42: invokevirtual ordinal : ()I
/*      */         //   45: iaload
/*      */         //   46: tableswitch default -> 124, 1 -> 76, 2 -> 88, 3 -> 100, 4 -> 112
/*      */         //   76: aload_2
/*      */         //   77: checkcast java/lang/Boolean
/*      */         //   80: aload_3
/*      */         //   81: checkcast java/lang/Boolean
/*      */         //   84: invokevirtual compareTo : (Ljava/lang/Boolean;)I
/*      */         //   87: ireturn
/*      */         //   88: aload_2
/*      */         //   89: checkcast java/lang/Long
/*      */         //   92: aload_3
/*      */         //   93: checkcast java/lang/Long
/*      */         //   96: invokevirtual compareTo : (Ljava/lang/Long;)I
/*      */         //   99: ireturn
/*      */         //   100: aload_2
/*      */         //   101: checkcast java/lang/Integer
/*      */         //   104: aload_3
/*      */         //   105: checkcast java/lang/Integer
/*      */         //   108: invokevirtual compareTo : (Ljava/lang/Integer;)I
/*      */         //   111: ireturn
/*      */         //   112: aload_2
/*      */         //   113: checkcast java/lang/String
/*      */         //   116: aload_3
/*      */         //   117: checkcast java/lang/String
/*      */         //   120: invokevirtual compareTo : (Ljava/lang/String;)I
/*      */         //   123: ireturn
/*      */         //   124: iconst_0
/*      */         //   125: ireturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #542	-> 0
/*      */         //   #543	-> 5
/*      */         //   #544	-> 10
/*      */         //   #545	-> 18
/*      */         //   #546	-> 20
/*      */         //   #547	-> 24
/*      */         //   #548	-> 26
/*      */         //   #549	-> 30
/*      */         //   #551	-> 32
/*      */         //   #553	-> 76
/*      */         //   #555	-> 88
/*      */         //   #557	-> 100
/*      */         //   #559	-> 112
/*      */         //   #561	-> 124
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	126	0	this	Lcom/google/protobuf/TextFormat$Printer$MapEntryAdapter;
/*      */         //   0	126	1	b	Lcom/google/protobuf/TextFormat$Printer$MapEntryAdapter;
/*      */         //   5	121	2	aKey	Ljava/lang/Object;
/*      */         //   10	116	3	bKey	Ljava/lang/Object;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void printFieldValue(Descriptors.FieldDescriptor field, Object value, Appendable output) throws IOException {
/*  579 */       printFieldValue(field, value, TextFormat.setSingleLineOutput(output, this.singleLine));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void printFieldValue(Descriptors.FieldDescriptor field, Object value, TextFormat.TextGenerator generator) throws IOException {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: aload_3
/*      */       //   3: invokespecial shouldRedact : (Lcom/google/protobuf/Descriptors$FieldDescriptor;Lcom/google/protobuf/TextFormat$TextGenerator;)Z
/*      */       //   6: ifeq -> 31
/*      */       //   9: aload_3
/*      */       //   10: ldc_w '[REDACTED]'
/*      */       //   13: invokevirtual print : (Ljava/lang/CharSequence;)V
/*      */       //   16: aload_1
/*      */       //   17: invokevirtual getJavaType : ()Lcom/google/protobuf/Descriptors$FieldDescriptor$JavaType;
/*      */       //   20: getstatic com/google/protobuf/Descriptors$FieldDescriptor$JavaType.MESSAGE : Lcom/google/protobuf/Descriptors$FieldDescriptor$JavaType;
/*      */       //   23: if_acmpne -> 30
/*      */       //   26: aload_3
/*      */       //   27: invokevirtual eol : ()V
/*      */       //   30: return
/*      */       //   31: getstatic com/google/protobuf/TextFormat$1.$SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type : [I
/*      */       //   34: aload_1
/*      */       //   35: invokevirtual getType : ()Lcom/google/protobuf/Descriptors$FieldDescriptor$Type;
/*      */       //   38: invokevirtual ordinal : ()I
/*      */       //   41: iaload
/*      */       //   42: tableswitch default -> 386, 1 -> 128, 2 -> 128, 3 -> 128, 4 -> 142, 5 -> 142, 6 -> 142, 7 -> 156, 8 -> 170, 9 -> 184, 10 -> 198, 11 -> 198, 12 -> 215, 13 -> 215, 14 -> 232, 15 -> 286, 16 -> 335, 17 -> 377, 18 -> 377
/*      */       //   128: aload_3
/*      */       //   129: aload_2
/*      */       //   130: checkcast java/lang/Integer
/*      */       //   133: invokevirtual toString : ()Ljava/lang/String;
/*      */       //   136: invokevirtual print : (Ljava/lang/CharSequence;)V
/*      */       //   139: goto -> 386
/*      */       //   142: aload_3
/*      */       //   143: aload_2
/*      */       //   144: checkcast java/lang/Long
/*      */       //   147: invokevirtual toString : ()Ljava/lang/String;
/*      */       //   150: invokevirtual print : (Ljava/lang/CharSequence;)V
/*      */       //   153: goto -> 386
/*      */       //   156: aload_3
/*      */       //   157: aload_2
/*      */       //   158: checkcast java/lang/Boolean
/*      */       //   161: invokevirtual toString : ()Ljava/lang/String;
/*      */       //   164: invokevirtual print : (Ljava/lang/CharSequence;)V
/*      */       //   167: goto -> 386
/*      */       //   170: aload_3
/*      */       //   171: aload_2
/*      */       //   172: checkcast java/lang/Float
/*      */       //   175: invokevirtual toString : ()Ljava/lang/String;
/*      */       //   178: invokevirtual print : (Ljava/lang/CharSequence;)V
/*      */       //   181: goto -> 386
/*      */       //   184: aload_3
/*      */       //   185: aload_2
/*      */       //   186: checkcast java/lang/Double
/*      */       //   189: invokevirtual toString : ()Ljava/lang/String;
/*      */       //   192: invokevirtual print : (Ljava/lang/CharSequence;)V
/*      */       //   195: goto -> 386
/*      */       //   198: aload_3
/*      */       //   199: aload_2
/*      */       //   200: checkcast java/lang/Integer
/*      */       //   203: invokevirtual intValue : ()I
/*      */       //   206: invokestatic unsignedToString : (I)Ljava/lang/String;
/*      */       //   209: invokevirtual print : (Ljava/lang/CharSequence;)V
/*      */       //   212: goto -> 386
/*      */       //   215: aload_3
/*      */       //   216: aload_2
/*      */       //   217: checkcast java/lang/Long
/*      */       //   220: invokevirtual longValue : ()J
/*      */       //   223: invokestatic unsignedToString : (J)Ljava/lang/String;
/*      */       //   226: invokevirtual print : (Ljava/lang/CharSequence;)V
/*      */       //   229: goto -> 386
/*      */       //   232: aload_3
/*      */       //   233: ldc_w '"'
/*      */       //   236: invokevirtual print : (Ljava/lang/CharSequence;)V
/*      */       //   239: aload_3
/*      */       //   240: aload_0
/*      */       //   241: getfield escapeNonAscii : Z
/*      */       //   244: ifeq -> 257
/*      */       //   247: aload_2
/*      */       //   248: checkcast java/lang/String
/*      */       //   251: invokestatic escapeText : (Ljava/lang/String;)Ljava/lang/String;
/*      */       //   254: goto -> 273
/*      */       //   257: aload_2
/*      */       //   258: checkcast java/lang/String
/*      */       //   261: invokestatic escapeDoubleQuotesAndBackslashes : (Ljava/lang/String;)Ljava/lang/String;
/*      */       //   264: ldc_w '\\n'
/*      */       //   267: ldc_w '\n'
/*      */       //   270: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
/*      */       //   273: invokevirtual print : (Ljava/lang/CharSequence;)V
/*      */       //   276: aload_3
/*      */       //   277: ldc_w '"'
/*      */       //   280: invokevirtual print : (Ljava/lang/CharSequence;)V
/*      */       //   283: goto -> 386
/*      */       //   286: aload_3
/*      */       //   287: ldc_w '"'
/*      */       //   290: invokevirtual print : (Ljava/lang/CharSequence;)V
/*      */       //   293: aload_2
/*      */       //   294: instanceof com/google/protobuf/ByteString
/*      */       //   297: ifeq -> 314
/*      */       //   300: aload_3
/*      */       //   301: aload_2
/*      */       //   302: checkcast com/google/protobuf/ByteString
/*      */       //   305: invokestatic escapeBytes : (Lcom/google/protobuf/ByteString;)Ljava/lang/String;
/*      */       //   308: invokevirtual print : (Ljava/lang/CharSequence;)V
/*      */       //   311: goto -> 325
/*      */       //   314: aload_3
/*      */       //   315: aload_2
/*      */       //   316: checkcast [B
/*      */       //   319: invokestatic escapeBytes : ([B)Ljava/lang/String;
/*      */       //   322: invokevirtual print : (Ljava/lang/CharSequence;)V
/*      */       //   325: aload_3
/*      */       //   326: ldc_w '"'
/*      */       //   329: invokevirtual print : (Ljava/lang/CharSequence;)V
/*      */       //   332: goto -> 386
/*      */       //   335: aload_2
/*      */       //   336: checkcast com/google/protobuf/Descriptors$EnumValueDescriptor
/*      */       //   339: invokevirtual getIndex : ()I
/*      */       //   342: iconst_m1
/*      */       //   343: if_icmpne -> 363
/*      */       //   346: aload_3
/*      */       //   347: aload_2
/*      */       //   348: checkcast com/google/protobuf/Descriptors$EnumValueDescriptor
/*      */       //   351: invokevirtual getNumber : ()I
/*      */       //   354: invokestatic toString : (I)Ljava/lang/String;
/*      */       //   357: invokevirtual print : (Ljava/lang/CharSequence;)V
/*      */       //   360: goto -> 386
/*      */       //   363: aload_3
/*      */       //   364: aload_2
/*      */       //   365: checkcast com/google/protobuf/Descriptors$EnumValueDescriptor
/*      */       //   368: invokevirtual getName : ()Ljava/lang/String;
/*      */       //   371: invokevirtual print : (Ljava/lang/CharSequence;)V
/*      */       //   374: goto -> 386
/*      */       //   377: aload_0
/*      */       //   378: aload_2
/*      */       //   379: checkcast com/google/protobuf/MessageOrBuilder
/*      */       //   382: aload_3
/*      */       //   383: invokespecial print : (Lcom/google/protobuf/MessageOrBuilder;Lcom/google/protobuf/TextFormat$TextGenerator;)V
/*      */       //   386: return
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #585	-> 0
/*      */       //   #586	-> 9
/*      */       //   #587	-> 16
/*      */       //   #588	-> 26
/*      */       //   #590	-> 30
/*      */       //   #592	-> 31
/*      */       //   #596	-> 128
/*      */       //   #597	-> 139
/*      */       //   #602	-> 142
/*      */       //   #603	-> 153
/*      */       //   #606	-> 156
/*      */       //   #607	-> 167
/*      */       //   #610	-> 170
/*      */       //   #611	-> 181
/*      */       //   #614	-> 184
/*      */       //   #615	-> 195
/*      */       //   #619	-> 198
/*      */       //   #620	-> 212
/*      */       //   #624	-> 215
/*      */       //   #625	-> 229
/*      */       //   #628	-> 232
/*      */       //   #629	-> 239
/*      */       //   #630	-> 240
/*      */       //   #631	-> 247
/*      */       //   #632	-> 257
/*      */       //   #629	-> 273
/*      */       //   #633	-> 276
/*      */       //   #634	-> 283
/*      */       //   #637	-> 286
/*      */       //   #638	-> 293
/*      */       //   #639	-> 300
/*      */       //   #641	-> 314
/*      */       //   #643	-> 325
/*      */       //   #644	-> 332
/*      */       //   #647	-> 335
/*      */       //   #649	-> 346
/*      */       //   #651	-> 363
/*      */       //   #653	-> 374
/*      */       //   #657	-> 377
/*      */       //   #660	-> 386
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	387	0	this	Lcom/google/protobuf/TextFormat$Printer;
/*      */       //   0	387	1	field	Lcom/google/protobuf/Descriptors$FieldDescriptor;
/*      */       //   0	387	2	value	Ljava/lang/Object;
/*      */       //   0	387	3	generator	Lcom/google/protobuf/TextFormat$TextGenerator;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean shouldRedact(Descriptors.FieldDescriptor field, TextFormat.TextGenerator generator) {
/*  668 */       Descriptors.FieldDescriptor.RedactionState state = field.getRedactionState();
/*  669 */       return (this.enablingSafeDebugFormat && state.redact);
/*      */     }
/*      */ 
/*      */     
/*      */     public String printToString(MessageOrBuilder message) {
/*  674 */       return printToString(message, FieldReporterLevel.PRINTER_PRINT_TO_STRING);
/*      */     }
/*      */     
/*      */     String printToString(MessageOrBuilder message, FieldReporterLevel level) {
/*      */       try {
/*  679 */         StringBuilder text = new StringBuilder();
/*  680 */         if (this.enablingSafeDebugFormat) {
/*  681 */           applyUnstablePrefix(text);
/*      */         }
/*  683 */         print(message, text, level);
/*  684 */         return text.toString();
/*  685 */       } catch (IOException e) {
/*  686 */         throw new IllegalStateException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public String printToString(UnknownFieldSet fields) {
/*      */       try {
/*  693 */         StringBuilder text = new StringBuilder();
/*  694 */         if (this.enablingSafeDebugFormat) {
/*  695 */           applyUnstablePrefix(text);
/*      */         }
/*  697 */         print(fields, text);
/*  698 */         return text.toString();
/*  699 */       } catch (IOException e) {
/*  700 */         throw new IllegalStateException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public String shortDebugString(MessageOrBuilder message) {
/*  712 */       return emittingSingleLine(true)
/*  713 */         .printToString(message, FieldReporterLevel.SHORT_DEBUG_STRING);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     @InlineMe(replacement = "this.emittingSingleLine(true).printFieldToString(field, value)")
/*      */     public String shortDebugString(Descriptors.FieldDescriptor field, Object value) {
/*  726 */       return emittingSingleLine(true).printFieldToString(field, value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     @InlineMe(replacement = "this.emittingSingleLine(true).printToString(fields)")
/*      */     public String shortDebugString(UnknownFieldSet fields) {
/*  738 */       return emittingSingleLine(true).printToString(fields);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private static void printUnknownFieldValue(int tag, Object value, TextFormat.TextGenerator generator, boolean redact) throws IOException {
/*  744 */       switch (WireFormat.getTagWireType(tag)) {
/*      */         case 0:
/*  746 */           generator.print(
/*  747 */               redact ? 
/*  748 */               String.format("UNKNOWN_VARINT %s", new Object[] { "[REDACTED]"
/*  749 */                 }) : TextFormat.unsignedToString(((Long)value).longValue()));
/*      */           return;
/*      */         case 5:
/*  752 */           generator.print(
/*  753 */               redact ? 
/*  754 */               String.format("UNKNOWN_FIXED32 %s", new Object[] { "[REDACTED]"
/*  755 */                 }) : String.format((Locale)null, "0x%08x", new Object[] { value }));
/*      */           return;
/*      */         case 1:
/*  758 */           generator.print(
/*  759 */               redact ? 
/*  760 */               String.format("UNKNOWN_FIXED64 %s", new Object[] { "[REDACTED]"
/*  761 */                 }) : String.format((Locale)null, "0x%016x", new Object[] { value }));
/*      */           return;
/*      */         
/*      */         case 2:
/*      */           try {
/*  766 */             UnknownFieldSet message = UnknownFieldSet.parseFrom((ByteString)value);
/*  767 */             generator.print("{");
/*  768 */             generator.eol();
/*  769 */             generator.indent();
/*  770 */             printUnknownFields(message, generator, redact);
/*  771 */             generator.outdent();
/*  772 */             generator.print("}");
/*  773 */           } catch (InvalidProtocolBufferException e) {
/*      */             
/*  775 */             if (redact) {
/*  776 */               generator.print(String.format("UNKNOWN_STRING %s", new Object[] { "[REDACTED]" }));
/*      */             } else {
/*      */               
/*  779 */               generator.print("\"");
/*  780 */               generator.print(TextFormat.escapeBytes((ByteString)value));
/*  781 */               generator.print("\"");
/*      */             } 
/*      */           }  return;
/*      */         case 3:
/*  785 */           printUnknownFields((UnknownFieldSet)value, generator, redact);
/*      */           return;
/*      */       } 
/*  788 */       throw new IllegalArgumentException("Bad tag: " + tag);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void printMessage(MessageOrBuilder message, TextFormat.TextGenerator generator) throws IOException {
/*  794 */       for (Map.Entry<Descriptors.FieldDescriptor, Object> field : message.getAllFields().entrySet()) {
/*  795 */         printField(field.getKey(), field.getValue(), generator);
/*      */       }
/*  797 */       printUnknownFields(message.getUnknownFields(), generator, this.enablingSafeDebugFormat);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void printShortRepeatedField(Descriptors.FieldDescriptor field, Object value, TextFormat.TextGenerator generator) throws IOException {
/*  803 */       generator.print(field.getName());
/*  804 */       generator.print(": ");
/*  805 */       generator.print("[");
/*  806 */       String separator = "";
/*  807 */       for (Object element : value) {
/*  808 */         generator.print(separator);
/*  809 */         printFieldValue(field, element, generator);
/*  810 */         separator = ", ";
/*      */       } 
/*  812 */       generator.print("]");
/*  813 */       generator.eol();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void printSingleField(Descriptors.FieldDescriptor field, Object value, TextFormat.TextGenerator generator) throws IOException {
/*  819 */       if (field.isExtension()) {
/*  820 */         generator.print("[");
/*      */         
/*  822 */         if (field.getContainingType().getOptions().getMessageSetWireFormat() && field
/*  823 */           .getType() == Descriptors.FieldDescriptor.Type.MESSAGE && field
/*  824 */           .isOptional() && field
/*      */           
/*  826 */           .getExtensionScope() == field.getMessageType()) {
/*  827 */           generator.print(field.getMessageType().getFullName());
/*      */         } else {
/*  829 */           generator.print(field.getFullName());
/*      */         } 
/*  831 */         generator.print("]");
/*      */       }
/*  833 */       else if (field.isGroupLike()) {
/*      */         
/*  835 */         generator.print(field.getMessageType().getName());
/*      */       } else {
/*  837 */         generator.print(field.getName());
/*      */       } 
/*      */ 
/*      */       
/*  841 */       if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/*  842 */         generator.maybePrintSilentMarker();
/*  843 */         generator.print("{");
/*  844 */         generator.eol();
/*  845 */         generator.indent();
/*      */       } else {
/*  847 */         generator.print(":");
/*  848 */         generator.maybePrintSilentMarker();
/*      */       } 
/*      */       
/*  851 */       printFieldValue(field, value, generator);
/*      */       
/*  853 */       if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/*  854 */         generator.outdent();
/*  855 */         generator.print("}");
/*      */       } 
/*  857 */       generator.eol();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private static void printUnknownFields(UnknownFieldSet unknownFields, TextFormat.TextGenerator generator, boolean redact) throws IOException {
/*  863 */       if (unknownFields.isEmpty()) {
/*      */         return;
/*      */       }
/*  866 */       for (Map.Entry<Integer, UnknownFieldSet.Field> entry : unknownFields.asMap().entrySet()) {
/*  867 */         int number = ((Integer)entry.getKey()).intValue();
/*  868 */         UnknownFieldSet.Field field = entry.getValue();
/*  869 */         printUnknownField(number, 0, field
/*  870 */             .getVarintList(), generator, redact);
/*  871 */         printUnknownField(number, 5, field
/*  872 */             .getFixed32List(), generator, redact);
/*  873 */         printUnknownField(number, 1, field
/*  874 */             .getFixed64List(), generator, redact);
/*  875 */         printUnknownField(number, 2, field
/*      */ 
/*      */             
/*  878 */             .getLengthDelimitedList(), generator, redact);
/*      */ 
/*      */         
/*  881 */         for (UnknownFieldSet value : field.getGroupList()) {
/*  882 */           generator.print(((Integer)entry.getKey()).toString());
/*  883 */           generator.maybePrintSilentMarker();
/*  884 */           generator.print("{");
/*  885 */           generator.eol();
/*  886 */           generator.indent();
/*  887 */           printUnknownFields(value, generator, redact);
/*  888 */           generator.outdent();
/*  889 */           generator.print("}");
/*  890 */           generator.eol();
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static void printUnknownField(int number, int wireType, List<?> values, TextFormat.TextGenerator generator, boolean redact) throws IOException {
/*  902 */       for (Object value : values) {
/*  903 */         generator.print(String.valueOf(number));
/*  904 */         generator.print(":");
/*  905 */         generator.maybePrintSilentMarker();
/*  906 */         printUnknownFieldValue(wireType, value, generator, redact);
/*  907 */         generator.eol();
/*      */       } 
/*      */     } } enum FieldReporterLevel { REPORT_ALL(0), TEXT_GENERATOR(1),
/*      */     PRINT(2),
/*      */     PRINTER_PRINT_TO_STRING(3),
/*      */     TEXTFORMAT_PRINT_TO_STRING(4),
/*      */     PRINT_UNICODE(5),
/*      */     SHORT_DEBUG_STRING(6),
/*      */     LEGACY_MULTILINE(7),
/*      */     LEGACY_SINGLE_LINE(8),
/*      */     DEBUG_MULTILINE(9),
/*      */     DEBUG_SINGLE_LINE(10),
/*      */     ABSTRACT_TO_STRING(11),
/*      */     ABSTRACT_BUILDER_TO_STRING(12),
/*      */     ABSTRACT_MUTABLE_TO_STRING(13),
/*      */     REPORT_NONE(14); private final int index; FieldReporterLevel(int index) { this.index = index; } }
/*      */   @Deprecated
/*      */   @InlineMe(replacement = "TextFormat.printer().print(message, output)", imports = {"com.google.protobuf.TextFormat"})
/*  925 */   public static void print(MessageOrBuilder message, Appendable output) throws IOException { printer().print(message, output); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void printUnicode(MessageOrBuilder message, Appendable output) throws IOException {
/*  936 */     printer()
/*  937 */       .escapingNonAscii(false)
/*  938 */       .print(message, output, Printer.FieldReporterLevel.PRINT_UNICODE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static String printToString(MessageOrBuilder message) {
/*  948 */     return printer().printToString(message, Printer.FieldReporterLevel.TEXTFORMAT_PRINT_TO_STRING);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static String printToUnicodeString(MessageOrBuilder message) {
/*  959 */     return printer()
/*  960 */       .escapingNonAscii(false)
/*  961 */       .printToString(message, Printer.FieldReporterLevel.PRINT_UNICODE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @InlineMe(replacement = "TextFormat.printer().printFieldValue(field, value, output)", imports = {"com.google.protobuf.TextFormat"})
/*      */   public static void printFieldValue(Descriptors.FieldDescriptor field, Object value, Appendable output) throws IOException {
/*  980 */     printer().printFieldValue(field, value, output);
/*      */   }
/*      */ 
/*      */   
/*      */   public static String unsignedToString(int value) {
/*  985 */     if (value >= 0) {
/*  986 */       return Integer.toString(value);
/*      */     }
/*  988 */     return Long.toString(value & 0xFFFFFFFFL);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String unsignedToString(long value) {
/*  994 */     if (value >= 0L) {
/*  995 */       return Long.toString(value);
/*      */     }
/*      */ 
/*      */     
/*  999 */     return BigInteger.valueOf(value & Long.MAX_VALUE).setBit(63).toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private static TextGenerator setSingleLineOutput(Appendable output, boolean singleLine) {
/* 1004 */     return new TextGenerator(output, singleLine, null, Printer.FieldReporterLevel.TEXT_GENERATOR, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextGenerator setSingleLineOutput(Appendable output, boolean singleLine, Descriptors.Descriptor rootMessageType, Printer.FieldReporterLevel fieldReporterLevel, boolean shouldEmitSilentMarker) {
/* 1014 */     return new TextGenerator(output, singleLine, rootMessageType, fieldReporterLevel, shouldEmitSilentMarker);
/*      */   }
/*      */ 
/*      */   
/*      */   private static final class TextGenerator
/*      */   {
/*      */     private final Appendable output;
/* 1021 */     private final StringBuilder indent = new StringBuilder();
/*      */ 
/*      */     
/*      */     private final boolean singleLineMode;
/*      */ 
/*      */     
/*      */     private boolean shouldEmitSilentMarker;
/*      */ 
/*      */     
/*      */     private boolean atStartOfLine = false;
/*      */ 
/*      */     
/*      */     private final TextFormat.Printer.FieldReporterLevel fieldReporterLevel;
/*      */ 
/*      */     
/*      */     private final Descriptors.Descriptor rootMessageType;
/*      */ 
/*      */ 
/*      */     
/*      */     private TextGenerator(Appendable output, boolean singleLineMode, Descriptors.Descriptor rootMessageType, TextFormat.Printer.FieldReporterLevel fieldReporterLevel, boolean shouldEmitSilentMarker) {
/* 1041 */       this.output = output;
/* 1042 */       this.singleLineMode = singleLineMode;
/* 1043 */       this.rootMessageType = rootMessageType;
/* 1044 */       this.fieldReporterLevel = fieldReporterLevel;
/* 1045 */       this.shouldEmitSilentMarker = shouldEmitSilentMarker;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void indent() {
/* 1054 */       this.indent.append("  ");
/*      */     }
/*      */ 
/*      */     
/*      */     public void outdent() {
/* 1059 */       int length = this.indent.length();
/* 1060 */       if (length == 0) {
/* 1061 */         throw new IllegalArgumentException(" Outdent() without matching Indent().");
/*      */       }
/* 1063 */       this.indent.setLength(length - 2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void print(CharSequence text) throws IOException {
/* 1071 */       if (this.atStartOfLine) {
/* 1072 */         this.atStartOfLine = false;
/* 1073 */         this.output.append(this.singleLineMode ? " " : this.indent);
/*      */       } 
/* 1075 */       this.output.append(text);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void eol() throws IOException {
/* 1084 */       if (!this.singleLineMode) {
/* 1085 */         this.output.append("\n");
/*      */       }
/* 1087 */       this.atStartOfLine = true;
/*      */     }
/*      */     
/*      */     void maybePrintSilentMarker() throws IOException {
/* 1091 */       if (this.shouldEmitSilentMarker) {
/* 1092 */         this.output.append(" \t ");
/* 1093 */         this.shouldEmitSilentMarker = false;
/*      */       } else {
/* 1095 */         this.output.append(" ");
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class Tokenizer
/*      */   {
/*      */     private final CharSequence text;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String currentToken;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1128 */     private int pos = 0;
/*      */ 
/*      */     
/* 1131 */     private int line = 0;
/* 1132 */     private int column = 0;
/* 1133 */     private int lineInfoTrackingPos = 0;
/*      */ 
/*      */ 
/*      */     
/* 1137 */     private int previousLine = 0;
/* 1138 */     private int previousColumn = 0;
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean containsSilentMarkerAfterCurrentToken = false;
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean containsSilentMarkerAfterPrevToken = false;
/*      */ 
/*      */ 
/*      */     
/*      */     private Tokenizer(CharSequence text) {
/* 1151 */       this.text = text;
/* 1152 */       skipWhitespace();
/* 1153 */       nextToken();
/*      */     }
/*      */     
/*      */     int getPreviousLine() {
/* 1157 */       return this.previousLine;
/*      */     }
/*      */     
/*      */     int getPreviousColumn() {
/* 1161 */       return this.previousColumn;
/*      */     }
/*      */     
/*      */     int getLine() {
/* 1165 */       return this.line;
/*      */     }
/*      */     
/*      */     int getColumn() {
/* 1169 */       return this.column;
/*      */     }
/*      */     
/*      */     boolean getContainsSilentMarkerAfterCurrentToken() {
/* 1173 */       return this.containsSilentMarkerAfterCurrentToken;
/*      */     }
/*      */     
/*      */     boolean getContainsSilentMarkerAfterPrevToken() {
/* 1177 */       return this.containsSilentMarkerAfterPrevToken;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean atEnd() {
/* 1182 */       return (this.currentToken.length() == 0);
/*      */     }
/*      */ 
/*      */     
/*      */     void nextToken() {
/* 1187 */       this.previousLine = this.line;
/* 1188 */       this.previousColumn = this.column;
/*      */ 
/*      */       
/* 1191 */       while (this.lineInfoTrackingPos < this.pos) {
/* 1192 */         if (this.text.charAt(this.lineInfoTrackingPos) == '\n') {
/* 1193 */           this.line++;
/* 1194 */           this.column = 0;
/*      */         } else {
/* 1196 */           this.column++;
/*      */         } 
/* 1198 */         this.lineInfoTrackingPos++;
/*      */       } 
/*      */ 
/*      */       
/* 1202 */       if (this.pos == this.text.length()) {
/* 1203 */         this.currentToken = "";
/*      */       } else {
/* 1205 */         this.currentToken = nextTokenInternal();
/* 1206 */         skipWhitespace();
/*      */       } 
/*      */     }
/*      */     
/*      */     private String nextTokenInternal() {
/* 1211 */       int textLength = this.text.length();
/* 1212 */       int startPos = this.pos;
/* 1213 */       char startChar = this.text.charAt(startPos);
/*      */       
/* 1215 */       int endPos = this.pos;
/* 1216 */       if (isAlphaUnder(startChar)) {
/* 1217 */         while (++endPos != textLength) {
/* 1218 */           char c = this.text.charAt(endPos);
/* 1219 */           if (!isAlphaUnder(c) && !isDigitPlusMinus(c)) {
/*      */             break;
/*      */           }
/*      */         } 
/* 1223 */       } else if (isDigitPlusMinus(startChar) || startChar == '.') {
/* 1224 */         if (startChar == '.') {
/* 1225 */           if (++endPos == textLength) {
/* 1226 */             return nextTokenSingleChar();
/*      */           }
/*      */           
/* 1229 */           if (!isDigitPlusMinus(this.text.charAt(endPos))) {
/* 1230 */             return nextTokenSingleChar();
/*      */           }
/*      */         } 
/*      */         
/* 1234 */         while (++endPos != textLength) {
/* 1235 */           char c = this.text.charAt(endPos);
/* 1236 */           if (!isDigitPlusMinus(c) && !isAlphaUnder(c) && c != '.') {
/*      */             break;
/*      */           }
/*      */         } 
/* 1240 */       } else if (startChar == '"' || startChar == '\'') {
/* 1241 */         while (++endPos != textLength) {
/* 1242 */           char c = this.text.charAt(endPos);
/* 1243 */           if (c == startChar) {
/* 1244 */             endPos++; break;
/*      */           } 
/* 1246 */           if (c == '\n')
/*      */             break; 
/* 1248 */           if (c == '\\') {
/* 1249 */             if (++endPos == textLength)
/*      */               break; 
/* 1251 */             if (this.text.charAt(endPos) == '\n') {
/*      */               break;
/*      */             }
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1261 */         return nextTokenSingleChar();
/*      */       } 
/*      */       
/* 1264 */       this.pos = endPos;
/* 1265 */       return this.text.subSequence(startPos, endPos).toString();
/*      */     }
/*      */ 
/*      */     
/*      */     private static boolean isAlphaUnder(char c) {
/* 1270 */       return (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || c == '_');
/*      */     }
/*      */ 
/*      */     
/*      */     private static boolean isDigitPlusMinus(char c) {
/* 1275 */       return (('0' <= c && c <= '9') || c == '+' || c == '-');
/*      */     }
/*      */ 
/*      */     
/*      */     private static boolean isWhitespace(char c) {
/* 1280 */       return (c == ' ' || c == '\f' || c == '\n' || c == '\r' || c == '\t');
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String nextTokenSingleChar() {
/* 1292 */       char c = this.text.charAt(this.pos++);
/* 1293 */       switch (c) {
/*      */         case ':':
/* 1295 */           return ":";
/*      */         case ',':
/* 1297 */           return ",";
/*      */         case '[':
/* 1299 */           return "[";
/*      */         case ']':
/* 1301 */           return "]";
/*      */         case '{':
/* 1303 */           return "{";
/*      */         case '}':
/* 1305 */           return "}";
/*      */         case '<':
/* 1307 */           return "<";
/*      */         case '>':
/* 1309 */           return ">";
/*      */       } 
/*      */       
/* 1312 */       return String.valueOf(c);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void skipWhitespace() {
/* 1318 */       int textLength = this.text.length();
/* 1319 */       int startPos = this.pos;
/*      */       
/* 1321 */       int endPos = this.pos - 1;
/* 1322 */       while (++endPos != textLength) {
/* 1323 */         char c = this.text.charAt(endPos);
/* 1324 */         if (c == '#') { do {  }
/* 1325 */           while (++endPos != textLength && 
/* 1326 */             this.text.charAt(endPos) != '\n');
/*      */ 
/*      */ 
/*      */           
/* 1330 */           if (endPos == textLength)
/*      */             break;  continue; }
/*      */         
/* 1333 */         if (isWhitespace(c));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1340 */       this.pos = endPos;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean tryConsume(String token) {
/* 1348 */       if (this.currentToken.equals(token)) {
/* 1349 */         nextToken();
/* 1350 */         return true;
/*      */       } 
/* 1352 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void consume(String token) throws TextFormat.ParseException {
/* 1361 */       if (!tryConsume(token)) {
/* 1362 */         throw parseException("Expected \"" + token + "\".");
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     boolean lookingAtInteger() {
/* 1368 */       if (this.currentToken.length() == 0) {
/* 1369 */         return false;
/*      */       }
/*      */       
/* 1372 */       return isDigitPlusMinus(this.currentToken.charAt(0));
/*      */     }
/*      */ 
/*      */     
/*      */     boolean lookingAt(String text) {
/* 1377 */       return this.currentToken.equals(text);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String consumeIdentifier() throws TextFormat.ParseException {
/* 1385 */       for (int i = 0; i < this.currentToken.length(); ) {
/* 1386 */         char c = this.currentToken.charAt(i);
/* 1387 */         if (isAlphaUnder(c) || ('0' <= c && c <= '9') || c == '.') {
/*      */           i++; continue;
/*      */         } 
/* 1390 */         throw parseException("Expected identifier. Found '" + this.currentToken + "'");
/*      */       } 
/*      */ 
/*      */       
/* 1394 */       String result = this.currentToken;
/* 1395 */       nextToken();
/* 1396 */       return result;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean tryConsumeIdentifier() {
/*      */       try {
/* 1405 */         consumeIdentifier();
/* 1406 */         return true;
/* 1407 */       } catch (ParseException e) {
/* 1408 */         return false;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int consumeInt32() throws TextFormat.ParseException {
/*      */       try {
/* 1418 */         int result = TextFormat.parseInt32(this.currentToken);
/* 1419 */         nextToken();
/* 1420 */         return result;
/* 1421 */       } catch (NumberFormatException e) {
/* 1422 */         throw integerParseException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int consumeUInt32() throws TextFormat.ParseException {
/*      */       try {
/* 1432 */         int result = TextFormat.parseUInt32(this.currentToken);
/* 1433 */         nextToken();
/* 1434 */         return result;
/* 1435 */       } catch (NumberFormatException e) {
/* 1436 */         throw integerParseException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     long consumeInt64() throws TextFormat.ParseException {
/*      */       try {
/* 1446 */         long result = TextFormat.parseInt64(this.currentToken);
/* 1447 */         nextToken();
/* 1448 */         return result;
/* 1449 */       } catch (NumberFormatException e) {
/* 1450 */         throw integerParseException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean tryConsumeInt64() {
/*      */       try {
/* 1460 */         consumeInt64();
/* 1461 */         return true;
/* 1462 */       } catch (ParseException e) {
/* 1463 */         return false;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     long consumeUInt64() throws TextFormat.ParseException {
/*      */       try {
/* 1473 */         long result = TextFormat.parseUInt64(this.currentToken);
/* 1474 */         nextToken();
/* 1475 */         return result;
/* 1476 */       } catch (NumberFormatException e) {
/* 1477 */         throw integerParseException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryConsumeUInt64() {
/*      */       try {
/* 1487 */         consumeUInt64();
/* 1488 */         return true;
/* 1489 */       } catch (ParseException e) {
/* 1490 */         return false;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double consumeDouble() throws TextFormat.ParseException {
/* 1501 */       switch (this.currentToken.toLowerCase(Locale.ROOT)) {
/*      */         case "-inf":
/*      */         case "-infinity":
/* 1504 */           nextToken();
/* 1505 */           return Double.NEGATIVE_INFINITY;
/*      */         case "inf":
/*      */         case "infinity":
/* 1508 */           nextToken();
/* 1509 */           return Double.POSITIVE_INFINITY;
/*      */         case "nan":
/* 1511 */           nextToken();
/* 1512 */           return Double.NaN;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1518 */         double result = Double.parseDouble(this.currentToken);
/* 1519 */         nextToken();
/* 1520 */         return result;
/* 1521 */       } catch (NumberFormatException e) {
/* 1522 */         throw floatParseException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryConsumeDouble() {
/*      */       try {
/* 1532 */         consumeDouble();
/* 1533 */         return true;
/* 1534 */       } catch (ParseException e) {
/* 1535 */         return false;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float consumeFloat() throws TextFormat.ParseException {
/* 1546 */       switch (this.currentToken.toLowerCase(Locale.ROOT)) {
/*      */         case "-inf":
/*      */         case "-inff":
/*      */         case "-infinity":
/*      */         case "-infinityf":
/* 1551 */           nextToken();
/* 1552 */           return Float.NEGATIVE_INFINITY;
/*      */         case "inf":
/*      */         case "inff":
/*      */         case "infinity":
/*      */         case "infinityf":
/* 1557 */           nextToken();
/* 1558 */           return Float.POSITIVE_INFINITY;
/*      */         case "nan":
/*      */         case "nanf":
/* 1561 */           nextToken();
/* 1562 */           return Float.NaN;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1568 */         float result = Float.parseFloat(this.currentToken);
/* 1569 */         nextToken();
/* 1570 */         return result;
/* 1571 */       } catch (NumberFormatException e) {
/* 1572 */         throw floatParseException(e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean tryConsumeFloat() {
/*      */       try {
/* 1582 */         consumeFloat();
/* 1583 */         return true;
/* 1584 */       } catch (ParseException e) {
/* 1585 */         return false;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean consumeBoolean() throws TextFormat.ParseException {
/* 1594 */       if (this.currentToken.equals("true") || this.currentToken
/* 1595 */         .equals("True") || this.currentToken
/* 1596 */         .equals("t") || this.currentToken
/* 1597 */         .equals("1")) {
/* 1598 */         nextToken();
/* 1599 */         return true;
/* 1600 */       }  if (this.currentToken.equals("false") || this.currentToken
/* 1601 */         .equals("False") || this.currentToken
/* 1602 */         .equals("f") || this.currentToken
/* 1603 */         .equals("0")) {
/* 1604 */         nextToken();
/* 1605 */         return false;
/*      */       } 
/* 1607 */       throw parseException("Expected \"true\" or \"false\". Found \"" + this.currentToken + "\".");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String consumeString() throws TextFormat.ParseException {
/* 1616 */       return consumeByteString().toStringUtf8();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @CanIgnoreReturnValue
/*      */     ByteString consumeByteString() throws TextFormat.ParseException {
/* 1625 */       List<ByteString> list = new ArrayList<>();
/* 1626 */       consumeByteString(list);
/* 1627 */       while (this.currentToken.startsWith("'") || this.currentToken.startsWith("\"")) {
/* 1628 */         consumeByteString(list);
/*      */       }
/* 1630 */       return ByteString.copyFrom(list);
/*      */     }
/*      */ 
/*      */     
/*      */     boolean tryConsumeByteString() {
/*      */       try {
/* 1636 */         consumeByteString();
/* 1637 */         return true;
/* 1638 */       } catch (ParseException e) {
/* 1639 */         return false;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void consumeByteString(List<ByteString> list) throws TextFormat.ParseException {
/* 1649 */       char quote = (this.currentToken.length() > 0) ? this.currentToken.charAt(0) : Character.MIN_VALUE;
/* 1650 */       if (quote != '"' && quote != '\'') {
/* 1651 */         throw parseException("Expected string.");
/*      */       }
/*      */       
/* 1654 */       if (this.currentToken.length() < 2 || this.currentToken.charAt(this.currentToken.length() - 1) != quote) {
/* 1655 */         throw parseException("String missing ending quote.");
/*      */       }
/*      */       
/*      */       try {
/* 1659 */         String escaped = this.currentToken.substring(1, this.currentToken.length() - 1);
/* 1660 */         ByteString result = TextFormat.unescapeBytes(escaped);
/* 1661 */         nextToken();
/* 1662 */         list.add(result);
/* 1663 */       } catch (InvalidEscapeSequenceException e) {
/* 1664 */         throw parseException(e.getMessage());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TextFormat.ParseException parseException(String description) {
/* 1674 */       return new TextFormat.ParseException(this.line + 1, this.column + 1, description);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TextFormat.ParseException parseExceptionPreviousToken(String description) {
/* 1683 */       return new TextFormat.ParseException(this.previousLine + 1, this.previousColumn + 1, description);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private TextFormat.ParseException integerParseException(NumberFormatException e) {
/* 1691 */       return parseException("Couldn't parse integer: " + e.getMessage());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private TextFormat.ParseException floatParseException(NumberFormatException e) {
/* 1699 */       return parseException("Couldn't parse number: " + e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class ParseException
/*      */     extends IOException
/*      */   {
/*      */     private static final long serialVersionUID = 3196188060225107702L;
/*      */     private final int line;
/*      */     private final int column;
/*      */     
/*      */     public ParseException(String message) {
/* 1712 */       this(-1, -1, message);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ParseException(int line, int column, String message) {
/* 1722 */       super(Integer.toString(line) + ":" + column + ": " + message);
/* 1723 */       this.line = line;
/* 1724 */       this.column = column;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getLine() {
/* 1732 */       return this.line;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getColumn() {
/* 1740 */       return this.column;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static class UnknownFieldParseException
/*      */     extends ParseException
/*      */   {
/*      */     private final String unknownField;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public UnknownFieldParseException(String message) {
/* 1759 */       this(-1, -1, "", message);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public UnknownFieldParseException(int line, int column, String unknownField, String message) {
/* 1771 */       super(line, column, message);
/* 1772 */       this.unknownField = unknownField;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getUnknownField() {
/* 1779 */       return this.unknownField;
/*      */     }
/*      */   }
/*      */   
/* 1783 */   private static final Parser PARSER = Parser.newBuilder().build();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Parser getParser() {
/* 1790 */     return PARSER;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void merge(Readable input, Message.Builder builder) throws IOException {
/* 1795 */     PARSER.merge(input, builder);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void merge(CharSequence input, Message.Builder builder) throws ParseException {
/* 1801 */     PARSER.merge(input, builder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends Message> T parse(CharSequence input, Class<T> protoClass) throws ParseException {
/* 1811 */     Message.Builder builder = ((Message)Internal.<Message>getDefaultInstance(protoClass)).newBuilderForType();
/* 1812 */     merge(input, builder);
/*      */     
/* 1814 */     return (T)builder.build();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void merge(Readable input, ExtensionRegistry extensionRegistry, Message.Builder builder) throws IOException {
/* 1827 */     PARSER.merge(input, extensionRegistry, builder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void merge(CharSequence input, ExtensionRegistry extensionRegistry, Message.Builder builder) throws ParseException {
/* 1839 */     PARSER.merge(input, extensionRegistry, builder);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends Message> T parse(CharSequence input, ExtensionRegistry extensionRegistry, Class<T> protoClass) throws ParseException {
/* 1853 */     Message.Builder builder = ((Message)Internal.<Message>getDefaultInstance(protoClass)).newBuilderForType();
/* 1854 */     merge(input, extensionRegistry, builder);
/*      */     
/* 1856 */     return (T)builder.build();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Parser
/*      */   {
/*      */     private final TypeRegistry typeRegistry;
/*      */ 
/*      */ 
/*      */     
/*      */     private final boolean allowUnknownFields;
/*      */ 
/*      */ 
/*      */     
/*      */     private final boolean allowUnknownEnumValues;
/*      */ 
/*      */     
/*      */     private final boolean allowUnknownExtensions;
/*      */ 
/*      */     
/*      */     private final SingularOverwritePolicy singularOverwritePolicy;
/*      */ 
/*      */     
/*      */     private TextFormatParseInfoTree.Builder parseInfoTreeBuilder;
/*      */ 
/*      */     
/*      */     private final int recursionLimit;
/*      */ 
/*      */     
/*      */     private static final int BUFFER_SIZE = 4096;
/*      */ 
/*      */ 
/*      */     
/*      */     private void detectSilentMarker(TextFormat.Tokenizer tokenizer, Descriptors.Descriptor immediateMessageType, String fieldName) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public enum SingularOverwritePolicy
/*      */     {
/* 1897 */       ALLOW_SINGULAR_OVERWRITES,
/*      */       
/* 1899 */       FORBID_SINGULAR_OVERWRITES;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Parser(TypeRegistry typeRegistry, boolean allowUnknownFields, boolean allowUnknownEnumValues, boolean allowUnknownExtensions, SingularOverwritePolicy singularOverwritePolicy, TextFormatParseInfoTree.Builder parseInfoTreeBuilder, int recursionLimit) {
/* 1918 */       this.typeRegistry = typeRegistry;
/* 1919 */       this.allowUnknownFields = allowUnknownFields;
/* 1920 */       this.allowUnknownEnumValues = allowUnknownEnumValues;
/* 1921 */       this.allowUnknownExtensions = allowUnknownExtensions;
/* 1922 */       this.singularOverwritePolicy = singularOverwritePolicy;
/* 1923 */       this.parseInfoTreeBuilder = parseInfoTreeBuilder;
/* 1924 */       this.recursionLimit = recursionLimit;
/*      */     }
/*      */ 
/*      */     
/*      */     public static Builder newBuilder() {
/* 1929 */       return new Builder();
/*      */     }
/*      */     public static class Builder { private boolean allowUnknownFields; private boolean allowUnknownEnumValues; private boolean allowUnknownExtensions; private TextFormat.Parser.SingularOverwritePolicy singularOverwritePolicy; private TextFormatParseInfoTree.Builder parseInfoTreeBuilder; private TypeRegistry typeRegistry; private int recursionLimit;
/*      */       
/*      */       public Builder() {
/* 1934 */         this.allowUnknownFields = false;
/* 1935 */         this.allowUnknownEnumValues = false;
/* 1936 */         this.allowUnknownExtensions = false;
/* 1937 */         this.singularOverwritePolicy = TextFormat.Parser.SingularOverwritePolicy.ALLOW_SINGULAR_OVERWRITES;
/*      */         
/* 1939 */         this.parseInfoTreeBuilder = null;
/* 1940 */         this.typeRegistry = TypeRegistry.getEmptyTypeRegistry();
/* 1941 */         this.recursionLimit = 100;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Builder setTypeRegistry(TypeRegistry typeRegistry) {
/* 1950 */         this.typeRegistry = typeRegistry;
/* 1951 */         return this;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Builder setAllowUnknownFields(boolean allowUnknownFields) {
/* 1963 */         this.allowUnknownFields = allowUnknownFields;
/* 1964 */         return this;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Builder setAllowUnknownExtensions(boolean allowUnknownExtensions) {
/* 1973 */         this.allowUnknownExtensions = allowUnknownExtensions;
/* 1974 */         return this;
/*      */       }
/*      */ 
/*      */       
/*      */       public Builder setSingularOverwritePolicy(TextFormat.Parser.SingularOverwritePolicy p) {
/* 1979 */         this.singularOverwritePolicy = p;
/* 1980 */         return this;
/*      */       }
/*      */       
/*      */       public Builder setParseInfoTreeBuilder(TextFormatParseInfoTree.Builder parseInfoTreeBuilder) {
/* 1984 */         this.parseInfoTreeBuilder = parseInfoTreeBuilder;
/* 1985 */         return this;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Builder setRecursionLimit(int recursionLimit) {
/* 1993 */         this.recursionLimit = recursionLimit;
/* 1994 */         return this;
/*      */       }
/*      */       
/*      */       public TextFormat.Parser build() {
/* 1998 */         return new TextFormat.Parser(this.typeRegistry, this.allowUnknownFields, this.allowUnknownEnumValues, this.allowUnknownExtensions, this.singularOverwritePolicy, this.parseInfoTreeBuilder, this.recursionLimit);
/*      */       } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void merge(Readable input, Message.Builder builder) throws IOException {
/* 2013 */       merge(input, ExtensionRegistry.getEmptyRegistry(), builder);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void merge(CharSequence input, Message.Builder builder) throws TextFormat.ParseException {
/* 2021 */       merge(input, ExtensionRegistry.getEmptyRegistry(), builder);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void merge(Readable input, ExtensionRegistry extensionRegistry, Message.Builder builder) throws IOException {
/* 2041 */       merge(toStringBuilder(input), extensionRegistry, builder);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static StringBuilder toStringBuilder(Readable input) throws IOException {
/* 2049 */       StringBuilder text = new StringBuilder();
/* 2050 */       CharBuffer buffer = CharBuffer.allocate(4096);
/*      */       while (true) {
/* 2052 */         int n = input.read(buffer);
/* 2053 */         if (n == -1) {
/*      */           break;
/*      */         }
/* 2056 */         Java8Compatibility.flip(buffer);
/* 2057 */         text.append(buffer, 0, n);
/*      */       } 
/* 2059 */       return text;
/*      */     }
/*      */     static final class UnknownField { final String message;
/*      */       final Type type;
/*      */       
/* 2064 */       enum Type { FIELD,
/* 2065 */         EXTENSION; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       UnknownField(String message, Type type) {
/* 2072 */         this.message = message;
/* 2073 */         this.type = type;
/*      */       } }
/*      */ 
/*      */     
/*      */     enum Type { FIELD, EXTENSION; }
/*      */     
/*      */     private void checkUnknownFields(List<UnknownField> unknownFields) throws TextFormat.ParseException {
/* 2080 */       if (unknownFields.isEmpty()) {
/*      */         return;
/*      */       }
/*      */       
/* 2084 */       StringBuilder msg = new StringBuilder("Input contains unknown fields and/or extensions:");
/* 2085 */       for (UnknownField field : unknownFields) {
/* 2086 */         msg.append('\n').append(field.message);
/*      */       }
/*      */       
/* 2089 */       if (this.allowUnknownFields) {
/* 2090 */         TextFormat.logger.warning(msg.toString());
/*      */         
/*      */         return;
/*      */       } 
/* 2094 */       int firstErrorIndex = 0;
/* 2095 */       if (this.allowUnknownExtensions) {
/* 2096 */         boolean allUnknownExtensions = true;
/* 2097 */         for (UnknownField field : unknownFields) {
/* 2098 */           if (field.type == UnknownField.Type.FIELD) {
/* 2099 */             allUnknownExtensions = false;
/*      */             break;
/*      */           } 
/* 2102 */           firstErrorIndex++;
/*      */         } 
/* 2104 */         if (allUnknownExtensions) {
/* 2105 */           TextFormat.logger.warning(msg.toString());
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/* 2110 */       String[] lineColumn = ((UnknownField)unknownFields.get(firstErrorIndex)).message.split(":");
/* 2111 */       throw new TextFormat.ParseException(
/* 2112 */           Integer.parseInt(lineColumn[0]), Integer.parseInt(lineColumn[1]), msg.toString());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void merge(CharSequence input, ExtensionRegistry extensionRegistry, Message.Builder builder) throws TextFormat.ParseException {
/* 2124 */       TextFormat.Tokenizer tokenizer = new TextFormat.Tokenizer(input);
/* 2125 */       MessageReflection.BuilderAdapter target = new MessageReflection.BuilderAdapter(builder);
/* 2126 */       List<UnknownField> unknownFields = new ArrayList<>();
/*      */       
/* 2128 */       while (!tokenizer.atEnd()) {
/* 2129 */         mergeField(tokenizer, extensionRegistry, target, unknownFields, this.recursionLimit);
/*      */       }
/* 2131 */       checkUnknownFields(unknownFields);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void mergeField(TextFormat.Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MessageReflection.MergeTarget target, List<UnknownField> unknownFields, int recursionLimit) throws TextFormat.ParseException {
/* 2142 */       mergeField(tokenizer, extensionRegistry, target, this.parseInfoTreeBuilder, unknownFields, recursionLimit);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void mergeField(TextFormat.Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MessageReflection.MergeTarget target, TextFormatParseInfoTree.Builder parseTreeBuilder, List<UnknownField> unknownFields, int recursionLimit) throws TextFormat.ParseException {
/*      */       String name;
/* 2160 */       Descriptors.FieldDescriptor field = null;
/*      */       
/* 2162 */       int startLine = tokenizer.getLine();
/* 2163 */       int startColumn = tokenizer.getColumn();
/* 2164 */       Descriptors.Descriptor type = target.getDescriptorForType();
/* 2165 */       ExtensionRegistry.ExtensionInfo extension = null;
/*      */       
/* 2167 */       if ("google.protobuf.Any".equals(type.getFullName()) && tokenizer.tryConsume("[")) {
/* 2168 */         if (recursionLimit < 1) {
/* 2169 */           throw tokenizer.parseException("Message is nested too deep");
/*      */         }
/* 2171 */         mergeAnyFieldValue(tokenizer, extensionRegistry, target, parseTreeBuilder, unknownFields, type, recursionLimit - 1);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2182 */       if (tokenizer.tryConsume("[")) {
/*      */         
/* 2184 */         StringBuilder nameBuilder = new StringBuilder(tokenizer.consumeIdentifier());
/* 2185 */         while (tokenizer.tryConsume(".")) {
/* 2186 */           nameBuilder.append('.');
/* 2187 */           nameBuilder.append(tokenizer.consumeIdentifier());
/*      */         } 
/* 2189 */         name = nameBuilder.toString();
/*      */         
/* 2191 */         extension = target.findExtensionByName(extensionRegistry, name);
/*      */         
/* 2193 */         if (extension == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2199 */           String message = (tokenizer.getPreviousLine() + 1) + ":" + (tokenizer.getPreviousColumn() + 1) + ":\t" + type.getFullName() + ".[" + name + "]";
/*      */ 
/*      */ 
/*      */           
/* 2203 */           unknownFields.add(new UnknownField(message, UnknownField.Type.EXTENSION));
/*      */         } else {
/* 2205 */           if (extension.descriptor.getContainingType() != type) {
/* 2206 */             throw tokenizer.parseExceptionPreviousToken("Extension \"" + name + "\" does not extend message type \"" + type
/*      */ 
/*      */ 
/*      */                 
/* 2210 */                 .getFullName() + "\".");
/*      */           }
/*      */           
/* 2213 */           field = extension.descriptor;
/*      */         } 
/*      */         
/* 2216 */         tokenizer.consume("]");
/*      */       } else {
/* 2218 */         name = tokenizer.consumeIdentifier();
/* 2219 */         field = type.findFieldByName(name);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2224 */         if (field == null) {
/*      */ 
/*      */           
/* 2227 */           String lowerName = name.toLowerCase(Locale.US);
/* 2228 */           field = type.findFieldByName(lowerName);
/*      */           
/* 2230 */           if (field != null && !field.isGroupLike()) {
/* 2231 */             field = null;
/*      */           }
/* 2233 */           if (field != null && !field.getMessageType().getName().equals(name)) {
/* 2234 */             field = null;
/*      */           }
/*      */         } 
/*      */         
/* 2238 */         if (field == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2244 */           String message = (tokenizer.getPreviousLine() + 1) + ":" + (tokenizer.getPreviousColumn() + 1) + ":\t" + type.getFullName() + "." + name;
/*      */ 
/*      */           
/* 2247 */           unknownFields.add(new UnknownField(message, UnknownField.Type.FIELD));
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2252 */       if (field == null) {
/* 2253 */         detectSilentMarker(tokenizer, type, name);
/* 2254 */         guessFieldTypeAndSkip(tokenizer, type, recursionLimit);
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 2259 */       if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 2260 */         detectSilentMarker(tokenizer, type, field.getFullName());
/* 2261 */         tokenizer.tryConsume(":");
/* 2262 */         if (parseTreeBuilder != null)
/*      */         {
/* 2264 */           TextFormatParseInfoTree.Builder childParseTreeBuilder = parseTreeBuilder.getBuilderForSubMessageField(field);
/* 2265 */           consumeFieldValues(tokenizer, extensionRegistry, target, field, extension, childParseTreeBuilder, unknownFields, recursionLimit);
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 2275 */           consumeFieldValues(tokenizer, extensionRegistry, target, field, extension, parseTreeBuilder, unknownFields, recursionLimit);
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 2286 */         detectSilentMarker(tokenizer, type, field.getFullName());
/* 2287 */         tokenizer.consume(":");
/* 2288 */         consumeFieldValues(tokenizer, extensionRegistry, target, field, extension, parseTreeBuilder, unknownFields, recursionLimit);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2299 */       if (parseTreeBuilder != null) {
/* 2300 */         parseTreeBuilder.setLocation(field, TextFormatParseLocation.create(startLine, startColumn));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2305 */       if (!tokenizer.tryConsume(";")) {
/* 2306 */         tokenizer.tryConsume(",");
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private String consumeFullTypeName(TextFormat.Tokenizer tokenizer) throws TextFormat.ParseException {
/* 2312 */       if (!tokenizer.tryConsume("[")) {
/* 2313 */         return tokenizer.consumeIdentifier();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2318 */       String name = tokenizer.consumeIdentifier();
/* 2319 */       while (tokenizer.tryConsume(".")) {
/* 2320 */         name = name + "." + tokenizer.consumeIdentifier();
/*      */       }
/* 2322 */       if (tokenizer.tryConsume("/")) {
/* 2323 */         name = name + "/" + tokenizer.consumeIdentifier();
/* 2324 */         while (tokenizer.tryConsume(".")) {
/* 2325 */           name = name + "." + tokenizer.consumeIdentifier();
/*      */         }
/*      */       } 
/* 2328 */       tokenizer.consume("]");
/* 2329 */       return name;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void consumeFieldValues(TextFormat.Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MessageReflection.MergeTarget target, Descriptors.FieldDescriptor field, ExtensionRegistry.ExtensionInfo extension, TextFormatParseInfoTree.Builder parseTreeBuilder, List<UnknownField> unknownFields, int recursionLimit) throws TextFormat.ParseException {
/* 2347 */       if (field.isRepeated() && tokenizer.tryConsume("[")) {
/* 2348 */         if (!tokenizer.tryConsume("]")) {
/*      */           while (true) {
/* 2350 */             consumeFieldValue(tokenizer, extensionRegistry, target, field, extension, parseTreeBuilder, unknownFields, recursionLimit);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2359 */             if (tokenizer.tryConsume("]")) {
/*      */               break;
/*      */             }
/*      */             
/* 2363 */             tokenizer.consume(",");
/*      */           } 
/*      */         }
/*      */       } else {
/* 2367 */         consumeFieldValue(tokenizer, extensionRegistry, target, field, extension, parseTreeBuilder, unknownFields, recursionLimit);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void consumeFieldValue(TextFormat.Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MessageReflection.MergeTarget target, Descriptors.FieldDescriptor field, ExtensionRegistry.ExtensionInfo extension, TextFormatParseInfoTree.Builder parseTreeBuilder, List<UnknownField> unknownFields, int recursionLimit) throws TextFormat.ParseException {
/* 2390 */       if (this.singularOverwritePolicy == SingularOverwritePolicy.FORBID_SINGULAR_OVERWRITES && 
/* 2391 */         !field.isRepeated()) {
/* 2392 */         if (target.hasField(field))
/* 2393 */           throw tokenizer.parseExceptionPreviousToken("Non-repeated field \"" + field
/* 2394 */               .getFullName() + "\" cannot be overwritten."); 
/* 2395 */         if (field.getContainingOneof() != null && target
/* 2396 */           .hasOneof(field.getContainingOneof())) {
/* 2397 */           Descriptors.OneofDescriptor oneof = field.getContainingOneof();
/* 2398 */           throw tokenizer.parseExceptionPreviousToken("Field \"" + field
/*      */               
/* 2400 */               .getFullName() + "\" is specified along with field \"" + target
/*      */               
/* 2402 */               .getOneofFieldDescriptor(oneof).getFullName() + "\", another member of oneof \"" + oneof
/*      */               
/* 2404 */               .getName() + "\".");
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2409 */       Object value = null;
/*      */       
/* 2411 */       if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 2412 */         String endToken; if (recursionLimit < 1) {
/* 2413 */           throw tokenizer.parseException("Message is nested too deep");
/*      */         }
/*      */ 
/*      */         
/* 2417 */         if (tokenizer.tryConsume("<")) {
/* 2418 */           endToken = ">";
/*      */         } else {
/* 2420 */           tokenizer.consume("{");
/* 2421 */           endToken = "}";
/*      */         } 
/*      */         
/* 2424 */         Message defaultInstance = (extension == null) ? null : extension.defaultInstance;
/*      */         
/* 2426 */         MessageReflection.MergeTarget subField = target.newMergeTargetForField(field, defaultInstance);
/*      */         
/* 2428 */         while (!tokenizer.tryConsume(endToken)) {
/* 2429 */           if (tokenizer.atEnd()) {
/* 2430 */             throw tokenizer.parseException("Expected \"" + endToken + "\".");
/*      */           }
/* 2432 */           mergeField(tokenizer, extensionRegistry, subField, parseTreeBuilder, unknownFields, recursionLimit - 1);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2441 */         value = subField.finish();
/*      */       } else {
/* 2443 */         Descriptors.EnumDescriptor enumType; String id; switch (field.getType()) {
/*      */           case INT32:
/*      */           case SINT32:
/*      */           case SFIXED32:
/* 2447 */             value = Integer.valueOf(tokenizer.consumeInt32());
/*      */             break;
/*      */           
/*      */           case INT64:
/*      */           case SINT64:
/*      */           case SFIXED64:
/* 2453 */             value = Long.valueOf(tokenizer.consumeInt64());
/*      */             break;
/*      */           
/*      */           case UINT32:
/*      */           case FIXED32:
/* 2458 */             value = Integer.valueOf(tokenizer.consumeUInt32());
/*      */             break;
/*      */           
/*      */           case UINT64:
/*      */           case FIXED64:
/* 2463 */             value = Long.valueOf(tokenizer.consumeUInt64());
/*      */             break;
/*      */           
/*      */           case FLOAT:
/* 2467 */             value = Float.valueOf(tokenizer.consumeFloat());
/*      */             break;
/*      */           
/*      */           case DOUBLE:
/* 2471 */             value = Double.valueOf(tokenizer.consumeDouble());
/*      */             break;
/*      */           
/*      */           case BOOL:
/* 2475 */             value = Boolean.valueOf(tokenizer.consumeBoolean());
/*      */             break;
/*      */           
/*      */           case STRING:
/* 2479 */             value = tokenizer.consumeString();
/*      */             break;
/*      */           
/*      */           case BYTES:
/* 2483 */             value = tokenizer.consumeByteString();
/*      */             break;
/*      */           
/*      */           case ENUM:
/* 2487 */             enumType = field.getEnumType();
/*      */             
/* 2489 */             if (tokenizer.lookingAtInteger()) {
/* 2490 */               int number = tokenizer.consumeInt32();
/*      */ 
/*      */ 
/*      */               
/* 2494 */               value = enumType.isClosed() ? enumType.findValueByNumber(number) : enumType.findValueByNumberCreatingIfUnknown(number);
/* 2495 */               if (value == null) {
/*      */ 
/*      */                 
/* 2498 */                 String unknownValueMsg = "Enum type \"" + enumType.getFullName() + "\" has no value with number " + number + '.';
/*      */ 
/*      */ 
/*      */                 
/* 2502 */                 if (this.allowUnknownEnumValues) {
/* 2503 */                   TextFormat.logger.warning(unknownValueMsg);
/*      */                   return;
/*      */                 } 
/* 2506 */                 throw tokenizer.parseExceptionPreviousToken("Enum type \"" + enumType
/*      */                     
/* 2508 */                     .getFullName() + "\" has no value with number " + number + '.');
/*      */               } 
/*      */ 
/*      */               
/*      */               break;
/*      */             } 
/*      */             
/* 2515 */             id = tokenizer.consumeIdentifier();
/* 2516 */             value = enumType.findValueByName(id);
/* 2517 */             if (value == null) {
/*      */ 
/*      */               
/* 2520 */               String unknownValueMsg = "Enum type \"" + enumType.getFullName() + "\" has no value named \"" + id + "\".";
/*      */ 
/*      */ 
/*      */               
/* 2524 */               if (this.allowUnknownEnumValues) {
/* 2525 */                 TextFormat.logger.warning(unknownValueMsg);
/*      */                 return;
/*      */               } 
/* 2528 */               throw tokenizer.parseExceptionPreviousToken(unknownValueMsg);
/*      */             } 
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case MESSAGE:
/*      */           case GROUP:
/* 2537 */             throw new RuntimeException("Can't get here.");
/*      */         } 
/*      */       
/*      */       } 
/* 2541 */       if (field.isRepeated()) {
/*      */ 
/*      */         
/* 2544 */         target.addRepeatedField(field, value);
/*      */       } else {
/* 2546 */         target.setField(field, value);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void mergeAnyFieldValue(TextFormat.Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MessageReflection.MergeTarget target, TextFormatParseInfoTree.Builder parseTreeBuilder, List<UnknownField> unknownFields, Descriptors.Descriptor anyDescriptor, int recursionLimit) throws TextFormat.ParseException {
/*      */       String anyEndToken;
/* 2560 */       StringBuilder typeUrlBuilder = new StringBuilder();
/*      */       
/*      */       while (true) {
/* 2563 */         typeUrlBuilder.append(tokenizer.consumeIdentifier());
/* 2564 */         if (tokenizer.tryConsume("]")) {
/*      */           break;
/*      */         }
/* 2567 */         if (tokenizer.tryConsume("/")) {
/* 2568 */           typeUrlBuilder.append("/"); continue;
/* 2569 */         }  if (tokenizer.tryConsume(".")) {
/* 2570 */           typeUrlBuilder.append("."); continue;
/*      */         } 
/* 2572 */         throw tokenizer.parseExceptionPreviousToken("Expected a valid type URL.");
/*      */       } 
/*      */       
/* 2575 */       detectSilentMarker(tokenizer, anyDescriptor, typeUrlBuilder.toString());
/* 2576 */       tokenizer.tryConsume(":");
/*      */       
/* 2578 */       if (tokenizer.tryConsume("<")) {
/* 2579 */         anyEndToken = ">";
/*      */       } else {
/* 2581 */         tokenizer.consume("{");
/* 2582 */         anyEndToken = "}";
/*      */       } 
/* 2584 */       String typeUrl = typeUrlBuilder.toString();
/* 2585 */       Descriptors.Descriptor contentType = null;
/*      */       try {
/* 2587 */         contentType = this.typeRegistry.getDescriptorForTypeUrl(typeUrl);
/* 2588 */       } catch (InvalidProtocolBufferException e) {
/* 2589 */         throw tokenizer.parseException("Invalid valid type URL. Found: " + typeUrl);
/*      */       } 
/* 2591 */       if (contentType == null) {
/* 2592 */         throw tokenizer.parseException("Unable to parse Any of type: " + typeUrl + ". Please make sure that the TypeRegistry contains the descriptors for the given types.");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2599 */       Message.Builder contentBuilder = DynamicMessage.getDefaultInstance(contentType).newBuilderForType();
/* 2600 */       MessageReflection.BuilderAdapter contentTarget = new MessageReflection.BuilderAdapter(contentBuilder);
/*      */       
/* 2602 */       while (!tokenizer.tryConsume(anyEndToken)) {
/* 2603 */         mergeField(tokenizer, extensionRegistry, contentTarget, parseTreeBuilder, unknownFields, recursionLimit);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2612 */       target.setField(anyDescriptor.findFieldByName("type_url"), typeUrlBuilder.toString());
/* 2613 */       target.setField(anyDescriptor
/* 2614 */           .findFieldByName("value"), contentBuilder.build().toByteString());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void skipField(TextFormat.Tokenizer tokenizer, Descriptors.Descriptor type, int recursionLimit) throws TextFormat.ParseException {
/* 2620 */       String name = consumeFullTypeName(tokenizer);
/* 2621 */       detectSilentMarker(tokenizer, type, name);
/* 2622 */       guessFieldTypeAndSkip(tokenizer, type, recursionLimit);
/*      */ 
/*      */ 
/*      */       
/* 2626 */       if (!tokenizer.tryConsume(";")) {
/* 2627 */         tokenizer.tryConsume(",");
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void skipFieldMessage(TextFormat.Tokenizer tokenizer, Descriptors.Descriptor type, int recursionLimit) throws TextFormat.ParseException {
/*      */       String delimiter;
/* 2637 */       if (tokenizer.tryConsume("<")) {
/* 2638 */         delimiter = ">";
/*      */       } else {
/* 2640 */         tokenizer.consume("{");
/* 2641 */         delimiter = "}";
/*      */       } 
/* 2643 */       while (!tokenizer.lookingAt(">") && !tokenizer.lookingAt("}")) {
/* 2644 */         skipField(tokenizer, type, recursionLimit);
/*      */       }
/* 2646 */       tokenizer.consume(delimiter);
/*      */     }
/*      */ 
/*      */     
/*      */     private void skipFieldValue(TextFormat.Tokenizer tokenizer) throws TextFormat.ParseException {
/* 2651 */       if (!tokenizer.tryConsumeByteString() && 
/* 2652 */         !tokenizer.tryConsumeIdentifier() && 
/* 2653 */         !tokenizer.tryConsumeInt64() && 
/* 2654 */         !tokenizer.tryConsumeUInt64() && 
/* 2655 */         !tokenizer.tryConsumeDouble() && 
/* 2656 */         !tokenizer.tryConsumeFloat()) {
/* 2657 */         throw tokenizer.parseException("Invalid field value: " + tokenizer.currentToken);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void guessFieldTypeAndSkip(TextFormat.Tokenizer tokenizer, Descriptors.Descriptor type, int recursionLimit) throws TextFormat.ParseException {
/* 2672 */       boolean semicolonConsumed = tokenizer.tryConsume(":");
/* 2673 */       if (tokenizer.lookingAt("[")) {
/*      */ 
/*      */         
/* 2676 */         skipFieldShortFormedRepeated(tokenizer, semicolonConsumed, type, recursionLimit);
/* 2677 */       } else if (semicolonConsumed && !tokenizer.lookingAt("{") && !tokenizer.lookingAt("<")) {
/* 2678 */         skipFieldValue(tokenizer);
/*      */       } else {
/* 2680 */         if (recursionLimit < 1) {
/* 2681 */           throw tokenizer.parseException("Message is nested too deep");
/*      */         }
/* 2683 */         skipFieldMessage(tokenizer, type, recursionLimit - 1);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void skipFieldShortFormedRepeated(TextFormat.Tokenizer tokenizer, boolean scalarAllowed, Descriptors.Descriptor type, int recursionLimit) throws TextFormat.ParseException {
/* 2695 */       if (!tokenizer.tryConsume("[") || tokenizer.tryConsume("]")) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*      */       while (true) {
/* 2701 */         if (tokenizer.lookingAt("{") || tokenizer.lookingAt("<")) {
/*      */           
/* 2703 */           if (recursionLimit < 1) {
/* 2704 */             throw tokenizer.parseException("Message is nested too deep");
/*      */           }
/* 2706 */           skipFieldMessage(tokenizer, type, recursionLimit - 1);
/* 2707 */         } else if (scalarAllowed) {
/*      */           
/* 2709 */           skipFieldValue(tokenizer);
/*      */         } else {
/* 2711 */           throw tokenizer.parseException("Invalid repeated scalar field: missing \":\" before \"[\".");
/*      */         } 
/*      */         
/* 2714 */         if (tokenizer.tryConsume("]")) {
/*      */           break;
/*      */         }
/* 2717 */         tokenizer.consume(",");
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String escapeBytes(ByteString input) {
/* 2735 */     return TextFormatEscaper.escapeBytes(input);
/*      */   }
/*      */   public static class Builder {
/*      */     private boolean allowUnknownFields = false;
/*      */     private boolean allowUnknownEnumValues = false;
/* 2740 */     private boolean allowUnknownExtensions = false; private TextFormat.Parser.SingularOverwritePolicy singularOverwritePolicy = TextFormat.Parser.SingularOverwritePolicy.ALLOW_SINGULAR_OVERWRITES; private TextFormatParseInfoTree.Builder parseInfoTreeBuilder = null; private TypeRegistry typeRegistry = TypeRegistry.getEmptyTypeRegistry(); private int recursionLimit = 100; public Builder setTypeRegistry(TypeRegistry typeRegistry) { this.typeRegistry = typeRegistry; return this; } public Builder setAllowUnknownFields(boolean allowUnknownFields) { this.allowUnknownFields = allowUnknownFields; return this; } public Builder setAllowUnknownExtensions(boolean allowUnknownExtensions) { this.allowUnknownExtensions = allowUnknownExtensions; return this; } public Builder setSingularOverwritePolicy(TextFormat.Parser.SingularOverwritePolicy p) { this.singularOverwritePolicy = p; return this; } public Builder setParseInfoTreeBuilder(TextFormatParseInfoTree.Builder parseInfoTreeBuilder) { this.parseInfoTreeBuilder = parseInfoTreeBuilder; return this; } public Builder setRecursionLimit(int recursionLimit) { this.recursionLimit = recursionLimit; return this; } public TextFormat.Parser build() { return new TextFormat.Parser(this.typeRegistry, this.allowUnknownFields, this.allowUnknownEnumValues, this.allowUnknownExtensions, this.singularOverwritePolicy, this.parseInfoTreeBuilder, this.recursionLimit); } } public static String escapeBytes(byte[] input) { return TextFormatEscaper.escapeBytes(input); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteString unescapeBytes(CharSequence charString) throws InvalidEscapeSequenceException {
/* 2750 */     ByteString input = ByteString.copyFromUtf8(charString.toString());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2758 */     byte[] result = new byte[input.size()];
/* 2759 */     int pos = 0;
/* 2760 */     for (int i = 0; i < input.size(); i++) {
/* 2761 */       byte c = input.byteAt(i);
/* 2762 */       if (c == 92) {
/* 2763 */         if (i + 1 < input.size()) {
/* 2764 */           i++;
/* 2765 */           c = input.byteAt(i);
/* 2766 */           if (isOctal(c)) {
/*      */             
/* 2768 */             int code = digitValue(c);
/* 2769 */             if (i + 1 < input.size() && isOctal(input.byteAt(i + 1))) {
/* 2770 */               i++;
/* 2771 */               code = code * 8 + digitValue(input.byteAt(i));
/*      */             } 
/* 2773 */             if (i + 1 < input.size() && isOctal(input.byteAt(i + 1))) {
/* 2774 */               i++;
/* 2775 */               code = code * 8 + digitValue(input.byteAt(i));
/*      */             } 
/*      */             
/* 2778 */             result[pos++] = (byte)code;
/*      */           } else {
/* 2780 */             int code; int codepoint; int offset; Character.UnicodeBlock unicodeBlock; int[] codepoints; byte[] chUtf8; switch (c) {
/*      */               case 97:
/* 2782 */                 result[pos++] = 7;
/*      */                 break;
/*      */               case 98:
/* 2785 */                 result[pos++] = 8;
/*      */                 break;
/*      */               case 102:
/* 2788 */                 result[pos++] = 12;
/*      */                 break;
/*      */               case 110:
/* 2791 */                 result[pos++] = 10;
/*      */                 break;
/*      */               case 114:
/* 2794 */                 result[pos++] = 13;
/*      */                 break;
/*      */               case 116:
/* 2797 */                 result[pos++] = 9;
/*      */                 break;
/*      */               case 118:
/* 2800 */                 result[pos++] = 11;
/*      */                 break;
/*      */               case 92:
/* 2803 */                 result[pos++] = 92;
/*      */                 break;
/*      */               case 39:
/* 2806 */                 result[pos++] = 39;
/*      */                 break;
/*      */               case 34:
/* 2809 */                 result[pos++] = 34;
/*      */                 break;
/*      */               case 63:
/* 2812 */                 result[pos++] = 63;
/*      */                 break;
/*      */ 
/*      */               
/*      */               case 120:
/* 2817 */                 code = 0;
/* 2818 */                 if (i + 1 < input.size() && isHex(input.byteAt(i + 1))) {
/* 2819 */                   i++;
/* 2820 */                   code = digitValue(input.byteAt(i));
/*      */                 } else {
/* 2822 */                   throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\x' with no digits");
/*      */                 } 
/*      */                 
/* 2825 */                 if (i + 1 < input.size() && isHex(input.byteAt(i + 1))) {
/* 2826 */                   i++;
/* 2827 */                   code = code * 16 + digitValue(input.byteAt(i));
/*      */                 } 
/* 2829 */                 result[pos++] = (byte)code;
/*      */                 break;
/*      */ 
/*      */               
/*      */               case 117:
/* 2834 */                 i++;
/* 2835 */                 if (i + 3 < input.size() && 
/* 2836 */                   isHex(input.byteAt(i)) && 
/* 2837 */                   isHex(input.byteAt(i + 1)) && 
/* 2838 */                   isHex(input.byteAt(i + 2)) && 
/* 2839 */                   isHex(input.byteAt(i + 3))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2845 */                   char ch = (char)(digitValue(input.byteAt(i)) << 12 | digitValue(input.byteAt(i + 1)) << 8 | digitValue(input.byteAt(i + 2)) << 4 | digitValue(input.byteAt(i + 3)));
/*      */                   
/* 2847 */                   if (ch >= '?' && ch <= '?') {
/* 2848 */                     throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\u' refers to a surrogate");
/*      */                   }
/*      */                   
/* 2851 */                   byte[] arrayOfByte = Character.toString(ch).getBytes(Internal.UTF_8);
/* 2852 */                   System.arraycopy(arrayOfByte, 0, result, pos, arrayOfByte.length);
/* 2853 */                   pos += arrayOfByte.length;
/* 2854 */                   i += 3; break;
/*      */                 } 
/* 2856 */                 throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\u' with too few hex chars");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               case 85:
/* 2863 */                 i++;
/* 2864 */                 if (i + 7 >= input.size()) {
/* 2865 */                   throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\U' with too few hex chars");
/*      */                 }
/*      */                 
/* 2868 */                 codepoint = 0;
/* 2869 */                 for (offset = i; offset < i + 8; offset++) {
/* 2870 */                   byte b = input.byteAt(offset);
/* 2871 */                   if (!isHex(b)) {
/* 2872 */                     throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\U' with too few hex chars");
/*      */                   }
/*      */                   
/* 2875 */                   codepoint = codepoint << 4 | digitValue(b);
/*      */                 } 
/* 2877 */                 if (!Character.isValidCodePoint(codepoint)) {
/* 2878 */                   throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\U" + input
/*      */                       
/* 2880 */                       .substring(i, i + 8).toStringUtf8() + "' is not a valid code point value");
/*      */                 }
/*      */                 
/* 2883 */                 unicodeBlock = Character.UnicodeBlock.of(codepoint);
/* 2884 */                 if (unicodeBlock != null && (unicodeBlock
/* 2885 */                   .equals(Character.UnicodeBlock.LOW_SURROGATES) || unicodeBlock
/* 2886 */                   .equals(Character.UnicodeBlock.HIGH_SURROGATES) || unicodeBlock
/* 2887 */                   .equals(Character.UnicodeBlock.HIGH_PRIVATE_USE_SURROGATES)))
/*      */                 {
/* 2889 */                   throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\U" + input
/*      */                       
/* 2891 */                       .substring(i, i + 8).toStringUtf8() + "' refers to a surrogate code unit");
/*      */                 }
/*      */                 
/* 2894 */                 codepoints = new int[1];
/* 2895 */                 codepoints[0] = codepoint;
/* 2896 */                 chUtf8 = (new String(codepoints, 0, 1)).getBytes(Internal.UTF_8);
/* 2897 */                 System.arraycopy(chUtf8, 0, result, pos, chUtf8.length);
/* 2898 */                 pos += chUtf8.length;
/* 2899 */                 i += 7;
/*      */                 break;
/*      */               
/*      */               default:
/* 2903 */                 throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\" + (char)c + '\'');
/*      */             } 
/*      */           
/*      */           } 
/*      */         } else {
/* 2908 */           throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\' at end of string.");
/*      */         } 
/*      */       } else {
/*      */         
/* 2912 */         result[pos++] = c;
/*      */       } 
/*      */     } 
/*      */     
/* 2916 */     return (result.length == pos) ? 
/* 2917 */       ByteString.wrap(result) : 
/* 2918 */       ByteString.copyFrom(result, 0, pos);
/*      */   }
/*      */ 
/*      */   
/*      */   public static class InvalidEscapeSequenceException
/*      */     extends IOException
/*      */   {
/*      */     private static final long serialVersionUID = -8164033650142593304L;
/*      */ 
/*      */     
/*      */     InvalidEscapeSequenceException(String description) {
/* 2929 */       super(description);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String escapeText(String input) {
/* 2939 */     return escapeBytes(ByteString.copyFromUtf8(input));
/*      */   }
/*      */ 
/*      */   
/*      */   public static String escapeDoubleQuotesAndBackslashes(String input) {
/* 2944 */     return TextFormatEscaper.escapeDoubleQuotesAndBackslashes(input);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String unescapeText(String input) throws InvalidEscapeSequenceException {
/* 2952 */     return unescapeBytes(input).toStringUtf8();
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isOctal(byte c) {
/* 2957 */     return (48 <= c && c <= 55);
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isHex(byte c) {
/* 2962 */     return ((48 <= c && c <= 57) || (97 <= c && c <= 102) || (65 <= c && c <= 70));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int digitValue(byte c) {
/* 2970 */     if (48 <= c && c <= 57)
/* 2971 */       return c - 48; 
/* 2972 */     if (97 <= c && c <= 122) {
/* 2973 */       return c - 97 + 10;
/*      */     }
/* 2975 */     return c - 65 + 10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int parseInt32(String text) throws NumberFormatException {
/* 2985 */     return (int)parseInteger(text, true, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int parseUInt32(String text) throws NumberFormatException {
/* 2995 */     return (int)parseInteger(text, false, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static long parseInt64(String text) throws NumberFormatException {
/* 3004 */     return parseInteger(text, true, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static long parseUInt64(String text) throws NumberFormatException {
/* 3014 */     return parseInteger(text, false, true);
/*      */   }
/*      */ 
/*      */   
/*      */   private static long parseInteger(String text, boolean isSigned, boolean isLong) throws NumberFormatException {
/* 3019 */     int pos = 0;
/*      */     
/* 3021 */     boolean negative = false;
/* 3022 */     if (text.startsWith("-", pos)) {
/* 3023 */       if (!isSigned) {
/* 3024 */         throw new NumberFormatException("Number must be positive: " + text);
/*      */       }
/* 3026 */       pos++;
/* 3027 */       negative = true;
/*      */     } 
/*      */     
/* 3030 */     int radix = 10;
/* 3031 */     if (text.startsWith("0x", pos)) {
/* 3032 */       pos += 2;
/* 3033 */       radix = 16;
/* 3034 */     } else if (text.startsWith("0", pos)) {
/* 3035 */       radix = 8;
/*      */     } 
/*      */     
/* 3038 */     String numberText = text.substring(pos);
/*      */     
/* 3040 */     long result = 0L;
/* 3041 */     if (numberText.length() < 16) {
/*      */       
/* 3043 */       result = Long.parseLong(numberText, radix);
/* 3044 */       if (negative) {
/* 3045 */         result = -result;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3051 */       if (!isLong) {
/* 3052 */         if (isSigned) {
/* 3053 */           if (result > 2147483647L || result < -2147483648L) {
/* 3054 */             throw new NumberFormatException("Number out of range for 32-bit signed integer: " + text);
/*      */           
/*      */           }
/*      */         }
/* 3058 */         else if (result >= 4294967296L || result < 0L) {
/* 3059 */           throw new NumberFormatException("Number out of range for 32-bit unsigned integer: " + text);
/*      */         }
/*      */       
/*      */       }
/*      */     } else {
/*      */       
/* 3065 */       BigInteger bigValue = new BigInteger(numberText, radix);
/* 3066 */       if (negative) {
/* 3067 */         bigValue = bigValue.negate();
/*      */       }
/*      */ 
/*      */       
/* 3071 */       if (!isLong) {
/* 3072 */         if (isSigned) {
/* 3073 */           if (bigValue.bitLength() > 31) {
/* 3074 */             throw new NumberFormatException("Number out of range for 32-bit signed integer: " + text);
/*      */           
/*      */           }
/*      */         }
/* 3078 */         else if (bigValue.bitLength() > 32) {
/* 3079 */           throw new NumberFormatException("Number out of range for 32-bit unsigned integer: " + text);
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 3084 */       else if (isSigned) {
/* 3085 */         if (bigValue.bitLength() > 63) {
/* 3086 */           throw new NumberFormatException("Number out of range for 64-bit signed integer: " + text);
/*      */         
/*      */         }
/*      */       }
/* 3090 */       else if (bigValue.bitLength() > 64) {
/* 3091 */         throw new NumberFormatException("Number out of range for 64-bit unsigned integer: " + text);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3097 */       result = bigValue.longValue();
/*      */     } 
/*      */     
/* 3100 */     return result;
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\TextFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */