package mods.eln.item.regulator;

import mods.eln.sim.RegulatorProcess;

import static mods.eln.item.regulator.IRegulatorDescriptor.RegulatorType.OnOff;

public class RegulatorOnOffDescriptor extends IRegulatorDescriptor {

    private double hysteresis;

    public RegulatorOnOffDescriptor(String name, double hysteresis) {
        super(name);
        this.hysteresis = hysteresis;
    }

    @Override
    public RegulatorType getType() {
        return OnOff;
    }

    @Override
    public void applyTo(RegulatorProcess regulator, double workingPoint, double P, double I, double D) {
        regulator.setOnOff(hysteresis, workingPoint);
    }
}
