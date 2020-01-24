package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultCaret;

public class UISwingImpl implements UI {

    private JFrame window;
    private JTextArea text;
    private JTextField inString;
    private JButton go;
    private JPanel sPanel;
    private BlockingQueue<String> mq;
    private DefaultCaret caret;
    List<String> l = new ArrayList<>();

    int pos = 0;

    public UISwingImpl(String title) {

        window = new JFrame(title);
        window.setLayout(new BorderLayout());
        text = new JTextArea();
        text.setLineWrap(true);
        text.setEditable(false);
        text.setBackground(Color.LIGHT_GRAY/*new Color(255, 220, 220)*/);
        text.setForeground(Color.BLUE);
        text.setFont(new Font("Sansserif", Font.BOLD, 21));
        caret = (DefaultCaret) text.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        window.add(new JScrollPane(text), BorderLayout.CENTER);
        sPanel = new JPanel();
        sPanel.setLayout(new BorderLayout());
        window.add(sPanel, BorderLayout.SOUTH);
        inString = new JTextField();
        inString.setFont(new Font("Sansserif", Font.BOLD | Font.ITALIC, 18));
        go = new JButton("Send");
        go.setForeground(Color.WHITE);
        go.setBackground(Color.BLUE);
        mq = new ArrayBlockingQueue<>(100);

        ActionListener goAction = new GoListener();
        ActionListener upAction = e -> {
            if (pos <= l.size() - 1) {
                inString.setText(l.get(l.size() - pos - 1));
                inString.requestFocusInWindow();
                pos++;
            }
        };
        ActionListener downAction = e -> {
            if (pos == 1) {
                inString.setText("");
                inString.requestFocusInWindow();
                pos--;
            } else if (pos != 0 && pos > 1 && !l.isEmpty()) {
                pos--;
                inString.setText(l.get(l.size() - pos));
                inString.requestFocusInWindow();
            }
        };
        ActionListener dateCreate = e -> {
            pos = 0;
            inString.setText(LocalDate.now().toString());
        };
        go.addActionListener(goAction);
        go.addActionListener(upAction);
        go.addActionListener(downAction);
        go.addActionListener(dateCreate);
        inString.registerKeyboardAction(goAction, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inString.registerKeyboardAction(upAction, KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inString.registerKeyboardAction(downAction, KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inString.registerKeyboardAction(dateCreate, KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        sPanel.add(inString, BorderLayout.CENTER);
        sPanel.add(go, BorderLayout.EAST);
        window.setSize(900, 800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationByPlatform(false);
        window.setLocation(375, 25);
        window.setVisible(true);
        inString.requestFocusInWindow();
        
        text.addMouseWheelListener(mouseWheelEvent
                -> {
            if (mouseWheelEvent.isControlDown()) {
                int scrolled = mouseWheelEvent.getUnitsToScroll();
                Font font = text.getFont();
                int fontSize = font.getSize();
                fontSize += -(scrolled / 3);
                Font newFont = new Font(font.getFontName(), font.getStyle(), fontSize);
                text.setFont(newFont);
            } else {
                text.getParent().dispatchEvent(mouseWheelEvent);
            }
        });

    }

    @Override
    public int takeChoiceInput(int max) {
        int choice = -1;
        while (choice < 0 || choice > max) {
            try {
                choice = Integer.parseInt(inputString());
                if (choice < 0 || choice > max) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                text.append("Not a valid number, try again!\n");
            }
        }
        return choice;
    }

    @Override
    public void printResult(Object o) {
        text.append(o + "\n");
    }

    @Override
    public String inputDate() {

        boolean loop = true;
        String date = null;
        while (loop) {
            date = inputString();
            LocalDate now = LocalDate.now();
            try {
                LocalDate tryDate = LocalDate.parse(date, FORMATTER);
                
                if (!(tryDate.isEqual(now) || tryDate.isAfter(now))) {
                    throw new Exception();
                }
                loop=false;
            } catch (Exception e) {
                outputString("Not a valid date, try again");
            }

        }
        return date;

//        boolean loop = true;
//        String date = null;
//        String regex = "^((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0[13578])|(1[02]))[\\-]((0[1-9])|([1-2][0-9])|(3[01])))|(((0[469])|(11))[\\-]((0[1-9])|([1-2][0-9])|(30)))|(02[\\-]((0[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0[13578])|(1[02]))[\\-]((0[1-9])|([1-2][0-9])|(3[01])))|(((0[469])|(11))[\\-]((0[1-9])|([1-2][0-9])|(30)))|(02[\\-]((0[1-9])|(1[0-9])|(2[0-8]))))))$";
//        while (loop) {
//
//            date = inputString();
//            if (date.matches(regex)) {
//                loop = false;
//            } else {
//                outputString("Not a valid date, try again");
//            }
//        }
//        return date;
    }

    @Override
    public String inputString() {

        try {
            return mq.take().trim();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Should not happen";
        }
    }

    @Override
    public void outputString(String s) {
        text.append(s + "\n");
    }

    @Override
    public void pressEnterToContinue() {
        outputString("");
        outputString("Press Enter to continue...");
        inputString();
    }

    @Override
    public void clear() {
        text.setText("");
    }

    @Override
    public void exit() {
        window.dispose();
        System.exit(0);
    }

    class GoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String input = inString.getText().trim();
                mq.put(input);
                text.append(input + "\n");
                pos = 0;
                if (!(input.isEmpty() || input.matches("^[0-9]\\d*$"))) {

                    if (l.size() == 30) {
                        l.remove(0);
                    }
                    l.add(input);
                }

                Thread.sleep(1);
                inString.setText("");
                inString.requestFocusInWindow();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

}
