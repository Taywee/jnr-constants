// WARNING: This file is autogenerated. DO NOT EDIT!
// Generated 2010-07-31 18:57:14 +1000
package jnr.constants.platform;
public enum Sock implements jnr.constants.Constant {
SOCK_STREAM,
SOCK_DGRAM,
SOCK_RAW,
SOCK_RDM,
SOCK_SEQPACKET,
SOCK_MAXADDRLEN,
__UNKNOWN_CONSTANT__;
private static final ConstantResolver<Sock> resolver = 
ConstantResolver.getResolver(Sock.class, 20000, 29999);
public final int intValue() { return (int) resolver.longValue(this); }
public final long longValue() { return resolver.longValue(this); }
public final String description() { return resolver.description(this); }
public final boolean defined() { return resolver.defined(this); }
public final String toString() { return description(); }
public static Sock valueOf(long value) { 
    return resolver.valueOf(value);
}
}
