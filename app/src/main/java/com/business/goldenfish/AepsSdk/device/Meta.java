package com.business.goldenfish.AepsSdk.device;

import org.simpleframework.xml.Attribute;

public class Meta {
    @Attribute(name = "dc", required = false)
    public String dc;
    @Attribute(name = "dpId", required = false)
    public String dpId;
    @Attribute(name = "mc", required = false)
    public String mc;
    @Attribute(name = "mi", required = false)
    public String mi;
    @Attribute(name = "rdsId", required = false)
    public String rdsId;
    @Attribute(name = "rdsVer", required = false)
    public String rdsVer;
    @Attribute(name = "udc", required = false)
    public String udc;
}
