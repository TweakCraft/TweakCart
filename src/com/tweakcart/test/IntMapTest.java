package com.tweakcart.test;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import com.tweakcart.model.IntMap;
import com.tweakcart.model.SignParser;

public class IntMapTest {
    public static void main(String[] args){
//        System.out.println(IntMap.getIntIndex(0, (byte) 0));
//        System.out.println(IntMap.getIntIndex(1, (byte)0));
//        System.out.println(IntMap.getIntIndex(2, (byte)0));
//        System.out.println(IntMap.getIntIndex(3, (byte)0));
//        System.out.println(IntMap.getIntIndex(4, (byte)0));
//        System.out.println(IntMap.getIntIndex(5, (byte)0));
//        System.out.println(IntMap.getIntIndex(6, (byte)0));//sapling
//        System.out.println(IntMap.getIntIndex(6, (byte)1));//sapling
//        System.out.println(IntMap.getIntIndex(6, (byte)2));//sapling
//        System.out.println(IntMap.getIntIndex(7, (byte)0));
//        System.out.println(IntMap.getIntIndex(8, (byte)0));
//        System.out.println(IntMap.getIntIndex(9, (byte)0));
//        System.out.println(IntMap.getIntIndex(10, (byte)0));
//        System.out.println(IntMap.getIntIndex(11, (byte)0));
//        System.out.println(IntMap.getIntIndex(12, (byte)0));
//        System.out.println(IntMap.getIntIndex(13, (byte)0));
//        System.out.println(IntMap.getIntIndex(14, (byte)0));
//        System.out.println(IntMap.getIntIndex(15, (byte)0));
//        System.out.println(IntMap.getIntIndex(16, (byte)0));
//        System.out.println(IntMap.getIntIndex(17, (byte)0));//wood
//        System.out.println(IntMap.getIntIndex(17, (byte)1));//wood
//        System.out.println(IntMap.getIntIndex(17, (byte)2));//wood
//        System.out.println(IntMap.getIntIndex(18, (byte)0));//leaves
//        System.out.println(IntMap.getIntIndex(18, (byte)1));//leaves
//        System.out.println(IntMap.getIntIndex(18, (byte)2));//leaves
//        System.out.println(IntMap.getIntIndex(19, (byte)0));
//        System.out.println(IntMap.getIntIndex(20, (byte)0));
//        System.out.println(IntMap.getIntIndex(21, (byte)0));
//        System.out.println(IntMap.getIntIndex(22, (byte)0));
//        System.out.println(IntMap.getIntIndex(23, (byte)0));
//        System.out.println(IntMap.getIntIndex(24, (byte)0));
//        System.out.println(IntMap.getIntIndex(25, (byte)0));
//        System.out.println(IntMap.getIntIndex(26, (byte)0));
//        System.out.println(IntMap.getIntIndex(27, (byte)0));
//        System.out.println(IntMap.getIntIndex(28, (byte)0));
//        System.out.println(IntMap.getIntIndex(29, (byte)0));
//        System.out.println(IntMap.getIntIndex(30, (byte)0));
//        System.out.println(IntMap.getIntIndex(31, (byte)0));//tallgrass
//        System.out.println(IntMap.getIntIndex(32, (byte)0));
//        System.out.println(IntMap.getIntIndex(33, (byte)0));
//        System.out.println(IntMap.getIntIndex(34, (byte)0));
//        System.out.println(IntMap.getIntIndex(35, (byte)0));//wool
//        System.out.println(IntMap.getIntIndex(35, (byte)1));//wool
//        System.out.println(IntMap.getIntIndex(35, (byte)2));//wool
//        System.out.println(IntMap.getIntIndex(35, (byte)3));//wool
//        System.out.println(IntMap.getIntIndex(35, (byte)4));//wool
//        System.out.println(IntMap.getIntIndex(35, (byte)5));//wool
//        System.out.println(IntMap.getIntIndex(35, (byte)6));//wool
//        System.out.println(IntMap.getIntIndex(35, (byte)7));//wool
//        System.out.println(IntMap.getIntIndex(35, (byte)8));//wool
//        System.out.println(IntMap.getIntIndex(35, (byte)9));//wool
//        System.out.println(IntMap.getIntIndex(35, (byte)10));//wool
//        System.out.println(IntMap.getIntIndex(35, (byte)11));//wool
//        System.out.println(IntMap.getIntIndex(35, (byte)12));//wool
//        System.out.println(IntMap.getIntIndex(35, (byte)13));//wool
//        System.out.println(IntMap.getIntIndex(35, (byte)14));//wool
//        System.out.println(IntMap.getIntIndex(35, (byte)15));//wool
//        System.out.println(IntMap.getIntIndex(36, (byte)0));
//        System.out.println(IntMap.getIntIndex(37, (byte)0));
//        System.out.println(IntMap.getIntIndex(38, (byte)0));
//        System.out.println(IntMap.getIntIndex(39, (byte)0));
//        System.out.println(IntMap.getIntIndex(40, (byte)0));
//        System.out.println(IntMap.getIntIndex(41, (byte)0));
//        System.out.println(IntMap.getIntIndex(42, (byte)0));
//        System.out.println(IntMap.getIntIndex(43, (byte)0));//doubleslabs, only inventory edit
        System.out.println("STEPS");
        System.out.println(IntMap.getIntIndex(44, (byte)0));//slabs <----- DIT IS SOWIESO FOUT
        System.out.println(IntMap.getIntIndex(44, (byte)1));//slabs <----- DIT IS SOWIESO FOUT
        System.out.println(IntMap.getIntIndex(44, (byte)2));//slabs <----- DIT IS SOWIESO FOUT
        System.out.println(IntMap.getIntIndex(44, (byte)3));//slabs <----- DIT IS SOWIESO FOUT
        System.out.println(IntMap.getIntIndex(44, (byte)4));//slabs <----- DIT IS SOWIESO FOUT
        System.out.println(IntMap.getIntIndex(44, (byte)5));//slabs <----- DIT IS SOWIESO FOUT
        System.out.println(IntMap.getIntIndex(44, (byte)6));//slabs <----- DIT IS SOWIESO FOUT
//        System.out.println(IntMap.getIntIndex(45, (byte)0));
//        System.out.println(IntMap.getIntIndex(46, (byte)0));
//        System.out.println(IntMap.getIntIndex(47, (byte)0));
//        System.out.println(IntMap.getIntIndex(48, (byte)0));
//        System.out.println(IntMap.getIntIndex(49, (byte)0));
//        System.out.println(IntMap.getIntIndex(50, (byte)0));
//        System.out.println(IntMap.getIntIndex(51, (byte)0));
//        System.out.println(IntMap.getIntIndex(52, (byte)0));
//        System.out.println(IntMap.getIntIndex(53, (byte)0));
//        System.out.println(IntMap.getIntIndex(54, (byte)0));
//        System.out.println(IntMap.getIntIndex(55, (byte)0));
//        System.out.println(IntMap.getIntIndex(56, (byte)0));
//        System.out.println(IntMap.getIntIndex(57, (byte)0));
//        System.out.println(IntMap.getIntIndex(58, (byte)0));
//        System.out.println(IntMap.getIntIndex(59, (byte)0));
//        System.out.println(IntMap.getIntIndex(60, (byte)0));
//        System.out.println(IntMap.getIntIndex(61, (byte)0));
//        System.out.println(IntMap.getIntIndex(62, (byte)0));
//        System.out.println(IntMap.getIntIndex(63, (byte)0));
//        System.out.println(IntMap.getIntIndex(64, (byte)0));
//        System.out.println(IntMap.getIntIndex(65, (byte)0));
//        System.out.println(IntMap.getIntIndex(66, (byte)0));
//        System.out.println(IntMap.getIntIndex(67, (byte)0));
//        System.out.println(IntMap.getIntIndex(68, (byte)0));
//        System.out.println(IntMap.getIntIndex(69, (byte)0));
//        System.out.println(IntMap.getIntIndex(70, (byte)0));
//        System.out.println(IntMap.getIntIndex(71, (byte)0));
//        System.out.println(IntMap.getIntIndex(72, (byte)0));
//        System.out.println(IntMap.getIntIndex(73, (byte)0));
//        System.out.println(IntMap.getIntIndex(74, (byte)0));
//        System.out.println(IntMap.getIntIndex(75, (byte)0));
//        System.out.println(IntMap.getIntIndex(76, (byte)0));
//        System.out.println(IntMap.getIntIndex(77, (byte)0));
//        System.out.println(IntMap.getIntIndex(78, (byte)0));
//        System.out.println(IntMap.getIntIndex(79, (byte)0));
//        System.out.println(IntMap.getIntIndex(80, (byte)0));
//        System.out.println(IntMap.getIntIndex(81, (byte)0));
//        System.out.println(IntMap.getIntIndex(82, (byte)0));
//        System.out.println(IntMap.getIntIndex(83, (byte)0));
//        System.out.println(IntMap.getIntIndex(84, (byte)0));
//        System.out.println(IntMap.getIntIndex(85, (byte)0));
//        System.out.println(IntMap.getIntIndex(86, (byte)0));
//        System.out.println(IntMap.getIntIndex(87, (byte)0));
//        System.out.println(IntMap.getIntIndex(88, (byte)0));
//        System.out.println(IntMap.getIntIndex(89, (byte)0));
//        System.out.println(IntMap.getIntIndex(90, (byte)0));
//        System.out.println(IntMap.getIntIndex(91, (byte)0));
//        System.out.println(IntMap.getIntIndex(92, (byte)0));
//        System.out.println(IntMap.getIntIndex(93, (byte)0));
//        System.out.println(IntMap.getIntIndex(94, (byte)0));
//        System.out.println(IntMap.getIntIndex(95, (byte)0));
//        System.out.println(IntMap.getIntIndex(96, (byte)0)); //==Eind van Alle blockid's==
//        
//        System.out.println(IntMap.getIntIndex(256, (byte)0));
//        System.out.println(IntMap.getIntIndex(257, (byte)0));
//        System.out.println(IntMap.getIntIndex(258, (byte)0));
//        System.out.println(IntMap.getIntIndex(259, (byte)0));
//        System.out.println(IntMap.getIntIndex(260, (byte)0));
//        System.out.println(IntMap.getIntIndex(261, (byte)0));
//        System.out.println(IntMap.getIntIndex(262, (byte)0));
//        System.out.println(IntMap.getIntIndex(263, (byte)0));//coal
//        System.out.println("coal incoming");
//        System.out.println(IntMap.getIntIndex(263, (byte)1));//coal
//        System.out.println(IntMap.getIntIndex(264, (byte)0));        
//        System.out.println(IntMap.getIntIndex(265, (byte)0));
//        System.out.println(IntMap.getIntIndex(266, (byte)0));
//        System.out.println(IntMap.getIntIndex(267, (byte)0));
//        System.out.println(IntMap.getIntIndex(268, (byte)0));
//        System.out.println(IntMap.getIntIndex(269, (byte)0));
//        System.out.println(IntMap.getIntIndex(270, (byte)0));
//        System.out.println(IntMap.getIntIndex(271, (byte)0));
//        System.out.println(IntMap.getIntIndex(272, (byte)0));
//        System.out.println(IntMap.getIntIndex(273, (byte)0));
//        System.out.println(IntMap.getIntIndex(274, (byte)0));
//        System.out.println(IntMap.getIntIndex(275, (byte)0));
//        System.out.println(IntMap.getIntIndex(276, (byte)0));
//        System.out.println(IntMap.getIntIndex(277, (byte)0));
//        System.out.println(IntMap.getIntIndex(278, (byte)0));
//        System.out.println(IntMap.getIntIndex(279, (byte)0));
//        System.out.println(IntMap.getIntIndex(280, (byte)0));
//        System.out.println(IntMap.getIntIndex(281, (byte)0));
//        System.out.println(IntMap.getIntIndex(282, (byte)0));
//        System.out.println(IntMap.getIntIndex(283, (byte)0));
//        System.out.println(IntMap.getIntIndex(284, (byte)0));
//        System.out.println(IntMap.getIntIndex(285, (byte)0));
//        System.out.println(IntMap.getIntIndex(286, (byte)0));
//        System.out.println(IntMap.getIntIndex(287, (byte)0));
//        System.out.println(IntMap.getIntIndex(288, (byte)0));
//        System.out.println(IntMap.getIntIndex(289, (byte)0));
//        System.out.println(IntMap.getIntIndex(290, (byte)0));
//        System.out.println(IntMap.getIntIndex(291, (byte)0));
//        System.out.println(IntMap.getIntIndex(292, (byte)0));
//        System.out.println(IntMap.getIntIndex(293, (byte)0));
//        System.out.println(IntMap.getIntIndex(294, (byte)0));
//        System.out.println(IntMap.getIntIndex(295, (byte)0));
//        System.out.println(IntMap.getIntIndex(296, (byte)0));
//        System.out.println(IntMap.getIntIndex(297, (byte)0));
//        System.out.println(IntMap.getIntIndex(298, (byte)0));
//        System.out.println(IntMap.getIntIndex(299, (byte)0));
//        System.out.println(IntMap.getIntIndex(300, (byte)0));
//        System.out.println(IntMap.getIntIndex(301, (byte)0));
//        System.out.println(IntMap.getIntIndex(302, (byte)0));
//        System.out.println(IntMap.getIntIndex(303, (byte)0));
//        System.out.println(IntMap.getIntIndex(304, (byte)0));
//        System.out.println(IntMap.getIntIndex(305, (byte)0));
//        System.out.println(IntMap.getIntIndex(306, (byte)0));
//        System.out.println(IntMap.getIntIndex(307, (byte)0));
//        System.out.println(IntMap.getIntIndex(308, (byte)0));
//        System.out.println(IntMap.getIntIndex(309, (byte)0));
//        System.out.println(IntMap.getIntIndex(310, (byte)0));
//        System.out.println(IntMap.getIntIndex(311, (byte)0));
//        System.out.println(IntMap.getIntIndex(312, (byte)0));
//        System.out.println(IntMap.getIntIndex(313, (byte)0));
//        System.out.println(IntMap.getIntIndex(314, (byte)0));
//        System.out.println(IntMap.getIntIndex(315, (byte)0));
//        System.out.println(IntMap.getIntIndex(316, (byte)0));
//        System.out.println(IntMap.getIntIndex(317, (byte)0));
//        System.out.println(IntMap.getIntIndex(318, (byte)0));
//        System.out.println(IntMap.getIntIndex(319, (byte)0));
//        System.out.println(IntMap.getIntIndex(320, (byte)0));
//        System.out.println(IntMap.getIntIndex(321, (byte)0));
//        System.out.println(IntMap.getIntIndex(322, (byte)0));
//        System.out.println(IntMap.getIntIndex(323, (byte)0));
//        System.out.println(IntMap.getIntIndex(324, (byte)0));
//        System.out.println(IntMap.getIntIndex(325, (byte)0));
//        System.out.println(IntMap.getIntIndex(326, (byte)0));
//        System.out.println(IntMap.getIntIndex(327, (byte)0));
//        System.out.println(IntMap.getIntIndex(328, (byte)0));
//        System.out.println(IntMap.getIntIndex(329, (byte)0));
//        System.out.println(IntMap.getIntIndex(330, (byte)0));
//        System.out.println(IntMap.getIntIndex(331, (byte)0));
//        System.out.println(IntMap.getIntIndex(332, (byte)0));
//        System.out.println(IntMap.getIntIndex(333, (byte)0));
//        System.out.println(IntMap.getIntIndex(334, (byte)0));
//        System.out.println(IntMap.getIntIndex(335, (byte)0));
//        System.out.println(IntMap.getIntIndex(336, (byte)0));
//        System.out.println(IntMap.getIntIndex(337, (byte)0));
//        System.out.println(IntMap.getIntIndex(338, (byte)0));
//        System.out.println(IntMap.getIntIndex(339, (byte)0));
//        System.out.println(IntMap.getIntIndex(340, (byte)0));
//        System.out.println(IntMap.getIntIndex(341, (byte)0));
//        System.out.println(IntMap.getIntIndex(342, (byte)0));
//        System.out.println(IntMap.getIntIndex(343, (byte)0));
//        System.out.println(IntMap.getIntIndex(344, (byte)0));
//        System.out.println(IntMap.getIntIndex(345, (byte)0));
//        System.out.println(IntMap.getIntIndex(346, (byte)0));
//        System.out.println(IntMap.getIntIndex(347, (byte)0));
//        System.out.println(IntMap.getIntIndex(348, (byte)0));
//        System.out.println(IntMap.getIntIndex(349, (byte)0));
//        System.out.println(IntMap.getIntIndex(350, (byte)0));
//        System.out.println("==Wol==");
//        System.out.println(IntMap.getIntIndex(351, (byte)0)); //WOL
//        System.out.println(IntMap.getIntIndex(351, (byte)1)); //WOL
//        System.out.println(IntMap.getIntIndex(351, (byte)2)); //WOL
//        System.out.println(IntMap.getIntIndex(351, (byte)3)); //WOL
//        System.out.println(IntMap.getIntIndex(351, (byte)4)); //WOL
//        System.out.println(IntMap.getIntIndex(351, (byte)5)); //WOL
//        System.out.println(IntMap.getIntIndex(351, (byte)6)); //WOL
//        System.out.println(IntMap.getIntIndex(351, (byte)7)); //WOL
//        System.out.println(IntMap.getIntIndex(351, (byte)8)); //WOL
//        System.out.println(IntMap.getIntIndex(351, (byte)9)); //WOL
//        System.out.println(IntMap.getIntIndex(351, (byte)10)); //WOL
//        System.out.println(IntMap.getIntIndex(351, (byte)11)); //WOL
//        System.out.println(IntMap.getIntIndex(351, (byte)12)); //WOL
//        System.out.println(IntMap.getIntIndex(351, (byte)13)); //WOL
//        System.out.println(IntMap.getIntIndex(351, (byte)14)); //WOL
//        System.out.println(IntMap.getIntIndex(351, (byte)15)); //WOL
//        System.out.println(IntMap.getIntIndex(352, (byte) 0));
//        System.out.println(IntMap.getIntIndex(353, (byte) 0));
//        System.out.println(IntMap.getIntIndex(354, (byte) 0));
//        System.out.println(IntMap.getIntIndex(355, (byte) 0));
//        System.out.println(IntMap.getIntIndex(356, (byte) 0));
//        System.out.println(IntMap.getIntIndex(357, (byte) 0));
//        System.out.println(IntMap.getIntIndex(358, (byte) 0));
//        System.out.println(IntMap.getIntIndex(359, (byte) 0));
//        System.out.println(IntMap.getIntIndex(2256, (byte) 0));
//        System.out.println(IntMap.getIntIndex(3333, (byte) 0));
        
        System.out.println("Material Size: " + Material.values().length + " Total: " + (Material.values().length + 54));
        System.out.println("IntMap reporting size: " + IntMap.mapSize);
        System.out.println("Wol data 4: " + new MaterialData(351, (byte)4));
        
        System.out.println(SignParser.buildIntMap("44,0"));
    }
}
