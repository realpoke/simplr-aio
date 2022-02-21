package utils.widget.filters;

import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.ui.RS2Widget;

import java.util.Arrays;
import java.util.stream.Stream;

public class WidgetSpriteFilter implements Filter<RS2Widget> {

    private final int spriteID;

    public WidgetSpriteFilter(final int spriteID) {
        this.spriteID = spriteID;
    }

    @Override
    public boolean match(final RS2Widget widget) {
        return Stream.of(spriteID)
                .anyMatch(message -> Arrays.stream(widget.getSprites()).anyMatch(i -> i == spriteID));
    }
}