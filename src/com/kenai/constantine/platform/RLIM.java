// WARNING: This file is autogenerated. DO NOT EDIT!
// Generated Fri Nov 28 12:28:03 +1000 2008
package com.kenai.constantine.platform;
public enum RLIM implements com.kenai.constantine.Constant {
RLIM_NLIMITS,
RLIM_INFINITY,
RLIM_SAVED_MAX,
RLIM_SAVED_CUR,
__UNKNOWN_CONSTANT__;
private static final ConstantResolver<RLIM> resolver 
= ConstantResolver.getResolver(RLIM.class, 20000, 29999);
public final int value() { return resolver.intValue(this); }
public final String description() { return resolver.description(this); }
public final String toString() { return description(); }
public final static RLIM valueOf(int value) { 
    return resolver.valueOf(value);
}
}
