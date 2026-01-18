/*     */ package com.hypixel.hytale.codec.schema.metadata.ui;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*     */ import com.hypixel.hytale.codec.schema.config.Schema;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class UIEditor implements Metadata {
/*  13 */   public static final CodecMapCodec<EditorComponent> CODEC = new CodecMapCodec("component");
/*  14 */   public static final Timeline TIMELINE = new Timeline();
/*  15 */   public static final WeightedTimeline WEIGHTED_TIMELINE = new WeightedTimeline();
/*     */   
/*     */   private final EditorComponent component;
/*     */   
/*     */   public UIEditor(EditorComponent component) {
/*  20 */     this.component = component;
/*     */   }
/*     */ 
/*     */   
/*     */   public void modify(@Nonnull Schema schema) {
/*  25 */     schema.getHytale().setUiEditorComponent(this.component);
/*     */   }
/*     */   
/*     */   public static void init() {
/*  29 */     CODEC.register("Timeline", Timeline.class, (Codec)Timeline.CODEC);
/*  30 */     CODEC.register("WeightedTimeline", WeightedTimeline.class, (Codec)WeightedTimeline.CODEC);
/*  31 */     CODEC.register("Number", FormattedNumber.class, (Codec)FormattedNumber.CODEC);
/*  32 */     CODEC.register("Text", TextField.class, (Codec)TextField.CODEC);
/*  33 */     CODEC.register("MultilineText", MultilineTextField.class, (Codec)MultilineTextField.CODEC);
/*  34 */     CODEC.register("Dropdown", Dropdown.class, (Codec)Dropdown.CODEC);
/*  35 */     CODEC.register("Icon", Icon.class, (Codec)Icon.CODEC);
/*  36 */     CODEC.register("LocalizationKey", LocalizationKeyField.class, (Codec)LocalizationKeyField.CODEC);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Timeline
/*     */     implements EditorComponent
/*     */   {
/*  43 */     public static final BuilderCodec<Timeline> CODEC = BuilderCodec.builder(Timeline.class, Timeline::new)
/*  44 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Icon
/*     */     implements EditorComponent
/*     */   {
/*     */     public static final BuilderCodec<Icon> CODEC;
/*     */ 
/*     */     
/*     */     private String defaultPathTemplate;
/*     */ 
/*     */     
/*     */     private int width;
/*     */     
/*     */     private int height;
/*     */ 
/*     */     
/*     */     static {
/*  64 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Icon.class, Icon::new).addField(new KeyedCodec("defaultPathTemplate", (Codec)Codec.STRING, true, true), (o, i) -> o.defaultPathTemplate = i, o -> o.defaultPathTemplate)).addField(new KeyedCodec("width", (Codec)Codec.INTEGER, true, true), (o, i) -> o.width = i.intValue(), o -> Integer.valueOf(o.width))).addField(new KeyedCodec("height", (Codec)Codec.INTEGER, true, true), (o, i) -> o.height = i.intValue(), o -> Integer.valueOf(o.height))).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Icon(String defaultPathTemplate, int width, int height) {
/*  75 */       this.defaultPathTemplate = defaultPathTemplate;
/*  76 */       this.width = width;
/*  77 */       this.height = height;
/*     */     }
/*     */     
/*     */     public Icon() {}
/*     */   }
/*     */   
/*     */   public static class WeightedTimeline implements EditorComponent {
/*  84 */     public static final BuilderCodec<WeightedTimeline> CODEC = BuilderCodec.builder(WeightedTimeline.class, WeightedTimeline::new)
/*  85 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class FormattedNumber
/*     */     implements EditorComponent
/*     */   {
/*     */     public static final BuilderCodec<FormattedNumber> CODEC;
/*     */ 
/*     */     
/*     */     private Double step;
/*     */ 
/*     */     
/*     */     private String suffix;
/*     */     
/*     */     private Integer maxDecimalPlaces;
/*     */ 
/*     */     
/*     */     static {
/* 105 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FormattedNumber.class, FormattedNumber::new).addField(new KeyedCodec("step", (Codec)Codec.DOUBLE, false, true), (o, i) -> o.step = i, o -> o.step)).addField(new KeyedCodec("suffix", (Codec)Codec.STRING, false, true), (o, i) -> o.suffix = i, o -> o.suffix)).addField(new KeyedCodec("maxDecimalPlaces", (Codec)Codec.INTEGER, false, true), (o, i) -> o.maxDecimalPlaces = i, o -> o.maxDecimalPlaces)).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public FormattedNumber(Double step, String suffix, Integer maxDecimalPlaces) {
/* 112 */       this.step = step;
/* 113 */       this.suffix = suffix;
/* 114 */       this.maxDecimalPlaces = maxDecimalPlaces;
/*     */     }
/*     */ 
/*     */     
/*     */     public FormattedNumber() {}
/*     */     
/*     */     @Nonnull
/*     */     public FormattedNumber setStep(Double step) {
/* 122 */       this.step = step;
/* 123 */       return this;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public FormattedNumber setSuffix(String suffix) {
/* 128 */       this.suffix = suffix;
/* 129 */       return this;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public FormattedNumber setMaxDecimalPlaces(Integer maxDecimalPlaces) {
/* 134 */       this.maxDecimalPlaces = maxDecimalPlaces;
/* 135 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Dropdown
/*     */     implements EditorComponent
/*     */   {
/*     */     public static final BuilderCodec<Dropdown> CODEC;
/*     */     private String dataSet;
/*     */     
/*     */     static {
/* 146 */       CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(Dropdown.class, Dropdown::new).addField(new KeyedCodec("dataSet", (Codec)Codec.STRING, false, true), (o, i) -> o.dataSet = i, o -> o.dataSet)).build();
/*     */     }
/*     */ 
/*     */     
/*     */     public Dropdown(String dataSet) {
/* 151 */       this.dataSet = dataSet;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Dropdown() {}
/*     */   }
/*     */   
/*     */   public static class TextField
/*     */     implements EditorComponent
/*     */   {
/*     */     public static final BuilderCodec<TextField> CODEC;
/*     */     private String dataSet;
/*     */     
/*     */     static {
/* 165 */       CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(TextField.class, TextField::new).addField(new KeyedCodec("dataSet", (Codec)Codec.STRING, false, true), (o, i) -> o.dataSet = i, o -> o.dataSet)).build();
/*     */     }
/*     */ 
/*     */     
/*     */     public TextField(String dataSet) {
/* 170 */       this.dataSet = dataSet;
/*     */     }
/*     */     
/*     */     protected TextField() {}
/*     */   }
/*     */   
/*     */   public static class MultilineTextField
/*     */     implements EditorComponent {
/* 178 */     public static final BuilderCodec<MultilineTextField> CODEC = BuilderCodec.builder(MultilineTextField.class, MultilineTextField::new)
/* 179 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class LocalizationKeyField
/*     */     implements EditorComponent
/*     */   {
/*     */     public static final BuilderCodec<LocalizationKeyField> CODEC;
/*     */ 
/*     */     
/*     */     private String keyTemplate;
/*     */ 
/*     */     
/*     */     private boolean generateDefaultKey;
/*     */ 
/*     */     
/*     */     static {
/* 197 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(LocalizationKeyField.class, LocalizationKeyField::new).addField(new KeyedCodec("keyTemplate", (Codec)Codec.STRING, false, true), (o, i) -> o.keyTemplate = i, o -> o.keyTemplate)).addField(new KeyedCodec("generateDefaultKey", (Codec)Codec.BOOLEAN, false, true), (o, i) -> o.generateDefaultKey = i.booleanValue(), o -> Boolean.valueOf(o.generateDefaultKey))).build();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public LocalizationKeyField(String keyTemplate) {
/* 203 */       this(keyTemplate, false);
/*     */     }
/*     */     
/*     */     public LocalizationKeyField(String keyTemplate, boolean generateDefaultKey) {
/* 207 */       this.keyTemplate = keyTemplate;
/* 208 */       this.generateDefaultKey = generateDefaultKey;
/*     */     }
/*     */     
/*     */     public LocalizationKeyField() {}
/*     */   }
/*     */   
/*     */   public static interface EditorComponent {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\schema\metadat\\ui\UIEditor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */