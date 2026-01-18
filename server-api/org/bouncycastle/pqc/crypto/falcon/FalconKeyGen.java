package org.bouncycastle.pqc.crypto.falcon;

import org.bouncycastle.crypto.digests.SHAKEDigest;
import org.bouncycastle.util.Pack;

class FalconKeyGen {
  private static final short[] REV10 = new short[] { 
      0, 512, 256, 768, 128, 640, 384, 896, 64, 576, 
      320, 832, 192, 704, 448, 960, 32, 544, 288, 800, 
      160, 672, 416, 928, 96, 608, 352, 864, 224, 736, 
      480, 992, 16, 528, 272, 784, 144, 656, 400, 912, 
      80, 592, 336, 848, 208, 720, 464, 976, 48, 560, 
      304, 816, 176, 688, 432, 944, 112, 624, 368, 880, 
      240, 752, 496, 1008, 8, 520, 264, 776, 136, 648, 
      392, 904, 72, 584, 328, 840, 200, 712, 456, 968, 
      40, 552, 296, 808, 168, 680, 424, 936, 104, 616, 
      360, 872, 232, 744, 488, 1000, 24, 536, 280, 792, 
      152, 664, 408, 920, 88, 600, 344, 856, 216, 728, 
      472, 984, 56, 568, 312, 824, 184, 696, 440, 952, 
      120, 632, 376, 888, 248, 760, 504, 1016, 4, 516, 
      260, 772, 132, 644, 388, 900, 68, 580, 324, 836, 
      196, 708, 452, 964, 36, 548, 292, 804, 164, 676, 
      420, 932, 100, 612, 356, 868, 228, 740, 484, 996, 
      20, 532, 276, 788, 148, 660, 404, 916, 84, 596, 
      340, 852, 212, 724, 468, 980, 52, 564, 308, 820, 
      180, 692, 436, 948, 116, 628, 372, 884, 244, 756, 
      500, 1012, 12, 524, 268, 780, 140, 652, 396, 908, 
      76, 588, 332, 844, 204, 716, 460, 972, 44, 556, 
      300, 812, 172, 684, 428, 940, 108, 620, 364, 876, 
      236, 748, 492, 1004, 28, 540, 284, 796, 156, 668, 
      412, 924, 92, 604, 348, 860, 220, 732, 476, 988, 
      60, 572, 316, 828, 188, 700, 444, 956, 124, 636, 
      380, 892, 252, 764, 508, 1020, 2, 514, 258, 770, 
      130, 642, 386, 898, 66, 578, 322, 834, 194, 706, 
      450, 962, 34, 546, 290, 802, 162, 674, 418, 930, 
      98, 610, 354, 866, 226, 738, 482, 994, 18, 530, 
      274, 786, 146, 658, 402, 914, 82, 594, 338, 850, 
      210, 722, 466, 978, 50, 562, 306, 818, 178, 690, 
      434, 946, 114, 626, 370, 882, 242, 754, 498, 1010, 
      10, 522, 266, 778, 138, 650, 394, 906, 74, 586, 
      330, 842, 202, 714, 458, 970, 42, 554, 298, 810, 
      170, 682, 426, 938, 106, 618, 362, 874, 234, 746, 
      490, 1002, 26, 538, 282, 794, 154, 666, 410, 922, 
      90, 602, 346, 858, 218, 730, 474, 986, 58, 570, 
      314, 826, 186, 698, 442, 954, 122, 634, 378, 890, 
      250, 762, 506, 1018, 6, 518, 262, 774, 134, 646, 
      390, 902, 70, 582, 326, 838, 198, 710, 454, 966, 
      38, 550, 294, 806, 166, 678, 422, 934, 102, 614, 
      358, 870, 230, 742, 486, 998, 22, 534, 278, 790, 
      150, 662, 406, 918, 86, 598, 342, 854, 214, 726, 
      470, 982, 54, 566, 310, 822, 182, 694, 438, 950, 
      118, 630, 374, 886, 246, 758, 502, 1014, 14, 526, 
      270, 782, 142, 654, 398, 910, 78, 590, 334, 846, 
      206, 718, 462, 974, 46, 558, 302, 814, 174, 686, 
      430, 942, 110, 622, 366, 878, 238, 750, 494, 1006, 
      30, 542, 286, 798, 158, 670, 414, 926, 94, 606, 
      350, 862, 222, 734, 478, 990, 62, 574, 318, 830, 
      190, 702, 446, 958, 126, 638, 382, 894, 254, 766, 
      510, 1022, 1, 513, 257, 769, 129, 641, 385, 897, 
      65, 577, 321, 833, 193, 705, 449, 961, 33, 545, 
      289, 801, 161, 673, 417, 929, 97, 609, 353, 865, 
      225, 737, 481, 993, 17, 529, 273, 785, 145, 657, 
      401, 913, 81, 593, 337, 849, 209, 721, 465, 977, 
      49, 561, 305, 817, 177, 689, 433, 945, 113, 625, 
      369, 881, 241, 753, 497, 1009, 9, 521, 265, 777, 
      137, 649, 393, 905, 73, 585, 329, 841, 201, 713, 
      457, 969, 41, 553, 297, 809, 169, 681, 425, 937, 
      105, 617, 361, 873, 233, 745, 489, 1001, 25, 537, 
      281, 793, 153, 665, 409, 921, 89, 601, 345, 857, 
      217, 729, 473, 985, 57, 569, 313, 825, 185, 697, 
      441, 953, 121, 633, 377, 889, 249, 761, 505, 1017, 
      5, 517, 261, 773, 133, 645, 389, 901, 69, 581, 
      325, 837, 197, 709, 453, 965, 37, 549, 293, 805, 
      165, 677, 421, 933, 101, 613, 357, 869, 229, 741, 
      485, 997, 21, 533, 277, 789, 149, 661, 405, 917, 
      85, 597, 341, 853, 213, 725, 469, 981, 53, 565, 
      309, 821, 181, 693, 437, 949, 117, 629, 373, 885, 
      245, 757, 501, 1013, 13, 525, 269, 781, 141, 653, 
      397, 909, 77, 589, 333, 845, 205, 717, 461, 973, 
      45, 557, 301, 813, 173, 685, 429, 941, 109, 621, 
      365, 877, 237, 749, 493, 1005, 29, 541, 285, 797, 
      157, 669, 413, 925, 93, 605, 349, 861, 221, 733, 
      477, 989, 61, 573, 317, 829, 189, 701, 445, 957, 
      125, 637, 381, 893, 253, 765, 509, 1021, 3, 515, 
      259, 771, 131, 643, 387, 899, 67, 579, 323, 835, 
      195, 707, 451, 963, 35, 547, 291, 803, 163, 675, 
      419, 931, 99, 611, 355, 867, 227, 739, 483, 995, 
      19, 531, 275, 787, 147, 659, 403, 915, 83, 595, 
      339, 851, 211, 723, 467, 979, 51, 563, 307, 819, 
      179, 691, 435, 947, 115, 627, 371, 883, 243, 755, 
      499, 1011, 11, 523, 267, 779, 139, 651, 395, 907, 
      75, 587, 331, 843, 203, 715, 459, 971, 43, 555, 
      299, 811, 171, 683, 427, 939, 107, 619, 363, 875, 
      235, 747, 491, 1003, 27, 539, 283, 795, 155, 667, 
      411, 923, 91, 603, 347, 859, 219, 731, 475, 987, 
      59, 571, 315, 827, 187, 699, 443, 955, 123, 635, 
      379, 891, 251, 763, 507, 1019, 7, 519, 263, 775, 
      135, 647, 391, 903, 71, 583, 327, 839, 199, 711, 
      455, 967, 39, 551, 295, 807, 167, 679, 423, 935, 
      103, 615, 359, 871, 231, 743, 487, 999, 23, 535, 
      279, 791, 151, 663, 407, 919, 87, 599, 343, 855, 
      215, 727, 471, 983, 55, 567, 311, 823, 183, 695, 
      439, 951, 119, 631, 375, 887, 247, 759, 503, 1015, 
      15, 527, 271, 783, 143, 655, 399, 911, 79, 591, 
      335, 847, 207, 719, 463, 975, 47, 559, 303, 815, 
      175, 687, 431, 943, 111, 623, 367, 879, 239, 751, 
      495, 1007, 31, 543, 287, 799, 159, 671, 415, 927, 
      95, 607, 351, 863, 223, 735, 479, 991, 63, 575, 
      319, 831, 191, 703, 447, 959, 127, 639, 383, 895, 
      255, 767, 511, 1023 };
  
  private static final long[] gauss_1024_12289 = new long[] { 
      1283868770400643928L, 6416574995475331444L, 4078260278032692663L, 2353523259288686585L, 1227179971273316331L, 575931623374121527L, 242543240509105209L, 91437049221049666L, 30799446349977173L, 9255276791179340L, 
      2478152334826140L, 590642893610164L, 125206034929641L, 23590435911403L, 3948334035941L, 586753615614L, 77391054539L, 9056793210L, 940121950L, 86539696L, 
      7062824L, 510971L, 32764L, 1862L, 94L, 4L, 0L };
  
  private static final int[] MAX_BL_SMALL = new int[] { 
      1, 1, 2, 2, 4, 7, 14, 27, 53, 106, 
      209 };
  
  private static final int[] MAX_BL_LARGE = new int[] { 2, 2, 5, 7, 12, 21, 40, 78, 157, 308 };
  
  private static final int[] bitlength_avg = new int[] { 
      4, 11, 24, 50, 102, 202, 401, 794, 1577, 3138, 
      6308 };
  
  private static final int[] bitlength_std = new int[] { 
      0, 1, 1, 1, 1, 2, 4, 5, 8, 13, 
      25 };
  
  private static final int DEPTH_INT_FG = 4;
  
  private static int mkn(int paramInt) {
    return 1 << paramInt;
  }
  
  private static int modp_set(int paramInt1, int paramInt2) {
    int i = paramInt1;
    i += paramInt2 & -(i >>> 31);
    return i;
  }
  
  private static int modp_norm(int paramInt1, int paramInt2) {
    return paramInt1 - (paramInt2 & (paramInt1 - (paramInt2 + 1 >>> 1) >>> 31) - 1);
  }
  
  private static int modp_ninv31(int paramInt) {
    int i = 2 - paramInt;
    i *= 2 - paramInt * i;
    i *= 2 - paramInt * i;
    i *= 2 - paramInt * i;
    i *= 2 - paramInt * i;
    return Integer.MAX_VALUE & -i;
  }
  
  private static int modp_R(int paramInt) {
    return Integer.MIN_VALUE - paramInt;
  }
  
  private static int modp_add(int paramInt1, int paramInt2, int paramInt3) {
    int i = paramInt1 + paramInt2 - paramInt3;
    i += paramInt3 & -(i >>> 31);
    return i;
  }
  
  private static int modp_sub(int paramInt1, int paramInt2, int paramInt3) {
    int i = paramInt1 - paramInt2;
    i += paramInt3 & -(i >>> 31);
    return i;
  }
  
  private static int modp_montymul(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    long l1 = toUnsignedLong(paramInt1) * toUnsignedLong(paramInt2);
    long l2 = (l1 * paramInt4 & 0x7FFFFFFFL) * paramInt3;
    int i = (int)(l1 + l2 >>> 31L) - paramInt3;
    i += paramInt3 & -(i >>> 31);
    return i;
  }
  
  private static int modp_R2(int paramInt1, int paramInt2) {
    null = modp_R(paramInt1);
    null = modp_add(null, null, paramInt1);
    null = modp_montymul(null, null, paramInt1, paramInt2);
    null = modp_montymul(null, null, paramInt1, paramInt2);
    null = modp_montymul(null, null, paramInt1, paramInt2);
    null = modp_montymul(null, null, paramInt1, paramInt2);
    null = modp_montymul(null, null, paramInt1, paramInt2);
    return null + (paramInt1 & -(null & 0x1)) >>> 1;
  }
  
  private static int modp_Rx(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    paramInt1--;
    int i = paramInt4;
    int j = modp_R(paramInt2);
    for (byte b = 0; 1 << b <= paramInt1; b++) {
      if ((paramInt1 & 1 << b) != 0)
        j = modp_montymul(j, i, paramInt2, paramInt3); 
      i = modp_montymul(i, i, paramInt2, paramInt3);
    } 
    return j;
  }
  
  private static int modp_div(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    int j = paramInt3 - 2;
    int i = paramInt5;
    for (byte b = 30; b >= 0; b--) {
      i = modp_montymul(i, i, paramInt3, paramInt4);
      int k = modp_montymul(i, paramInt2, paramInt3, paramInt4);
      i ^= (i ^ k) & -(j >>> b & 0x1);
    } 
    i = modp_montymul(i, 1, paramInt3, paramInt4);
    return modp_montymul(paramInt1, i, paramInt3, paramInt4);
  }
  
  private static void modp_mkgm2(int[] paramArrayOfint1, int paramInt1, int[] paramArrayOfint2, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    int i = mkn(paramInt3);
    int i1 = modp_R2(paramInt5, paramInt6);
    paramInt4 = modp_montymul(paramInt4, i1, paramInt5, paramInt6);
    int j;
    for (j = paramInt3; j < 10; j++)
      paramInt4 = modp_montymul(paramInt4, paramInt4, paramInt5, paramInt6); 
    int k = modp_div(i1, paramInt4, paramInt5, paramInt6, modp_R(paramInt5));
    j = 10 - paramInt3;
    int n = modp_R(paramInt5);
    int m = n;
    for (byte b = 0; b < i; b++) {
      short s = REV10[b << j];
      paramArrayOfint1[paramInt1 + s] = m;
      paramArrayOfint2[paramInt2 + s] = n;
      m = modp_montymul(m, paramInt4, paramInt5, paramInt6);
      n = modp_montymul(n, k, paramInt5, paramInt6);
    } 
  }
  
  private static void modp_NTT2_ext(int[] paramArrayOfint1, int paramInt1, int paramInt2, int[] paramArrayOfint2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    if (paramInt4 == 0)
      return; 
    int k = mkn(paramInt4);
    int i = k;
    int j;
    for (j = 1; j < k; j <<= 1) {
      int m = i >> 1;
      byte b = 0;
      int n;
      for (n = 0; b < j; n += i) {
        int i1 = paramArrayOfint2[paramInt3 + j + b];
        int i2 = paramInt1 + n * paramInt2;
        int i3 = i2 + m * paramInt2;
        byte b1 = 0;
        while (b1 < m) {
          int i4 = paramArrayOfint1[i2];
          int i5 = modp_montymul(paramArrayOfint1[i3], i1, paramInt5, paramInt6);
          paramArrayOfint1[i2] = modp_add(i4, i5, paramInt5);
          paramArrayOfint1[i3] = modp_sub(i4, i5, paramInt5);
          b1++;
          i2 += paramInt2;
          i3 += paramInt2;
        } 
        b++;
      } 
      i = m;
    } 
  }
  
  private static void modp_iNTT2_ext(int[] paramArrayOfint1, int paramInt1, int paramInt2, int[] paramArrayOfint2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    if (paramInt4 == 0)
      return; 
    int k = mkn(paramInt4);
    int i = 1;
    int j;
    for (j = k; j > 1; j >>= 1) {
      int i1 = j >> 1;
      int i2 = i << 1;
      byte b1 = 0;
      int i3;
      for (i3 = 0; b1 < i1; i3 += i2) {
        int i4 = paramArrayOfint2[paramInt3 + i1 + b1];
        int i5 = paramInt1 + i3 * paramInt2;
        int i6 = i5 + i * paramInt2;
        byte b2 = 0;
        while (b2 < i) {
          int i7 = paramArrayOfint1[i5];
          int i8 = paramArrayOfint1[i6];
          paramArrayOfint1[i5] = modp_add(i7, i8, paramInt5);
          paramArrayOfint1[i6] = modp_montymul(modp_sub(i7, i8, paramInt5), i4, paramInt5, paramInt6);
          b2++;
          i5 += paramInt2;
          i6 += paramInt2;
        } 
        b1++;
      } 
      i = i2;
    } 
    int m = 1 << 31 - paramInt4;
    byte b = 0;
    int n;
    for (n = paramInt1; b < k; n += paramInt2) {
      paramArrayOfint1[n] = modp_montymul(paramArrayOfint1[n], m, paramInt5, paramInt6);
      b++;
    } 
  }
  
  private static void modp_NTT2(int[] paramArrayOfint1, int paramInt1, int[] paramArrayOfint2, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    modp_NTT2_ext(paramArrayOfint1, paramInt1, 1, paramArrayOfint2, paramInt2, paramInt3, paramInt4, paramInt5);
  }
  
  private static void modp_iNTT2(int[] paramArrayOfint1, int paramInt1, int[] paramArrayOfint2, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    modp_iNTT2_ext(paramArrayOfint1, paramInt1, 1, paramArrayOfint2, paramInt2, paramInt3, paramInt4, paramInt5);
  }
  
  private static void modp_poly_rec_res(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    int i = 1 << paramInt2 - 1;
    for (byte b = 0; b < i; b++) {
      int j = paramArrayOfint[paramInt1 + (b << 1)];
      int k = paramArrayOfint[paramInt1 + (b << 1) + 1];
      paramArrayOfint[paramInt1 + b] = modp_montymul(modp_montymul(j, k, paramInt3, paramInt4), paramInt5, paramInt3, paramInt4);
    } 
  }
  
  private static void zint_sub(int[] paramArrayOfint1, int paramInt1, int[] paramArrayOfint2, int paramInt2, int paramInt3, int paramInt4) {
    int i = 0;
    int j = -paramInt4;
    for (byte b = 0; b < paramInt3; b++) {
      int n = paramInt1 + b;
      int k = paramArrayOfint1[n];
      int m = k - paramArrayOfint2[paramInt2 + b] - i;
      i = m >>> 31;
      k ^= (m & Integer.MAX_VALUE ^ k) & j;
      paramArrayOfint1[n] = k;
    } 
  }
  
  private static int zint_mul_small(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3) {
    int i = 0;
    for (byte b = 0; b < paramInt2; b++) {
      long l = toUnsignedLong(paramArrayOfint[paramInt1 + b]) * toUnsignedLong(paramInt3) + i;
      paramArrayOfint[paramInt1 + b] = (int)l & Integer.MAX_VALUE;
      i = (int)(l >> 31L);
    } 
    return i;
  }
  
  private static int zint_mod_small_unsigned(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    int i = 0;
    int j = paramInt2;
    while (j-- > 0) {
      i = modp_montymul(i, paramInt5, paramInt3, paramInt4);
      int k = paramArrayOfint[paramInt1 + j] - paramInt3;
      k += paramInt3 & -(k >>> 31);
      i = modp_add(i, k, paramInt3);
    } 
    return i;
  }
  
  private static int zint_mod_small_signed(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    if (paramInt2 == 0)
      return 0; 
    null = zint_mod_small_unsigned(paramArrayOfint, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    return modp_sub(null, paramInt6 & -(paramArrayOfint[paramInt1 + paramInt2 - 1] >>> 30), paramInt3);
  }
  
  private static void zint_add_mul_small(int[] paramArrayOfint1, int paramInt1, int[] paramArrayOfint2, int paramInt2, int paramInt3, int paramInt4) {
    int i = 0;
    for (byte b = 0; b < paramInt3; b++) {
      int j = paramArrayOfint1[paramInt1 + b];
      int k = paramArrayOfint2[paramInt2 + b];
      long l = toUnsignedLong(k) * toUnsignedLong(paramInt4) + toUnsignedLong(j) + toUnsignedLong(i);
      paramArrayOfint1[paramInt1 + b] = (int)l & Integer.MAX_VALUE;
      i = (int)(l >>> 31L);
    } 
    paramArrayOfint1[paramInt1 + paramInt3] = i;
  }
  
  private static void zint_norm_zero(int[] paramArrayOfint1, int paramInt1, int[] paramArrayOfint2, int paramInt2, int paramInt3) {
    int j = 0;
    int k = 0;
    int i = paramInt3;
    while (i-- > 0) {
      int m = paramArrayOfint1[paramInt1 + i];
      int n = paramArrayOfint2[paramInt2 + i] >>> 1 | k << 30;
      k = paramArrayOfint2[paramInt2 + i] & 0x1;
      int i1 = n - m;
      i1 = -i1 >>> 31 | -(i1 >>> 31);
      j |= i1 & (j & 0x1) - 1;
    } 
    zint_sub(paramArrayOfint1, paramInt1, paramArrayOfint2, paramInt2, paramInt3, j >>> 31);
  }
  
  private static void zint_rebuild_CRT(int[] paramArrayOfint1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfint2, int paramInt6) {
    paramArrayOfint2[paramInt6] = (FalconSmallPrimeList.PRIMES[0]).p;
    byte b;
    for (b = 1; b < paramInt2; b++) {
      int j = (FalconSmallPrimeList.PRIMES[b]).p;
      int m = (FalconSmallPrimeList.PRIMES[b]).s;
      int k = modp_ninv31(j);
      int n = modp_R2(j, k);
      byte b1 = 0;
      int i;
      for (i = paramInt1; b1 < paramInt4; i += paramInt3) {
        int i1 = paramArrayOfint1[i + b];
        int i2 = zint_mod_small_unsigned(paramArrayOfint1, i, b, j, k, n);
        int i3 = modp_montymul(m, modp_sub(i1, i2, j), j, k);
        zint_add_mul_small(paramArrayOfint1, i, paramArrayOfint2, paramInt6, b, i3);
        b1++;
      } 
      paramArrayOfint2[paramInt6 + b] = zint_mul_small(paramArrayOfint2, paramInt6, b, j);
    } 
    if (paramInt5 != 0) {
      b = 0;
      int i;
      for (i = paramInt1; b < paramInt4; i += paramInt3) {
        zint_norm_zero(paramArrayOfint1, i, paramArrayOfint2, paramInt6, paramInt2);
        b++;
      } 
    } 
  }
  
  private static void zint_negate(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3) {
    int i = paramInt3;
    int j = -paramInt3 >>> 1;
    for (byte b = 0; b < paramInt2; b++) {
      int k = paramArrayOfint[paramInt1 + b];
      k = (k ^ j) + i;
      paramArrayOfint[paramInt1 + b] = k & Integer.MAX_VALUE;
      i = k >>> 31;
    } 
  }
  
  private static int zint_co_reduce(int[] paramArrayOfint1, int paramInt1, int[] paramArrayOfint2, int paramInt2, int paramInt3, long paramLong1, long paramLong2, long paramLong3, long paramLong4) {
    long l1 = 0L;
    long l2 = 0L;
    for (byte b = 0; b < paramInt3; b++) {
      int k = paramArrayOfint1[paramInt1 + b];
      int m = paramArrayOfint2[paramInt2 + b];
      long l3 = k * paramLong1 + m * paramLong2 + l1;
      long l4 = k * paramLong3 + m * paramLong4 + l2;
      if (b > 0) {
        paramArrayOfint1[paramInt1 + b - 1] = (int)l3 & Integer.MAX_VALUE;
        paramArrayOfint2[paramInt2 + b - 1] = (int)l4 & Integer.MAX_VALUE;
      } 
      l1 = l3 >> 31L;
      l2 = l4 >> 31L;
    } 
    paramArrayOfint1[paramInt1 + paramInt3 - 1] = (int)l1;
    paramArrayOfint2[paramInt2 + paramInt3 - 1] = (int)l2;
    int i = (int)(l1 >>> 63L);
    int j = (int)(l2 >>> 63L);
    zint_negate(paramArrayOfint1, paramInt1, paramInt3, i);
    zint_negate(paramArrayOfint2, paramInt2, paramInt3, j);
    return i | j << 1;
  }
  
  private static void zint_finish_mod(int[] paramArrayOfint1, int paramInt1, int paramInt2, int[] paramArrayOfint2, int paramInt3, int paramInt4) {
    int i = 0;
    byte b;
    for (b = 0; b < paramInt2; b++)
      i = paramArrayOfint1[paramInt1 + b] - paramArrayOfint2[paramInt3 + b] - i >>> 31; 
    int j = -paramInt4 >>> 1;
    int k = -(paramInt4 | 1 - i);
    i = paramInt4;
    for (b = 0; b < paramInt2; b++) {
      int m = paramArrayOfint1[paramInt1 + b];
      int n = (paramArrayOfint2[paramInt3 + b] ^ j) & k;
      m = m - n - i;
      paramArrayOfint1[paramInt1 + b] = m & Integer.MAX_VALUE;
      i = m >>> 31;
    } 
  }
  
  private static void zint_co_reduce_mod(int[] paramArrayOfint1, int paramInt1, int[] paramArrayOfint2, int paramInt2, int[] paramArrayOfint3, int paramInt3, int paramInt4, int paramInt5, long paramLong1, long paramLong2, long paramLong3, long paramLong4) {
    long l1 = 0L;
    long l2 = 0L;
    int i = (paramArrayOfint1[paramInt1] * (int)paramLong1 + paramArrayOfint2[paramInt2] * (int)paramLong2) * paramInt5 & Integer.MAX_VALUE;
    int j = (paramArrayOfint1[paramInt1] * (int)paramLong3 + paramArrayOfint2[paramInt2] * (int)paramLong4) * paramInt5 & Integer.MAX_VALUE;
    for (byte b = 0; b < paramInt4; b++) {
      int k = paramArrayOfint1[paramInt1 + b];
      int m = paramArrayOfint2[paramInt2 + b];
      long l3 = k * paramLong1 + m * paramLong2 + paramArrayOfint3[paramInt3 + b] * toUnsignedLong(i) + l1;
      long l4 = k * paramLong3 + m * paramLong4 + paramArrayOfint3[paramInt3 + b] * toUnsignedLong(j) + l2;
      if (b > 0) {
        paramArrayOfint1[paramInt1 + b - 1] = (int)l3 & Integer.MAX_VALUE;
        paramArrayOfint2[paramInt2 + b - 1] = (int)l4 & Integer.MAX_VALUE;
      } 
      l1 = l3 >> 31L;
      l2 = l4 >> 31L;
    } 
    paramArrayOfint1[paramInt1 + paramInt4 - 1] = (int)l1;
    paramArrayOfint2[paramInt2 + paramInt4 - 1] = (int)l2;
    zint_finish_mod(paramArrayOfint1, paramInt1, paramInt4, paramArrayOfint3, paramInt3, (int)(l1 >>> 63L));
    zint_finish_mod(paramArrayOfint2, paramInt2, paramInt4, paramArrayOfint3, paramInt3, (int)(l2 >>> 63L));
  }
  
  private static int zint_bezout(int[] paramArrayOfint1, int paramInt1, int[] paramArrayOfint2, int paramInt2, int[] paramArrayOfint3, int paramInt3, int[] paramArrayOfint4, int paramInt4, int paramInt5, int[] paramArrayOfint5, int paramInt6) {
    if (paramInt5 == 0)
      return 0; 
    int i = paramInt1;
    int k = paramInt2;
    int j = paramInt6;
    int m = j + paramInt5;
    int n = m + paramInt5;
    int i1 = n + paramInt5;
    int i2 = modp_ninv31(paramArrayOfint3[paramInt3]);
    int i3 = modp_ninv31(paramArrayOfint4[paramInt4]);
    System.arraycopy(paramArrayOfint3, paramInt3, paramArrayOfint5, n, paramInt5);
    System.arraycopy(paramArrayOfint4, paramInt4, paramArrayOfint5, i1, paramInt5);
    paramArrayOfint1[i] = 1;
    paramArrayOfint2[k] = 0;
    int i6;
    for (i6 = 1; i6 < paramInt5; i6++) {
      paramArrayOfint1[i + i6] = 0;
      paramArrayOfint2[k + i6] = 0;
    } 
    System.arraycopy(paramArrayOfint4, paramInt4, paramArrayOfint5, j, paramInt5);
    System.arraycopy(paramArrayOfint3, paramInt3, paramArrayOfint5, m, paramInt5);
    paramArrayOfint5[m] = paramArrayOfint5[m] - 1;
    for (int i4 = 62 * paramInt5 + 30; i4 >= 30; i4 -= 30) {
      i6 = -1;
      int i8 = -1;
      int i9 = 0;
      int i10 = 0;
      int i11 = 0;
      int i12 = 0;
      int i7 = paramInt5;
      while (i7-- > 0) {
        int i16 = paramArrayOfint5[n + i7];
        int i17 = paramArrayOfint5[i1 + i7];
        i9 ^= (i9 ^ i16) & i6;
        i10 ^= (i10 ^ i16) & i8;
        i11 ^= (i11 ^ i17) & i6;
        i12 ^= (i12 ^ i17) & i8;
        i8 = i6;
        i6 &= ((i16 | i17) + Integer.MAX_VALUE >>> 31) - 1;
      } 
      i10 |= i9 & i8;
      i9 &= i8 ^ 0xFFFFFFFF;
      i12 |= i11 & i8;
      i11 &= i8 ^ 0xFFFFFFFF;
      long l1 = (toUnsignedLong(i9) << 31L) + toUnsignedLong(i10);
      long l2 = (toUnsignedLong(i11) << 31L) + toUnsignedLong(i12);
      int i13 = paramArrayOfint5[n];
      int i14 = paramArrayOfint5[i1];
      long l3 = 1L;
      long l4 = 0L;
      long l5 = 0L;
      long l6 = 1L;
      for (byte b1 = 0; b1 < 31; b1++) {
        long l = l2 - l1;
        int i16 = (int)((l ^ (l1 ^ l2) & (l1 ^ l)) >>> 63L);
        int i17 = i13 >> b1 & 0x1;
        int i18 = i14 >> b1 & 0x1;
        int i19 = i17 & i18 & i16;
        int i20 = i17 & i18 & (i16 ^ 0xFFFFFFFF);
        int i21 = i19 | i17 ^ 0x1;
        i13 -= i14 & -i19;
        l1 -= l2 & -toUnsignedLong(i19);
        l3 -= l5 & -(i19);
        l4 -= l6 & -(i19);
        i14 -= i13 & -i20;
        l2 -= l1 & -toUnsignedLong(i20);
        l5 -= l3 & -(i20);
        l6 -= l4 & -(i20);
        i13 += i13 & i21 - 1;
        l3 += l3 & i21 - 1L;
        l4 += l4 & i21 - 1L;
        l1 ^= (l1 ^ l1 >> 1L) & -toUnsignedLong(i21);
        i14 += i14 & -i21;
        l5 += l5 & -(i21);
        l6 += l6 & -(i21);
        l2 ^= (l2 ^ l2 >> 1L) & toUnsignedLong(i21) - 1L;
      } 
      int i15 = zint_co_reduce(paramArrayOfint5, n, paramArrayOfint5, i1, paramInt5, l3, l4, l5, l6);
      l3 -= l3 + l3 & -((i15 & 0x1));
      l4 -= l4 + l4 & -((i15 & 0x1));
      l5 -= l5 + l5 & -((i15 >>> 1));
      l6 -= l6 + l6 & -((i15 >>> 1));
      zint_co_reduce_mod(paramArrayOfint1, i, paramArrayOfint5, j, paramArrayOfint4, paramInt4, paramInt5, i3, l3, l4, l5, l6);
      zint_co_reduce_mod(paramArrayOfint2, k, paramArrayOfint5, m, paramArrayOfint3, paramInt3, paramInt5, i2, l3, l4, l5, l6);
    } 
    int i5 = paramArrayOfint5[n] ^ 0x1;
    for (byte b = 1; b < paramInt5; b++)
      i5 |= paramArrayOfint5[n + b]; 
    return 1 - ((i5 | -i5) >>> 31) & paramArrayOfint3[paramInt3] & paramArrayOfint4[paramInt4];
  }
  
  private static void zint_add_scaled_mul_small(int[] paramArrayOfint1, int paramInt1, int paramInt2, int[] paramArrayOfint2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
    if (paramInt4 == 0)
      return; 
    int j = -(paramArrayOfint2[paramInt3 + paramInt4 - 1] >>> 30) >>> 1;
    int k = 0;
    int m = 0;
    for (int i = paramInt6; i < paramInt2; i++) {
      int n = i - paramInt6;
      int i1 = (n < paramInt4) ? paramArrayOfint2[paramInt3 + n] : j;
      int i2 = i1 << paramInt7 & Integer.MAX_VALUE | k;
      k = i1 >>> 31 - paramInt7;
      long l = toUnsignedLong(i2) * paramInt5 + toUnsignedLong(paramArrayOfint1[paramInt1 + i]) + m;
      paramArrayOfint1[paramInt1 + i] = (int)l & Integer.MAX_VALUE;
      int i3 = (int)(l >>> 31L);
      m = i3;
    } 
  }
  
  private static void zint_sub_scaled(int[] paramArrayOfint1, int paramInt1, int paramInt2, int[] paramArrayOfint2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    if (paramInt4 == 0)
      return; 
    int j = -(paramArrayOfint2[paramInt3 + paramInt4 - 1] >>> 30) >>> 1;
    int k = 0;
    int m = 0;
    for (int i = paramInt5; i < paramInt2; i++) {
      int n = i - paramInt5;
      int i2 = (n < paramInt4) ? paramArrayOfint2[paramInt3 + n] : j;
      int i3 = i2 << paramInt6 & Integer.MAX_VALUE | k;
      k = i2 >>> 31 - paramInt6;
      int i1 = paramArrayOfint1[paramInt1 + i] - i3 - m;
      paramArrayOfint1[paramInt1 + i] = i1 & Integer.MAX_VALUE;
      m = i1 >>> 31;
    } 
  }
  
  private static int zint_one_to_plain(int[] paramArrayOfint, int paramInt) {
    int i = paramArrayOfint[paramInt];
    i |= (i & 0x40000000) << 1;
    return i;
  }
  
  private static void poly_big_to_fp(double[] paramArrayOfdouble, int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = mkn(paramInt4);
    if (paramInt2 == 0) {
      for (byte b1 = 0; b1 < i; b1++)
        paramArrayOfdouble[b1] = 0.0D; 
      return;
    } 
    byte b = 0;
    while (b < i) {
      int j = -(paramArrayOfint[paramInt1 + paramInt2 - 1] >>> 30);
      int m = j >>> 1;
      int k = j & 0x1;
      double d1 = 0.0D;
      double d2 = 1.0D;
      byte b1 = 0;
      while (b1 < paramInt2) {
        int n = (paramArrayOfint[paramInt1 + b1] ^ m) + k;
        k = n >>> 31;
        n &= Integer.MAX_VALUE;
        n -= n << 1 & j;
        d1 += n * d2;
        b1++;
        d2 *= 2.147483648E9D;
      } 
      paramArrayOfdouble[b] = d1;
      b++;
      paramInt1 += paramInt3;
    } 
  }
  
  private static int poly_big_to_small(byte[] paramArrayOfbyte, int paramInt1, int[] paramArrayOfint, int paramInt2, int paramInt3, int paramInt4) {
    int i = mkn(paramInt4);
    for (byte b = 0; b < i; b++) {
      int j = zint_one_to_plain(paramArrayOfint, paramInt2 + b);
      if (j < -paramInt3 || j > paramInt3)
        return 0; 
      paramArrayOfbyte[paramInt1 + b] = (byte)j;
    } 
    return 1;
  }
  
  private static void poly_sub_scaled(int[] paramArrayOfint1, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfint2, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfint3, int paramInt7, int paramInt8, int paramInt9) {
    int i = mkn(paramInt9);
    for (byte b = 0; b < i; b++) {
      int j = -paramArrayOfint3[b];
      int k = paramInt1 + b * paramInt3;
      int m = paramInt4;
      for (byte b1 = 0; b1 < i; b1++) {
        zint_add_scaled_mul_small(paramArrayOfint1, k, paramInt2, paramArrayOfint2, m, paramInt5, j, paramInt7, paramInt8);
        if (b + b1 == i - 1) {
          k = paramInt1;
          j = -j;
        } else {
          k += paramInt3;
        } 
        m += paramInt6;
      } 
    } 
  }
  
  private static void poly_sub_scaled_ntt(int[] paramArrayOfint1, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfint2, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfint3, int paramInt7, int paramInt8, int paramInt9, int[] paramArrayOfint4, int paramInt10) {
    int i2 = mkn(paramInt9);
    int i3 = paramInt5 + 1;
    int i = paramInt10;
    int j = i + mkn(paramInt9);
    int k = j + mkn(paramInt9);
    int m = k + i2 * i3;
    byte b;
    for (b = 0; b < i3; b++) {
      int i6 = (FalconSmallPrimeList.PRIMES[b]).p;
      int i7 = modp_ninv31(i6);
      int i8 = modp_R2(i6, i7);
      int i9 = modp_Rx(paramInt5, i6, i7, i8);
      modp_mkgm2(paramArrayOfint4, i, paramArrayOfint4, j, paramInt9, (FalconSmallPrimeList.PRIMES[b]).g, i6, i7);
      byte b1;
      for (b1 = 0; b1 < i2; b1++)
        paramArrayOfint4[m + b1] = modp_set(paramArrayOfint3[b1], i6); 
      modp_NTT2(paramArrayOfint4, m, paramArrayOfint4, i, paramInt9, i6, i7);
      b1 = 0;
      int i5 = paramInt4;
      int i4;
      for (i4 = k + b; b1 < i2; i4 += i3) {
        paramArrayOfint4[i4] = zint_mod_small_signed(paramArrayOfint2, i5, paramInt5, i6, i7, i8, i9);
        b1++;
        i5 += paramInt6;
      } 
      modp_NTT2_ext(paramArrayOfint4, k + b, i3, paramArrayOfint4, i, paramInt9, i6, i7);
      b1 = 0;
      for (i4 = k + b; b1 < i2; i4 += i3) {
        paramArrayOfint4[i4] = modp_montymul(modp_montymul(paramArrayOfint4[m + b1], paramArrayOfint4[i4], i6, i7), i8, i6, i7);
        b1++;
      } 
      modp_iNTT2_ext(paramArrayOfint4, k + b, i3, paramArrayOfint4, j, paramInt9, i6, i7);
    } 
    zint_rebuild_CRT(paramArrayOfint4, k, i3, i3, i2, 1, paramArrayOfint4, m);
    b = 0;
    int n = paramInt1;
    int i1;
    for (i1 = k; b < i2; i1 += i3) {
      zint_sub_scaled(paramArrayOfint1, n, paramInt2, paramArrayOfint4, i1, i3, paramInt7, paramInt8);
      b++;
      n += paramInt3;
    } 
  }
  
  private static long get_rng_u64(SHAKEDigest paramSHAKEDigest) {
    byte[] arrayOfByte = new byte[8];
    paramSHAKEDigest.doOutput(arrayOfByte, 0, arrayOfByte.length);
    return Pack.littleEndianToLong(arrayOfByte, 0);
  }
  
  private static int mkgauss(SHAKEDigest paramSHAKEDigest, int paramInt) {
    int i = 1 << 10 - paramInt;
    int j = 0;
    for (byte b = 0; b < i; b++) {
      long l = get_rng_u64(paramSHAKEDigest);
      int n = (int)(l >>> 63L);
      l &= Long.MAX_VALUE;
      int k = (int)(l - gauss_1024_12289[0] >>> 63L);
      int m = 0;
      l = get_rng_u64(paramSHAKEDigest);
      l &= Long.MAX_VALUE;
      for (byte b1 = 1; b1 < gauss_1024_12289.length; b1++) {
        int i1 = (int)(l - gauss_1024_12289[b1] >>> 63L) ^ 0x1;
        m |= b1 & -(i1 & (k ^ 0x1));
        k |= i1;
      } 
      m = (m ^ -n) + n;
      j += m;
    } 
    return j;
  }
  
  private static int poly_small_sqnorm(byte[] paramArrayOfbyte, int paramInt) {
    int i = mkn(paramInt);
    int j = 0;
    int k = 0;
    for (byte b = 0; b < i; b++) {
      byte b1 = paramArrayOfbyte[b];
      j += b1 * b1;
      k |= j;
    } 
    return j | -(k >>> 31);
  }
  
  private static void poly_small_to_fp(double[] paramArrayOfdouble, int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
    int i = mkn(paramInt2);
    for (byte b = 0; b < i; b++)
      paramArrayOfdouble[paramInt1 + b] = paramArrayOfbyte[b]; 
  }
  
  private static void make_fg_step(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    int i = 1 << paramInt2;
    int j = i >> 1;
    int m = MAX_BL_SMALL[paramInt3];
    int n = MAX_BL_SMALL[paramInt3 + 1];
    int i1 = paramInt1;
    int i2 = i1 + j * n;
    int i3 = i2 + j * n;
    int i4 = i3 + i * m;
    int i5 = i4 + i * m;
    int i6 = i5 + i;
    int i7 = i6 + i;
    System.arraycopy(paramArrayOfint, paramInt1, paramArrayOfint, i3, 2 * i * m);
    int k;
    for (k = 0; k < m; k++) {
      int i8 = (FalconSmallPrimeList.PRIMES[k]).p;
      int i9 = modp_ninv31(i8);
      int i10 = modp_R2(i8, i9);
      modp_mkgm2(paramArrayOfint, i5, paramArrayOfint, i6, paramInt2, (FalconSmallPrimeList.PRIMES[k]).g, i8, i9);
      byte b = 0;
      int i11;
      for (i11 = i3 + k; b < i; i11 += m) {
        paramArrayOfint[i7 + b] = paramArrayOfint[i11];
        b++;
      } 
      if (paramInt4 == 0)
        modp_NTT2(paramArrayOfint, i7, paramArrayOfint, i5, paramInt2, i8, i9); 
      b = 0;
      for (i11 = i1 + k; b < j; i11 += n) {
        int i12 = paramArrayOfint[i7 + (b << 1)];
        int i13 = paramArrayOfint[i7 + (b << 1) + 1];
        paramArrayOfint[i11] = modp_montymul(modp_montymul(i12, i13, i8, i9), i10, i8, i9);
        b++;
      } 
      if (paramInt4 != 0)
        modp_iNTT2_ext(paramArrayOfint, i3 + k, m, paramArrayOfint, i6, paramInt2, i8, i9); 
      b = 0;
      for (i11 = i4 + k; b < i; i11 += m) {
        paramArrayOfint[i7 + b] = paramArrayOfint[i11];
        b++;
      } 
      if (paramInt4 == 0)
        modp_NTT2(paramArrayOfint, i7, paramArrayOfint, i5, paramInt2, i8, i9); 
      b = 0;
      for (i11 = i2 + k; b < j; i11 += n) {
        int i12 = paramArrayOfint[i7 + (b << 1)];
        int i13 = paramArrayOfint[i7 + (b << 1) + 1];
        paramArrayOfint[i11] = modp_montymul(modp_montymul(i12, i13, i8, i9), i10, i8, i9);
        b++;
      } 
      if (paramInt4 != 0)
        modp_iNTT2_ext(paramArrayOfint, i4 + k, m, paramArrayOfint, i6, paramInt2, i8, i9); 
      if (paramInt5 == 0) {
        modp_iNTT2_ext(paramArrayOfint, i1 + k, n, paramArrayOfint, i6, paramInt2 - 1, i8, i9);
        modp_iNTT2_ext(paramArrayOfint, i2 + k, n, paramArrayOfint, i6, paramInt2 - 1, i8, i9);
      } 
    } 
    zint_rebuild_CRT(paramArrayOfint, i3, m, m, i, 1, paramArrayOfint, i5);
    zint_rebuild_CRT(paramArrayOfint, i4, m, m, i, 1, paramArrayOfint, i5);
    for (k = m; k < n; k++) {
      int i8 = (FalconSmallPrimeList.PRIMES[k]).p;
      int i9 = modp_ninv31(i8);
      int i10 = modp_R2(i8, i9);
      int i11 = modp_Rx(m, i8, i9, i10);
      modp_mkgm2(paramArrayOfint, i5, paramArrayOfint, i6, paramInt2, (FalconSmallPrimeList.PRIMES[k]).g, i8, i9);
      byte b = 0;
      int i12;
      for (i12 = i3; b < i; i12 += m) {
        paramArrayOfint[i7 + b] = zint_mod_small_signed(paramArrayOfint, i12, m, i8, i9, i10, i11);
        b++;
      } 
      modp_NTT2(paramArrayOfint, i7, paramArrayOfint, i5, paramInt2, i8, i9);
      b = 0;
      for (i12 = i1 + k; b < j; i12 += n) {
        int i13 = paramArrayOfint[i7 + (b << 1)];
        int i14 = paramArrayOfint[i7 + (b << 1) + 1];
        paramArrayOfint[i12] = modp_montymul(modp_montymul(i13, i14, i8, i9), i10, i8, i9);
        b++;
      } 
      b = 0;
      for (i12 = i4; b < i; i12 += m) {
        paramArrayOfint[i7 + b] = zint_mod_small_signed(paramArrayOfint, i12, m, i8, i9, i10, i11);
        b++;
      } 
      modp_NTT2(paramArrayOfint, i7, paramArrayOfint, i5, paramInt2, i8, i9);
      b = 0;
      for (i12 = i2 + k; b < j; i12 += n) {
        int i13 = paramArrayOfint[i7 + (b << 1)];
        int i14 = paramArrayOfint[i7 + (b << 1) + 1];
        paramArrayOfint[i12] = modp_montymul(modp_montymul(i13, i14, i8, i9), i10, i8, i9);
        b++;
      } 
      if (paramInt5 == 0) {
        modp_iNTT2_ext(paramArrayOfint, i1 + k, n, paramArrayOfint, i6, paramInt2 - 1, i8, i9);
        modp_iNTT2_ext(paramArrayOfint, i2 + k, n, paramArrayOfint, i6, paramInt2 - 1, i8, i9);
      } 
    } 
  }
  
  private static void make_fg(int[] paramArrayOfint, int paramInt1, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt2, int paramInt3, int paramInt4) {
    int i = mkn(paramInt2);
    int j = paramInt1;
    int k = j + i;
    int m = (FalconSmallPrimeList.PRIMES[0]).p;
    for (byte b1 = 0; b1 < i; b1++) {
      paramArrayOfint[j + b1] = modp_set(paramArrayOfbyte1[b1], m);
      paramArrayOfint[k + b1] = modp_set(paramArrayOfbyte2[b1], m);
    } 
    if (paramInt3 == 0 && paramInt4 != 0) {
      int i2 = (FalconSmallPrimeList.PRIMES[0]).p;
      int i3 = modp_ninv31(i2);
      int n = k + i;
      int i1 = n + i;
      modp_mkgm2(paramArrayOfint, n, paramArrayOfint, i1, paramInt2, (FalconSmallPrimeList.PRIMES[0]).g, i2, i3);
      modp_NTT2(paramArrayOfint, j, paramArrayOfint, n, paramInt2, i2, i3);
      modp_NTT2(paramArrayOfint, k, paramArrayOfint, n, paramInt2, i2, i3);
      return;
    } 
    for (byte b2 = 0; b2 < paramInt3; b2++)
      make_fg_step(paramArrayOfint, paramInt1, paramInt2 - b2, b2, (b2 != 0) ? 1 : 0, (b2 + 1 < paramInt3 || paramInt4 != 0) ? 1 : 0); 
  }
  
  private static int solve_NTRU_deepest(int paramInt, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int[] paramArrayOfint) {
    int i = MAX_BL_SMALL[paramInt];
    byte b = 0;
    int j = b + i;
    int k = j + i;
    int m = k + i;
    int n = m + i;
    make_fg(paramArrayOfint, k, paramArrayOfbyte1, paramArrayOfbyte2, paramInt, paramInt, 0);
    zint_rebuild_CRT(paramArrayOfint, k, i, i, 2, 0, paramArrayOfint, n);
    if (zint_bezout(paramArrayOfint, j, paramArrayOfint, b, paramArrayOfint, k, paramArrayOfint, m, i, paramArrayOfint, n) == 0)
      return 0; 
    char c = '、';
    return (zint_mul_small(paramArrayOfint, b, i, c) != 0 || zint_mul_small(paramArrayOfint, j, i, c) != 0) ? 0 : 1;
  }
  
  private static int solve_NTRU_intermediate(int paramInt1, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt2, int[] paramArrayOfint) {
    int i = paramInt1 - paramInt2;
    int j = 1 << i;
    int k = j >> 1;
    int m = MAX_BL_SMALL[paramInt2];
    int n = MAX_BL_SMALL[paramInt2 + 1];
    int i1 = MAX_BL_LARGE[paramInt2];
    int i4 = 0;
    int i5 = i4 + n * k;
    int i8 = i5 + n * k;
    make_fg(paramArrayOfint, i8, paramArrayOfbyte1, paramArrayOfbyte2, paramInt1, paramInt2, 1);
    int i6 = 0;
    int i7 = i6 + j * i1;
    int i10 = i7 + j * i1;
    int i16 = j * m;
    System.arraycopy(paramArrayOfint, i8, paramArrayOfint, i10, i16 + i16);
    i8 = i10;
    int i9 = i8 + i16;
    i10 = i9 + i16;
    i16 = k * n;
    System.arraycopy(paramArrayOfint, i4, paramArrayOfint, i10, i16 + i16);
    i4 = i10;
    i5 = i4 + i16;
    byte b;
    for (b = 0; b < i1; b++) {
      int i17 = (FalconSmallPrimeList.PRIMES[b]).p;
      int i18 = modp_ninv31(i17);
      int i19 = modp_R2(i17, i18);
      int i20 = modp_Rx(n, i17, i18, i19);
      byte b1 = 0;
      int i21 = i4;
      int i22 = i5;
      int i23 = i6 + b;
      int i24;
      for (i24 = i7 + b; b1 < k; i24 += i1) {
        paramArrayOfint[i23] = zint_mod_small_signed(paramArrayOfint, i21, n, i17, i18, i19, i20);
        paramArrayOfint[i24] = zint_mod_small_signed(paramArrayOfint, i22, n, i17, i18, i19, i20);
        b1++;
        i21 += n;
        i22 += n;
        i23 += i1;
      } 
    } 
    for (b = 0; b < i1; b++) {
      int i19 = (FalconSmallPrimeList.PRIMES[b]).p;
      int i20 = modp_ninv31(i19);
      int i21 = modp_R2(i19, i20);
      if (b == m) {
        zint_rebuild_CRT(paramArrayOfint, i8, m, m, j, 1, paramArrayOfint, i10);
        zint_rebuild_CRT(paramArrayOfint, i9, m, m, j, 1, paramArrayOfint, i10);
      } 
      int i22 = i10;
      int i23 = i22 + j;
      int i24 = i23 + j;
      int i25 = i24 + j;
      modp_mkgm2(paramArrayOfint, i22, paramArrayOfint, i23, i, (FalconSmallPrimeList.PRIMES[b]).g, i19, i20);
      if (b < m) {
        byte b2 = 0;
        int i28 = i8 + b;
        int i29;
        for (i29 = i9 + b; b2 < j; i29 += m) {
          paramArrayOfint[i24 + b2] = paramArrayOfint[i28];
          paramArrayOfint[i25 + b2] = paramArrayOfint[i29];
          b2++;
          i28 += m;
        } 
        modp_iNTT2_ext(paramArrayOfint, i8 + b, m, paramArrayOfint, i23, i, i19, i20);
        modp_iNTT2_ext(paramArrayOfint, i9 + b, m, paramArrayOfint, i23, i, i19, i20);
      } else {
        int i30 = modp_Rx(m, i19, i20, i21);
        byte b2 = 0;
        int i28 = i8;
        int i29;
        for (i29 = i9; b2 < j; i29 += m) {
          paramArrayOfint[i24 + b2] = zint_mod_small_signed(paramArrayOfint, i28, m, i19, i20, i21, i30);
          paramArrayOfint[i25 + b2] = zint_mod_small_signed(paramArrayOfint, i29, m, i19, i20, i21, i30);
          b2++;
          i28 += m;
        } 
        modp_NTT2(paramArrayOfint, i24, paramArrayOfint, i22, i, i19, i20);
        modp_NTT2(paramArrayOfint, i25, paramArrayOfint, i22, i, i19, i20);
      } 
      int i26 = i25 + j;
      int i27 = i26 + k;
      byte b1 = 0;
      int i17 = i6 + b;
      int i18;
      for (i18 = i7 + b; b1 < k; i18 += i1) {
        paramArrayOfint[i26 + b1] = paramArrayOfint[i17];
        paramArrayOfint[i27 + b1] = paramArrayOfint[i18];
        b1++;
        i17 += i1;
      } 
      modp_NTT2(paramArrayOfint, i26, paramArrayOfint, i22, i - 1, i19, i20);
      modp_NTT2(paramArrayOfint, i27, paramArrayOfint, i22, i - 1, i19, i20);
      b1 = 0;
      i17 = i6 + b;
      for (i18 = i7 + b; b1 < k; i18 += i1 << 1) {
        int i28 = paramArrayOfint[i24 + (b1 << 1)];
        int i29 = paramArrayOfint[i24 + (b1 << 1) + 1];
        int i30 = paramArrayOfint[i25 + (b1 << 1)];
        int i31 = paramArrayOfint[i25 + (b1 << 1) + 1];
        int i32 = modp_montymul(paramArrayOfint[i26 + b1], i21, i19, i20);
        int i33 = modp_montymul(paramArrayOfint[i27 + b1], i21, i19, i20);
        paramArrayOfint[i17] = modp_montymul(i31, i32, i19, i20);
        paramArrayOfint[i17 + i1] = modp_montymul(i30, i32, i19, i20);
        paramArrayOfint[i18] = modp_montymul(i29, i33, i19, i20);
        paramArrayOfint[i18 + i1] = modp_montymul(i28, i33, i19, i20);
        b1++;
        i17 += i1 << 1;
      } 
      modp_iNTT2_ext(paramArrayOfint, i6 + b, i1, paramArrayOfint, i23, i, i19, i20);
      modp_iNTT2_ext(paramArrayOfint, i7 + b, i1, paramArrayOfint, i23, i, i19, i20);
    } 
    zint_rebuild_CRT(paramArrayOfint, i6, i1, i1, j, 1, paramArrayOfint, i10);
    zint_rebuild_CRT(paramArrayOfint, i7, i1, i1, j, 1, paramArrayOfint, i10);
    double[] arrayOfDouble1 = new double[j];
    double[] arrayOfDouble2 = new double[j];
    double[] arrayOfDouble3 = new double[j];
    double[] arrayOfDouble4 = new double[j];
    double[] arrayOfDouble5 = new double[j >> 1];
    int[] arrayOfInt = new int[j];
    int i2 = Math.min(m, 10);
    poly_big_to_fp(arrayOfDouble3, paramArrayOfint, i8 + m - i2, i2, m, i);
    poly_big_to_fp(arrayOfDouble4, paramArrayOfint, i9 + m - i2, i2, m, i);
    int i11 = 31 * (m - i2);
    int i12 = bitlength_avg[paramInt2] - 6 * bitlength_std[paramInt2];
    int i13 = bitlength_avg[paramInt2] + 6 * bitlength_std[paramInt2];
    FalconFFT.FFT(arrayOfDouble3, 0, i);
    FalconFFT.FFT(arrayOfDouble4, 0, i);
    FalconFFT.poly_invnorm2_fft(arrayOfDouble5, 0, arrayOfDouble3, 0, arrayOfDouble4, 0, i);
    FalconFFT.poly_adj_fft(arrayOfDouble3, 0, i);
    FalconFFT.poly_adj_fft(arrayOfDouble4, 0, i);
    int i3 = i1;
    int i14 = 31 * i1;
    int i15 = i14 - i12;
    while (true) {
      double d2;
      i2 = Math.min(i3, 10);
      int i17 = 31 * (i3 - i2);
      poly_big_to_fp(arrayOfDouble1, paramArrayOfint, i6 + i3 - i2, i2, i1, i);
      poly_big_to_fp(arrayOfDouble2, paramArrayOfint, i7 + i3 - i2, i2, i1, i);
      FalconFFT.FFT(arrayOfDouble1, 0, i);
      FalconFFT.FFT(arrayOfDouble2, 0, i);
      FalconFFT.poly_mul_fft(arrayOfDouble1, 0, arrayOfDouble3, 0, i);
      FalconFFT.poly_mul_fft(arrayOfDouble2, 0, arrayOfDouble4, 0, i);
      FalconFFT.poly_add(arrayOfDouble2, 0, arrayOfDouble1, 0, i);
      FalconFFT.poly_mul_autoadj_fft(arrayOfDouble2, 0, arrayOfDouble5, 0, i);
      FalconFFT.iFFT(arrayOfDouble2, 0, i);
      int i18 = i15 - i17 + i11;
      if (i18 < 0) {
        i18 = -i18;
        d2 = 2.0D;
      } else {
        d2 = 0.5D;
      } 
      double d1 = 1.0D;
      while (i18 != 0) {
        if ((i18 & 0x1) != 0)
          d1 *= d2; 
        i18 >>= 1;
        d2 *= d2;
      } 
      for (b = 0; b < j; b++) {
        double d = arrayOfDouble2[b] * d1;
        if (-2.147483647E9D >= d || d >= 2.147483647E9D)
          return 0; 
        arrayOfInt[b] = (int)FPREngine.fpr_rint(d);
      } 
      int i21 = i15 / 31;
      int i20 = i15 % 31;
      if (paramInt2 <= 4) {
        poly_sub_scaled_ntt(paramArrayOfint, i6, i3, i1, paramArrayOfint, i8, m, m, arrayOfInt, i21, i20, i, paramArrayOfint, i10);
        poly_sub_scaled_ntt(paramArrayOfint, i7, i3, i1, paramArrayOfint, i9, m, m, arrayOfInt, i21, i20, i, paramArrayOfint, i10);
      } else {
        poly_sub_scaled(paramArrayOfint, i6, i3, i1, paramArrayOfint, i8, m, m, arrayOfInt, i21, i20, i);
        poly_sub_scaled(paramArrayOfint, i7, i3, i1, paramArrayOfint, i9, m, m, arrayOfInt, i21, i20, i);
      } 
      int i19 = i15 + i13 + 10;
      if (i19 < i14) {
        i14 = i19;
        if (i3 * 31 >= i14 + 31)
          i3--; 
      } 
      if (i15 <= 0) {
        if (i3 < m) {
          b = 0;
          while (b < j) {
            i18 = -(paramArrayOfint[i6 + i3 - 1] >>> 30) >>> 1;
            for (i17 = i3; i17 < m; i17++)
              paramArrayOfint[i6 + i17] = i18; 
            i18 = -(paramArrayOfint[i7 + i3 - 1] >>> 30) >>> 1;
            for (i17 = i3; i17 < m; i17++)
              paramArrayOfint[i7 + i17] = i18; 
            b++;
            i6 += i1;
            i7 += i1;
          } 
        } 
        b = 0;
        int i22 = 0;
        int i23;
        for (i23 = 0; b < j << 1; i23 += i1) {
          System.arraycopy(paramArrayOfint, i23, paramArrayOfint, i22, m);
          b++;
          i22 += m;
        } 
        return 1;
      } 
      i15 -= 25;
      if (i15 < 0)
        i15 = 0; 
    } 
  }
  
  private static int solve_NTRU_binary_depth1(int paramInt, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int[] paramArrayOfint) {
    byte b1 = 1;
    int j = 1 << paramInt;
    int i = paramInt - b1;
    int k = 1 << i;
    int m = k >> 1;
    int n = MAX_BL_SMALL[b1];
    int i1 = MAX_BL_SMALL[b1 + 1];
    int i2 = MAX_BL_LARGE[b1];
    byte b3 = 0;
    int i3 = b3 + i1 * m;
    int i4 = i3 + i1 * m;
    int i5 = i4 + i2 * k;
    byte b2;
    for (b2 = 0; b2 < i2; b2++) {
      int i9 = (FalconSmallPrimeList.PRIMES[b2]).p;
      int i10 = modp_ninv31(i9);
      int i11 = modp_R2(i9, i10);
      int i12 = modp_Rx(i1, i9, i10, i11);
      byte b = 0;
      int i13 = b3;
      int i14 = i3;
      int i15 = i4 + b2;
      int i16;
      for (i16 = i5 + b2; b < m; i16 += i2) {
        paramArrayOfint[i15] = zint_mod_small_signed(paramArrayOfint, i13, i1, i9, i10, i11, i12);
        paramArrayOfint[i16] = zint_mod_small_signed(paramArrayOfint, i14, i1, i9, i10, i11, i12);
        b++;
        i13 += i1;
        i14 += i1;
        i15 += i2;
      } 
    } 
    System.arraycopy(paramArrayOfint, i4, paramArrayOfint, 0, i2 * k);
    i4 = 0;
    System.arraycopy(paramArrayOfint, i5, paramArrayOfint, i4 + i2 * k, i2 * k);
    i5 = i4 + i2 * k;
    int i6 = i5 + i2 * k;
    int i7 = i6 + n * k;
    int i8 = i7 + n * k;
    for (b2 = 0; b2 < i2; b2++) {
      int i11 = (FalconSmallPrimeList.PRIMES[b2]).p;
      int i12 = modp_ninv31(i11);
      int i13 = modp_R2(i11, i12);
      int i14 = i8;
      int i15 = i14 + j;
      int i16 = i15 + k;
      int i17 = i16 + j;
      modp_mkgm2(paramArrayOfint, i14, paramArrayOfint, i15, paramInt, (FalconSmallPrimeList.PRIMES[b2]).g, i11, i12);
      byte b;
      for (b = 0; b < j; b++) {
        paramArrayOfint[i16 + b] = modp_set(paramArrayOfbyte1[b], i11);
        paramArrayOfint[i17 + b] = modp_set(paramArrayOfbyte2[b], i11);
      } 
      modp_NTT2(paramArrayOfint, i16, paramArrayOfint, i14, paramInt, i11, i12);
      modp_NTT2(paramArrayOfint, i17, paramArrayOfint, i14, paramInt, i11, i12);
      for (int i20 = paramInt; i20 > i; i20--) {
        modp_poly_rec_res(paramArrayOfint, i16, i20, i11, i12, i13);
        modp_poly_rec_res(paramArrayOfint, i17, i20, i11, i12, i13);
      } 
      System.arraycopy(paramArrayOfint, i15, paramArrayOfint, i14 + k, k);
      i15 = i14 + k;
      System.arraycopy(paramArrayOfint, i16, paramArrayOfint, i15 + k, k);
      i16 = i15 + k;
      System.arraycopy(paramArrayOfint, i17, paramArrayOfint, i16 + k, k);
      i17 = i16 + k;
      int i18 = i17 + k;
      int i19 = i18 + m;
      b = 0;
      int i9 = i4 + b2;
      int i10;
      for (i10 = i5 + b2; b < m; i10 += i2) {
        paramArrayOfint[i18 + b] = paramArrayOfint[i9];
        paramArrayOfint[i19 + b] = paramArrayOfint[i10];
        b++;
        i9 += i2;
      } 
      modp_NTT2(paramArrayOfint, i18, paramArrayOfint, i14, i - 1, i11, i12);
      modp_NTT2(paramArrayOfint, i19, paramArrayOfint, i14, i - 1, i11, i12);
      b = 0;
      i9 = i4 + b2;
      for (i10 = i5 + b2; b < m; i10 += i2 << 1) {
        int i21 = paramArrayOfint[i16 + (b << 1)];
        int i22 = paramArrayOfint[i16 + (b << 1) + 1];
        int i23 = paramArrayOfint[i17 + (b << 1)];
        int i24 = paramArrayOfint[i17 + (b << 1) + 1];
        int i25 = modp_montymul(paramArrayOfint[i18 + b], i13, i11, i12);
        int i26 = modp_montymul(paramArrayOfint[i19 + b], i13, i11, i12);
        paramArrayOfint[i9] = modp_montymul(i24, i25, i11, i12);
        paramArrayOfint[i9 + i2] = modp_montymul(i23, i25, i11, i12);
        paramArrayOfint[i10] = modp_montymul(i22, i26, i11, i12);
        paramArrayOfint[i10 + i2] = modp_montymul(i21, i26, i11, i12);
        b++;
        i9 += i2 << 1;
      } 
      modp_iNTT2_ext(paramArrayOfint, i4 + b2, i2, paramArrayOfint, i15, i, i11, i12);
      modp_iNTT2_ext(paramArrayOfint, i5 + b2, i2, paramArrayOfint, i15, i, i11, i12);
      if (b2 < n) {
        modp_iNTT2(paramArrayOfint, i16, paramArrayOfint, i15, i, i11, i12);
        modp_iNTT2(paramArrayOfint, i17, paramArrayOfint, i15, i, i11, i12);
        b = 0;
        i9 = i6 + b2;
        for (i10 = i7 + b2; b < k; i10 += n) {
          paramArrayOfint[i9] = paramArrayOfint[i16 + b];
          paramArrayOfint[i10] = paramArrayOfint[i17 + b];
          b++;
          i9 += n;
        } 
      } 
    } 
    zint_rebuild_CRT(paramArrayOfint, i4, i2, i2, k << 1, 1, paramArrayOfint, i8);
    zint_rebuild_CRT(paramArrayOfint, i6, n, n, k << 1, 1, paramArrayOfint, i8);
    double[] arrayOfDouble1 = new double[k];
    double[] arrayOfDouble2 = new double[k];
    poly_big_to_fp(arrayOfDouble1, paramArrayOfint, i4, i2, i2, i);
    poly_big_to_fp(arrayOfDouble2, paramArrayOfint, i5, i2, i2, i);
    System.arraycopy(paramArrayOfint, i6, paramArrayOfint, 0, 2 * n * k);
    i6 = 0;
    i7 = i6 + n * k;
    double[] arrayOfDouble3 = new double[k];
    double[] arrayOfDouble4 = new double[k];
    poly_big_to_fp(arrayOfDouble3, paramArrayOfint, i6, n, n, i);
    poly_big_to_fp(arrayOfDouble4, paramArrayOfint, i7, n, n, i);
    FalconFFT.FFT(arrayOfDouble1, 0, i);
    FalconFFT.FFT(arrayOfDouble2, 0, i);
    FalconFFT.FFT(arrayOfDouble3, 0, i);
    FalconFFT.FFT(arrayOfDouble4, 0, i);
    double[] arrayOfDouble5 = new double[k];
    double[] arrayOfDouble6 = new double[k >> 1];
    FalconFFT.poly_add_muladj_fft(arrayOfDouble5, arrayOfDouble1, arrayOfDouble2, arrayOfDouble3, arrayOfDouble4, i);
    FalconFFT.poly_invnorm2_fft(arrayOfDouble6, 0, arrayOfDouble3, 0, arrayOfDouble4, 0, i);
    FalconFFT.poly_mul_autoadj_fft(arrayOfDouble5, 0, arrayOfDouble6, 0, i);
    FalconFFT.iFFT(arrayOfDouble5, 0, i);
    for (b2 = 0; b2 < k; b2++) {
      double d = arrayOfDouble5[b2];
      if (d >= 9.223372036854776E18D || -9.223372036854776E18D >= d)
        return 0; 
      arrayOfDouble5[b2] = FPREngine.fpr_rint(d);
    } 
    FalconFFT.FFT(arrayOfDouble5, 0, i);
    FalconFFT.poly_mul_fft(arrayOfDouble3, 0, arrayOfDouble5, 0, i);
    FalconFFT.poly_mul_fft(arrayOfDouble4, 0, arrayOfDouble5, 0, i);
    FalconFFT.poly_sub(arrayOfDouble1, 0, arrayOfDouble3, 0, i);
    FalconFFT.poly_sub(arrayOfDouble2, 0, arrayOfDouble4, 0, i);
    FalconFFT.iFFT(arrayOfDouble1, 0, i);
    FalconFFT.iFFT(arrayOfDouble2, 0, i);
    i5 = i4 + k;
    for (b2 = 0; b2 < k; b2++) {
      paramArrayOfint[i4 + b2] = (int)FPREngine.fpr_rint(arrayOfDouble1[b2]);
      paramArrayOfint[i5 + b2] = (int)FPREngine.fpr_rint(arrayOfDouble2[b2]);
    } 
    return 1;
  }
  
  private static int solve_NTRU_binary_depth0(int paramInt, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int[] paramArrayOfint) {
    int i = 1 << paramInt;
    int j = i >> 1;
    int k = (FalconSmallPrimeList.PRIMES[0]).p;
    int m = modp_ninv31(k);
    int n = modp_R2(k, m);
    byte b2 = 0;
    int i1 = b2 + j;
    int i9 = i1 + j;
    int i10 = i9 + i;
    int i7 = i10 + i;
    int i8 = i7 + i;
    modp_mkgm2(paramArrayOfint, i7, paramArrayOfint, i8, paramInt, (FalconSmallPrimeList.PRIMES[0]).g, k, m);
    byte b1;
    for (b1 = 0; b1 < j; b1++) {
      paramArrayOfint[b2 + b1] = modp_set(zint_one_to_plain(paramArrayOfint, b2 + b1), k);
      paramArrayOfint[i1 + b1] = modp_set(zint_one_to_plain(paramArrayOfint, i1 + b1), k);
    } 
    modp_NTT2(paramArrayOfint, b2, paramArrayOfint, i7, paramInt - 1, k, m);
    modp_NTT2(paramArrayOfint, i1, paramArrayOfint, i7, paramInt - 1, k, m);
    for (b1 = 0; b1 < i; b1++) {
      paramArrayOfint[i9 + b1] = modp_set(paramArrayOfbyte1[b1], k);
      paramArrayOfint[i10 + b1] = modp_set(paramArrayOfbyte2[b1], k);
    } 
    modp_NTT2(paramArrayOfint, i9, paramArrayOfint, i7, paramInt, k, m);
    modp_NTT2(paramArrayOfint, i10, paramArrayOfint, i7, paramInt, k, m);
    for (b1 = 0; b1 < i; b1 += 2) {
      int i13 = paramArrayOfint[i9 + b1];
      int i14 = paramArrayOfint[i9 + b1 + 1];
      int i15 = paramArrayOfint[i10 + b1];
      int i16 = paramArrayOfint[i10 + b1 + 1];
      int i17 = modp_montymul(paramArrayOfint[b2 + (b1 >> 1)], n, k, m);
      int i18 = modp_montymul(paramArrayOfint[i1 + (b1 >> 1)], n, k, m);
      paramArrayOfint[i9 + b1] = modp_montymul(i16, i17, k, m);
      paramArrayOfint[i9 + b1 + 1] = modp_montymul(i15, i17, k, m);
      paramArrayOfint[i10 + b1] = modp_montymul(i14, i18, k, m);
      paramArrayOfint[i10 + b1 + 1] = modp_montymul(i13, i18, k, m);
    } 
    modp_iNTT2(paramArrayOfint, i9, paramArrayOfint, i8, paramInt, k, m);
    modp_iNTT2(paramArrayOfint, i10, paramArrayOfint, i8, paramInt, k, m);
    i1 = b2 + i;
    int i2 = i1 + i;
    System.arraycopy(paramArrayOfint, i9, paramArrayOfint, b2, 2 * i);
    int i3 = i2 + i;
    int i4 = i3 + i;
    int i5 = i4 + i;
    int i6 = i5 + i;
    modp_mkgm2(paramArrayOfint, i2, paramArrayOfint, i3, paramInt, (FalconSmallPrimeList.PRIMES[0]).g, k, m);
    modp_NTT2(paramArrayOfint, b2, paramArrayOfint, i2, paramInt, k, m);
    modp_NTT2(paramArrayOfint, i1, paramArrayOfint, i2, paramInt, k, m);
    paramArrayOfint[i6] = modp_set(paramArrayOfbyte1[0], k);
    paramArrayOfint[i5] = modp_set(paramArrayOfbyte1[0], k);
    for (b1 = 1; b1 < i; b1++) {
      paramArrayOfint[i5 + b1] = modp_set(paramArrayOfbyte1[b1], k);
      paramArrayOfint[i6 + i - b1] = modp_set(-paramArrayOfbyte1[b1], k);
    } 
    modp_NTT2(paramArrayOfint, i5, paramArrayOfint, i2, paramInt, k, m);
    modp_NTT2(paramArrayOfint, i6, paramArrayOfint, i2, paramInt, k, m);
    for (b1 = 0; b1 < i; b1++) {
      int i13 = modp_montymul(paramArrayOfint[i6 + b1], n, k, m);
      paramArrayOfint[i3 + b1] = modp_montymul(i13, paramArrayOfint[b2 + b1], k, m);
      paramArrayOfint[i4 + b1] = modp_montymul(i13, paramArrayOfint[i5 + b1], k, m);
    } 
    paramArrayOfint[i6] = modp_set(paramArrayOfbyte2[0], k);
    paramArrayOfint[i5] = modp_set(paramArrayOfbyte2[0], k);
    for (b1 = 1; b1 < i; b1++) {
      paramArrayOfint[i5 + b1] = modp_set(paramArrayOfbyte2[b1], k);
      paramArrayOfint[i6 + i - b1] = modp_set(-paramArrayOfbyte2[b1], k);
    } 
    modp_NTT2(paramArrayOfint, i5, paramArrayOfint, i2, paramInt, k, m);
    modp_NTT2(paramArrayOfint, i6, paramArrayOfint, i2, paramInt, k, m);
    for (b1 = 0; b1 < i; b1++) {
      int i13 = modp_montymul(paramArrayOfint[i6 + b1], n, k, m);
      paramArrayOfint[i3 + b1] = modp_add(paramArrayOfint[i3 + b1], modp_montymul(i13, paramArrayOfint[i1 + b1], k, m), k);
      paramArrayOfint[i4 + b1] = modp_add(paramArrayOfint[i4 + b1], modp_montymul(i13, paramArrayOfint[i5 + b1], k, m), k);
    } 
    modp_mkgm2(paramArrayOfint, i2, paramArrayOfint, i5, paramInt, (FalconSmallPrimeList.PRIMES[0]).g, k, m);
    modp_iNTT2(paramArrayOfint, i3, paramArrayOfint, i5, paramInt, k, m);
    modp_iNTT2(paramArrayOfint, i4, paramArrayOfint, i5, paramInt, k, m);
    for (b1 = 0; b1 < i; b1++) {
      paramArrayOfint[i2 + b1] = modp_norm(paramArrayOfint[i3 + b1], k);
      paramArrayOfint[i3 + b1] = modp_norm(paramArrayOfint[i4 + b1], k);
    } 
    double[] arrayOfDouble = new double[3 * i];
    byte b3 = 0;
    int i11 = b3 + i;
    int i12 = i11 + i;
    for (b1 = 0; b1 < i; b1++)
      arrayOfDouble[i12 + b1] = paramArrayOfint[i3 + b1]; 
    FalconFFT.FFT(arrayOfDouble, i12, paramInt);
    System.arraycopy(arrayOfDouble, i12, arrayOfDouble, i11, j);
    i12 = i11 + j;
    for (b1 = 0; b1 < i; b1++)
      arrayOfDouble[i12 + b1] = paramArrayOfint[i2 + b1]; 
    FalconFFT.FFT(arrayOfDouble, i12, paramInt);
    FalconFFT.poly_div_autoadj_fft(arrayOfDouble, i12, arrayOfDouble, i11, paramInt);
    FalconFFT.iFFT(arrayOfDouble, i12, paramInt);
    for (b1 = 0; b1 < i; b1++)
      paramArrayOfint[i2 + b1] = modp_set((int)FPREngine.fpr_rint(arrayOfDouble[i12 + b1]), k); 
    i3 = i2 + i;
    i4 = i3 + i;
    i5 = i4 + i;
    i6 = i5 + i;
    modp_mkgm2(paramArrayOfint, i3, paramArrayOfint, i4, paramInt, (FalconSmallPrimeList.PRIMES[0]).g, k, m);
    for (b1 = 0; b1 < i; b1++) {
      paramArrayOfint[i5 + b1] = modp_set(paramArrayOfbyte1[b1], k);
      paramArrayOfint[i6 + b1] = modp_set(paramArrayOfbyte2[b1], k);
    } 
    modp_NTT2(paramArrayOfint, i2, paramArrayOfint, i3, paramInt, k, m);
    modp_NTT2(paramArrayOfint, i5, paramArrayOfint, i3, paramInt, k, m);
    modp_NTT2(paramArrayOfint, i6, paramArrayOfint, i3, paramInt, k, m);
    for (b1 = 0; b1 < i; b1++) {
      int i13 = modp_montymul(paramArrayOfint[i2 + b1], n, k, m);
      paramArrayOfint[b2 + b1] = modp_sub(paramArrayOfint[b2 + b1], modp_montymul(i13, paramArrayOfint[i5 + b1], k, m), k);
      paramArrayOfint[i1 + b1] = modp_sub(paramArrayOfint[i1 + b1], modp_montymul(i13, paramArrayOfint[i6 + b1], k, m), k);
    } 
    modp_iNTT2(paramArrayOfint, b2, paramArrayOfint, i4, paramInt, k, m);
    modp_iNTT2(paramArrayOfint, i1, paramArrayOfint, i4, paramInt, k, m);
    for (b1 = 0; b1 < i; b1++) {
      paramArrayOfint[b2 + b1] = modp_norm(paramArrayOfint[b2 + b1], k);
      paramArrayOfint[i1 + b1] = modp_norm(paramArrayOfint[i1 + b1], k);
    } 
    return 1;
  }
  
  private static int solve_NTRU(int paramInt1, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, int[] paramArrayOfint) {
    byte b3 = 0;
    int i = mkn(paramInt1);
    if (solve_NTRU_deepest(paramInt1, paramArrayOfbyte2, paramArrayOfbyte3, paramArrayOfint) == 0)
      return 0; 
    if (paramInt1 <= 2) {
      int i4 = paramInt1;
      while (i4-- > 0) {
        if (solve_NTRU_intermediate(paramInt1, paramArrayOfbyte2, paramArrayOfbyte3, i4, paramArrayOfint) == 0)
          return 0; 
      } 
    } else {
      int i4 = paramInt1;
      while (i4-- > 2) {
        if (solve_NTRU_intermediate(paramInt1, paramArrayOfbyte2, paramArrayOfbyte3, i4, paramArrayOfint) == 0)
          return 0; 
      } 
      if (solve_NTRU_binary_depth1(paramInt1, paramArrayOfbyte2, paramArrayOfbyte3, paramArrayOfint) == 0)
        return 0; 
      if (solve_NTRU_binary_depth0(paramInt1, paramArrayOfbyte2, paramArrayOfbyte3, paramArrayOfint) == 0)
        return 0; 
    } 
    byte[] arrayOfByte = new byte[i];
    if (poly_big_to_small(paramArrayOfbyte1, 0, paramArrayOfint, 0, paramInt2, paramInt1) == 0 || poly_big_to_small(arrayOfByte, b3, paramArrayOfint, i, paramInt2, paramInt1) == 0)
      return 0; 
    byte b2 = 0;
    int j = b2 + i;
    int k = j + i;
    int m = k + i;
    int n = m + i;
    int i1 = (FalconSmallPrimeList.PRIMES[0]).p;
    int i2 = modp_ninv31(i1);
    modp_mkgm2(paramArrayOfint, n, paramArrayOfint, 0, paramInt1, (FalconSmallPrimeList.PRIMES[0]).g, i1, i2);
    byte b1;
    for (b1 = 0; b1 < i; b1++)
      paramArrayOfint[b2 + b1] = modp_set(arrayOfByte[b3 + b1], i1); 
    for (b1 = 0; b1 < i; b1++) {
      paramArrayOfint[j + b1] = modp_set(paramArrayOfbyte2[b1], i1);
      paramArrayOfint[k + b1] = modp_set(paramArrayOfbyte3[b1], i1);
      paramArrayOfint[m + b1] = modp_set(paramArrayOfbyte1[b1], i1);
    } 
    modp_NTT2(paramArrayOfint, j, paramArrayOfint, n, paramInt1, i1, i2);
    modp_NTT2(paramArrayOfint, k, paramArrayOfint, n, paramInt1, i1, i2);
    modp_NTT2(paramArrayOfint, m, paramArrayOfint, n, paramInt1, i1, i2);
    modp_NTT2(paramArrayOfint, b2, paramArrayOfint, n, paramInt1, i1, i2);
    int i3 = modp_montymul(12289, 1, i1, i2);
    for (b1 = 0; b1 < i; b1++) {
      int i4 = modp_sub(modp_montymul(paramArrayOfint[j + b1], paramArrayOfint[b2 + b1], i1, i2), modp_montymul(paramArrayOfint[k + b1], paramArrayOfint[m + b1], i1, i2), i1);
      if (i4 != i3)
        return 0; 
    } 
    return 1;
  }
  
  private static void poly_small_mkgauss(SHAKEDigest paramSHAKEDigest, byte[] paramArrayOfbyte, int paramInt) {
    int i = mkn(paramInt);
    int j = 0;
    for (byte b = 0; b < i; b++) {
      int k;
      while (true) {
        k = mkgauss(paramSHAKEDigest, paramInt);
        if (k < -127 || k > 127)
          continue; 
        if (b == i - 1) {
          if ((j ^ k & 0x1) == 0)
            continue; 
          break;
        } 
        j ^= k & 0x1;
        break;
      } 
      paramArrayOfbyte[b] = (byte)k;
    } 
  }
  
  static void keygen(SHAKEDigest paramSHAKEDigest, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, short[] paramArrayOfshort, int paramInt) {
    int i = mkn(paramInt);
    while (true) {
      boolean bool1;
      boolean bool2;
      double[] arrayOfDouble = new double[3 * i];
      poly_small_mkgauss(paramSHAKEDigest, paramArrayOfbyte1, paramInt);
      poly_small_mkgauss(paramSHAKEDigest, paramArrayOfbyte2, paramInt);
      int i2 = 1 << FalconCodec.max_fg_bits[paramInt] - 1;
      byte b1;
      for (b1 = 0; b1 < i; b1++) {
        if (paramArrayOfbyte1[b1] >= i2 || paramArrayOfbyte1[b1] <= -i2 || paramArrayOfbyte2[b1] >= i2 || paramArrayOfbyte2[b1] <= -i2) {
          i2 = -1;
          break;
        } 
      } 
      if (i2 < 0)
        continue; 
      int m = poly_small_sqnorm(paramArrayOfbyte1, paramInt);
      int n = poly_small_sqnorm(paramArrayOfbyte2, paramInt);
      int i1 = m + n | -((m | n) >>> 31);
      if ((i1 & 0xFFFFFFFFL) >= 16823L)
        continue; 
      byte b2 = 0;
      int j = b2 + i;
      int k = j + i;
      poly_small_to_fp(arrayOfDouble, b2, paramArrayOfbyte1, paramInt);
      poly_small_to_fp(arrayOfDouble, j, paramArrayOfbyte2, paramInt);
      FalconFFT.FFT(arrayOfDouble, b2, paramInt);
      FalconFFT.FFT(arrayOfDouble, j, paramInt);
      FalconFFT.poly_invnorm2_fft(arrayOfDouble, k, arrayOfDouble, b2, arrayOfDouble, j, paramInt);
      FalconFFT.poly_adj_fft(arrayOfDouble, b2, paramInt);
      FalconFFT.poly_adj_fft(arrayOfDouble, j, paramInt);
      FalconFFT.poly_mulconst(arrayOfDouble, b2, 12289.0D, paramInt);
      FalconFFT.poly_mulconst(arrayOfDouble, j, 12289.0D, paramInt);
      FalconFFT.poly_mul_autoadj_fft(arrayOfDouble, b2, arrayOfDouble, k, paramInt);
      FalconFFT.poly_mul_autoadj_fft(arrayOfDouble, j, arrayOfDouble, k, paramInt);
      FalconFFT.iFFT(arrayOfDouble, b2, paramInt);
      FalconFFT.iFFT(arrayOfDouble, j, paramInt);
      double d = 0.0D;
      for (b1 = 0; b1 < i; b1++)
        d += arrayOfDouble[b2 + b1] * arrayOfDouble[b2 + b1] + arrayOfDouble[j + b1] * arrayOfDouble[j + b1]; 
      if (d >= 16822.4121D)
        continue; 
      short[] arrayOfShort = new short[2 * i];
      if (paramArrayOfshort == null) {
        bool1 = false;
        paramArrayOfshort = arrayOfShort;
        bool2 = bool1 + i;
      } else {
        bool1 = false;
        bool2 = false;
      } 
      if (FalconVrfy.compute_public(paramArrayOfshort, bool1, paramArrayOfbyte1, paramArrayOfbyte2, paramInt, arrayOfShort, bool2) == 0)
        continue; 
      int[] arrayOfInt = (paramInt > 2) ? new int[28 * i] : new int[28 * i * 3];
      i2 = (1 << FalconCodec.max_FG_bits[paramInt] - 1) - 1;
      if (solve_NTRU(paramInt, paramArrayOfbyte3, paramArrayOfbyte1, paramArrayOfbyte2, i2, arrayOfInt) == 0)
        continue; 
      break;
    } 
  }
  
  private static long toUnsignedLong(int paramInt) {
    return paramInt & 0xFFFFFFFFL;
  }
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bouncycastle\pqc\crypto\falcon\FalconKeyGen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */