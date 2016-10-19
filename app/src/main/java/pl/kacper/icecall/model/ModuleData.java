package pl.kacper.icecall.model;

/**
 * Created by kacper on 02.01.16.
 * General interface necessary to implement by every module data.
 */
public interface ModuleData {
    boolean getEnabled();

    void setEnabled(boolean state);

    String getModuleName();
}
