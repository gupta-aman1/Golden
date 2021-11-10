package com.example.goldenfish.AepsSdk.device;

import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "additional_info")
public class additional_info {
    @ElementList(inline = true, name = "Param", required = false)
    public List<Param> params;
}
