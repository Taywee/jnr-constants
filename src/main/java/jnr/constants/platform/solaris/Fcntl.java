// WARNING: This file is autogenerated. DO NOT EDIT!
// Generated 2016-11-03 14:14:22 -0500
package jnr.constants.platform.solaris;
public enum Fcntl implements jnr.constants.Constant {
F_DUPFD(0L),
F_GETFD(1L),
F_SETFD(2L),
F_GETFL(3L),
F_SETFL(4L),
F_GETOWN(23L),
F_SETOWN(24L),
F_GETLK(33L),
F_SETLK(34L),
F_SETLKW(35L),
// F_CHKCLEAN not defined
// F_PREALLOCATE not defined
// F_SETSIZE not defined
// F_RDADVISE not defined
// F_RDAHEAD not defined
// F_READBOOTSTRAP not defined
// F_WRITEBOOTSTRAP not defined
// F_NOCACHE not defined
// F_LOG2PHYS not defined
// F_GETPATH not defined
// F_FULLFSYNC not defined
// F_PATHPKG_CHECK not defined
// F_FREEZE_FS not defined
// F_THAW_FS not defined
// F_GLOBAL_NOCACHE not defined
// F_ADDSIGS not defined
// F_MARKDEPENDENCY not defined
F_RDLCK(1L),
F_UNLCK(3L),
F_WRLCK(2L);
// F_ALLOCATECONTIG not defined
// F_ALLOCATEALL not defined
private final long value;
private Fcntl(long value) { this.value = value; }
public static final long MIN_VALUE = 0L;
public static final long MAX_VALUE = 35L;

public final int intValue() { return (int) value; }
public final long longValue() { return value; }
public final boolean defined() { return true; }
}
