/*     */ package com.hypixel.hytale.server.core.modules.collision.commands;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*     */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HitboxCommand
/*     */   extends AbstractCommandCollection
/*     */ {
/*     */   public HitboxCommand() {
/*  34 */     super("hitbox", "server.commands.hitbox.desc");
/*  35 */     addSubCommand((AbstractCommand)new HitboxExtentsCommand());
/*  36 */     addUsageVariant((AbstractCommand)new HitboxGetCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class HitboxGetCommand
/*     */     extends CommandBase
/*     */   {
/*     */     @Nonnull
/*  47 */     private final RequiredArg<String> hitboxArg = withRequiredArg("hitbox", "server.commands.hitbox.hitbox.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public HitboxGetCommand() {
/*  53 */       super("server.commands.hitbox.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void executeSync(@Nonnull CommandContext context) {
/*  58 */       String name = (String)this.hitboxArg.get(context);
/*  59 */       BlockBoundingBoxes boundingBox = (BlockBoundingBoxes)BlockBoundingBoxes.getAssetMap().getAsset(name);
/*     */       
/*  61 */       if (boundingBox != null) {
/*  62 */         BlockBoundingBoxes.RotatedVariantBoxes rotated = boundingBox.get(Rotation.None, Rotation.None, Rotation.None);
/*  63 */         context.sendMessage(Message.translation("server.commands.hitbox.boundingBox")
/*  64 */             .param("box", HitboxCommand.formatBox(rotated.getBoundingBox())));
/*     */         
/*  66 */         Box[] details = rotated.getDetailBoxes();
/*  67 */         if (details.length > 0) {
/*  68 */           Message header = Message.translation("server.commands.hitbox.details.header");
/*     */ 
/*     */           
/*  71 */           Set<Message> detailMessages = (Set<Message>)Arrays.<Box>stream(details).map(HitboxCommand::formatBox).collect(Collectors.toSet());
/*  72 */           context.sendMessage(MessageFormat.list(header, detailMessages));
/*     */         } 
/*     */       } else {
/*  75 */         context.sendMessage(Message.translation("server.commands.hitbox.notFound").param("name", name));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class HitboxExtentsCommand
/*     */     extends CommandBase
/*     */   {
/*     */     @Nonnull
/*  88 */     private final OptionalArg<Double> thresholdArg = withOptionalArg("threshold", "server.commands.hitbox.extents.threshold.desc", (ArgumentType)ArgTypes.DOUBLE);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public HitboxExtentsCommand() {
/*  94 */       super("extents", "server.commands.hitbox.extents.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void executeSync(@Nonnull CommandContext context) {
/*  99 */       BlockTypeAssetMap<String, BlockType> blockTypeAssetMap = BlockType.getAssetMap();
/* 100 */       IndexedLookupTableAssetMap<String, BlockBoundingBoxes> boundingBoxAssetMap = BlockBoundingBoxes.getAssetMap();
/*     */       
/* 102 */       int totalNumberOfFillerBlocks = 0;
/*     */       
/* 104 */       double threshold = this.thresholdArg.provided(context) ? ((Double)this.thresholdArg.get(context)).doubleValue() : 0.5D;
/*     */       
/* 106 */       for (BlockType blockType : blockTypeAssetMap.getAssetMap().values()) {
/* 107 */         Box boundingBox = ((BlockBoundingBoxes)boundingBoxAssetMap.getAsset(blockType.getHitboxTypeIndex())).get(0).getBoundingBox();
/*     */         
/* 109 */         double width = boundingBox.width();
/* 110 */         double height = boundingBox.height();
/* 111 */         double depth = boundingBox.depth();
/*     */         
/* 113 */         int blockWidth = Math.max(MathUtil.floor(width), 1);
/* 114 */         int blockHeight = Math.max(MathUtil.floor(height), 1);
/* 115 */         int blockDepth = Math.max(MathUtil.floor(depth), 1);
/*     */         
/* 117 */         if (width - blockWidth > threshold) blockWidth++; 
/* 118 */         if (height - blockHeight > threshold) blockHeight++; 
/* 119 */         if (depth - blockDepth > threshold) blockDepth++;
/*     */         
/* 121 */         int numberOfBlocks = blockWidth * blockHeight * blockDepth;
/* 122 */         int numberOfFillerBlocks = numberOfBlocks - 1;
/*     */         
/* 124 */         totalNumberOfFillerBlocks += numberOfFillerBlocks;
/*     */       } 
/*     */       
/* 127 */       context.sendMessage(Message.translation("server.commands.hitbox.extentsThresholdNeeded")
/* 128 */           .param("threshold", threshold)
/* 129 */           .param("nb", totalNumberOfFillerBlocks));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static Message formatBox(@Nonnull Box box) {
/* 141 */     return Message.translation("server.commands.hitbox.box")
/* 142 */       .param("minX", box.min.x)
/* 143 */       .param("minY", box.min.y)
/* 144 */       .param("minZ", box.min.z)
/* 145 */       .param("maxX", box.max.x)
/* 146 */       .param("maxY", box.max.y)
/* 147 */       .param("maxZ", box.max.z);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\commands\HitboxCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */