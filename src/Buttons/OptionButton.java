package Buttons;

import javax.swing.*;
import java.awt.*;

public class OptionButton extends JButton {

    public OptionButton(String title) {
        super(title);

    }

    @Override
    public void paintComponent(Graphics graphics) {
        final Graphics2D gr = (Graphics2D) graphics.create();

        gr.setPaint(new GradientPaint(new Point(0, 0), Color.BLACK, new Point(0, getHeight()), Color.BLACK));
        gr.fillRect(0, 0, getWidth(), getHeight());
        gr.setPaint(Color.WHITE);
        gr.drawString(getText(),  (getHeight() / 2) + 3, (getHeight() / 2) + 3);
        gr.dispose();

        super.paintComponent(gr);
    }

}
