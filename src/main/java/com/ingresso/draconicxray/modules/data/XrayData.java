package com.ingresso.draconicxray.modules.data;

import com.brandon3055.draconicevolution.api.modules.data.ModuleData;

public record XrayData(short ticks) implements ModuleData<XrayData> {
    @Override
    public XrayData combine(XrayData other) {
        return other;
    }
}
