package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigurationScreen extends JFrame {
    public ConfigurationScreen() {
        JFrame frame = new JFrame("Configurations");
        frame.setSize(750,550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        //frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(50, 60, 50, 0));

        JPanel configurationPanel = new JPanel();
        configurationPanel.setLayout(new GridLayout(9, 3,5,5));

        JLabel fieldWidthLabel = new JLabel("Field Width (No of cells):");
        JSlider fieldWidthSlider = new JSlider(5, 15, 10);
        fieldWidthSlider.setMajorTickSpacing(1);
        fieldWidthSlider.setPaintTicks(true);
        fieldWidthSlider.setPaintLabels(true);

        JLabel fieldWidthValueLabel = new JLabel(String.valueOf(fieldWidthSlider.getValue()));
        fieldWidthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                fieldWidthValueLabel.setText(String.valueOf(fieldWidthSlider.getValue()));
            }
        });
        JLabel fieldHeightLabel = new JLabel("Field Height (No of cells):");
        JSlider fieldHeightSlider = new JSlider(15, 30, 20);
        fieldHeightSlider.setMajorTickSpacing(1);
        fieldHeightSlider.setPaintTicks(true);
        fieldHeightSlider.setPaintLabels(true);

        JLabel fieldHeightValueLabel = new JLabel(String.valueOf(fieldHeightSlider.getValue()));
        fieldHeightSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                fieldHeightValueLabel.setText(String.valueOf(fieldHeightSlider.getValue()));
            }
        });


        JLabel gameLevelLabel = new JLabel("Game Level:");
        JSlider gameLevelSlider = new JSlider(1, 10, 4);
        gameLevelSlider.setMajorTickSpacing(1);
        gameLevelSlider.setPaintTicks(true);
        gameLevelSlider.setPaintLabels(true);

        JLabel gameLevelValueLabel = new JLabel(String.valueOf(gameLevelSlider.getValue()));
        gameLevelSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                gameLevelValueLabel.setText(String.valueOf(gameLevelSlider.getValue()));
            }
        });

        JCheckBox musicCheckbox = new JCheckBox("Music (On|Off):");
        musicCheckbox.setSelected(true);
        JLabel musicStatusLabel = new JLabel("On");

        JCheckBox soundEffectCheckbox = new JCheckBox("Sound Effect (On|Off):");
        soundEffectCheckbox.setSelected(true);
        JLabel soundEffectStatusLabel = new JLabel("On");

        JCheckBox aiPlayCheckbox = new JCheckBox("AI Play (On|Off):");
        JLabel aiPlayStatusLabel = new JLabel("Off");

        JCheckBox extendModeCheckbox = new JCheckBox("Extend Mode (On|Off):");
        JLabel extendModeStatusLabel = new JLabel("Off");

        configurationPanel.add(fieldWidthLabel);
        configurationPanel.add(fieldWidthSlider);
        configurationPanel.add(fieldWidthValueLabel);

        configurationPanel.add(fieldHeightLabel);
        configurationPanel.add(fieldHeightSlider);
        configurationPanel.add(fieldHeightValueLabel);

        configurationPanel.add(gameLevelLabel);
        configurationPanel.add(gameLevelSlider);
        configurationPanel.add(gameLevelValueLabel);

        configurationPanel.add(musicCheckbox);
        configurationPanel.add(musicStatusLabel);
        musicCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                musicStatusLabel.setText((musicCheckbox.isSelected() ? "On" : "Off"));
            }
        });
        configurationPanel.add(new JLabel());

        configurationPanel.add(soundEffectCheckbox);
        configurationPanel.add(soundEffectStatusLabel);
        soundEffectCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                soundEffectStatusLabel.setText((soundEffectCheckbox.isSelected() ? "On" : "Off"));
            }
        });
        configurationPanel.add(new JLabel());

        configurationPanel.add(aiPlayCheckbox);
        configurationPanel.add(aiPlayStatusLabel);
        aiPlayCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aiPlayStatusLabel.setText((aiPlayCheckbox.isSelected() ? "On" : "Off"));
            }
        });
        configurationPanel.add(new JLabel());

        configurationPanel.add(extendModeCheckbox);
        configurationPanel.add(extendModeStatusLabel);
        extendModeCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                extendModeStatusLabel.setText((extendModeCheckbox.isSelected() ? "On" : "Off"));
            }
        });
        //configurationPanel.add(new JLabel());
        mainPanel.add(configurationPanel);

        JPanel buttonPanel = getButtonPanel(frame);

        JLabel titleLabel = new JLabel("Configurations", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 35));
        titleLabel.setOpaque(true);
        titleLabel.setForeground(Color.BLACK);
        //mainPanel.add(backButton, BorderLayout.NORTH);

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static JPanel getButtonPanel(JFrame frame) {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainMenuScreen().showMainScreen();
                frame.dispose();
            }
        });
        backButton.setBackground(Color.lightGray);
        backButton.setOpaque(true);
        Dimension buttonSize = new Dimension(150,40);
        backButton.setPreferredSize(buttonSize);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        return buttonPanel;
    }
}

