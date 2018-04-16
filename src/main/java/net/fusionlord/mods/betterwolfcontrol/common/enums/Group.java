package net.fusionlord.mods.betterwolfcontrol.common.enums;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;

import java.util.Locale;

/**
 * Created by FusionLord on 4/15/2018.
 */

public enum Group implements IStringSerializable {
    ALL(EnumDyeColor.WHITE, TextFormatting.UNDERLINE),
    WHITE(EnumDyeColor.WHITE, TextFormatting.WHITE),
    ORANGE(EnumDyeColor.ORANGE, TextFormatting.GOLD),
    MAGENTA(EnumDyeColor.MAGENTA, TextFormatting.LIGHT_PURPLE),
    LIGHT_BLUE(EnumDyeColor.LIGHT_BLUE, TextFormatting.BLUE),
    YELLOW(EnumDyeColor.YELLOW, TextFormatting.YELLOW),
    LIME(EnumDyeColor.LIME, TextFormatting.GREEN),
    PINK(EnumDyeColor.PINK, TextFormatting.LIGHT_PURPLE),
    GRAY(EnumDyeColor.GRAY, TextFormatting.DARK_GRAY),
    SILVER(EnumDyeColor.SILVER, TextFormatting.GRAY),
    CYAN(EnumDyeColor.CYAN, TextFormatting.DARK_AQUA),
    PURPLE(EnumDyeColor.PURPLE, TextFormatting.DARK_PURPLE),
    BLUE(EnumDyeColor.BLUE, TextFormatting.DARK_BLUE),
    BROWN(EnumDyeColor.BROWN, TextFormatting.GOLD),
    GREEN(EnumDyeColor.GREEN, TextFormatting.DARK_GREEN),
    RED(EnumDyeColor.RED, TextFormatting.DARK_RED),
    BLACK(EnumDyeColor.BLACK, TextFormatting.BLACK);

    public static final Group[] VALUES = values();

    public EnumDyeColor DYE;
    public TextFormatting TEXT;

    Group(EnumDyeColor dye, TextFormatting text) {
        DYE = dye;
        TEXT = text;
    }


    @Override
    public String getName() {
        return name().toLowerCase(Locale.ENGLISH);
    }
}
