/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2000-2006 Keith Godfrey and Maxym Mykhalchuk
               Home page: http://www.omegat.org/
               Support center: http://groups.yahoo.com/group/OmegaT/

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 **************************************************************************/

package org.omegat.gui.filters2;

import gen.core.filters.Filter;
import gen.core.filters.Filters;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.omegat.core.Core;
import org.omegat.filters2.IFilter;
import org.omegat.filters2.master.FilterMaster;
import org.omegat.filters2.master.FiltersTableModel;
import org.omegat.util.OStrings;

/**
 * Main dialog for for setting up filters. Filter is a class that allows for
 * reading and writing a single file format. OmegaT has different filters for
 * different supported file formats. E.g. HTML, OpenOffice etc.
 * 
 * @author Maxym Mykhalchuk
 */
@SuppressWarnings("serial")
public class FiltersCustomizer extends JDialog implements ListSelectionListener {
    private Filters config;
    public Filters result;
    /**
     * Flag if this customizer shows project specific filters or not
     */
    private boolean isProjectSpecific;

    /** Creates new form FilterCustomizer */
    public FiltersCustomizer(Frame parent, boolean projectSpecific) {
        super(parent, true);
        isProjectSpecific = projectSpecific;

        FilterMaster filterProjectInstance = null;
        if (projectSpecific) {
            filterProjectInstance = Core.getProject().getFilterMaster();
        }
        if (filterProjectInstance == null) {
            config = FilterMaster.getInstance().cloneConfig();
        } else {
            config = filterProjectInstance.cloneConfig();
        }
        

        // HP
        // Handle escape key to close the window
        KeyStroke escape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escape, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);
        // END HP

        initComponents();

        getRootPane().setDefaultButton(okButton);
        filtersTable.setModel(new FiltersTableModel(config));
        filtersTable.getSelectionModel().addListSelectionListener(this);

        if (projectSpecific) {
            setTitle(OStrings.getString("FILTERSCUSTOMIZER_TITLE_PROJECTSPECIFIC"));
        } else {
            projectSpecificCB.setVisible(false);
        }
        if (projectSpecific && filterProjectInstance == null) {
            filtersTable.setEnabled(false);
            filtersTable.setFocusable(false);
        } else {
            if (projectSpecific) projectSpecificCB.setSelected(true);
            filtersTable.setEnabled(true);
            filtersTable.setFocusable(true);
        }

        // hack for "autoresizing" the dialog
        // accomodating table dimensions
        Dimension tableSize = filtersTable.getPreferredSize();
        tableSize.height = tableSize.height + 70;
        filtersScrollPane.setPreferredSize(tableSize);
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dialogSize = getSize();
        setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting())
            return;
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();
        if (lsm.isSelectionEmpty()) {
            editButton.setEnabled(false);
            optionsButton.setEnabled(false);
        } else {
            editButton.setEnabled(true);
            int fIdx = filtersTable.getSelectedRow();
            Filter currFilter = config.getFilter().get(fIdx);
            IFilter f = FilterMaster.getInstance().getFilterInstance(currFilter.getClassName());
            optionsButton.setEnabled(f.hasOptions());
        }
    }

    /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
    public Filters getResult() {
        return result;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonPanel = new javax.swing.JPanel();
        toDefaultsButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        filtersScrollPane = new javax.swing.JScrollPane();
        filtersTable = new javax.swing.JTable();
        description = new javax.swing.JTextArea();
        editButton = new javax.swing.JButton();
        optionsButton = new javax.swing.JButton();
        projectSpecificCB = new javax.swing.JCheckBox();

        setTitle(OStrings.getString("FILTERSCUSTOMIZER_TITLE")); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        org.openide.awt.Mnemonics.setLocalizedText(toDefaultsButton, OStrings.getString("BUTTON_TO_DEFAULTS")); // NOI18N
        toDefaultsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toDefaultsButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(toDefaultsButton);

        jLabel1.setPreferredSize(new java.awt.Dimension(20, 0));
        buttonPanel.add(jLabel1);

        org.openide.awt.Mnemonics.setLocalizedText(okButton, OStrings.getString("BUTTON_OK")); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(okButton);

        org.openide.awt.Mnemonics.setLocalizedText(cancelButton, OStrings.getString("BUTTON_CANCEL")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(cancelButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        getContentPane().add(buttonPanel, gridBagConstraints);

        filtersScrollPane.setFocusable(false);
        filtersScrollPane.setViewportView(filtersTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        getContentPane().add(filtersScrollPane, gridBagConstraints);

        description.setBackground(javax.swing.UIManager.getDefaults().getColor("Label.background"));
        description.setEditable(false);
        description.setFont(new JLabel().getFont());
        description.setLineWrap(true);
        description.setText(OStrings.getString("FILTERSCUSTOMIZER_DESCRIPTION")); // NOI18N
        description.setWrapStyleWord(true);
        description.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        getContentPane().add(description, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(editButton, OStrings.getString("BUTTON_EDIT")); // NOI18N
        editButton.setToolTipText(OStrings.getString("FILTERSCUSTOMIZER_BUTTON_EDIT_HINT")); // NOI18N
        editButton.setEnabled(false);
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        getContentPane().add(editButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(optionsButton, OStrings.getString("FILTERSCUSTOMIZER_BUTTON_OPTIONS")); // NOI18N
        optionsButton.setEnabled(false);
        optionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        getContentPane().add(optionsButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(projectSpecificCB, OStrings.getString("FILTERSCUSTOMIZER_CHECKBOX_PROJECTSPECIFIC")); // NOI18N
        projectSpecificCB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        projectSpecificCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projectSpecificCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(projectSpecificCB, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void projectSpecificCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projectSpecificCBActionPerformed
        if (projectSpecificCB.isSelected()) {
            filtersTable.setEnabled(true);
            filtersTable.setFocusable(true);
        } else {
            filtersTable.setEnabled(false);
            filtersTable.setFocusable(false);
        }
    }//GEN-LAST:event_projectSpecificCBActionPerformed

    private void optionsButtonActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_optionsButtonActionPerformed
    {// GEN-HEADEREND:event_optionsButtonActionPerformed
        int fIdx = filtersTable.getSelectedRow();
        Filter currFilter = config.getFilter().get(fIdx);
        IFilter f = FilterMaster.getInstance().getFilterInstance(currFilter.getClassName());

        // new options handling
        Map<String, String> newConfig = f.changeOptions(this, FilterMaster.forFilter(currFilter.getOption()));
        if (newConfig != null) {
            FilterMaster.setOptions(currFilter, newConfig);
        }
    }// GEN-LAST:event_optionsButtonActionPerformed

    private void toDefaultsButtonActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_toDefaultsButtonActionPerformed
    {// GEN-HEADEREND:event_toDefaultsButtonActionPerformed
        config = FilterMaster.getInstance().createDefaultFiltersConfig();
        filtersTable.setModel(new FiltersTableModel(config));
    }// GEN-LAST:event_toDefaultsButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_editButtonActionPerformed
        int row = filtersTable.getSelectedRow();
        FilterEditor editor = new FilterEditor(this, config.getFilter().get(row));
        editor.setVisible(true);
        if (editor.result != null) {
            config.getFilter().set(row, editor.result);
        }
    }// GEN-LAST:event_editButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_okButtonActionPerformed
    {
        doClose(config);
    }// GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_cancelButtonActionPerformed
    {
        doClose(null);
    }// GEN-LAST:event_cancelButtonActionPerformed

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt)// GEN-FIRST:event_closeDialog
    {
        doClose(null);
    }// GEN-LAST:event_closeDialog

    private void doClose(Filters res) {
        if (isProjectSpecific && projectSpecificCB.isSelected()==false) {
            result = null;
        } else {
            result = res;
        }
        setVisible(false);
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextArea description;
    private javax.swing.JButton editButton;
    private javax.swing.JScrollPane filtersScrollPane;
    private javax.swing.JTable filtersTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton okButton;
    private javax.swing.JButton optionsButton;
    private javax.swing.JCheckBox projectSpecificCB;
    private javax.swing.JButton toDefaultsButton;
    // End of variables declaration//GEN-END:variables
}
