package paint;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.canvas.paint.Painter;
import utils.method_provider.CustomMethodProvider;

import java.awt.*;
import java.util.Arrays;

public class NameBlockerPaint implements Painter {

    private final CustomMethodProvider mp;

    public NameBlockerPaint (final CustomMethodProvider mp) {
        this.mp = mp;
    }

    @Override
    public void onPaint(Graphics2D g) {
        RS2Widget widget = mp.getWidgets().getWidgetContainingText(162, mp.myPlayer().getName());
        if (widget != null) {
            g.setColor(Color.decode("#B8A17A"));
            g.setFont(new Font(g.getFont().toString(), 0, 12));
            g.fillRect(widget.getAbsX() + (widget.getMessage().contains("<img=") ? 12 : 0), (widget.getAbsY() + 2), (g.getFontMetrics().stringWidth(mp.myPlayer().getName())), (widget.getHeight() - 2));
        }

        RS2Widget widget2 = mp.getWidgets().getWidgetContainingText(217, mp.myPlayer().getName());
        if (widget2 != null) {
            g.setColor(Color.decode("#B8A17A"));
            g.fillRect(widget2.getAbsX(), widget2.getAbsY(), widget2.getWidth(), widget2.getHeight());
        }
    }
}
