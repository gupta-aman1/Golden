package com.example.goldenfish.AepsSdk.device;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Namespace(reference = "http://www.uidai.gov.in/authentication/uid-auth-request/2.0")
@Root(name = "Auth")
public class AuthReq {
    @Element(name = "Hmac", required = false)
    public String Hmac;
    @Attribute(name = "ac", required = true)
    public String ac;
    @Element(name = "Data", required = false)
    public Data data;
    @Attribute(name = "lk", required = true)
    public String lk;
    @Element(name = "Meta", required = false)
    public Meta meta;
    @Attribute(name = "rc", required = true)
    public String rc;
    @Attribute(name = "sa", required = true)
    public String sa;
    @Element(name = "Skey", required = false)
    public Skey skey;
    @Attribute(name = "tid", required = true)
    public String tid;
    @Attribute(name = "txn", required = true)
    public String txn;
    @Attribute(name = "uid", required = true)
    public String uid;
    @Element(name = "Uses", required = false)
    public Uses uses;
    @Attribute(name = "ver", required = true)
    public String ver;
}
