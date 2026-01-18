/*    */ package com.hypixel.hytale.server.core.modules.collision.commands;
/*    */ 
/*    */ import com.hypixel.hytale.math.shape.Box;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
/*    */ import java.util.Arrays;
/*    */ import java.util.Set;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nonnull;
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
/*    */ 
/*    */ 
/*    */ class HitboxGetCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 47 */   private final RequiredArg<String> hitboxArg = withRequiredArg("hitbox", "server.commands.hitbox.hitbox.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HitboxGetCommand() {
/* 53 */     super("server.commands.hitbox.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 58 */     String name = (String)this.hitboxArg.get(context);
/* 59 */     BlockBoundingBoxes boundingBox = (BlockBoundingBoxes)BlockBoundingBoxes.getAssetMap().getAsset(name);
/*    */     
/* 61 */     if (boundingBox != null) {
/* 62 */       BlockBoundingBoxes.RotatedVariantBoxes rotated = boundingBox.get(Rotation.None, Rotation.None, Rotation.None);
/* 63 */       context.sendMessage(Message.translation("server.commands.hitbox.boundingBox")
/* 64 */           .param("box", HitboxCommand.formatBox(rotated.getBoundingBox())));
/*    */       
/* 66 */       Box[] details = rotated.getDetailBoxes();
/* 67 */       if (details.length > 0) {
/* 68 */         Message header = Message.translation("server.commands.hitbox.details.header");
/*    */ 
/*    */         
/* 71 */         Set<Message> detailMessages = (Set<Message>)Arrays.<Box>stream(details).map(HitboxCommand::formatBox).collect(Collectors.toSet());
/* 72 */         context.sendMessage(MessageFormat.list(header, detailMessages));
/*    */       } 
/*    */     } else {
/* 75 */       context.sendMessage(Message.translation("server.commands.hitbox.notFound").param("name", name));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\collision\commands\HitboxCommand$HitboxGetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */