/*     */ package com.hypixel.hytale.server.core.ui.builder;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.CustomUICommand;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.CustomUICommandType;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.ui.Anchor;
/*     */ import com.hypixel.hytale.server.core.ui.DropdownEntryInfo;
/*     */ import com.hypixel.hytale.server.core.ui.ItemGridSlot;
/*     */ import com.hypixel.hytale.server.core.ui.LocalizableString;
/*     */ import com.hypixel.hytale.server.core.ui.Value;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import org.bson.BsonArray;
/*     */ import org.bson.BsonDocument;
/*     */ import org.bson.BsonDouble;
/*     */ import org.bson.BsonValue;
/*     */ 
/*     */ public class UICommandBuilder {
/*  19 */   private static final Map<Class, Codec> CODEC_MAP = (Map<Class, Codec>)new Object2ObjectOpenHashMap();
/*  20 */   public static final CustomUICommand[] EMPTY_COMMAND_ARRAY = new CustomUICommand[0];
/*     */   @Nonnull
/*  22 */   private final List<CustomUICommand> commands = (List<CustomUICommand>)new ObjectArrayList();
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public UICommandBuilder clear(String selector) {
/*  27 */     this.commands.add(new CustomUICommand(CustomUICommandType.Clear, selector, null, null));
/*  28 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public UICommandBuilder remove(String selector) {
/*  33 */     this.commands.add(new CustomUICommand(CustomUICommandType.Remove, selector, null, null));
/*  34 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public UICommandBuilder append(String documentPath) {
/*  39 */     this.commands.add(new CustomUICommand(CustomUICommandType.Append, null, null, documentPath));
/*  40 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public UICommandBuilder append(String selector, String documentPath) {
/*  45 */     this.commands.add(new CustomUICommand(CustomUICommandType.Append, selector, null, documentPath));
/*  46 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public UICommandBuilder appendInline(String selector, String document) {
/*  51 */     this.commands.add(new CustomUICommand(CustomUICommandType.AppendInline, selector, null, document));
/*  52 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public UICommandBuilder insertBefore(String selector, String documentPath) {
/*  57 */     this.commands.add(new CustomUICommand(CustomUICommandType.InsertBefore, selector, null, documentPath));
/*  58 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public UICommandBuilder insertBeforeInline(String selector, String document) {
/*  63 */     this.commands.add(new CustomUICommand(CustomUICommandType.InsertBeforeInline, selector, null, document));
/*  64 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   @Deprecated
/*     */   private UICommandBuilder setBsonValue(String selector, BsonValue bsonValue) {
/*  71 */     BsonDocument valueWrapper = new BsonDocument();
/*  72 */     valueWrapper.put("0", bsonValue);
/*  73 */     this.commands.add(new CustomUICommand(CustomUICommandType.Set, selector, valueWrapper.toJson(), null));
/*  74 */     return this;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public <T> UICommandBuilder set(String selector, @Nonnull Value<T> ref) {
/*  79 */     if (ref.getValue() != null) throw new IllegalArgumentException("Method only accepts references without a direct value"); 
/*  80 */     return setBsonValue(selector, ValueCodec.REFERENCE_ONLY.encode(ref));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public UICommandBuilder setNull(String selector) {
/*  85 */     return setBsonValue(selector, (BsonValue)BsonNull.VALUE);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public UICommandBuilder set(String selector, @Nonnull String str) {
/*  90 */     return setBsonValue(selector, (BsonValue)new BsonString(str));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public UICommandBuilder set(String selector, @Nonnull Message message) {
/*  95 */     return setBsonValue(selector, Message.CODEC.encode(message, (ExtraInfo)EmptyExtraInfo.EMPTY));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public UICommandBuilder set(String selector, boolean b) {
/* 100 */     return setBsonValue(selector, (BsonValue)new BsonBoolean(b));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public UICommandBuilder set(String selector, float n) {
/* 105 */     return setBsonValue(selector, (BsonValue)new BsonDouble(n));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public UICommandBuilder set(String selector, int n) {
/* 110 */     return setBsonValue(selector, (BsonValue)new BsonInt32(n));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public UICommandBuilder set(String selector, double n) {
/* 115 */     return setBsonValue(selector, (BsonValue)new BsonDouble(n));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public UICommandBuilder setObject(String selector, @Nonnull Object data) {
/* 120 */     Codec codec = CODEC_MAP.get(data.getClass());
/* 121 */     if (codec == null) throw new IllegalArgumentException(data.getClass().getName() + " is not a compatible class");
/*     */     
/* 123 */     return setBsonValue(selector, codec.encode(data));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public <T> UICommandBuilder set(String selector, @Nonnull T[] data) {
/* 128 */     Codec codec = CODEC_MAP.get(data.getClass().getComponentType());
/* 129 */     if (codec == null) throw new IllegalArgumentException(data.getClass().getName() + " is not a compatible class");
/*     */     
/* 131 */     BsonArray arr = new BsonArray();
/* 132 */     for (T d : data) arr.add(codec.encode(d)); 
/* 133 */     return setBsonValue(selector, (BsonValue)arr);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public <T> UICommandBuilder set(String selector, @Nonnull List<T> data) {
/* 138 */     Codec<T> codec = null;
/*     */     
/* 140 */     BsonArray arr = new BsonArray();
/* 141 */     for (T d : data) {
/* 142 */       if (codec == null) {
/* 143 */         codec = CODEC_MAP.get(d.getClass());
/* 144 */         if (codec == null) throw new IllegalArgumentException(data.getClass().getName() + " is not a compatible class");
/*     */       
/*     */       } 
/* 147 */       arr.add(codec.encode(d));
/*     */     } 
/* 149 */     return setBsonValue(selector, (BsonValue)arr);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public CustomUICommand[] getCommands() {
/* 154 */     return (CustomUICommand[])this.commands.toArray(x$0 -> new CustomUICommand[x$0]);
/*     */   }
/*     */   
/*     */   static {
/* 158 */     CODEC_MAP.put(Area.class, Area.CODEC);
/* 159 */     CODEC_MAP.put(ItemGridSlot.class, ItemGridSlot.CODEC);
/* 160 */     CODEC_MAP.put(ItemStack.class, ItemStack.CODEC);
/* 161 */     CODEC_MAP.put(LocalizableString.class, LocalizableString.CODEC);
/* 162 */     CODEC_MAP.put(PatchStyle.class, PatchStyle.CODEC);
/* 163 */     CODEC_MAP.put(DropdownEntryInfo.class, DropdownEntryInfo.CODEC);
/* 164 */     CODEC_MAP.put(Anchor.class, Anchor.CODEC);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\ui\builder\UICommandBuilder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */