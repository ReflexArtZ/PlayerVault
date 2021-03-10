package de.emeraldmc.playervault.guis;

import de.emeraldmc.playervault.PlayerVault;
import de.emeraldmc.playervault.objects.VaultEntry;
import de.emeraldmc.playervault.objects.VaultStorage;

import java.util.ArrayList;

public class VaultGui extends AbstractGUI {
    private final static int SIZE = 9*6;
    private final static String TITLE = PlayerVault.configuration.getGuiTitle();

    private VaultStorage storage;

    public VaultGui(VaultStorage storage) {
        super(TITLE.replaceAll("%id%", String.valueOf(storage.getVaultID())), SIZE);
        this.storage = storage;
        setItems();
    }

    private void setItems() {
        if (storage == null) return;
        for (VaultEntry entry : storage.getVaultEntries()) {
            if (entry != null) {
                setItem(entry.getSlot(), entry.getStack(), null);
            }
        }
    }

    public void updateVaultEntries(ArrayList<VaultEntry> entries) {
        storage.setVaultEntries(entries);
    }

    public VaultStorage getStorage() {
        return storage;
    }
}
