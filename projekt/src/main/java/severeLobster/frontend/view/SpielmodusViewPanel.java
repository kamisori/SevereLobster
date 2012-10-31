package severeLobster.frontend.view;

import infrastructure.constants.enums.SpielmodusEnumeration;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import severeLobster.frontend.controller.ISpielmodusView;
import severeLobster.frontend.controller.SpielmodusViewController;

public class SpielmodusViewPanel extends JPanel implements ISpielmodusView {

    private final JComboBox spielmodusChoiceComboBox;
    private final InnerComboBoxModelForSpielmodusChoice innerComboBoxModel;

    private SpielmodusViewController controller;

    public SpielmodusViewPanel() {
        this.innerComboBoxModel = new InnerComboBoxModelForSpielmodusChoice();
        this.spielmodusChoiceComboBox = new JComboBox(innerComboBoxModel);
        spielmodusChoiceComboBox
                .addItemListener(new InnerSpielmodusChoiceBoxItemListener());

        // Add renderer later
        add(spielmodusChoiceComboBox);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }

    @Override
    public void setSpielmodusController(
            SpielmodusViewController spielmodusController) {
        this.controller = spielmodusController;
        final SpielmodusEnumeration currentSpielmodus = controller
                .getSelectionSpielmodus();
        setDisplayedSpielmodus(currentSpielmodus);
    }

    @Override
    public void setDisplayedSpielmodus(SpielmodusEnumeration newSpielmodus) {
        innerComboBoxModel.setSelectedItem(newSpielmodus);
    }

    private class InnerSpielmodusChoiceBoxItemListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent arg0) {
            /** Only react on new selections. - Never react on deselections. */
            if (ItemEvent.SELECTED == arg0.getStateChange()) {
                final JComboBox sourceComboBox = (JComboBox) arg0.getSource();
                final SpielmodusEnumeration selectedSpielmodus = (SpielmodusEnumeration) sourceComboBox
                        .getSelectedItem();
                controller.setSpielmodus(selectedSpielmodus);
            }
        }
    }

    private class InnerComboBoxModelForSpielmodusChoice extends
            AbstractListModel implements ComboBoxModel {
        private SpielmodusEnumeration selectedSpielmodus;

        @Override
        public Object getElementAt(int arg0) {

            if (arg0 == 1) {
                return SpielmodusEnumeration.EDITIEREN;
            } else {
                return SpielmodusEnumeration.SPIELEN;
            }
        }

        @Override
        public int getSize() {
            return 2;
        }

        @Override
        public Object getSelectedItem() {
            return selectedSpielmodus;
        }

        @Override
        public void setSelectedItem(Object arg0) {
            /** Only change, if item is not selected already: */
            if (null != arg0) {
                if ((SpielmodusEnumeration) arg0 != selectedSpielmodus) {
                    selectedSpielmodus = (SpielmodusEnumeration) arg0;
                    fireContentsChanged(this, 0, getSize());
                }
            } else {
                selectedSpielmodus = SpielmodusEnumeration.SPIELEN;
                fireContentsChanged(this, 0, getSize());
            }
        }

    }

}
