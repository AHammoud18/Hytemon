package com.hypixel.hytale.protocol.io;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface PacketStatsEntry {
  public static final int RECENT_SECONDS = 30;
  
  int getPacketId();
  
  @Nullable
  String getName();
  
  boolean hasData();
  
  int getSentCount();
  
  long getSentUncompressedTotal();
  
  long getSentCompressedTotal();
  
  long getSentUncompressedMin();
  
  long getSentUncompressedMax();
  
  long getSentCompressedMin();
  
  long getSentCompressedMax();
  
  double getSentUncompressedAvg();
  
  double getSentCompressedAvg();
  
  @Nonnull
  PacketStatsRecorder.RecentStats getSentRecently();
  
  int getReceivedCount();
  
  long getReceivedUncompressedTotal();
  
  long getReceivedCompressedTotal();
  
  long getReceivedUncompressedMin();
  
  long getReceivedUncompressedMax();
  
  long getReceivedCompressedMin();
  
  long getReceivedCompressedMax();
  
  double getReceivedUncompressedAvg();
  
  double getReceivedCompressedAvg();
  
  @Nonnull
  PacketStatsRecorder.RecentStats getReceivedRecently();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\io\PacketStatsRecorder$PacketStatsEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */