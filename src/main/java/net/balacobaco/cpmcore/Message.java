package net.balacobaco.cpmcore;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class Message {
    public static Component process(String msg) {
        Component text = MiniMessage.miniMessage().deserialize(msg).decoration(TextDecoration.ITALIC, false);

        return text;
    }
}
