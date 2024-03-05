package com.icss.poie.tools.excel.utils.xlsx;

import lombok.Getter;

@Getter
public enum ColorIndex {

    BLACK1(0,"#000000"),
    WHITE1(1,"#FFFFFF"),
    RED1(2,"#FF0000"),
    BRIGHT_GREEN1(3,"#00FF00"),
    BLUE1(4,"#0000FF"),
    YELLOW1(5,"#FFFF00"),
    PINK1(6,"#FF00FF"),
    TURQUOISE1(7,"#00FFFF"),
    BLACK(8,"#000000"),
    WHITE(9,"#FFFFFF"),
    AUTOMATIC(4,"#FFFFFF"),
    RED(10,"#FF0000"),
    BRIGHT_GREEN(11,"#00FF00"),
    BLUE(12,"#0000FF"),
    YELLOW(13,"#FFFF00"),
    PINK(14,"#FF00FF"),
    TURQUOISE(15,"#00FFFF"),
    DARK_RED(16,"#800000"),
    GREEN(17,"#008000"),
    DARK_BLUE(18,"#000080"),
    DARK_YELLOW(19,"#808000"),
    VIOLET(20,"#800080"),
    TEAL(21,"#008080"),
    GREY_25_PERCENT(22,"#C0C0C0"),
    GREY_50_PERCENT(23,"#808080"),
    CORNFLOWER_BLUE(24,"#9999FF"),
    MAROON(25,"#993366"),
    LEMON_CHIFFON(26,"#FFFFCC"),
    LIGHT_TURQUOISE1(27,"#CCFFFF"),
    ORCHID(28,"#660066"),
    CORAL(29,"#FF8080"),
    ROYAL_BLUE(30,"#0066CC"),
    LIGHT_CORNFLOWER_BLUE(31,"#CCCCFF"),
    SKY_BLUE(40,"#00CCFF"),
    LIGHT_TURQUOISE(41,"#CCFFFF"),
    LIGHT_GREEN(42,"#CCFFCC"),
    LIGHT_YELLOW(43,"#FFFF99"),
    PALE_BLUE(44,"#99CCFF"),
    ROSE(45,"#FF99CC"),
    LAVENDER(46,"#CC99FF"),
    TAN(47,"#FFCC99"),
    LIGHT_BLUE(48,"#3366FF"),
    AQUA(49,"#33CCCC"),
    LIME(50,"#99CC00"),
    GOLD(51,"#FFCC00"),
    LIGHT_ORANGE(52,"#FF9900"),
    ORANGE(53,"#FF6600"),
    BLUE_GREY(54,"#666699"),
    GREY_40_PERCENT(55,"#969696"),
    DARK_TEAL(56,"#003366"),
    SEA_GREEN(57,"#339966"),
    DARK_GREEN(58,"#003300"),
    OLIVE_GREEN(59,"#333300"),
    BROWN(60,"#993300"),
    PLUM(61,"#993366"),
    INDIGO(62,"#333399"),
    GREY_80_PERCENT(63,"#333333"),
    AUTOMATIC2(64,"#FFFFFF");

    private final int index;
    private final String colorHexStr;

    ColorIndex(int index, String colorHexStr) {
        this.index = index;
        this.colorHexStr = colorHexStr;
    }

    public ColorIndex getColorIndex(int index){
        if(index < 0 || index >= 64){
            return ColorIndex.AUTOMATIC;
        }
        return ColorIndex.values()[index];
    }

}