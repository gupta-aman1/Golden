package com.example.goldenfish.AepsSdk.device;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "PidOptions", strict = false)
public class PidOptions {
    @Element(name = "CustOpts", required = false)
    public CustOpts CustOpts;
    @Element(name = "Opts", required = false)
    public Opts Opts;
    @Attribute(name = "ver", required = false)
    public String ver;

    private class CustOpts {
        private CustOpts() {
        }
    }
}
